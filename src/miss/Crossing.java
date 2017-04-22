package miss;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Crossing {

	private List<Road> in = new ArrayList<Road>();
	private List<Road> out = new ArrayList<Road>();

	public Crossing(List<Road> in, List<Road> out){
		this.in = in;
		this.out = out;
	}

	public List<Road> getIn() {
		return in;
	}

	public List<Road> getOut() {
		return out;
	}

	public void addIn(Road roadIn){
		in.add(roadIn);
	}
	public void addOut(Road roadOut){
		out.add(roadOut);
	}

}
