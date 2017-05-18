
	
package miss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import ec.util.MersenneTwisterFast;
import sim.util.Double2D;
import sim.util.MutableDouble2D;

public class Road {
    private int id;
    private List<Car> carsOnRoad;
    private int numCars;
    private MutableDouble2D position;
    private MersenneTwisterFast random;
    Double2D startPoint;
    Double2D endPoint;
    private boolean vertical;
	private boolean left;
	private boolean doubleRoad = false;
    private City city = City.getInstance();
	private List<Crossing> cityCrossings = city.getCrossings();

    public Road(Double2D startPoint, Double2D endPoint, boolean vertical, boolean left, boolean doubleRoad, int id){
		carsOnRoad = new ArrayList<>();
        random = new MersenneTwisterFast();
        numCars = 3;
        this.vertical = vertical;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.left = left;
		this.id = id;
		this.doubleRoad = doubleRoad;
        createCars(left, vertical, doubleRoad);
    }
	
	public void createCars(boolean left, boolean vertical, boolean doubleRoad){
		for(int i = 0; i < numCars; i++){
			createCarsOnRoads(left, doubleRoad);
		}
		sortListsOfCars(carsOnRoad, left);
	}
	
	public void setCarSpeedValues(Car car){
		double diff = getDiff(car);
		boolean isOnTheRoad = checkIfIsOnTheRoad(car);
		setLeft(car, left);
		setVertical(car, vertical);
	    if(checkIfNeedToStop(car)){
	        	car.setNeedToStop(true);
	        }
	    else if(diff > 1 && diff < 4 && isOnTheRoad){
            car.setNeedToSlowDown(true);
            car.setNeedToStop(false);
        }
        else if(diff <= 1 || !isOnTheRoad){
        	 if(checkIfNeedToChangeRoad(car)){
        		 if(changeRoad(car)){
					 car.getCarStatistics().addCrossings();
				 }
        	 }
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

    public boolean changeRoad(Car car){
   	
    	
		Road road = car.getCars().getRoad(car); //pobieram droge na ktorej znajduje sie auto
		boolean changed = false;
		int id = 0;
		if(road == null){
			System.out.println("Road jest nullem");
		}
		boolean isThereCrossing = false;
		

		
		for(Crossing crossing : cityCrossings){
			for(Road currRoad: crossing.getIn()){
			if(currRoad.getId() == road.getId()){ //skrzyzowanie na ktorym jest dane auto jako na drodze IN
				isThereCrossing = true;
				car.addNumberOfCoveredCrossings();
				if(road !=null){
					road.carsOnRoad.remove(car); //usuwam auto z tej drogi
				}

				List<Road> roads = crossing.getOut();

				Iterator<Road> iterator = roads.iterator();
				Road currentRoad;
				while(iterator.hasNext()){
					currentRoad = iterator.next();
					if(currentRoad.isDoubleRoad()){
						iterator.remove();
					}
				}
				 int size = roads.size();
				
				Random rand = new Random();
				int index = rand.nextInt(size);
				Road finalRoad = crossing.getOut().get(index);
				finalRoad.carsOnRoad.add(car); //dodanie auta do odpowiedniej drogi

				if(finalRoad.startPoint.getX() > (car.getCurrentPosition().getX()-5)
						&& finalRoad.startPoint.getX() < (car.getCurrentPosition().getX()+5)
						&& finalRoad.startPoint.getY() > (car.getCurrentPosition().getY()-5)
						&& finalRoad.startPoint.getY() < (car.getCurrentPosition().getY()+5)){
					//wyzej: sprawdzanie gdzie ma zostać dopisane auto - bufor kwadratowy 5x5

                    //ponizej: obliczenie nowej pozycji auta w zaleznosci od parametrow drogi
                    if(car.getCars().getRoad(car).isLeft() && !car.getCars().getRoad(car).isVertical()){
                        car.setCurrentPosition(new MutableDouble2D(finalRoad.startPoint.getX()-5, finalRoad.startPoint.getY()));
                    }
                    else if(!car.getCars().getRoad(car).isLeft() && !car.getCars().getRoad(car).isVertical()){
                        car.setCurrentPosition(new MutableDouble2D(finalRoad.startPoint.getX()+5, finalRoad.startPoint.getY()));
                    }
                    else if(!car.getCars().getRoad(car).isLeft() && car.getCars().getRoad(car).isVertical()){
                        car.setCurrentPosition(new MutableDouble2D(finalRoad.startPoint.getX(), finalRoad.startPoint.getY()+5));
                    }
                    else if(car.getCars().getRoad(car).isLeft() && car.getCars().getRoad(car).isVertical()){
                        car.setCurrentPosition(new MutableDouble2D(finalRoad.startPoint.getX(), finalRoad.startPoint.getY()-5));
                    }
				} else{
                    if(car.getCars().getRoad(car).isLeft() && !car.getCars().getRoad(car).isVertical()){
                        car.setCurrentPosition(new MutableDouble2D(finalRoad.endPoint.getX()-5, finalRoad.endPoint.getY()));
                    }
                    else if(!car.getCars().getRoad(car).isLeft() && !car.getCars().getRoad(car).isVertical()){
                        car.setCurrentPosition(new MutableDouble2D(finalRoad.endPoint.getX()+5, finalRoad.endPoint.getY()));
                    }
                    else if(!car.getCars().getRoad(car).isLeft() && car.getCars().getRoad(car).isVertical()){
                        car.setCurrentPosition(new MutableDouble2D(finalRoad.endPoint.getX(), finalRoad.endPoint.getY()+5));
                    }
                    else if(car.getCars().getRoad(car).isLeft() && car.getCars().getRoad(car).isVertical()){
                        car.setCurrentPosition(new MutableDouble2D(finalRoad.endPoint.getX(), finalRoad.endPoint.getY()-5));
                    }
				}
                car.setNeedToStop(false);
                changed = true;
				break;
			}
			}
		}
		if(!changed){
			//System.out.println("not changed :(");
	
		}
		return isThereCrossing;
    }
    
    private boolean checkIfNeedToChangeRoad(Car car){    	
    	double diff = getDiff(car);    	
    	if(carsOnRoad.indexOf(car) == 0){
    		if( diff <= 1 || !checkIfIsOnTheRoad(car)){    			
    			return true;
    		}
    	}    	
    	return false;
    }
    
    private void createCarsOnRoads(boolean left, boolean doubleRoad){
    	if(!doubleRoad){
	        if(vertical){
				if(left){
					position = new MutableDouble2D(startPoint.x, startPoint.y + 3 + (endPoint.y-startPoint.y-6) * random.nextDouble());
				}
				else{
					position = new MutableDouble2D(startPoint.x, startPoint.y + 3 + (endPoint.y-startPoint.y-6) * random.nextDouble());
				}
				Car car = new Car(position);
				carsOnRoad.add(car);
			}else{
				if(left){
					position =  new MutableDouble2D(startPoint.x + 3 + (endPoint.x-startPoint.x-6) * random.nextDouble(), startPoint.y);
				}
				if(!left){
					position =  new MutableDouble2D(startPoint.x + 3+ (endPoint.x-startPoint.x-6) * random.nextDouble(), startPoint.y);
				}
				Car car = new Car(position);
				carsOnRoad.add(car);
			}
    	}
    }

    
    private void sortListsOfCars(List<Car> carsOnRoad, boolean isLeft){
		if(!isLeft && !vertical){
			Collections.sort(carsOnRoad, new Comparator<Car>(){
				public int compare(Car c1, Car c2){
					Double c1x = new Double(c1.getCurrentPosition().x);
					Double c2x = new Double(c2.getCurrentPosition().x);
					return c2x.compareTo(c1x);
				}
			});
		}else if(!vertical){
			Collections.sort(carsOnRoad, new Comparator<Car>(){
				public int compare(Car c1, Car c2){
					Double c1x = new Double(c1.getCurrentPosition().x);
					Double c2x = new Double(c2.getCurrentPosition().x);
					return c1x.compareTo(c2x);
				}
			});
		}
		if(!isLeft && vertical){
			Collections.sort(carsOnRoad, new Comparator<Car>(){
				public int compare(Car c1, Car c2){
					Double c1y = new Double(c1.getCurrentPosition().y);
					Double c2y = new Double(c2.getCurrentPosition().y);
					return c2y.compareTo(c1y);
				}
			});
		}else if(vertical){
			Collections.sort(carsOnRoad, new Comparator<Car>(){
				public int compare(Car c1, Car c2){
					Double c1y = new Double(c1.getCurrentPosition().y);
					Double c2y = new Double(c2.getCurrentPosition().y);
					return c1y.compareTo(c2y);
				}
			});
		}
	}

	public List<Crossing> getCityCrossings() {
		return cityCrossings;
	}

    private boolean checkIfIsOnTheRoad(Car car){
    	int index;
		double diff;
		double currentPosition;
		boolean isOnTheRoad = false;
		int twilingZone = 0;
		if(vertical){
			index = carsOnRoad.indexOf(car);
			diff = getDiff(car);
			 currentPosition = carsOnRoad.get(index).getCurrentPosition().y;
			   if((startPoint.y + 3 < currentPosition) && (currentPosition < (endPoint.y - twilingZone))){
		            isOnTheRoad = true;
		        }else{
		            isOnTheRoad = false;
		            car.setNeedToStop(true);
		        }
		}
		else {
			index = carsOnRoad.indexOf(car);
			diff = getDiff(car);
			 currentPosition = carsOnRoad.get(index).getCurrentPosition().x;
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
			index = carsOnRoad.indexOf(car);
			 if (index != 0) {
		            diff = Math.abs(carsOnRoad.get(index - 1).getCurrentPosition().y - car.getCurrentPosition().y);
		        }else{
		            diff = 20;
		        }
		}
		else {
			index = carsOnRoad.indexOf(car);
			 if (index != 0) {
		            diff = Math.abs(carsOnRoad.get(index - 1).getCurrentPosition().x - car.getCurrentPosition().x);
		        }else{
		            diff = 20;
		        }
		}
		return diff;
    }
    private void setVertical(Car car, boolean vertical){
    	car.setVertical(vertical);
    }
    private void setLeft(Car car, boolean left){
    	car.setLeft(left);
    }
    private boolean checkIfNeedToStop(Car car){
    	int diff = (int) getDiff(car);
    	int index = carsOnRoad.indexOf(car);
    	if(index != 0){
	    	Car previous = carsOnRoad.get(index -1);
	    	if(checkIfIsOnTheRoad(car)){
	    		//if(diff <=1){
	    			if(previous.needToStop()){
	    				if(diff == 1){
	    				return true;
	    				}
	    			}
	    		//}
	    	}
    	
    	}
    	return false;
    }

	public boolean isLeft() {
		return left;
	}

	public boolean isVertical() {
		return vertical;
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setDoubleRoad(boolean doubleRoad){
    	this.doubleRoad = doubleRoad;
    }
    public boolean isDoubleRoad(){
    	return doubleRoad;
    }

}
	

	

	
	