package main;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

public class GamePanel extends JPanel implements Runnable {
	// SCREEN SETTINGS
	final int originalTileSize = 16; // 16x16 
	final int scale = 3;
	public final int tileSize = originalTileSize * scale; // 48x48 tile
	
	public final int maxScreenCols = 16;
	public final int maxScreenRows = 12;
	public final int screenWidth = maxScreenCols * tileSize; // 768 pixels
	public final int screenHeight = maxScreenRows * tileSize; // 576 pixels
	
	// WORLD SETTINGS
	public final int maxWorldCols = 50;
	public final int maxWorldRows = 50;
	public final int worldWidth = maxWorldCols * tileSize;
	public final int worldHeight = maxWorldRows * tileSize;
	
	//FPS for our game
	int FPS = 60;
	
	Thread gameThread;
	KeyHandler keyH = new KeyHandler();
	
	public Player player = new Player(this, keyH);
	
	TileManager tileM = new TileManager(this);
	
	public boolean gameIsDone = false;
	
	public CollisionChecker cChecker = new CollisionChecker(this);
	
	// Constructor for our GamePanel
	public GamePanel() {
		
		this.setPreferredSize(new Dimension (screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); // For smoothness
		this.addKeyListener(keyH);
		this.setFocusable(true); // Allows our GamePanel to receive keyBoard inputs
		
	}
	
	// This method starts our Thread, gameThread
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
		
	}

	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS; // Draw screen 60 times per second
											  // The 10^9 is becasue our time is in nano-seconds
		// Shows us the time in which we have to update the screen again
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		while(gameThread != null) {
			this.update(); 
			repaint(); // Used to call paintComponent
			double remainingTime = nextDrawTime - System.nanoTime();
			try {
				if(remainingTime < 0) {
					remainingTime = 0;
				}
				remainingTime = remainingTime / 1000000;
				Thread.sleep((long) remainingTime);
				nextDrawTime += drawInterval;
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
		
	}
	
	// Updates the information like character position
	public void update() {
		player.update();
	}
	
	// Draw the Screen with the updated information
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g); // Paints the background
		Graphics2D g2 = (Graphics2D) g; // Casting g to Graphics2D
		tileM.draw(g2);
		player.draw(g2);
		g2.dispose(); // To save some memory
	}
	
	public void gameOver(Player player) {
		int playerCol = player.worldX / tileSize;
		int playerRow = player.worldY / tileSize;
		
		if(tileM.mapTileNumber[playerCol][playerRow] == 6) {
			JOptionPane.showMessageDialog(null, "YOU FOUND MY HOUSE", "WELL DONE", JOptionPane.INFORMATION_MESSAGE);;
			System.exit(0);
		}
	}
}
