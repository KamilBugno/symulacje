package miss;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import sim.util.Double2D;



public class JsonParser {	
	
	private Map<Integer, List<Road>> roadsIn = new HashMap<Integer, List<Road>>();
	private Map<Integer, List<Road>> roadsOut = new HashMap<Integer, List<Road>>();
	List<Crossing> crossings = new ArrayList<Crossing>();
	
	public List<Crossing> parseAndCreate(){
		int crossingSize = parseAndCreateRoads();
		createCrossings(crossingSize);
		return crossings;
	}
	
	    @SuppressWarnings({ "unchecked", "static-access" })
		private int parseAndCreateRoads() {
	    	JSONParser parser = new JSONParser();
	    	Object json = null;
	    	int crossingsSize = 0;
			try {
				json = parser.parse(new FileReader(
				            "D:\\projekt symulacje\\src\\miss\\dane.txt"));
			} catch (IOException | ParseException e) {
				
				e.printStackTrace();
			}
			JSONObject jsonObject = (JSONObject) json;
			JSONArray values = (JSONArray)jsonObject.get("values");
			int size = values.size();
			for(int i = 0; i < size; i++){
				 JSONObject obj = (JSONObject) values.get(i);	
				 int crossingId = ((Long) obj.get("crossingId")).intValue();
				 if(crossingsSize < crossingId){
					 crossingsSize = crossingId;
				 }
	    		 boolean in = (boolean) obj.get("in");
		         boolean out = (boolean) obj.get("out");
		         JSONArray coordinates = (JSONArray)obj.get("coordinates");
		         boolean vertical = (boolean) obj.get("vertical");
		         boolean left = (boolean) obj.get("left");		         
		         createRoad(crossingId,in, out, coordinates, vertical, left);
		         
			}
			
			return crossingsSize + 1;
	    }
	    private void createRoad(int crossingId, boolean in, boolean out, JSONArray coordinates, boolean vertical, boolean left){
	    	Road road = new Road(new Double2D((Double)coordinates.get(0), (Double)coordinates.get(1)), 
    				new Double2D((Double)coordinates.get(2), (Double)coordinates.get(3)), vertical, left);
	    	List<Road> roads = new ArrayList<Road>();
	    	if(in){
	    		roads = roadsIn.get(crossingId);
	    		if(roads == null){
	    			roads = new ArrayList<Road>();
	    		}
	    		roads.add(road);
	    		roadsIn.put(crossingId, roads);
	    	}
	    	if(out){
	    		roads = roadsOut.get(crossingId);
	    		if(roads == null){
	    			roads = new ArrayList<Road>();
	    		}
	    		roads.add(road);
	    		roadsOut.put(crossingId, roads);
	    	}
	    }
	    
	    private void createCrossings(int crossingsSize){
	    	crossings = new ArrayList<Crossing>();
	    	Crossing crossing = null;
	    	List<Road> in = new ArrayList<Road>();
	    	List<Road> out = new ArrayList<Road>();
	    	
	    	for(int i = 0; i < crossingsSize; i++){
	    		in = (List<Road>) roadsIn.get(i);
	    		out = (List<Road>) roadsOut.get(i);
	    		crossing = new Crossing(in, out);
	    		crossings.add(crossing);
	    	}

	    }
	}
