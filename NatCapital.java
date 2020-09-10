import javax.swing.ImageIcon;

public class NatCapital extends City {
	
	public NatCapital (String name, int population, int cityX, int cityY) {
		super(name, population, cityX, cityY);
		setMarker(new ImageIcon("marker_nat.png"));
	}
	
	public String toString () {
		return this.getName() + " (Canada's capital)";
	}

}
