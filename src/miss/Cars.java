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
	
		
		Double2D point1 = new Double2D(0.0, 50.0);
		Double2D point2 = new Double2D(0.0, 49.0);
		
		Double2D point3 = new Double2D(50.0, 50.0);
		Double2D point4 = new Double2D(50.0, 49.0);
//		
//		
//		Double2D point5 = new Double2D(53.0, 50.0);
//		Double2D point6 = new Double2D(53.0, 49.0);
//		
//		Double2D point7 = new Double2D(100.0, 50.0);
//		Double2D point8 = new Double2D(100.0, 49.0);
//		
//		Double2D point9 = new Double2D(50.0, 0.0);
//		Double2D point10 = new Double2D(50.0, 50.0);
//		
//		Double2D point11 = new Double2D(50.0, 0.0);
//		Double2D point12 = new Double2D(50.0, 50.0);
//		
//		
//		roadsIn.add(new Road(point2, point4, false, true));
//		roadsIn.add(new Road(point1,point3, false, false));
//		roadsIn.add(new Road(point9, point10, true,true));
//		roadsIn.add(new Road(point11, point12, true, false));
//		
//		roadOut.add(new Road(point6,point8, false, true));
//		roadOut.add(new Road(point5,point7, false, false));
		crossing = new Crossing(roadsIn,roadsOut);
	}

	public void start(){
		super.start();
		yard.clear();

		for(Road road: crossing.getIn()){
			for(Car car: road.getCarsOnRoad()){
				schedule.scheduleRepeating(car);
			}
			for(Car car: road.getCarsOnVerticalRoad()){
				schedule.scheduleRepeating(car);
			}
		}
		for(Road road: crossing.getOut()){
			for(Car car: road.getCarsOnRoad()){
				schedule.scheduleRepeating(car);
			}
			for(Car car: road.getCarsOnVerticalRoad()){
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
			if(road.getCarsOnVerticalRoad().contains(car)){
				return road;
			}
		}
		for(Road road: crossing.getOut()){
			if(road.getCarsOnRoad().contains(car)){
				return road;
			}
			if(road.getCarsOnVerticalRoad().contains(car)){
				return road;
			}
		}	
		return null;
	}
}
