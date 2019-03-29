package tsar.hsb;

public class Controller {

	private Cell[][] cellArray, assistArray;
	private int width, height;

	public Controller() {

	}

	public void startGame(int x, int y) {
		this.width = x;
		this.height = y;

		initializeCellArray();
	}

	private void initializeCellArray() {
		cellArray = new Cell[width][height];
		assistArray = new Cell[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				cellArray[x][y] = new Cell(x, y);
				assistArray[x][y] = new Cell(x, y);
			}
		}
	}

	public void printCellArray() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				System.out.println("X Pos: " + cellArray[x][y].getXPosition() + " Y Pos: "
						+ cellArray[x][y].getYPosition() + " Neigh: " + cellArray[x][y].getNumNeighbours() + " Alive: "
						+ cellArray[x][y].isAlive());
			}
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	private void updateCells() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				cellArray[x][y].setNumNeighbours(countNeighbours(x, y));
			}
		}
	}

	public void runSimulation() {
		updateCells();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				cellArray[x][y].selfRegulate();
			}
		}
	}

	private int countNeighbours(int x, int y) {
		int neighbourCount = 0;

		for (int a = -1; a < 2; a++) {
			for (int b = -1; b < 2; b++) {
				if (a == 0 && b == 0) {

				} else {
					if (((a + x) >= 0 && (a + x) < width) && ((b + y) >= 0 && (b + y) < height)) {
						if (cellArray[a + x][b + y].isAlive()) {
							neighbourCount++;
						}
					}
				}
			}
		}

		return neighbourCount;
	}

	public void setCell(int x, int y, boolean isAlive) {
		if (isAlive) {
			this.cellArray[x][y].birthCell();
		} else {
			this.cellArray[x][y].killCell();
		}
	}

	public Cell[][] getArray() {
		return this.cellArray;
	}

}
