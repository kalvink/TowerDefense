/*
 * Kalvin Kao & Brandom Shim
 * Jan 17th 2013
 * 
 * This class sets the numbers that correspond to each type of tile.
 */

package Game;

public class Value { //values for every array element
	public static int groundGrass = 0;
	public static int groundStone = 1;

	public static int airAir = -1;
	public static int airHouse = 0;
	public static int airTrashCan = 1;
	public static int airTowerLaser = 2;

	public static int mobAir = -1;
	public static int mobRed = 0;

	public static int[] deathReward = { 5 }; //gold earned per kill
}
