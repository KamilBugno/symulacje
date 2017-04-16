package miss;

import sim.engine.SimState;
import sim.field.continuous.Continuous2D;
import sim.util.Double2D;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Cars extends SimState{

	private Continuous2D yard;
	private Crossing crossing;

	//do pomyslenia: co bedziemy tutaj przechowywac - skrzyzowania, ulice, czy jeszcze inna strukture?

	public Cars(long seed) {
		super(seed);
		yard = new Continuous2D(1.0,100,100);
		
		DataGenerator generator = new DataGenerator();
		try {
			generator.generate();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Road> roadsIn = new ArrayList<>();
		List<Road> roadsOut = new ArrayList<>();

		JsonParser parser = new JsonParser();
		parser.parseAndCreate(roadsIn, roadsOut);

		crossing = new Crossing(roadsIn,roadsOut);
	}

	public void start(){
		super.start();
		yard.clear();

		for(Road road: crossing.getIn()){
			for(Car car: road.getCarsOnRoad()){
				schedule.scheduleRepeating(car);
			}
		}
		for(Road road: crossing.getOut()){
			for(Car car: road.getCarsOnRoad()){
				schedule.scheduleRepeating(car);
			}
		}
	}

	public Continuous2D getYard() {
		return yard;
	}

	public Road getRoad(Car car){
		for(Road road: crossing.getIn()){
			if(road.getCarsOnRoad().contains(car)){
				return road;
			}
		}
		for(Road road: crossing.getOut()){
			if(road.getCarsOnRoad().contains(car)){
				return road;
			}
		}	
		return null;
	}

	public List<Car> getCarOutRoad(){
		List<Road> roads = crossing.getOut();
		List<Car> cars = new ArrayList<>();
		for (Road road : roads) {
			System.out.println(road.toString());
			cars.addAll(road.getCarsOnRoad());
		}
		for (Car car: cars){
			System.out.println(car.toString());
		}
		return cars;
	}
}
