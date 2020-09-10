import javax.swing.ImageIcon;

public class ProvCapital extends City {
	
	private String province;
	
	public ProvCapital(String name, int population, int cityX, int cityY, String province) {
		super(name, population, cityX, cityY);
		this.province = province;
		setMarker(new ImageIcon("marker_prov.png"));
	}
	
	public String toString () {
		return this.getName() + " (" + province + ")";
	}

}
