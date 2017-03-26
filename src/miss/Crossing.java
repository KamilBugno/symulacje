package miss;

import java.util.ArrayList;
import java.util.List;

public class Crossing {

	public List<Road> in = new ArrayList<Road>();
	public List<Road> out = new ArrayList<Road>();
	
	public Crossing(List<Road> in, List<Road> out){
		this.in = in;
		this.out = out;
	}
	
}
