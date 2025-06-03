package main;

import javax.swing.JFrame;

public class Main {
	public static void main(String args[]) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false); 
		window.setTitle("2D World");

		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		window.pack(); // Sizes the window to fit the contents of gamePanel
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gamePanel.startGameThread();
	}
}
