package main;

import entity.Entity;

public class CollisionChecker {
	GamePanel gp;
	
	// Constructor for CollisionChecker class
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	public void checkTile(Entity entity) {
		int entityLeftX = entity.worldX + entity.solidArea.x;
		int entityRightX = entity.worldX + entity.solidArea.width + entity.solidArea.x;
		int entityTopY = entity.worldY + entity.solidArea.y;
		int entityBottomY = entity.worldY + entity.solidArea.height + entity.solidArea.y;
		
		int entityLeftCol = entityLeftX / gp.tileSize;
		int entityRightCol = entityRightX / gp.tileSize;
		int entityTopRow = entityTopY / gp.tileSize;
		int entityBottomRow = entityBottomY / gp.tileSize;
		
		int tile1, tile2;
		
		switch(entity.direction){
			case "up":
				tile1 = gp.tileM.mapTileNumber[entityLeftCol][entityTopRow];
				tile2 = gp.tileM.mapTileNumber[entityRightCol][entityTopRow];
				if(gp.tileM.tile[tile1].collision == true ||
						gp.tileM.tile[tile2].collision == true) {
					entity.collisionOn = true;
				}
				break;
			case "down":
				tile1 = gp.tileM.mapTileNumber[entityLeftCol][entityBottomRow];
				tile2 = gp.tileM.mapTileNumber[entityRightCol][entityBottomRow];
				if(gp.tileM.tile[tile1].collision == true ||
						gp.tileM.tile[tile2].collision == true) {
					entity.collisionOn = true;
				}
				break;
			case "left":
				tile1 = gp.tileM.mapTileNumber[entityLeftCol][entityBottomRow];
				tile2 = gp.tileM.mapTileNumber[entityLeftCol][entityTopRow];
				if(gp.tileM.tile[tile1].collision == true ||
						gp.tileM.tile[tile2].collision == true) {
					entity.collisionOn = true;
				}
				break;
			case"right":
				tile1 = gp.tileM.mapTileNumber[entityRightCol][entityTopRow];
				tile2 = gp.tileM.mapTileNumber[entityRightCol][entityBottomRow];
				if(gp.tileM.tile[tile1].collision == true ||
						gp.tileM.tile[tile2].collision == true) {
					entity.collisionOn = true;
				}
				break;
		}
	}
}
