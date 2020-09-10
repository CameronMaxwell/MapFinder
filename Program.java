
public class Program {
	
	private static City[] cityArray; // Not allowed ANY OTHER variables (to deter using an array counter).

	public Program (boolean showMap) {

		cityArray = new City[5];
		
		MyFileReader reader = new MyFileReader("cities.txt");
		
		reader.readString(); // First line of headers.
		
		String cityName, type, province;
		int pop, locX, locY;
		City city = null;
		int cityIndex = 0;
		
		while (!reader.endOfFile()) {
			cityName = reader.readString();
			pop = reader.readInt();
			locX = reader.readInt();
			locY = reader.readInt();
			type = reader.readString();
		
			if (type.equals("nat_cap")) {
				city = new NatCapital(cityName, pop, locX, locY);
			} else if (type.equals("prov_cap")) {
				province = reader.readString();
				city = new ProvCapital(cityName, pop, locX, locY, province);
			} else if (type.equals("city")) {
				city = new City(cityName, pop, locX, locY);
			}
			
			if (cityIndex == cityArray.length - 1) {
				expandCapacity();
			}

			cityArray[cityIndex++] = city;
		}
		
		// Now that cities are loaded, calculate the stats and display them.
		if (showMap) {
			Map canadaMap = new Map();
			canadaMap.defaultText();
			for (int c = 0; c < cityIndex; c++) {
				canadaMap.addCity(cityArray[c]);
			}
		}
	}
	
	public City[] getCityList () {
		return cityArray;
	}
	
	private void expandCapacity () {
		City[] largerArray = new City[cityArray.length + 5];
		
		int i;
		for (i = 0; i < cityArray.length; i++) {
			largerArray[i] = cityArray[i];
		}
		
		cityArray = largerArray;
		
	}
	
	public static City[] findCitiesInRect (int sx, int sy, int ex, int ey) {
		// no arraylists or other data structures.
		
		City[] results = new City[cityArray.length];
		City city;
		int resIndex = 0;
		
		int i;
		for (i = 0; i < cityArray.length; i++) {
			city = cityArray[i];
			
			if (city == null) {
				continue;
			}
			
			// Within rectangle bounds.
			int x = city.getX();
			int y = city.getY();
			int minX = Math.min(sx, ex);
			int maxX = Math.max(sx, ex);
			int minY = Math.min(sy, ey);
			int maxY = Math.max(sy, ey);
			if ((minX < x && x < maxX) && (minY < y && y < maxY)) {
				results[resIndex++] = city;
			}
			
			// check if city's x and y are within the bounds of the selection.
		}
		
		
		return results;
	}
	
	/**
	 * Calculates the average, min, and max population of capitals (prov and nat) and of other cities.
	 */
	public static Object[] defaultTextboxInfo () {
		Object[] results = new Object[10];
		int c;
		int pop;
		int sumCapPop = 0;
		int numCapCities = 0;
		int minCapPopIndex = 0;
		int minCapPopValue = Integer.MAX_VALUE;
		int maxCapPopIndex = 0;
		int maxCapPopValue = Integer.MIN_VALUE;
		int sumNonPop = 0;
		int numNonCities = 0;
		int minNonPopIndex = 0;
		int minNonPopValue = Integer.MAX_VALUE;
		int maxNonPopIndex = 0;
		int maxNonPopValue = Integer.MIN_VALUE;
		for (c = 0; c < cityArray.length; c++) {
			if (cityArray[c] != null) {
				pop = cityArray[c].getPopulation(); // Get city population.
				
				if (cityArray[c] instanceof ProvCapital || cityArray[c] instanceof NatCapital) {
					sumCapPop += pop;
					numCapCities++;
					if (cityArray[c].getPopulation() < minCapPopValue) {
						minCapPopValue = pop;
						minCapPopIndex = c;
					}
					if (cityArray[c].getPopulation() > maxCapPopValue) {
						maxCapPopValue = pop;
						maxCapPopIndex = c;
					}
				} else if (cityArray[c] instanceof City) {
					sumNonPop += pop;
					numNonCities++;
					if (pop < minNonPopValue) {
						minNonPopValue = pop;
						minNonPopIndex = c;
					}
					if (pop > maxNonPopValue) {
						maxNonPopValue = pop;
						maxNonPopIndex = c;
					}
					
				}
				
			}
		}

		results[0] = ((double)sumCapPop/(double)numCapCities);
		results[1] = minCapPopValue;
		results[2] = cityArray[minCapPopIndex];
		results[3] = maxCapPopValue;
		results[4] = cityArray[maxCapPopIndex];
		
		results[5] = ((double)sumNonPop/(double)numNonCities);
		results[6] = minNonPopValue;
		results[7] = cityArray[minNonPopIndex];
		results[8] = maxNonPopValue;
		results[9] = cityArray[maxNonPopIndex];

		return results;
	}
	
	

	
	public static void main(String[] args) {
		new Program(true);
	}
	
}
