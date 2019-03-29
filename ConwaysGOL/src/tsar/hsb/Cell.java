package tsar.hsb;

import java.awt.Color;

public class Cell {

	private int xPos, yPos, numNeighbours;
	private boolean alive;
	private Color cellColor, deathColor = Color.DARK_GRAY, aliveColor = Color.YELLOW;

	public Cell(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.alive = false;
		this.numNeighbours = 0;
		this.cellColor = deathColor;
	}

	public int getXPosition() {
		return this.xPos;
	}

	public int getYPosition() {
		return this.yPos;
	}

	public void setAliveColor(Color c) {
		this.aliveColor = c;
	}

	public Color getCellColor() {
		return this.cellColor;
	}

	public boolean isAlive() {
		return this.alive;
	}

	public void setNumNeighbours(int num) {
		this.numNeighbours = num;
	}

	public int getNumNeighbours() {
		return this.numNeighbours;
	}

	public void selfRegulate() {
		if (isAlive()) {
			if (getNumNeighbours() == 2 || getNumNeighbours() == 3) {
				birthCell();
			} else {
				killCell();
			}
		} else {
			if (getNumNeighbours() == 3) {
				birthCell();
			}
		}
	}

	public void killCell() {
		this.alive = false;
		this.cellColor = deathColor;
	}

	public void birthCell() {
		this.alive = true;
		this.cellColor = aliveColor;
	}

}