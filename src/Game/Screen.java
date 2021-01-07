/*
 * Kalvin Kao & Brandom Shim
 * Dec 19th 2012
 * 
 * Screen is the JPanel placed on the Frame, and displays all screens and manipulates some variables such as coins/health.
 */

package Game;

import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;

public class Screen extends JPanel implements Runnable {
	public Thread thread = new Thread(this);

	// Tilesets
	public static Image[] tileset_ground = new Image[100];
	public static Image[] tileset_air = new Image[100];
	public static Image[] tileset_res = new Image[100];
	public static Image[] tileset_mob = new Image[100];

	public static int myWidth, myHeight;
	public static int coins = 30, health = 20; // amount of coins and health
	public static int killed = 0, killsToWin = 0, level = 1, maxlevel = 2;
	public static int winTime = 4000, winFrame = 0;

	public static boolean isFirst = true;
	public static boolean isDebug = false; // debugging toggle
	public static boolean isWin = false; // checks for win
	public static Point mse = new Point(0, 0); // x & y points

	public static Room room;
	public static Save save;
	public static Store store;
	public static Mob[] mobs = new Mob[100];

	public Screen(Frame frame) {
		// adding listeners
		frame.addMouseListener(new KeyHandel());
		frame.addMouseMotionListener(new KeyHandel());
		thread.start();
	}

	public static void hasWon() {//checks if they won and sets true
		if (killed == killsToWin) {
			isWin = true;
			killed = 0;
			coins = 0;
		}
	}

	public void define() {
		room = new Room();
		save = new Save();
		store = new Store();

		coins = 30; //coins for each level start

		// Import images
		for (int i = 0; i < tileset_ground.length; i++) {
			tileset_ground[i] = new ImageIcon("res/tileset_ground.png")
					.getImage();
			tileset_ground[i] = createImage(new FilteredImageSource(
					tileset_ground[i].getSource(), new CropImageFilter(0,
							26 * i, 26, 26)));
		}
		for (int i = 0; i < tileset_air.length; i++) {
			tileset_air[i] = new ImageIcon("res/tileset_air.png").getImage();
			tileset_air[i] = createImage(new FilteredImageSource(
					tileset_air[i].getSource(), new CropImageFilter(0, 26 * i,
							26, 26)));
		}

		// Import images
		tileset_res[0] = new ImageIcon("res/cell.png").getImage();
		tileset_res[1] = new ImageIcon("res/heart.png").getImage();
		tileset_res[2] = new ImageIcon("res/coin.png").getImage();

		tileset_mob[0] = new ImageIcon("res/mob1.png").getImage();

		// Loads the pathing/levels
		save.loadSave(new File("save/level" + level + ".level"));

		for (int i = 0; i < mobs.length; i++) {
			mobs[i] = new Mob();
		}
	}

	public void paintComponent(Graphics g) {
		if (isFirst) {
			myWidth = getWidth();
			myHeight = getHeight();
			define();

			isFirst = false;
		}

		// Sets background colour to blue
		g.setColor(new Color(10, 90, 160));

		// Fills the background
		g.fillRect(0, 0, getWidth(), getHeight());

		// Colour of the lines
		g.setColor(new Color(0, 0, 0));

		// Drawing Border lines

		// Drawing the left line
		g.drawLine(room.block[0][0].x - 1, 0, room.block[0][0].x - 1,
				room.block[room.worldHeight - 1][0].y + room.blockSize + 1);

		// Drawing the right line
		g.drawLine(room.block[0][room.worldWidth - 1].x + room.blockSize, 0,
				room.block[0][room.worldWidth - 1].x + room.blockSize,
				room.block[room.worldHeight - 1][0].y + room.blockSize + 1);

		// Drawing the bottom line
		g.drawLine(room.block[0][0].x, room.block[room.worldHeight - 1][0].y
				+ room.blockSize, room.block[0][room.worldWidth - 1].x
				+ room.blockSize, room.block[room.worldHeight - 1][0].y
				+ room.blockSize);

		room.draw(g); // Drawing the room.

		for (int i = 0; i < mobs.length; i++) {
			if (mobs[i].inGame) {
				mobs[i].draw(g);
			}
		}

		store.draw(g); // Drawing the store.

		// Game over screen
		if (health < 1) {
			g.setColor(new Color(0, 0, 0));
			g.fillRect(0, 0, myWidth, myHeight);
			g.setColor(new Color(255, 255, 255));
			g.setFont(new Font("Arial", Font.BOLD, 30));
			g.drawString("Game Over", 220, 400);
		}
		if (isWin) { //winning level screen
			g.setColor(new Color(70, 200, 70));
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(new Color(0, 0, 0));
			g.setFont(new Font("Arial", Font.BOLD, 30));
			if (level == maxlevel) {
				g.drawString("You have won the game!", 250, 400);
			} else {
				g.drawString(
						"You have won level 1. Please wait for the next level.",
						50, 400);

			}
		}
	}

	// mob spawn time
	public int spawnTime = 800, spawnFrame = 0;

	public void mobSpawner() { // Spawns mobs
		if (spawnFrame >= spawnTime) {
			for (int i = 0; i < mobs.length; i++) {
				if (!mobs[i].inGame) {
					mobs[i].spawnMob(Value.mobRed);
					break;
				}
			}
			spawnFrame = 0;
		} else {
			spawnFrame += 1;
		}
	}

	public void run() { // runs the methods for mobs movement
		while (true) {
			if (!isFirst && health > 0 && !isWin) {
				room.physics();
				mobSpawner(); // Calls the mob spawn method
				for (int i = 0; i < mobs.length; i++) {
					if (mobs[i].inGame) {
						mobs[i].physics();
					}
				}
			} else {
				if (isWin) {
					if (winFrame >= winTime) { //keeps win frame open for this long
						if (level == maxlevel) { //checks if the level is the max and exits ( won the game)
							System.exit(0);
						} else {
							level += 1; //increases level after winning
							define();
							isWin = false;
						}
						winFrame = 0;
					} else {
						winFrame += 1;
					}

				}
			}
			repaint();

			try {
				Thread.sleep(1);
			} catch (Exception e) {
			}
		}
	}
}
