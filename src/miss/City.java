package miss;

import java.util.ArrayList;
import java.util.List;

public class City {

	private List<Crossing> crossings = new ArrayList<Crossing>();
	private static final City instance = new City();
	private City(){		
	}
	public static City getInstance(){
		return instance;
	}
	public void addCrossing(Crossing crossing){
		System.out.println("aaaa(((((((((((((((((((((((((((((((((((((((((((((((((((((((((((a");
		crossings.add(crossing);
	}
	public List<Crossing> getCrossings(){
		return crossings;
	}
}
