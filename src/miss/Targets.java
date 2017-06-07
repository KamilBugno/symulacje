package miss;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Targets {
    private City city = City.getInstance();
	private List<Crossing> cityCrossings = city.getCrossings();
	public Map<Integer, List<Road>> targets = new HashMap<Integer, List<Road>>();
	
	
	
public void createTargets(){
	
	List<Road> roads;
	List<Integer> ids;
	
	

 ids = new ArrayList<>(Arrays.asList(0)); //	0 -> 0
 targets.put(0, createRoadList(ids));
 ids = new ArrayList<>(Arrays.asList(12, 10)); //	1 -> 12, 10
 targets.put(1, createRoadList(ids));
 ids = new ArrayList<>(Arrays.asList(23,28)); //	2 -> 23, 28
 targets.put(2, createRoadList(ids));
 ids = new ArrayList<>(Arrays.asList(5)); //	3 -> 5
 targets.put(3, createRoadList(ids));
 ids = new ArrayList<>(Arrays.asList(16,6,10)); //	4 -> 16, 6, 10
 targets.put(4, createRoadList(ids));
 ids = new ArrayList<>(Arrays.asList(5)); //	5 -> 5
 targets.put(5, createRoadList(ids));
 ids = new ArrayList<>(Arrays.asList(13,23,25)); //	6 -> 13, 23, 25
 targets.put(6, createRoadList(ids));
 ids = new ArrayList<>(Arrays.asList(9)); //	7 -> 9
 targets.put(7, createRoadList(ids));
 ids = new ArrayList<>(Arrays.asList(17, 2, 23, 25)); //	8 -> 17, 2, 23, 25
 targets.put(8, createRoadList(ids));
 ids = new ArrayList<>(Arrays.asList(9)); //	9 -> 9
 targets.put(9, createRoadList(ids));
 ids = new ArrayList<>(Arrays.asList(10)); //	10 -> 10
 targets.put(10, createRoadList(ids));

 ids = new ArrayList<>(Arrays.asList(13, 23, 21)); // 11 -> 13, 23, 21
 targets.put(11, createRoadList(ids));
 ids = new ArrayList<>(Arrays.asList(7, 9)); // 12 -> 7, 9
 targets.put(12, createRoadList(ids));
 ids = new ArrayList<>(Arrays.asList(23, 21)); // 13 -> 23, 21
 targets.put(13, createRoadList(ids));
 ids = new ArrayList<>(Arrays.asList(14)); // 14 -> 14
 targets.put(14, createRoadList(ids));
 ids = new ArrayList<>(Arrays.asList(17, 2, 0)); // 15 -> 17, 2, 0
 targets.put(15, createRoadList(ids));
 ids = new ArrayList<>(Arrays.asList(14)); // 16 -> 14
 targets.put(16, createRoadList(ids));
 ids = new ArrayList<>(Arrays.asList(2, 0)); // 17 -> 2, 0
 targets.put(17, createRoadList(ids));
 ids = new ArrayList<>(Arrays.asList(18)); // 18 -> 18
 targets.put(18, createRoadList(ids));
 ids = new ArrayList<>(Arrays.asList(22, 12, 10)); // 19 -> 22, 12, 10
 targets.put(19, createRoadList(ids));
 ids = new ArrayList<>(Arrays.asList(25)); // 20 -> 25
 targets.put(20, createRoadList(ids));
 ids = new ArrayList<>(Arrays.asList(21)); // 21 -> 21
 targets.put(21, createRoadList(ids));
 ids = new ArrayList<>(Arrays.asList(3, 5)); // 22 -> 3, 5
 targets.put(22, createRoadList(ids));
 ids = new ArrayList<>(Arrays.asList(18)); // 23 -> 18
 targets.put(23, createRoadList(ids));
 ids = new ArrayList<>(Arrays.asList(22, 3, 16, 14)); // 24 -> 22, 3, 16, 14
 targets.put(24, createRoadList(ids));
 ids = new ArrayList<>(Arrays.asList(25)); // 25 -> 25
 targets.put(25, createRoadList(ids));
	
}

public List<Road> getTarget(Integer id){
	return targets.get(id);
}


List<Road> createRoadList(List<Integer> ids){
		List<Road> roads = new ArrayList<Road>();
		Road r;
		for(Integer id: ids){
			r = findRoad(id);
			roads.add(r);
		}		
		return roads;
	}
	
	
	private Road findRoad(int id){
		for(Crossing crossing: cityCrossings){
			for(Road road: crossing.getOut()){
				if(road.getId() == id){
					return road;
				}
			}
		}
		return null;
	}
	
}
