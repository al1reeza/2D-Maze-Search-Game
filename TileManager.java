package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNumber[][];
	
	// Constructor for our TileManager class
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[10];
		mapTileNumber = new int[gp.maxWorldCols][gp.maxWorldRows];
		getTileImage();
		loadMap("/maps/map.txt");
	}
	
	/*
	 * This method will add the appropriate number to the mapTileNumber 2D array
	 * based on what the map text file says.
	 */
	public void loadMap(String filePath) {
		try {
			InputStream iS= getClass().getResourceAsStream(filePath);
			BufferedReader bR = new BufferedReader(new InputStreamReader(iS));
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxWorldCols && row < gp.maxWorldRows) {
				String line = bR.readLine();
				while(col < gp.maxWorldCols) {
					
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNumber[col][row] = num;
					col++;
				}
				if(col == gp.maxWorldCols) {
					col = 0;
					row++;
				}
			}
			bR.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getTileImage() {
		try {
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream(
					"/tiles/grass01.png"));
			tile[0].collision = false;
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream(
					"/tiles/wall.png"));
			tile[1].collision = true;
					
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream(
					"/tiles/water00.png"));
			tile[2].collision = true;
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream(
					"/tiles/earth.png"));
			tile[3].collision = false;
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream(
					"/tiles/tree.png"));
			tile[4].collision = true;
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream(
					"/tiles/sand.png"));
			tile[5].collision = false;
			
			tile[6] = new Tile();
			tile[6].image = ImageIO.read(getClass().getResource("/tiles/hut.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		int worldCol = 0;
		int worldRow = 0;
		
		// Goes as long as we are within the maxScreenCols and maxScreenRows
		while(worldCol < gp.maxWorldCols && worldRow < gp.maxWorldRows) {
			int tileNum = mapTileNumber[worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			// Draws the first row
			
			// Checks to make sure we only draw what we can see and not the whole map
			if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
		   	   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			}
			worldCol++;
			// Figures out when we are done with the first row and resets for the next one
			if(worldCol == gp.maxWorldCols) {
				worldCol = 0;
				worldRow++;
			}
		}
	}
}
