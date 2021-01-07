/*
 * Kalvin Kao & Brandom Shim
 * Jan 17th 2013
 * 
 * This class sets up the playerspace/map dimension and every tile size.
 */

package Game;

import java.awt.*;

public class Room {
	public int worldWidth = 12; // width of map
	public int worldHeight = 12; // height of map
	public int blockSize = 52; // size of each block

	public Block[][] block;

	public Room() {

		block = new Block[worldHeight][worldWidth];

		for (int y = 0; y < block.length; y++) {
			for (int x = 0; x < block[0].length; x++) {
				block[y][x] = new Block((Screen.myWidth / 2)
						- ((worldWidth * blockSize) / 2) + (x * blockSize), y
						* blockSize, blockSize, blockSize, Value.groundGrass,
						Value.airAir);
			}
		}
	}

	public void physics() {
		for (int y = 0; y < block.length; y++) {
			for (int x = 0; x < block[0].length; x++) {
				block[y][x].physics();
			}
		}
	}

	public void draw(Graphics g) {
		for (int y = 0; y < block.length; y++) {
			for (int x = 0; x < block[0].length; x++) {
				block[y][x].draw(g);
			}
		}
		for (int y = 0; y < block.length; y++) {
			for (int x = 0; x < block[0].length; x++) {
				block[y][x].fight(g);
			}
		}
	}
}
