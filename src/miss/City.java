package miss;

import java.util.ArrayList;
import java.util.List;

public class City {

	public List<Crossing> crossings = new ArrayList<Crossing>();
	private static final City instance = new City();
	private City(){		
	}
	public static City getInstance(){
		return instance;
	}
	public void addCrossing(Crossing crossing){
		crossings.add(crossing);
	}
	// tak sobie mysle, że lepszym pomysłem było trzymanie tego jako grafu,
	// bo jeżeli będziemy chcieli szukać jakiejś drogi z punktu A do punktu B 
	// to znalezienie drogi na grafie będzie proste, ale myślę, że to można spokojnie
	// zmienić później, a teraz wystarczy to po prostu jako lista skrzyżowań. 
	

	
}
