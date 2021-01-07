/*
 * Kalvin Kao & Brandom Shim
 * Jan 17th 2013
 * 
 * This class sets up the screen and frame.
 */

package Game;

import java.awt.*;
import javax.swing.*;

public class Frame extends JFrame {
	//title of frame
	public static String title = "Tower Defense - Kalvin Kao & Brandon Shim";
	
	//size of frame
	public static Dimension size = new Dimension(800, 800);

	
	//sets up frame
	public Frame() {
		setTitle(StartMenu.title);
		setSize(StartMenu.size);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		init();

	}

	//calls Screen class
	public void init() {
		Screen screen = new Screen(this);
		setLayout(new GridLayout(1, 1, 0, 0));
		add(screen);
		setVisible(true);
	}
}