
	
package miss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ec.util.MersenneTwisterFast;
import sim.util.Double2D;
import sim.util.MutableDouble2D;

public class Road {

    private List<Car> carsOnRoad;
    private int yardHeight;
    private int yardWidth;
    private int numCars;
    private MutableDouble2D position;
    private MersenneTwisterFast random;

    public Road(){
        carsOnRoad = new ArrayList<>();
        random = new MersenneTwisterFast();
        yardHeight = 100;
        yardWidth = 100;
        numCars = 10;
        createCars();
    }
	
	public void createCars(){
		for(int i = 0; i < numCars; i++){
			position =  new MutableDouble2D(yardWidth * random.nextDouble(), yardHeight * 0.5);
			Car car = new Car(position);
			carsOnRoad.add(car);
			//System.out.println(car.startPosition);
		}
		
		Collections.sort(carsOnRoad, new Comparator<Car>(){
			public int compare(Car c1, Car c2){
				Double c1x = new Double(c1.getCurrentPosition().x);
				Double c2x = new Double(c2.getCurrentPosition().x);
				return c2x.compareTo(c1x);
			}
		});
		
		
		for(Car car: carsOnRoad){
			System.out.println(car.getCurrentPosition().x);
		}
	}

	public void setCarSpeedValues(Car car){
		
		int index = carsOnRoad.indexOf(car);
		double diff;
		if(index != 0){
	//		System.out.println(carsOnRoad.get(index-1).currentPosition);
			diff = carsOnRoad.get(index - 1).getCurrentPosition().x - car.getCurrentPosition().x;

		//	System.out.println(diff + " between " + index + " and " + (index-1));
			if(diff < 3){
				car.setNeedToSlowDown(true);
			}
			else if( diff > 20){
				car.setNeedToSpeedUp(true);
			}
			else {
				car.setNeedToSlowDown(false);
				car.setNeedToSpeedUp(false);
			}
		}		
		
	}
	//zeby dwa auta nie byly w tym samym punkcie sprawdzac

    public List<Car> getCarsOnRoad() {
        return carsOnRoad;
    }

    public void setCarsOnRoad(List<Car> carsOnRoad) {
        this.carsOnRoad = carsOnRoad;
    }

    public int getYardHeight() {
        return yardHeight;
    }

    public void setYardHeight(int yardHeight) {
        this.yardHeight = yardHeight;
    }

    public int getYardWidth() {
        return yardWidth;
    }

    public void setYardWidth(int yardWidth) {
        this.yardWidth = yardWidth;
    }

    public int getNumCars() {
        return numCars;
    }

    public void setNumCars(int numCars) {
        this.numCars = numCars;
    }

    public MutableDouble2D getPosition() {
        return position;
    }

    public void setPosition(MutableDouble2D position) {
        this.position = position;
    }

    public MersenneTwisterFast getRandom() {
        return random;
    }

    public void setRandom(MersenneTwisterFast random) {
        this.random = random;
    }
}
	

	

	
	