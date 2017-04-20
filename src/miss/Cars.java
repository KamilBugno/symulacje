package miss;

import sim.engine.SimState;
import sim.field.continuous.Continuous2D;
import sim.util.Double2D;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Cars extends SimState{

	private Continuous2D yard;
	private City city = City.getInstance();
	private List<Crossing> cityCrossings = city.getCrossings();

	public Cars(long seed) {
		super(seed);
		yard = new Continuous2D(1.0,100,100);
		
		DataGenerator generator = new DataGenerator();
		try {
			generator.generate();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JsonParser parser = new JsonParser();
		List<Crossing> crossings = parser.parseAndCreate();
		for(Crossing crossing: crossings){
			city.addCrossing(crossing);
		}

	}

	public void start(){
		super.start();
		yard.clear();
		for(Crossing crossing: cityCrossings){
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
	}

	public Continuous2D getYard() {
		return yard;
	}

	public Road getRoad(Car car){
		for(Crossing crossing: cityCrossings){
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
		}
			return null;
		
	}
	public List<Road> getRoadsOut( Crossing c){
		for(Crossing crossing: cityCrossings){
			if(crossing.equals(c)){
				return crossing.getOut();
			}
		}
			return null;
	
	}
	public List<Car> getCarOutRoad(){
		List<Road> roads = new ArrayList<Road>();
		List<Road> out = new ArrayList<Road>();
		for(Crossing crossing: cityCrossings){
			out = crossing.getOut();
			roads.addAll(out);
		}
		List<Car> cars = new ArrayList<>();
		for (Road road : roads) {
			cars.addAll(road.getCarsOnRoad());
		}
		return cars;
	}
}
