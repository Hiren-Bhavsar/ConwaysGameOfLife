package tsar.hsb.GUI;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;

import tsar.hsb.Controller;

public class GameFrame extends JFrame {
	private int width, height, cellX, cellY;
	private Controller gameController;
	private JButton[][] buttonArray;
	private JPanel menuPanel, gamePanel;
	private boolean mouseClicked;
	private final int TIME_DELAY = 500;
	private Timer time;

	public GameFrame(int x, int y, int width, int height) {
		this.cellX = x;
		this.cellY = y;
		this.width = width;
		this.height = height;

		menuPanel = new JPanel();
		gamePanel = new JPanel();

		buttonArray = new JButton[x][y];
		gameController = new Controller();
		time = new Timer(TIME_DELAY, timerListener);
	}

	public void startGame() {
		// Start Game
		gameController.startGame(cellX, cellY);

		// Set Up Game JFrame
		this.setLayout(null);
		this.setSize(width, height);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Conway's Game Of Life By: BhavTsar");
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		// Set Up Menu JPanel
		menuPanel.setLayout(new GridLayout(1, 1));
		menuPanel.setSize(this.width, 20);
		menuPanel.setLocation(0, 0);
		menuPanel.setVisible(true);
		menuPanel.setBackground(Color.CYAN);
		this.add(menuPanel);

		// Set Up Game JPanel
		gamePanel.setLayout(new GridLayout(cellX, cellY));
		gamePanel.setSize(this.width, this.height - 40);
		gamePanel.setLocation(-2, 18);
		gamePanel.setVisible(true);
		gamePanel.setBackground(Color.RED);
		this.add(gamePanel);

		// Initialize JMenu
		initializeJMenu();

		// Initialize Button Array
		initializeButtonArray();

		this.revalidate();
		this.repaint();
	}

	private void initializeJMenu() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setVisible(true);
		JMenu file = new JMenu("File");
		JMenu template = new JMenu("Templates");
		JMenuItem exit = new JMenuItem("Exit");
		JMenuItem run = new JMenuItem("Run");
		JMenuItem stop = new JMenuItem("Stop");

		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		run.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				time.start();
			}
		});

		stop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				time.stop();
			}
		});

		file.add(run);
		file.add(stop);
		file.add(exit);
		menuBar.add(file);
		menuBar.add(template);
		menuPanel.add(menuBar);
	}

	private void transferButtonArray() {
		for (int x = 0; x < cellX; x++) {
			for (int y = 0; y < cellY; y++) {
				if (buttonArray[x][y].getBackground() == Color.DARK_GRAY) {
					gameController.setCell(x, y, false);
				}

				if (buttonArray[x][y].getBackground() == Color.YELLOW) {
					gameController.setCell(x, y, true);
				}
			}
		}
	}

	private void getGameArray() {
		for (int x = 0; x < cellX; x++) {
			for (int y = 0; y < cellY; y++) {
				if (gameController.getArray()[x][y].isAlive()) {

					buttonArray[x][y].setBackground(Color.YELLOW);
				} else {
					buttonArray[x][y].setBackground(Color.DARK_GRAY);
				}
			}
		}
		this.revalidate();
		this.repaint();
	}

	private void initializeButtonArray() {
		// Initialize All The Buttons
		for (int y = 0; y < cellY; y++) {
			for (int x = 0; x < cellX; x++) {
				buttonArray[x][y] = new JButton();
				buttonArray[x][y].setEnabled(true);
				buttonArray[x][y].setBackground(Color.DARK_GRAY);
				buttonArray[x][y].addActionListener(buttonListener);
				buttonArray[x][y].setFocusPainted(false);
				buttonArray[x][y].setVisible(true);
				gamePanel.add(buttonArray[x][y]);
			}
		}
	}

	ActionListener timerListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			transferButtonArray();
			gameController.runSimulation();
			getGameArray();
		}
	};

	ActionListener buttonListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton temp = (JButton) e.getSource();
			Color c = temp.getBackground() == Color.DARK_GRAY ? Color.YELLOW
					: temp.getBackground() == Color.YELLOW ? Color.DARK_GRAY : Color.DARK_GRAY;
			temp.setBackground(c);
		}
	};

}
