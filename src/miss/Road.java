
	
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
    private List<Car> carsOnVerticalRoad;
    private int yardHeight;
    private int yardWidth;
    private int numCars;
    private MutableDouble2D position;
    private MersenneTwisterFast random;
    Double2D startPoint;
    Double2D endPoint;
    private boolean vertical;
    private boolean left;

    public Road(Double2D startPoint, Double2D endPoint, boolean vertical, boolean left){
        carsOnRoad = new ArrayList<>();
        carsOnVerticalRoad = new ArrayList<>();
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
			if(!vertical){
				if(left){
					position =  new MutableDouble2D(startPoint.x + (endPoint.x-startPoint.x) * random.nextDouble(), yardHeight * 0.49);
				}
				if(!left){
					position =  new MutableDouble2D(startPoint.x + (endPoint.x-startPoint.x) * random.nextDouble(), yardHeight * 0.5);
				}
				Car car = new Car(position);
				carsOnRoad.add(car);
			}
			if (vertical){
				if(left){
					position = new MutableDouble2D(yardWidth*0.51, startPoint.y + (endPoint.y-startPoint.y) * random.nextDouble());
				}
				else{
					position = new MutableDouble2D(yardWidth*0.5, startPoint.y + (endPoint.y-startPoint.y) * random.nextDouble());
				}
				Car car = new Car(position);
				carsOnVerticalRoad.add(car);
			}
		}
		Collections.sort(carsOnRoad, new Comparator<Car>(){
			public int compare(Car c1, Car c2){
				Double c1x = new Double(c1.getCurrentPosition().x);
				Double c2x = new Double(c2.getCurrentPosition().x);
				return c2x.compareTo(c1x);
			}
		});
		Collections.sort(carsOnVerticalRoad, new Comparator<Car>(){
			public int compare(Car c1, Car c2){
				Double c1y = new Double(c1.getCurrentPosition().y);
				Double c2y = new Double(c2.getCurrentPosition().y);
				return c2y.compareTo(c1y);
			}
		});		
	}

	public void setCarSpeedValues(Car car){
		int index;
		double diff;
		double currentPosition;
		boolean isOnTheRoad = false;
		if(left){
			car.left = true;
		}
		if(vertical){
			car.vertical = true;
			index = carsOnVerticalRoad.indexOf(car);
			 if (index != 0) {
		            diff = Math.abs(carsOnVerticalRoad.get(index - 1).getCurrentPosition().y - car.getCurrentPosition().y);
		        }else{
		            diff = 20;
		        }
			 currentPosition = carsOnVerticalRoad.get(index).getCurrentPosition().y;
			   if((startPoint.y +3 < currentPosition) && (currentPosition < (endPoint.y - 3))){
		            isOnTheRoad = true;
		        }else{
		            isOnTheRoad = false;
		            car.setNeedToStop(true);
		        }
		}
		else {
			index = carsOnRoad.indexOf(car);
			 if (index != 0) {
		            diff = Math.abs(carsOnRoad.get(index - 1).getCurrentPosition().x - car.getCurrentPosition().x);
		        }else{
		            diff = 20;
		        }
			 currentPosition = carsOnRoad.get(index).getCurrentPosition().x;
			   if((startPoint.x +3 < currentPosition) && (currentPosition < (endPoint.x - 3))){
		            isOnTheRoad = true;
		        }else{
		            isOnTheRoad = false;
		            car.setNeedToStop(true);
		        }
		}     

      
       
        //if(!left){
	     

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
    public List<Car> getCarsOnVerticalRoad() {
        return carsOnVerticalRoad;
    }

}
	

	

	
	