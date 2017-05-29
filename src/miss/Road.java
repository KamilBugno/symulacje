
	
package miss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.SynchronousQueue;

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
	private boolean ifDebug = false;

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
				 car.setNeedToStop(true);
        		 if(!changeRoad(car).isEmpty()){
        			 System.out.println("changed road");
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

    public Crossing changeRoad(Car car){
   	 if(ifDebug)System.out.println("changeRoad "+car.toString());
   	 
   	 	List<Road> pathToTarget = car.pathToTarget;
   	 
   	 
    	
		Road road = car.getCars().getRoad(car); //pobieram droge na ktorej znajduje sie auto
		boolean changed = false;
		int id = 0;
		if(road == null){
			if(ifDebug)System.out.println("Road jest nullem");
		}

		Crossing currentCrossing = null;

		
		for(Crossing crossing : cityCrossings){
			for(Road currRoad: crossing.getIn()){
			if(currRoad.getId() == road.getId()){ //skrzyzowanie na ktorym jest dane auto jako na drodze IN
				List<Road> roads = crossing.getOut();
				Road finalRoad; //= car.getNextRoad();
				
				Iterator<Road> pathIterator = pathToTarget.iterator();
				finalRoad = pathIterator.next();
				System.out.println("----------------------------IS DOUBLE ROAD?!?!?!?!?!??!?!??!?! " + finalRoad.isDoubleRoad());
				System.out.println("carId: "+ car.toString() + " currentRoad: " + road.id);
				System.out.println("carId: "+ car.toString() + " nextRoad: " + finalRoad.id);
				

				if(finalRoad==null){
					int size = roads.size();
					Random rand = new Random();
					int index = rand.nextInt(size);
					finalRoad = crossing.getOut().get(index);
					if(ifDebug)System.out.println(car.toString() + " finalRoad==null, road "+road + " ,finalRoad "+finalRoad);
					car.setNextRoad(finalRoad);
				}

				Iterator<Road> iterator = roads.iterator();
				Road currentRoad;
				while(iterator.hasNext()){
					currentRoad = iterator.next();
					if(currentRoad.isDoubleRoad()){
						iterator.remove();
					}
				}
				Crossing emptyCrossing = new Crossing(new ArrayList<Road>(), new ArrayList<Road>());
				System.out.println("--------------------------------" );
				Map<Road, Boolean> lightCrossing = crossing.getLightCrossing();
				System.out.println("size: " + lightCrossing.size());
				boolean contains = lightCrossing.containsKey(finalRoad);
				System.out.println("--------------------------------contains: " + contains);
				boolean value = false; 
				if(contains){
					value = lightCrossing.get(finalRoad);
				}
				//boolean returned = crossing.getLightCrossing().get(finalRoad);
				System.out.println("--------------------------------" + value);
				if(!contains || !value){
					if(ifDebug)System.out.println(car.toString() + " return$$$");
					return emptyCrossing;			}
				pathIterator.remove();
				System.out.println("hello");
				if(ifDebug)System.out.println(car.toString() + " po return, road " + road + " ,finalRoad" + finalRoad);
				currentCrossing = crossing;
				car.addNumberOfCoveredCrossings();
				if(road !=null){
					road.carsOnRoad.remove(car); //usuwam auto z tej drogi
				}




				

				finalRoad.carsOnRoad.add(car); //dodanie auta do odpowiedniej drogi

				if(car == null){
					if(ifDebug)System.out.println("car == null "+car.toString()+ " road " + road+ " ,finalRoad " + finalRoad);
				}
				if(car.getCars() == null){
					if(ifDebug)System.out.println("car.getCars() == null "+car.toString()+ " road " + road+ " ,finalRoad " + finalRoad);
				}
				if(car.getCars().getRoad(car) == null){
					if(ifDebug)System.out.println("car.getCars().getRoad(car) == null "+car.toString()+ " road" + road+ " ,finalRoad " + finalRoad);
				}





				if(finalRoad.startPoint.getX() > (car.getCurrentPosition().getX()-5)
						&& finalRoad.startPoint.getX() < (car.getCurrentPosition().getX()+5)
						&& finalRoad.startPoint.getY() > (car.getCurrentPosition().getY()-5)
						&& finalRoad.startPoint.getY() < (car.getCurrentPosition().getY()+5)){
					//wyzej: sprawdzanie gdzie ma zostać dopisane auto - bufor kwadratowy 5x5

                    //ponizej: obliczenie nowej pozycji auta w zaleznosci od parametrow drogi
                    if(road.isLeft() && !road.isVertical()){
                        car.setCurrentPosition(new MutableDouble2D(finalRoad.startPoint.getX()-5, finalRoad.startPoint.getY()));
                    }
                    else if(road.isLeft() && !road.isVertical()){
                        car.setCurrentPosition(new MutableDouble2D(finalRoad.startPoint.getX()+5, finalRoad.startPoint.getY()));
                    }
                    else if(!road.isLeft() && road.isVertical()){
                        car.setCurrentPosition(new MutableDouble2D(finalRoad.startPoint.getX(), finalRoad.startPoint.getY()+5));
                    }
                    else if(road.isLeft() && road.isVertical()){
                        car.setCurrentPosition(new MutableDouble2D(finalRoad.startPoint.getX(), finalRoad.startPoint.getY()-5));
                    }
				} else{
                    if(road.isLeft() && !road.isVertical()){
                        car.setCurrentPosition(new MutableDouble2D(finalRoad.endPoint.getX()-5, finalRoad.endPoint.getY()));
                    }
                    else if(!road.isLeft() && !road.isVertical()){
                        car.setCurrentPosition(new MutableDouble2D(finalRoad.endPoint.getX()+5, finalRoad.endPoint.getY()));
                    }
                    else if(!road.isLeft() && road.isVertical()){
                        car.setCurrentPosition(new MutableDouble2D(finalRoad.endPoint.getX(), finalRoad.endPoint.getY()+5));
                    }
                    else if(road.isLeft() && road.isVertical()){
                        car.setCurrentPosition(new MutableDouble2D(finalRoad.endPoint.getX(), finalRoad.endPoint.getY()-5));
                    }
				}
				car.setNextRoad(null);
                car.setNeedToStop(false);
                changed = true;
				break;
			}
			}
		}
		if(!changed){
			//System.out.println("not changed :(");
	
		}
		return currentCrossing;
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
	
	public int findTargetRoad(int id){
		Random rand = new Random();
		int targetId = rand.nextInt(25);
		if(targetId == id){
			targetId = rand.nextInt(25);
		}
		return targetId;
	}
	
	public List<Road> findRoadsBetweenCurrentRoadAndTargetRoad(int currentId){
		List<Road> roads = new ArrayList<Road>();
		
		int targetId = findTargetRoad(currentId);
		Road currentRoad = this;
		Road targetRoad = null;
		Crossing currentCrossing = null;
		Crossing targetCrossing = null;
		Road secondRoad = null;
		for(Crossing crossing: cityCrossings){
			for(Road road: crossing.getIn()){
				if(road.id == targetId){
					targetRoad = road;
					targetCrossing = crossing;
				}
			}
			for(Road road: crossing.getOut()){
				if(road.id == targetId){
					targetRoad = road;
					targetCrossing = crossing;
				}
			}
		}
		currentCrossing = findCrossing(currentRoad);
		List<Road> currentCrossingDoubleRoads = findDoubleRoads(currentCrossing);
		List<Road> targetCrossingDoubleRoads = findDoubleRoads(targetCrossing);
		currentCrossingDoubleRoads.retainAll(targetCrossingDoubleRoads);

		if(currentCrossing == targetCrossing){
			roads.add(targetRoad);
			System.out.println("dodaję drogę 1" );
			return roads;
		}
		else if(!currentCrossingDoubleRoads.isEmpty()){
			secondRoad = findSecondRoad(currentCrossingDoubleRoads.get(0));
			System.out.println("dodaję drogę 2" );
		//	roads.add(currentCrossingDoubleRoads.get(0));
			roads.add(secondRoad);
			roads.add(targetRoad);
			return roads;
		}
		
		else {
			boolean contains = false;
			List<Road> doubleRoads = new ArrayList<Road>();
			int iter = 1; 
			int counter = 0; 
			while(!areCrossingsEqual(currentCrossing, targetCrossing)){				
				currentCrossingDoubleRoads = findDoubleRoads(currentCrossing);
				
				doubleRoads = findDoubleRoads(currentCrossing);
				currentCrossingDoubleRoads.retainAll(targetCrossingDoubleRoads);
				
				if(!currentCrossingDoubleRoads.isEmpty()){
					secondRoad = findSecondRoad(currentCrossingDoubleRoads.get(0));
					//roads.add(currentCrossingDoubleRoads.get(0));
					roads.add(secondRoad);
					System.out.println("dodaję drogę 3" );
					roads.add(targetRoad);
					return roads;
				}
				secondRoad = findSecondRoad(doubleRoads.get(0));
				contains = roads.contains(secondRoad);
				if(!contains){
					
				//	roads.add(doubleRoads.get(0));
					System.out.println("dodaję drogę 4" );
					roads.add(secondRoad);
					counter = 0;
					currentCrossing = findCrossing(doubleRoads.get(0));
				}
				else if(contains && counter < 20) {
					currentCrossing = findSecondCrossing(doubleRoads.get(0), currentCrossing);
				}
				else if(counter > 20 ){
					for(int i = iter; i < doubleRoads.size(); i++){
						iter = i;
						secondRoad = findSecondRoad(doubleRoads.get(i));
						contains = roads.contains(secondRoad);
						if(!contains){
							roads.add(secondRoad);
							
							System.out.println("dodaję drogę 5" );
							counter = 0;
							currentCrossing = findCrossing(doubleRoads.get(i));
						}
						else{
							currentCrossing = findSecondCrossing(doubleRoads.get(i), currentCrossing);
							}
						break;
					}
				}
				counter++;
			}
			roads.add(targetRoad);
		}
		return roads;
	}
	
	
	public Crossing findCrossing(Road road){
		Crossing crossing = null;
		
		for(Crossing c: cityCrossings){
			for(Road r: c.getIn()){
				if(road.id == r.id){
					crossing = c;
				}
			}
			for(Road r: c.getOut()){
				if(road.id == r.id){
					crossing = c;
				}
			}
		}
		
		return crossing;
	}
	
	public Crossing findSecondCrossing(Road r, Crossing c){
		Crossing crossing = null;
		
		for(Crossing cr: cityCrossings){
			for(Road road: cr.getIn()){
				if(road.id == r.id && cr != c){
					crossing = cr;
				}
			}
			for(Road road: cr.getOut()){
				if(road.id == r.id && cr != c){
					crossing = cr;
				}
			}
		}
		return crossing;
	}
	public Road findSecondRoad(Road doubleRoad){
		for(Crossing crossing: cityCrossings){
			for(Road road: crossing.getOut()){
				if(road.getId() == doubleRoad.getId() && !road.isDoubleRoad()){
					return road;
				}
			}
			for(Road road: crossing.getIn()){
				if(road.getId() == doubleRoad.getId() && !road.isDoubleRoad()){
					return road;
				}
			}
		}
		return doubleRoad;
	}
	
	public List<Road> findDoubleRoads(Crossing crossing){
		List<Road> roads = new ArrayList<Road>();
		List<Road> doubleRoads = new ArrayList<Road>();
		
		for(Crossing c: cityCrossings){
			for(Road road: c.getIn()){
				if(road.isDoubleRoad()){
					doubleRoads.add(road);
				}
			}
			for(Road road: c.getOut()){
				if(road.isDoubleRoad()){
					doubleRoads.add(road);
				}
			}
		}		
		
		for(Road road: crossing.getIn()){
			for(Road doubleRoad: doubleRoads){
				if(doubleRoad.id == road.id){
					roads.add(doubleRoad);
				}
			}
		}
		for(Road road: crossing.getOut()){
			for(Road doubleRoad: doubleRoads){
				if(doubleRoad.id == road.id){
					roads.add(doubleRoad);
				}
			}
		}
		if(roads.size() == 0){
			System.out.println("nie znalazlem doubleRoad" );
		}
		return roads;
	}

	private boolean areCrossingsEqual(Crossing c1, Crossing c2){
		
		if(c1.getIn() == c2.getIn() && c2.getOut() == c1.getOut()){
			return true;
		}
		
		return false;
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
	

	

	
	