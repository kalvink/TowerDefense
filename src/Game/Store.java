/*
 * Kalvin Kao & Brandom Shim
 * Jan 17th 2012
 * 
 * This class stores values, checks for user input, and displays some numbers.
 */

package Game;

import java.awt.*;

public class Store {
	public static int shopWidth = 8; // shop size
	public static int buttonSize = 52; // size of the shop buttons
	public static int cellSpace = 10; // space btwn each button
	public static int awayFromRoom = 29; // amount of spaces under the game
	public static int iconSize = 20; // size of icon
	public static int iconSpace = 3; // space of icon
	public static int iconTextY = 15;
	public static int itemIn = 4;
	public static int heldID = -1;
	public static int realID = -1;
	public static int[] buttonID = { Value.airTowerLaser, Value.airAir,
			Value.airAir, Value.airAir, Value.airAir, Value.airAir,
			Value.airAir, Value.airTrashCan };

	// Price of the towers in an array
	public static int[] buttonPrice = { 10, 0, 0, 0, 0, 0, 0, 0 };

	// Rectangles
	public Rectangle[] button = new Rectangle[shopWidth];
	public Rectangle buttonHealth;
	public Rectangle buttonCoins;

	public boolean holdsItem = false;

	public Store() {
		for (int i = 0; i < button.length; i++) {
			button[i] = new Rectangle((Screen.myWidth / 2)
					- ((shopWidth * (buttonSize + cellSpace)) / 2)
					+ ((buttonSize + cellSpace) * i),
					(Screen.room.block[Screen.room.worldHeight - 1][0].y)
							+ Screen.room.blockSize + awayFromRoom, buttonSize,
					buttonSize);
		}

		buttonHealth = new Rectangle(Screen.room.block[0][0].x - 1,
				button[0].y, iconSize, iconSize);
		buttonCoins = new Rectangle(Screen.room.block[0][0].x - 1, button[0].y
				+ button[0].height - iconSize, iconSize, iconSize);
	}

	public void click(int mouseButton) {
		if (mouseButton == 1) {// checks for click
			for (int i = 0; i < button.length; i++) {
				if (button[i].contains(Screen.mse)) {
					if (buttonID[i] != Value.airAir) {
						if (buttonID[i] == Value.airTrashCan) { // Deletes item
							holdsItem = false;
						} else {
							heldID = buttonID[i]; // holds item
							realID = i;
							holdsItem = true;
						}
					}
				}
			}
			if (holdsItem) {
				if (Screen.coins >= buttonPrice[realID]) {
					for (int y = 0; y < Screen.room.block.length; y++) {
						for (int x = 0; x < Screen.room.block[0].length; x++) {
							if (Screen.room.block[y][x].contains(Screen.mse)) {
								if (Screen.room.block[y][x].groundID != Value.groundStone
										&& Screen.room.block[y][x].airID == Value.airAir) {
									Screen.room.block[y][x].airID = heldID;
									Screen.coins -= buttonPrice[realID];
								}
							}

						}
					}
				}
			}
		}
	}

	public void draw(Graphics g) {
		for (int i = 0; i < button.length; i++) {
			if (button[i].contains(Screen.mse)) {
				g.setColor(new Color(255, 255, 255, 100));
				g.fillRect(button[i].x, button[i].y, button[i].width,
						button[i].height);
			}

			g.drawImage(Screen.tileset_res[0], button[i].x, button[i].y,
					button[i].width, button[i].height, null);

			if (buttonID[i] != Value.airAir)
				g.drawImage(Screen.tileset_air[buttonID[i]], button[i].x
						+ itemIn, button[i].y + itemIn, button[i].width
						- (itemIn * 2), button[i].height - (itemIn * 2), null);

			// Show tower costs
			if (buttonPrice[i] > 0) {
				g.setColor(new Color(255, 255, 255));
				g.setFont(new Font("Arial", Font.BOLD, 14));
				g.drawString("$" + buttonPrice[i], button[i].x + itemIn,
						button[i].y + itemIn + 10);

			}
		}

		g.drawImage(Screen.tileset_res[1], buttonHealth.x, buttonHealth.y,
				buttonHealth.width, buttonHealth.height, null);
		g.drawImage(Screen.tileset_res[2], buttonCoins.x, buttonCoins.y,
				buttonCoins.width, buttonCoins.height, null);

		g.setFont(new Font("Arial", Font.BOLD, 14)); // Sets the font

		// Makes the color of the font to white
		g.setColor(new Color(255, 255, 255));

		// Shows # of lives
		g.drawString("" + Screen.health, buttonHealth.x + buttonHealth.width
				+ iconSpace, buttonHealth.y + iconTextY);

		// Shows # of coins
		g.drawString("" + Screen.coins, buttonCoins.x + buttonCoins.width
				+ iconSpace, buttonCoins.y + iconTextY);

		// Shows kills required to win
		g.drawString("Kills required to win: "
				+ (Screen.killsToWin - Screen.killed), buttonCoins.x
				+ buttonCoins.width + iconSpace, buttonCoins.y + 40 + iconTextY);

		// Holding a tower in the mouse
		if (holdsItem) {
			g.drawImage(Screen.tileset_air[heldID], Screen.mse.x
					- ((button[0].width - (itemIn * 2)) / 2) + itemIn,
					Screen.mse.y - ((button[0].width - (itemIn * 2)) / 2)
							+ itemIn, button[0].width - (itemIn * 2),
					button[0].height - (itemIn * 2), null);
		}
	}
}
