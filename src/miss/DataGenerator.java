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

		array.add(createObject(1,-1, 1, false, true, 0.0, 49.0, 48.0, 49.0, false, true)); // droga a
		array.add(createObject(1,-1, 2, true, false, 0.0, 50.0, 48.0, 50.0, false, false)); // droga a
		
		array.add(createObject(1, 4, 3, false, true, 51.0, 49.0, 75.0, 49.0, false, true)); // droga i
		array.add(createObject(1, 4, 4, true, false, 51.0, 50.0, 75.0, 50.0, false, false)); // droga i
		
		array.add(createObject(4, -1, 5, true, false, 77.0, 49.0, 100.0, 49.0, false, true)); // droga h
		array.add(createObject(4, -1, 6, false, true, 77.0, 50.0, 100.0, 50.0, false, false)); // droga h
		
		array.add(createObject(2, 3, 7, true, false, 51.0, 21.0, 75.0, 21.0, false, true)); // droga d
		array.add(createObject(2, 3, 8, false, true, 51.0, 23.0, 75.0, 23.0, false, false)); // droga d
		
		array.add(createObject(3, -1, 9, true, false, 77.0, 21.0, 100.0, 21.0, false, true)); // droga g
		array.add(createObject(3, -1, 10, false, true, 77.0, 23.0, 100.0, 23.0, false, false)); // droga g
		
		array.add(createObject(2, -1, 11, false, true, 52.0, 0.0, 52.0, 20.0, true, true)); // droga c ///zmiana 4->3
		array.add(createObject(2, -1, 12, true, false, 50.0, 0.0, 50.0, 20.0, true, false)); // droga c ///zmiana 4->3
		
		array.add(createObject(1, 2, 13, true, false, 52.0, 21.0, 52.0, 47.0, true, true)); // droga b ///zmiana 4->3
		array.add(createObject(1, 2, 14, false, true, 50.0, 21.0, 50.0, 47.0, true, false)); // droga b ///zmiana 4->3
		
		array.add(createObject(3, -1, 15, false, true, 79.0, 0.0, 79.0, 20.0, true, true)); // droga f ///zmiana 5->4
		array.add(createObject(3, -1, 16, true, false, 77.0, 0.0, 77.0, 20.0, true, false)); // droga f ///zmiana 5->4
		
		array.add(createObject(4, 3, 17, true, false, 79.0, 21.0, 79.0, 47.0, true, true)); // droga e ///zmiana 5->4
		array.add(createObject(4, 3, 18, false, true, 77.0, 21.0, 77.0, 47.0, true, false)); // droga e ///zmiana 5->4
		
		array.add(createObject(0, -1, 19, false, true, 0.0, 73.0, 48.0, 73.0, false, true)); // droga k
		array.add(createObject(0, -1, 20, true, false, 0.0, 75.0, 48.0, 75.0, false, false)); // droga k
		
		array.add(createObject(0, -1, 21, true, false, 51.0, 73.0, 100.0, 73.0, false, true)); // droga m
		array.add(createObject(0, -1, 22, false, true, 51.0, 75.0, 100.0, 75.0, false, false)); // droga m
		
		array.add(createObject(0, 1, 23, false, true, 52.0, 50.0, 52.0, 70.0, true, true)); // droga j
		array.add(createObject(0, 1, 24, true, false, 50.0, 50.0, 50.0, 70.0, true, false)); // droga j
		
		array.add(createObject(0, -1, 25, true, false, 52.0, 73.0, 52.0, 100.0, true, true)); // droga l
		array.add(createObject(0, -1, 26, false, true, 50.0, 73.0, 50.0, 100.0, true, false)); // droga l
		JSONObject finalObject = new JSONObject();
		finalObject.put("values", array);
		FileWriter file = null;
		try {
			file = new FileWriter("D:\\projekt symulacje\\src\\miss\\dane.txt");
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
	public JSONObject createObject(int crossingId, int secondCrossingId, int roadId, boolean in, boolean out, double a, double b, double c, double d, boolean vertical, boolean left){
		JSONObject obj = new JSONObject();
		obj.put("crossingId", crossingId);
		obj.put("secondCrossingId", secondCrossingId);
		obj.put("roadId", roadId);
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
