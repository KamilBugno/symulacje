package miss;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
public class DataGenerator {

	@SuppressWarnings("unchecked")
	public void generate() throws IOException{
		JSONArray array = new JSONArray();

		array.add(createObject(true, false, 0.0, 49.0, 50.0, 49.0, false, true));
		array.add(createObject(false, true, 0.0, 50.0, 50.0, 50.0, false, false));
		array.add(createObject(false, true, 53.0, 50.0, 100.0, 50.0, false, false));		
		array.add(createObject(true, false, 50.0, 0.0, 50.0, 50.0, true, true));		
		array.add(createObject(true, false, 50.0, 0.0, 50.0, 50.0, true, false));		
		JSONObject finalObject = new JSONObject();
		finalObject.put("values", array);
		FileWriter file = null;
		try {
			file = new FileWriter("/home/ola/workspace/sym/src/miss/dane.txt");
				file.write(finalObject.toJSONString());

		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			file.flush();
			file.close();
		}		
	}
	@SuppressWarnings("unchecked")
	public JSONObject createObject(boolean in, boolean out, double a, double b, double c, double d, boolean vertical, boolean left){
		JSONObject obj = new JSONObject();
		obj.put("in", in);
		obj.put("out", out);
		JSONArray coordinates = new JSONArray();
		coordinates.add(a);
		coordinates.add(b);
		coordinates.add(c);
		coordinates.add(d);
		obj.put("coordinates", coordinates);
		obj.put("vertical", vertical);
		obj.put("left", left);
		return obj;
	}
	
}
