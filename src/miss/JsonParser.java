package miss;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import sim.util.Double2D;
public class JsonParser {	  
	    @SuppressWarnings({ "unchecked", "static-access" })
		public void parseAndCreate(List<Road> roadsIn, List<Road> roadsOut) {
	    	JSONParser parser = new JSONParser();
	    	Object json = null;
			try {
				json = parser.parse(new FileReader(
				            "/home/ola/workspace/sym/src/miss/dane.txt"));
			} catch (IOException | ParseException e) {
				
				e.printStackTrace();
			}
			JSONObject jsonObject = (JSONObject) json;
			JSONArray values = (JSONArray)jsonObject.get("values");
			int size = values.size();
			for(int i = 0; i < size; i++){
				System.out.println(size);
	    	
				JSONObject obj = (JSONObject) values.get(i);
	    		
	    		 boolean in = (boolean) obj.get("in");
		            boolean out = (boolean) obj.get("out");
		            JSONArray coordinates = (JSONArray)obj.get("coordinates");

		           boolean vertical = (boolean) obj.get("vertical");
		           boolean left = (boolean) obj.get("left");
		           createRoad(roadsIn, roadsOut, in, out, coordinates, vertical, left);
			}
	    }
	    public void createRoad(List<Road> roadsIn, List<Road> roadsOut, boolean in, boolean out, JSONArray coordinates, boolean vertical, boolean left){
	    	if(in){
	    		roadsIn.add(new Road(new Double2D((Double)coordinates.get(0), (Double)coordinates.get(1)), 
	    				new Double2D((Double)coordinates.get(2), (Double)coordinates.get(3)), vertical, left));
	    	}
	    	if(out){
	    		roadsOut.add(new Road(new Double2D((Double)coordinates.get(0), (Double)coordinates.get(1)), 
	    				new Double2D((Double)coordinates.get(2), (Double)coordinates.get(3)), vertical, left));
	    	}
	    }
	}
