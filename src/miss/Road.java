
	
package miss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ec.util.MersenneTwisterFast;
import sim.util.Double2D;
import sim.util.MutableDouble2D;

public class Road {

    private List<Car> carsHorizontalOnRoad;
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
        carsHorizontalOnRoad = new ArrayList<>();
        carsOnVerticalRoad = new ArrayList<>();
        random = new MersenneTwisterFast();
        yardHeight = 100;
        yardWidth = 100;
        numCars = 3;
        this.vertical = vertical;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.left = left;
        createCars(left, vertical);
    }
	
	public void createCars(boolean left, boolean vertical){
		for(int i = 0; i < numCars; i++){
			if(vertical){
				createCarsOnVerticalRoads(left);
			}
			else{
				createCarsOnHorizontalRoads(left);
			}
		}
		sortListsOfCars(carsHorizontalOnRoad, carsOnVerticalRoad, left);
	}
	
	public void setCarSpeedValues(Car car){
		double diff = getDiff(car);
		boolean isOnTheRoad = checkIfIsOnTheRoad(car);
		setLeft(car, left);
		setVertical(car, vertical);
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
		


    public List<Car> getCarsHorizontalOnRoad() {
        return carsHorizontalOnRoad;
    }
    public List<Car> getCarsOnVerticalRoad() {
        return carsOnVerticalRoad;
    }
    private void createCarsOnHorizontalRoads(boolean left){
		if(left){
			position =  new MutableDouble2D(startPoint.x + 3 + (endPoint.x-startPoint.x-6) * random.nextDouble(), startPoint.y);
		}
		if(!left){
			position =  new MutableDouble2D(startPoint.x + 3+ (endPoint.x-startPoint.x-6) * random.nextDouble(), startPoint.y);
		}
		Car car = new Car(position);
		carsHorizontalOnRoad.add(car);
    }
    private void createCarsOnVerticalRoads(boolean left){
		if(left){
			position = new MutableDouble2D(startPoint.x, startPoint.y + 3 + (endPoint.y-startPoint.y-6) * random.nextDouble());
		}
		else{
			position = new MutableDouble2D(startPoint.x, startPoint.y + 3 + (endPoint.y-startPoint.y-6) * random.nextDouble());
		}
		Car car = new Car(position);
		carsOnVerticalRoad.add(car);
    }
    
    private void sortListsOfCars(List<Car> carsOnHorizontalRoad, List<Car> carsOnVerticalRoad, boolean isLeft){
		if(!isLeft){
			Collections.sort(carsOnHorizontalRoad, new Comparator<Car>(){
				public int compare(Car c1, Car c2){
					Double c1x = new Double(c1.getCurrentPosition().x);
					Double c2x = new Double(c2.getCurrentPosition().x);
					return c2x.compareTo(c1x);
				}
			});
		}else{
			Collections.sort(carsOnHorizontalRoad, new Comparator<Car>(){
				public int compare(Car c1, Car c2){
					Double c1x = new Double(c1.getCurrentPosition().x);
					Double c2x = new Double(c2.getCurrentPosition().x);
					return c1x.compareTo(c2x);
				}
			});
		}
		if(!isLeft){
			Collections.sort(carsOnVerticalRoad, new Comparator<Car>(){
				public int compare(Car c1, Car c2){
					Double c1y = new Double(c1.getCurrentPosition().y);
					Double c2y = new Double(c2.getCurrentPosition().y);
					return c2y.compareTo(c1y);
				}
			});
		}else{
			Collections.sort(carsOnVerticalRoad, new Comparator<Car>(){
				public int compare(Car c1, Car c2){
					Double c1y = new Double(c1.getCurrentPosition().y);
					Double c2y = new Double(c2.getCurrentPosition().y);
					return c1y.compareTo(c2y);
				}
			});
		}
	}
    private boolean checkIfIsOnTheRoad(Car car){
    	int index;
		double diff;
		double currentPosition;
		boolean isOnTheRoad = false;
		int twilingZone = 0;
		if(vertical){
			index = carsOnVerticalRoad.indexOf(car);
			diff = getDiff(car);
			 currentPosition = carsOnVerticalRoad.get(index).getCurrentPosition().y;
			   if((startPoint.y + 3 < currentPosition) && (currentPosition < (endPoint.y - twilingZone))){
		            isOnTheRoad = true;
		        }else{
		            isOnTheRoad = false;
		            car.setNeedToStop(true);
		        }
		}
		else {
			index = carsHorizontalOnRoad.indexOf(car);
			diff = getDiff(car);
			 currentPosition = carsHorizontalOnRoad.get(index).getCurrentPosition().x;
			   if((startPoint.x + 3 < currentPosition) && (currentPosition < (endPoint.x - twilingZone))){
		            isOnTheRoad = true;
		        }else{
		            isOnTheRoad = false;
		            car.setNeedToStop(true);
		        }
		} 
		return isOnTheRoad;
    }
    private double getDiff(Car car){
    	int index;
		double diff;
		
		if(vertical){
			index = carsOnVerticalRoad.indexOf(car);
			 if (index != 0) {
		            diff = Math.abs(carsOnVerticalRoad.get(index - 1).getCurrentPosition().y - car.getCurrentPosition().y);
		        }else{
		            diff = 20;
		        }
		}
		else {
			index = carsHorizontalOnRoad.indexOf(car);
			 if (index != 0) {
		            diff = Math.abs(carsHorizontalOnRoad.get(index - 1).getCurrentPosition().x - car.getCurrentPosition().x);
		        }else{
		            diff = 20;
		        }
		}
		return diff;
    }
    private void setVertical(Car car, boolean vertical){
    	car.vertical = vertical;
    }
    private void setLeft(Car car, boolean left){
    	car.left = left;
    }
}
	

	

	
	