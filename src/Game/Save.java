/*
 * Kalvin Kao & Brandom Shim
 * Jan 17th 2013
 * 
 * This class reads map files to set up the map and reads the kills required for that level.
 */

package Game;

import java.io.*;
import java.util.*;

public class Save {

	// scans the pathing file
	public void loadSave(File loadPath) {
		try {
			Scanner loadScanner = new Scanner(loadPath);
			while (loadScanner.hasNext()) {
				Screen.killsToWin = loadScanner.nextInt(); //scans the kills required for each level

				for (int y = 0; y < Screen.room.block.length; y++) {
					for (int x = 0; x < Screen.room.block[0].length; x++) {
						Screen.room.block[y][x].groundID = loadScanner
								.nextInt();
					}
				}
				for (int y = 0; y < Screen.room.block.length; y++) {
					for (int x = 0; x < Screen.room.block[0].length; x++) {
						Screen.room.block[y][x].airID = loadScanner.nextInt();
					}
				}
			}
			loadScanner.close();
		} catch (Exception e) {
		}
	}
}
