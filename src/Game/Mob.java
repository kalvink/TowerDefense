/*
 * Kalvin Kao & Brandom Shim
 * Jan 17th 2012
 *
 *This class manages and creates the mobs, their behaviours and properties.
 */

package Game;

import java.awt.*;

public class Mob extends Rectangle {
	public int xC, yC; // x & y coordinates
	public int health; // health of mobs
	public int healthSpace = 3, healthHeight = 6; // health bar space and height
	public int mobSize = 52; // Size of the mob
	public int mobWalk = 0;
	public int upward = 0, downward = 1, right = 2, left = 3;
	public int direction = right;
	public int mobID = Value.mobAir;
	public boolean inGame = false; // for verifying if mobs are on map
	public boolean hasUpward = false;
	public boolean hasDownward = false;
	public boolean hasLeft = false;
	public boolean hasRight = false;

	public Mob() {

	}

	public void spawnMob(int mobID) { // Spawning mobs

		// searches for stone block
		for (int y = 0; y < Screen.room.block.length; y++) {
			if (Screen.room.block[y][0].groundID == Value.groundStone) {
				setBounds(Screen.room.block[y][0].x, Screen.room.block[y][0].y,
						mobSize, mobSize);
				xC = 0;
				yC = y;
			}
		}

		this.mobID = mobID;
		this.health = mobSize;

		inGame = true;
	}

	public void deleteMob() { // deletes the mob that reach the base
		inGame = false;
		direction = right;
		mobWalk = 0;
		Screen.room.block[0][0].getMoney(mobID);

	}

	public void loseHealth() { // decrease lives for every mob that reach base
		Screen.health -= 1;
	}

	// speed of mobs
	public int walkFrame = 0, walkSpeed = 12;

	public void physics() { // how mobs move through pathing
		if (walkFrame >= walkSpeed) {
			if (direction == right) {
				x += 1;
			} else if (direction == upward) {
				y -= 1;
			} else if (direction == downward) {
				y += 1;
			} else if (direction == left) {
				x -= 1;
			}

			mobWalk += 1;

			if (mobWalk == Screen.room.blockSize) {
				if (direction == right) {
					xC += 1;
					hasRight = true;
				} else if (direction == upward) {
					yC -= 1;
					hasUpward = true;
				} else if (direction == downward) {
					yC += 1;
					hasDownward = true;
				} else if (direction == left) {
					xC -= 1;
					hasLeft = true;
				}

				if (!hasUpward) {
					try {
						if (Screen.room.block[yC + 1][xC].groundID == Value.groundStone) {
							direction = downward;
						}
					} catch (Exception e) {
					}
				}
				if (!hasDownward) {
					try {
						if (Screen.room.block[yC - 1][xC].groundID == Value.groundStone) {
							direction = upward;
						}
					} catch (Exception e) {
					}
				}
				if (!hasLeft) {
					try {
						if (Screen.room.block[yC][xC + 1].groundID == Value.groundStone) {
							direction = right;
						}
					} catch (Exception e) {
					}
				}
				if (!hasRight) {
					try {
						if (Screen.room.block[yC][xC - 1].groundID == Value.groundStone) {
							direction = left;
						}
					} catch (Exception e) {
					}
				}

				if (Screen.room.block[yC][xC].airID == Value.airHouse) {
					deleteMob();
					loseHealth();
				}
				hasUpward = false;
				hasDownward = false;
				hasRight = false;
				hasLeft = false;
				mobWalk = 0;
			}
			walkFrame = 0;

		} else {
			walkFrame += 1;
		}
	}

	//causes the mob to lose health as it is damaged
	public void loseHealth(int amo) {
		health -= amo;
		
		checkDeath();
	}

	public void checkDeath() { //checks if mob health is 0 and deletes
		if (health == 0) {
			deleteMob();
		}

	}

	//checks if the mob is dead
	public boolean isDead() {
		if (inGame) {
			return false;
		} else {
			return true;
		}
	}

	//draws the mob and its health bar
	public void draw(Graphics g) {

		g.drawImage(Screen.tileset_mob[mobID], x, y, width, height, null);

		// Health Bar
		g.setColor(new Color(180, 50, 50));
		g.fillRect(x, y - (healthSpace + healthHeight), width, healthHeight);

		g.setColor(new Color(50, 180, 50)); // colour of health bar
		g.fillRect(x, y - (healthSpace + healthHeight), health, healthHeight);
		g.setColor(new Color(0, 0, 0)); // colour of health bar border
		// draws healthbar border
		g.drawRect(x, y - (healthSpace + healthHeight), health - 1,
				healthHeight - 1);
	}
}
