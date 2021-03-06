package miss;

import sim.engine.SimState;
import sim.engine.Steppable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Crossing implements Steppable {

	private List<Road> in = new ArrayList<Road>();
	private List<Road> out = new ArrayList<Road>();
	private Map<Road, Boolean> lightCrossing;
	int index;

	public Crossing(List<Road> in, List<Road> out){
		this.in = in;
		this.out = out;
		lightCrossing = new HashMap<>();
		for (Road road : out){
			lightCrossing.put(road,false);
		}
		if(out.size()>0){
			lightCrossing.put(out.get(0), true);
		}
		index = 0;

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

	@Override
	public void step(SimState simState) {
		//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		int outRoadSize = getOut().size();
		index = (index + 1)%outRoadSize;
		int currentChange = index -1;
		if(currentChange == -1){
			currentChange = outRoadSize - 1;
		}
		lightCrossing.put(out.get(currentChange), false);
		lightCrossing.put(out.get(index), true);
	}

	public Map<Road, Boolean> getLightCrossing() {
		return lightCrossing;
	}

	public void setLightCrossing(Map<Road, Boolean> lightCrossing) {
		this.lightCrossing = lightCrossing;
	}
}
