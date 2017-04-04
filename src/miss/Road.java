
	
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
    int startPoint;
    int endPoint;
    private boolean vertical;
    private boolean left;

    public Road(int startPoint, int endPoint, boolean vertical, boolean left){
        carsOnRoad = new ArrayList<>();
        random = new MersenneTwisterFast();
        yardHeight = 100;
        yardWidth = 100;
        numCars = 15;
        this.vertical = vertical;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.left = left;
        createCars(left, vertical);
    }
	
	public void createCars(boolean left, boolean vertical){
		for(int i = 0; i < numCars; i++){
			if(left){
				position =  new MutableDouble2D(startPoint + (endPoint-startPoint) * random.nextDouble(), yardHeight * 0.49);
			}
			if(!left){
				position =  new MutableDouble2D(startPoint + (endPoint-startPoint) * random.nextDouble(), yardHeight * 0.5);
			}
			Car car = new Car(position);
			carsOnRoad.add(car);

			//System.out.println("position.x: "+ position.x + ", position.y: " + position.y);
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
		if(left){
			car.left = true;
		}
		int index = carsOnRoad.indexOf(car);
		double diff;

        if (index != 0) {
            diff = Math.abs(carsOnRoad.get(index - 1).getCurrentPosition().x - car.getCurrentPosition().x);
        }else{
            diff = 20;
        }

        boolean isOnTheRoad = false;
        double currentPosition = carsOnRoad.get(index).getCurrentPosition().x;
        //if(!left){
	        if((startPoint +3 < currentPosition) && (currentPosition < (endPoint - 3))){
	            isOnTheRoad = true;
	        }else{
	            isOnTheRoad = false;
	            car.setNeedToStop(true);
	
	            //do pomyslenia: jak przeniesc auto z jednej ulicy na kolejna uzywajac klasy Crossing?
	
	        }
  //      }
//        if(left){
//        	if((currentPosition - 3 >= startPoint) && (currentPosition + 3 <= endPoint)){
//	            isOnTheRoad = true;
//	        }else{
//	            isOnTheRoad = false;
//	            car.setNeedToStop(true);
//	
//	            //do pomyslenia: jak przeniesc auto z jednej ulicy na kolejna uzywajac klasy Crossing?
//	
//	        }
//        }

		//	System.out.println(diff + " between " + index + " and " + (index-1));
        if(diff > 1 && diff < 4 && isOnTheRoad){
            car.setNeedToSlowDown(true);
            car.setNeedToStop(false);
        }
        else if(diff <= 1 || !isOnTheRoad){
            car.setNeedToStop(true);
        }
        else if( diff > 15 && isOnTheRoad){
            car.setNeedToSpeedUp(true);
            car.setNeedToStop(false);
        }
        else if(isOnTheRoad){
            car.setNeedToSlowDown(false);
            car.setNeedToSpeedUp(false);
            car.setNeedToStop(false);
        }
    }
		


    public List<Car> getCarsOnRoad() {
        return carsOnRoad;
    }

}
	

	

	
	