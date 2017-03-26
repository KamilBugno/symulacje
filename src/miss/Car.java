package miss;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Double2D;
import sim.util.MutableDouble2D;

public class Car implements Steppable{

	private MutableDouble2D startPosition;
	private MutableDouble2D currentPosition;
	private Cars cars;
	private boolean needToSlowDown;
	private boolean needToSpeedUp;
	private boolean isInitialStep = true;
	private double speed;
	
	public Car(MutableDouble2D startPosition)
	{
		this.startPosition = startPosition;
		this.currentPosition = startPosition;
	}
	

	@Override
	public void step(SimState state) {
	//	System.out.println(needToSlowDown + " " + needToSpeedUp);

		cars = (Cars) state;
		cars.getRoad().setCarSpeedValues(this);
		if(isInitialStep){
			cars.getYard().setObjectLocation(this, new Double2D(startPosition));
			isInitialStep = false;
		}

		Double2D me = cars.getYard().getObjectLocation(this);

		MutableDouble2D sumForces = new MutableDouble2D();
		if(!needToSlowDown && !needToSpeedUp){
			sumForces.x +=0.01;
		}
		if(needToSlowDown){
			sumForces.x +=0.0005;
		//	System.out.println("slowed down");
		}
		if(needToSpeedUp){
			sumForces.x +=0.015;
	//		System.out.println("speeded up");
		}
	//	System.out.println(sumForces);
		sumForces.addIn(me);
		cars.getYard().setObjectLocation(this, new Double2D(sumForces));
		currentPosition = sumForces;
		
	}

	public MutableDouble2D getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(MutableDouble2D startPosition) {
		this.startPosition = startPosition;
	}

	public MutableDouble2D getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(MutableDouble2D currentPosition) {
		this.currentPosition = currentPosition;
	}

	public Cars getCars() {
		return cars;
	}

	public void setCars(Cars cars) {
		this.cars = cars;
	}

	public boolean isNeedToSlowDown() {
		return needToSlowDown;
	}

	public void setNeedToSlowDown(boolean needToSlowDown) {
		this.needToSlowDown = needToSlowDown;
	}

	public boolean isNeedToSpeedUp() {
		return needToSpeedUp;
	}

	public void setNeedToSpeedUp(boolean needToSpeedUp) {
		this.needToSpeedUp = needToSpeedUp;
	}


	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
}
