import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class Map extends JFrame implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1;

	private static Container contentPane;
	
	private final static int frame_length = 1100; // 1000
	private final static int frame_height = 881; // 781
	private final static int map_length = 1000; // 1000
	private final static int map_height = 781; // 781
	
	private static int WINDOW_LEFT = 7;
	private static int WINDOW_TOP = 30;

	private static int ICON_WIDTH = 16;
	private static int ICON_HEIGHT = 37;
	
	private JLayeredPane mainPanel;
	
	private JLabel selection;
	private boolean isDragging = false;
	private int sx = -1, sy = -1;
	
	private JLabel textbox;

	/* Constructor. Creates a panel to represent the map and destroys
	       the panel when its window is closed.                                 */
	/* -------------------------------------------- */
	public Map() {
	/* -------------------------------------------- */
		super("Map of Canada");

		// Initialize panels.
		contentPane = getContentPane();
		mainPanel = new JLayeredPane();

		// Selection rectangle.
		selection = new JLabel();
		selection.setBackground(Color.blue);
		selection.setBounds(-1, -1, -1, -1);
		mainPanel.add(selection);

		// Textbox.
		textbox = new JLabel("");
		textbox.setVerticalAlignment(SwingConstants.TOP);
		textbox.setLocation(10, 10);
		textbox.setSize(250, 320);
		textbox.setBackground(Color.white);
		textbox.setBorder(BorderFactory.createLineBorder(Color.black));
		textbox.setOpaque(true);
		mainPanel.add(textbox);
		
		// Load Canada map.
		ImageIcon icon = new ImageIcon("canada.jpg");
		Image img = icon.getImage();
		Image scaledImage = img.getScaledInstance(map_length,map_height, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(scaledImage);
		JLabel canadaMap = new JLabel(icon);
		canadaMap.setSize(map_length, map_height);
		canadaMap.setLocation(50, 30);
		mainPanel.add(canadaMap);

		// Event listeners.
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		addWindowListener(new WindowAdapter( ) {
			public void windowClosing(WindowEvent event) {
				System.exit(0);
			}                
		}); 

		// Main panel properties.
		setSize(frame_length, frame_height);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		contentPane.setBackground(new Color(249, 249, 249));
		contentPane.add(mainPanel);
		contentPane.setFocusable(true);
		contentPane.requestFocusInWindow();
		revalidate();
	}

	/**
	 * Add a city marker to the map.
	 * @param city
	 */
	public void addCity (City city) {
		JLabel marker = new JLabel(city.getMarker());
		marker.addMouseListener(this);
		marker.setLocation(city.getX() - (ICON_WIDTH/2), city.getY() - ICON_HEIGHT);
		marker.setSize(ICON_WIDTH, ICON_HEIGHT);
		marker.setName(city.getName());
		marker.setToolTipText(city.getName());
		mainPanel.add(marker, 0);
		refresh();
	}

	/**
	 * Refresh the GUI.
	 */
	public void refresh () {
		revalidate();
		repaint();
	}

	/**
	 * Draw the blue translucent rectangle to indicate the selection.
	 * @param sx
	 * @param sy
	 * @param ex
	 * @param ey
	 */
	public void drawRect (int sx, int sy, int ex, int ey) {

		// Adjust so selections work in any direction.
		int tlx = sx;
		int tly = sy;
		int wdt = ex - sx;
		int hgt = ey - sy;

		if (sx > ex) {
			tlx = ex;
			wdt = sx - ex;
		}
		if (sy > ey) {
			tly = ey;
			hgt = sy - ey;
		}

		selection.setLocation(tlx, tly);
		selection.setSize(wdt, hgt);
		selection.setBackground(new Color(0.0f, 0.2f, 0.5f, 0.5f));
		selection.setOpaque(true);
		selection.setBorder(BorderFactory.createLineBorder(Color.black));
	}

	/**
	 * Capture a mouse click. Print out the city name if a marker is clicked. Reset the selection and textbox.
	 */
	public void mouseClicked(MouseEvent e) {
		// If a marker is clicked, print out the corresponding city name.
		if (e.getSource() instanceof JLabel) {
			JLabel clickedMarker = (JLabel)e.getSource();
			System.out.println(clickedMarker.getName());
		}
		
		isDragging = false;
		drawRect(-1, -1, -1, -1);
		defaultText();
		
	}

	public void mouseEntered(MouseEvent e) {
		// Not applicable.
	}

	public void mouseExited(MouseEvent e) {
		// Not applicable.
	}

	/**
	 * Capture a right-click press for selections.
	 */
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			isDragging = true;
			if (sx == -1 && sy == -1) {
				// First point of rectangle.
				sx = e.getX() - WINDOW_LEFT;
				sy = e.getY() - WINDOW_TOP;
			}
		}
	}

	/**
	 * Capture a mouse release to close the active selection (if there was one).
	 */
	public void mouseReleased(MouseEvent e) {
		isDragging = false;
		sx = -1;
		sy = -1;
	}

	public void mouseMoved(MouseEvent e) {
		// Not applicable.
	}
	
	/**
	 * Capture a mouse drag for updating an active selection.
	 */
	public void mouseDragged(MouseEvent e) {
		if (isDragging) {
			int ex = e.getX() - WINDOW_LEFT;
			int ey = e.getY() - WINDOW_TOP;
			drawRect(sx, sy, ex, ey);
			City[] results = Program.findCitiesInRect(sx, sy, ex, ey);
			selectionText(results);
		}
	}
	
	/**
	 * Display the list of cities in the array. This is used in conjunction with the findCitiesInRect() method in Program
	 * to find and then display the cities contained in the active selection.
	 * @param cities
	 */
	public void selectionText (City[] cities) {
		String str = "SELECTED CITIES<br />";
		int c = 0;
		while (cities[c] != null) {
			str += cities[c] + "<br/>";
			c++;
		}
		displayText(str);
	}
	
	
	/**
	 * Obtain the default population statistics from the Program's defaultTextboxInfo() method
	 * and display them in the upper-left panel.
	 */
	public void defaultText () {
		Object[] results = Program.defaultTextboxInfo();

		String str = "";
		
		str += "CAPITAL CITIES<br />";
		str += "Avg pop: " + results[0] + "<br />";
		str += "Min pop: " + results[1] + " in " + results[2] + "<br />";
		str += "Max pop: " + results[3] + " in " + results[4] + "<br />";
		
		str += "<br />NON-CAPITAL CITIES<br />";
		str += "Avg pop: " + results[5] + "<br />";
		str += "Min pop: " + results[6] + " in " + results[7] + "<br />";
		str += "Max pop: " + results[8] + " in " + results[9] + "<br />";

		displayText(str);
	}
	
	
	
	/**
	 * Display the given text in the upper-left corner textbox panel.
	 * @param text
	 */
	public void displayText (String text) {
		textbox.setText("<html>" + text + "</html>");
	}

}
