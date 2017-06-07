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
		System.out.println("jestem w konstruktorze crossing");
		for (Road road : out){
			
			lightCrossing.put(road,true);
		}
		for(Road road: in){
			if(road.isDoubleRoad()){
				lightCrossing.put(road, true);
			}
		}
		if(out.size()>0){
			lightCrossing.remove(out.get(0));
			lightCrossing.put(out.get(0), true);
		}
		index = 0;
		for(Road road: lightCrossing.keySet()){
			System.out.println("id drogi: " + road.getId() + " czy jest double? " + road.isDoubleRoad());
		}
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
		System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSssize: " );
		int outRoadSize = getOut().size();
		index = (index + 1)%outRoadSize;
		int currentChange = index -1;
		if(currentChange == -1){
			currentChange = outRoadSize - 1;
		}
		lightCrossing.put(out.get(currentChange), true);
		lightCrossing.put(out.get(index), true);
		
	}

	public Map<Road, Boolean> getLightCrossing() {
		System.out.println("jestem w get light crossing");
		return lightCrossing;
	}

	public void setLightCrossing(Map<Road, Boolean> lightCrossing) {
		this.lightCrossing = lightCrossing;
	}
	
	public boolean isEmpty(){
		if(getIn().isEmpty() && getOut().isEmpty()){
			System.out.println("empty");
			return true;
		}
		System.out.println(" not empty");
		return false;
	}
}
