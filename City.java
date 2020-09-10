import javax.swing.ImageIcon;

public class City {
	
	private String name;
	private int population;
	private int x;
	private int y;
	private ImageIcon marker;
	
	public City (String name, int population, int cityX, int cityY) {
		this.name = name;
		this.population = population;
		this.x = cityX;
		this.y = cityY;
		
		marker = new ImageIcon("marker_city.png");
	}
	
	public String getName () {
		return name;
	}
	
	public int getPopulation() {
		return population;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	
	public ImageIcon getMarker() {
		return marker;
	}
	
	public void setName (String name) {
		this.name = name;
	}
	
	public void setPopulation(int population) {
		this.population = population;
	}
	
	public void setMarker(ImageIcon icon) {
		marker = icon;
	}
	
	
	public boolean equals (City otherCity) {
		if ((Math.abs(this.getX() - otherCity.getX())) < 5 && (Math.abs(this.getY() - otherCity.getY()) < 5)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String toString () {
		return name;
	}
}
