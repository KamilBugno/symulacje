package miss;

import ec.util.MersenneTwisterFast;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Double2D;
import sim.util.MutableDouble2D;

public class Car implements Steppable{

	private MutableDouble2D startPosition;
	private MutableDouble2D currentPosition;
	private Cars cars;
	private boolean needToSlowDown;
	private boolean needToStop;
	private boolean needToSpeedUp;
	private boolean needToChangeRoad;
	private boolean isInitialStep = true;
	private double speed;
	private MersenneTwisterFast random;
	private double defaultSpeed;
	private boolean left;
	private boolean vertical;
	
	public Car(MutableDouble2D startPosition)
	{
		this.startPosition = startPosition;
		currentPosition = startPosition;
		random = new MersenneTwisterFast();
		if(random.nextDouble()> 0.5){
			defaultSpeed = 0.01;
		}else{
			defaultSpeed = 0.02;
		}
	}
	

	@Override
	public void step(SimState state) {

		cars = (Cars) state;
		cars.getRoad(this).setCarSpeedValues(this);

		if(isInitialStep){
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

}
