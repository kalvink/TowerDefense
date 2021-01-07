/* TOWER DEFENSE GAME - START HERE
 * HOW TO PLAY: Tower Defense game consists of having to prevent enemies from reaching the 
 * 				player's base by using towers with fixed ranges to attack the enemies. 
 * 		        The game ends when 20 enemies have reached the player's base. In order
 *	            to win you will have to kill a certain amount of enemies before you run out of 20
 *	       		lives.
 *
 * Kalvin Kao
 * Jan 17th 2013
 */

package Game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StartMenu extends JFrame implements ActionListener {
	// panel
	JPanel pnl = new JPanel();
	// buttons
	private JButton start;
	// title of frame
	public static String title = "Tower Defense - Kalvin Kao";
	// size of frame
	public static Dimension size = new Dimension(800, 800);
	// jmenu items
	JMenuItem about, exit, mute, t1, t2, t3, how;
	// option for dialog
	int option;

	// Loads the music
	ClassLoader load1 = this.getClass().getClassLoader();
	java.applet.AudioClip audio1 = JApplet.newAudioClip(load1
			.getResource("gm.wav"));
	ClassLoader load2 = this.getClass().getClassLoader();
	java.applet.AudioClip audio2 = JApplet.newAudioClip(load2
			.getResource("mgs.wav"));
	ClassLoader load3 = this.getClass().getClassLoader();
	java.applet.AudioClip audio3 = JApplet.newAudioClip(load3
			.getResource("rw.wav"));

	// Constructor
	public StartMenu() {
		init();
	}

	public void init() {
		// setting up the frame
		setTitle(title);
		setSize(size);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		pnl.setOpaque(false);
		pnl.setLayout(null);

		/*
		 * MENU BAR
		 */

		// Buttons on Menu Bar
		JMenu menu = new JMenu("Main Menu");
		JMenu options = new JMenu("Options");
		JMenu inst = new JMenu("Instructions");

		JMenuBar mBar = new JMenuBar();
		mBar.add(menu);
		mBar.add(options);
		mBar.add(inst);
		setJMenuBar(mBar);

		// Pop up Buttons
		about = new JMenuItem("About");
		exit = new JMenuItem("Exit");
		t1 = new JMenuItem("Play Track 1");
		t2 = new JMenuItem("Play Track 2");
		t3 = new JMenuItem("Play Track 3");
		mute = new JMenuItem("Stop Music");
		how = new JMenuItem("How to Play");

		// Adds the Menu items
		menu.add(about);
		menu.add(exit);
		options.add(mute);
		options.add(t1);
		options.add(t2);
		options.add(t3);
		inst.add(how);

		// Menu Bar ActionListeners
		about.addActionListener(this);
		exit.addActionListener(this);
		mute.addActionListener(this);
		t1.addActionListener(this);
		t2.addActionListener(this);
		t3.addActionListener(this);
		how.addActionListener(this);

		/*
		 * MENU BAR END
		 */

		// Buttons of Panel
		start = new JButton("Start"); // Start button
		start.setBounds(225, 550, 350, 55);
		start.addActionListener(this);
		pnl.add(start);

		add(pnl);
		setVisible(true);
		playSound();
	}

	// plays sound on start
	public void playSound() {
		new Thread(new Runnable() {

			public void run() {
				audio1.loop();
			}
		}).start();
	}

	public void actionPerformed(ActionEvent event) {

		// About
		if (event.getActionCommand().equals("About")) {
			JOptionPane.showMessageDialog(pnl,
					"Tower Defense\n Created By: Kalvin Kao",
					"About", JOptionPane.WARNING_MESSAGE);
		}
		// Exit Game
		if (event.getSource() == exit) {
			option = askMessage("Are you sure you want to exit?", "Exit Game",
					JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_OPTION)
				System.exit(0);
		}
		// Play Track 1
		if (event.getActionCommand().equals("Play Track 1")) {
			audio2.stop();
			audio3.stop();
			audio1.loop();
		}
		// Play Track 2
		if (event.getActionCommand().equals("Play Track 2")) {
			audio1.stop();
			audio3.stop();
			audio2.loop();
		}
		// Play Track 3
		if (event.getActionCommand().equals("Play Track 3")) {
			audio1.stop();
			audio2.stop();
			audio3.loop();
		}
		// Stop Music
		if (event.getActionCommand().equals("Stop Music")) {
			audio1.stop();
			audio2.stop();
			audio3.stop();
		}
		// How to Play
		if (event.getActionCommand().equals("How to Play")) {
			JOptionPane
					.showMessageDialog(
							pnl,
							"Tower Defense game consists of having to prevent enemies from \nreaching the "
									+ "player's base by using towers with fixed ranges to\n attack the enemies. "
									+ "The game ends when 20 enemies have reached\n the player's base. In order "
									+ "to win you will have to kill a certain amount\n of enemies before you run out of 20 "
									+ "lives.", "How to Play",
							JOptionPane.QUESTION_MESSAGE);
		}
		// Start Game
		if (event.getSource() == start) {
			setVisible(false);
			Frame frame = new Frame();

		}
	}

	// setting background image
	public static void main(String[] args) {
		StartMenu menu = new StartMenu();
		ImagePanel panel = new ImagePanel(new ImageIcon("bg.png").getImage());
		menu.getContentPane().add(panel);
		menu.pack();
		menu.setVisible(true);

	}

	// method for dialog
	public int askMessage(String msg, String tle, int op) {
		return JOptionPane.showConfirmDialog(null, msg, tle, op);

	}

}

// configuration for background
class ImagePanel extends JPanel {
	private Image img;

	public ImagePanel(String img) {
		this(new ImageIcon(img).getImage());
	}

	public ImagePanel(Image img) {
		this.img = img;
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}
}
