package miss;

import java.awt.List;
import java.util.ArrayList;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Double2D;
import sim.util.MutableDouble2D;

public class Car implements Steppable{
	
	public MutableDouble2D startPosition;
	public MutableDouble2D currentPosition;
	Cars cars;
	public boolean needToSlowDown = false;
	public boolean needToSpeedUp = false;
	int counter = 0;
	double speed;
	
	public Car(MutableDouble2D startPosition)
	{
		this.startPosition = startPosition;
		this.currentPosition = startPosition;
	}
	

	@Override
	public void step(SimState state) {
	//	System.out.println(needToSlowDown + " " + needToSpeedUp);

		cars = (Cars) state;
		cars.road.setCarSpeedValues(this);
		if(counter == 0){
		//	cars.yard.setObjectLocation(this, new Double(startPosition));
			cars.yard.setObjectLocation(this, new Double2D(startPosition));
			counter = 1;
		}

		Double2D me = cars.yard.getObjectLocation(this);

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
		cars.yard.setObjectLocation(this, new Double2D(sumForces));	
	//	Double2D me1 = cars.yard.getObjectLocation(this);
	//	System.out.println(me1);
		currentPosition = sumForces;
		
	}
	
	
	
}
