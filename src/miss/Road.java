
	
package miss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ec.util.MersenneTwisterFast;
import sim.util.Double2D;
import sim.util.MutableDouble2D;

public class Road {
	public List<Car> carsOnRoad = new ArrayList<Car>();	
	public Road(){
		createCars();
	}
	public int yardHeight = 100;
	public int yardWidth = 100;
	public int numCars = 10;
	public MutableDouble2D position;
	public MersenneTwisterFast random = new MersenneTwisterFast();
	
	public void createCars(){
		for(int i = 0; i < numCars; i++){
			position =  new MutableDouble2D(yardWidth * random.nextDouble(), yardHeight * 0.5);
			Car car = new Car(position);
			carsOnRoad.add(car);
			//System.out.println(car.startPosition);
		}
		
		Collections.sort(carsOnRoad, new Comparator<Car>(){
			public int compare(Car c1, Car c2){
				Double c1x = new Double(c1.currentPosition.x);
				Double c2x = new Double(c2.currentPosition.x);
				return c2x.compareTo(c1x);
			}
		});
		
		
		for(Car car: carsOnRoad){
			System.out.println(car.currentPosition.x);
		}
		}
	public void setCarSpeedValues(Car car){
		
		int index = carsOnRoad.indexOf(car);
		double diff;
		if(index != 0){
	//		System.out.println(carsOnRoad.get(index-1).currentPosition);
			diff = carsOnRoad.get(index - 1).currentPosition.x - car.currentPosition.x;
		
			//diff = Math.abs(diff);
		//	System.out.println(diff + " between " + index + " and " + (index-1));
			if(diff < 20){
				car.needToSlowDown = true;
			}
			else if( diff > 40){
				car.needToSpeedUp = true;
			}
			else {
				car.needToSlowDown = false;
				car.needToSpeedUp = false;
			}
		}		
		
	}
	//zeby dwa auta nie byly w tym samym punkcie sprawdzac
	public List<Car> getRoad(){
		return carsOnRoad;
	}
}
	

	

	
	