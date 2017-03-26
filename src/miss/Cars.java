package miss;

import sim.engine.SimState;
import sim.field.continuous.Continuous2D;

public class Cars extends SimState{
	
	public Continuous2D yard = new Continuous2D(1.0,100,100);
	Road road= new Road(); //raczej bedzie tu zbior wszystkich road i crossing, ale to tak na szybko

	public Cars(long seed) {
		super(seed);
	}
	
	public void start(){
		super.start();
		yard.clear();
		
		for(Car car: road.carsOnRoad){
			schedule.scheduleRepeating(car);
		}
	}

}
