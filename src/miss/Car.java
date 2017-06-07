package miss;

import ec.util.MersenneTwisterFast;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.ui.RefineryUtilities;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Double2D;
import sim.util.MutableDouble2D;

import java.util.List;
import java.util.Random;

public class Car implements Steppable{

	private MutableDouble2D startPosition;
	private MutableDouble2D currentPosition;
	private Cars cars;
	private boolean needToSlowDown;
	private boolean needToStop;
	private boolean needToSpeedUp;
	private boolean needToChangeRoad;
	private boolean isInitialStep = true;
	private MersenneTwisterFast random;
	private double defaultSpeed;
	private boolean left;
	private boolean vertical;
	private CarStatistics carStatistics;
	private long startTime;
	private boolean areStatisticsShowed = false;
	private int numberOfCoveredCrossings;
	private Road nextRoad;
	private Targets target = Targets.getInstance();
	private List<Road> pathToTarget;
	private Road thisCarsRoad;
	
	public Car(MutableDouble2D startPosition)
	{
		numberOfCoveredCrossings = 0;
		carStatistics = new CarStatistics();
		this.startPosition = startPosition;
		currentPosition = startPosition;
		random = new MersenneTwisterFast();
		if(random.nextDouble()> 0.5){
			defaultSpeed = 0.01;
		}else {
			defaultSpeed = 0.02;
		}
		startTime = System.nanoTime();
	}
	

	@Override
	public void step(SimState state) {

		generateStatistics();
		cars = (Cars) state;
		cars.getRoad(this).setCarSpeedValues(this);
		int id = cars.getRoad(this).getId();

		if(isInitialStep){
			pathToTarget = target.getTarget(id);
			thisCarsRoad = cars.getRoad(this);
			cars.getYard().setObjectLocation(this, new Double2D(startPosition));
			isInitialStep = false;
		}
		
		Double2D me = cars.getYard().getObjectLocation(this);
		MutableDouble2D sumForces = new MutableDouble2D();

		if(!vertical){
			if(!needToSlowDown && !needToSpeedUp && !needToStop){
				if(left){
					sumForces.x -= defaultSpeed;
				}
				else{
					sumForces.x += defaultSpeed;
				}
				
			}
			if(needToSlowDown && !needToStop){
				if(left){
					sumForces.x -= defaultSpeed/3;
				}
				else{
					sumForces.x += defaultSpeed/3;
				}
				
			}
			if(needToSpeedUp && !needToStop){
				if(left){
					sumForces.x -= 4*defaultSpeed/3;
				}
				else{
					sumForces.x += 4*defaultSpeed/3;
				}
				
			}
		}
		if(vertical){
			if(!needToSlowDown && !needToSpeedUp && !needToStop){
				if(left){
					sumForces.y -= defaultSpeed;
				}
				else{
					sumForces.y += defaultSpeed;
				}
				
			}
			if(needToSlowDown && !needToStop){
				if(left){
					sumForces.y -= defaultSpeed/3;
				}
				else{
					sumForces.y += defaultSpeed/3;
				}
				
			}
			if(needToSpeedUp && !needToStop){
				if(left){
					sumForces.y -= 4*defaultSpeed/3;
				}
				else{
					sumForces.y += 4*defaultSpeed/3;
				}
				
			}
		}
		if(!needToStop){
			sumForces.addIn(currentPosition);
			cars.getYard().setObjectLocation(this, new Double2D(sumForces));
			currentPosition = sumForces;
	
		}
	}

	private void generateStatistics() {
		if(!needToStop || isCrossing()) {
			if (needToSpeedUp) {
				carStatistics.addTimeOfSpeedingUp(System.nanoTime() - startTime);
			}
			if (needToSlowDown) {
				carStatistics.addTimeOfSlowingDown(System.nanoTime() - startTime);
			}
			if (needToStop) {
				carStatistics.addTimeOfStopping(System.nanoTime() - startTime);
			}
			if (!needToSlowDown && !needToSpeedUp && !needToStop) {
				carStatistics.addTimeOfDrivingWithoutSpeedChange(System.nanoTime() - startTime);
			}
			startTime = System.nanoTime();
		}else if(!areStatisticsShowed){
			//System.out.println(carStatistics);

			if(areCarsEndDriving()){
				CarStatistics carFullStatistics = cars.getCarFullStatistics();
				carFullStatistics.addTimeOfDrivingWithoutSpeedChange(carStatistics.timeOfDrivingWithoutSpeedChange);
				carFullStatistics.addTimeOfSlowingDown(carStatistics.timeOfSlowingDown);
				carFullStatistics.addTimeOfSpeedingUp(carStatistics.timeOfSpeedingUp);
				carFullStatistics.addTimeOfStopping(carStatistics.timeOfStopping);
				carFullStatistics.getList().add(carStatistics.numberOfCoveredCrossings);
			}

			//po jakims czasie dla jednego losowego auta sie wykonuje rysowanie
			if(!cars.isAreStatisticsShown() && System.nanoTime() - cars.getTimeStart() > 15000000000L) {
				cars.setAreStatisticsShown(true);
				PieChart demo = new PieChart("Statystyki aut", "Średni podzial czasu za wzgledu na czynnosci", cars.getCarFullStatistics());
				demo.pack();
				demo.setVisible(true);

				String name = "Histogram liczby pokonanych skrzyżowań przez auta";

				CrossingsStatisticsHistogram crossingsStatisticsHistogram =
						new CrossingsStatisticsHistogram(name,name, cars.getCarFullStatistics().getList());
				crossingsStatisticsHistogram.pack( );
				RefineryUtilities.centerFrameOnScreen( crossingsStatisticsHistogram );
				crossingsStatisticsHistogram.setVisible( true );




			}
			areStatisticsShowed = true;
		}
	}

	public boolean areCarsEndDriving(){ //nie dziala, bo auto tworza sie w magiczny sposob podczas dzialania programu
		int i = 0;
		for(Crossing crossing : cars.getCity().getCrossings()){
			for(Road road : crossing.getIn()){
				for (Car car : road.getCarsOnRoad()){
					i++;
					if(!car.areStatisticsShowed){
						//return false;
					}
				}
			}
			for(Road road : crossing.getOut()){
				for (Car car : road.getCarsOnRoad()){
					i++;
					if(!car.areStatisticsShowed){
						//return false;
					}
				}
			}
		}
		//System.out.println(i);
		return true;
	}


	public boolean isCrossing() {
		Road road = getCars().getRoad(this); //pobieram droge na ktorej znajduje sie auto
		boolean isThereCrossing = false;
		for (Crossing crossing : road.getCityCrossings()) {

			if (crossing.getIn().contains(road)) { //skrzyzowanie na ktorym jest dane auto jako na drodze IN
				isThereCrossing = true;
			}
		}
		return isThereCrossing;
	}

	public void setNeedToSlowDown(boolean needToSlowDown) {
		this.needToSlowDown = needToSlowDown;
	}

	public void setNeedToSpeedUp(boolean needToSpeedUp) {
		this.needToSpeedUp = needToSpeedUp;
	}

	public void setNeedToStop(boolean needToStop) {
		this.needToStop = needToStop;
	}
	
	public void setNeedToChangeRoad(boolean needToChangeRoad){
		this.needToChangeRoad = needToChangeRoad;
	}

	public Cars getCars() {
		return cars;
	}
	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isVertical() {
		return vertical;
	}
	public void setVertical(boolean vertical) {
		this.vertical = vertical;
	}

	public MutableDouble2D getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(MutableDouble2D startPosition) {
		this.startPosition = startPosition;
	}

	public void setCurrentPosition(MutableDouble2D currentPosition) {
		this.currentPosition = currentPosition;
	}

	public MutableDouble2D getCurrentPosition() {
		return currentPosition;
	}

	public CarStatistics getCarStatistics() {
		return carStatistics;
	}

	public void setCarStatistics(CarStatistics carStatistics) {
		this.carStatistics = carStatistics;
	}

	public void addNumberOfCoveredCrossings() {
		numberOfCoveredCrossings++;
	}
	public boolean needToStop(){
		return needToStop;
	}

//	public Road getNextRoad() {
//		return nextRoad;
//	}

	public void setNextRoad(Road nextRoad) {
		this.nextRoad = nextRoad;
	}
	public List<Road> getPath(){
		return pathToTarget;
	}
	public void removeFirstOnPath(){
		pathToTarget.remove(0);
	}
	public Road getNextRoad(){
		System.out.println("path to target size: --------------------------------" + pathToTarget.size());
		if(pathToTarget.size() != 0){
			System.out.println("1");
			System.out.println("id: " + pathToTarget.get(0).getId());
			return pathToTarget.get(0);
		}
		else{
			System.out.println("2");
			return thisCarsRoad;
		}
	}

}
