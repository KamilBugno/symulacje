package miss;

import sim.engine.SimState;
import sim.field.continuous.Continuous2D;

public class Cars extends SimState{

	private Continuous2D yard;
	private Road road;

	public Cars(long seed) {
		super(seed);
		yard = new Continuous2D(1.0,100,100);
		road= new Road(); //raczej bedzie tu zbior wszystkich road i crossing, ale to tak na szybko
	}
	
	public void start(){
		super.start();
		yard.clear();
		
		for(Car car: road.getCarsOnRoad()){
			schedule.scheduleRepeating(car);
		}
	}

	public Continuous2D getYard() {
		return yard;
	}

	public void setYard(Continuous2D yard) {
		this.yard = yard;
	}

	public Road getRoad() {
		return road;
	}

	public void setRoad(Road road) {
		this.road = road;
	}

}
