package com.cmt3319.game;

import java.util.ArrayList;

import android.graphics.PointF;
import android.graphics.RectF;

import com.cmt3319.game.Tile.TileID;
import com.cmt3319.interfaces.Graphics;
import com.cmt3319.interfaces.Pixmap;

/**
 * Creates and draws world based on {@link TileSet} and {@link Tile} and is responsible
 * for dealing with collisions with tilemaps and {@link Player}. 
 * 
 * Iterates through tileset received from {@link TileSet#getTileSet()} and adds tiles to list
 * based on number in array. Translates world to give sense of player movement.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class Map {

	private final int WORLDMOVEMENT = 180, MAPHALFWAY = 18;
	
	private ArrayList<Tile> allTiles;
	private ArrayList <MovingObject> enemies;

	private TileSet tileSet;

	private float screenWidth;

	public Map(float screenWidth) {
		enemies = new ArrayList<MovingObject>();

		allTiles = new ArrayList<Tile>();

		tileSet = new TileSet();

		this.screenWidth = screenWidth;
		createWorld();
	}

	 /**
	  * Called from the constructor used to load the initial tilemap into {@link Map#allTiles}. Receives tile map
	  * from {@link TileSet#getTileSet()}, iterates through tile map and stores location, where
	  * x = i % 40, and y = Math.floor(tileArray.length - 1 / 40. This places the first item 
	  * in the array in the bottom left position on screen, the second to the right of this, etc.
	  * 
	  * Based on the math's from: 
	  * 	http://gamedevelopment.tutsplus.com/tutorials/an-introduction-to-creating-a-tile-map-engine--gamedev-10900
	  * 
	  *  The tiles are added to {@link Map#allTiles} and when i = the middle, {@link Tile#setMiddleTile(Boolean)} is
	  *  set to true. When a tile is removed from the list (because it is off the screen), this variable is
	  *  checked and if it equals true, it signifies that a new tile map is required. This is performed in
	  *  {@link Map#updateWorldLocation(float, Score)}.
	  *  
	  */
	private void createWorld() {
		Pixmap wall = Assets.getInstance().getWall();
		Pixmap hazard = Assets.getInstance().getHazard();
		Pixmap collectible = Assets.getInstance().getCollectible();
		Pixmap reverseGravity = Assets.getInstance().getReverseGravity();
		Pixmap enemy = Assets.getInstance().getEnemy();
		Pixmap enemyReversed = Assets.getInstance().getEnemyReversed();

		int[] tileArray = tileSet.getTileSet();

		int w = 40;
		int b = tileArray.length-1;
		for (int i = 0; i < tileArray.length; i++){
			
			int x = i  % w;
			int y = (int) Math.floor(b / w);
			b--;
			switch (tileArray[i]) {
			case 0:
			
				Tile tile = new Tile(wall, x , y, TileID.WALL);

				allTiles.add(tile);
				if (i == MAPHALFWAY) {
					tile.setMiddleTile(true);
				}

				break;

			case 1:
	
				Tile hazardTile = new Tile(hazard,  x , y, TileID.HAZARD);

				allTiles.add(hazardTile);
				if (i == MAPHALFWAY) {
					hazardTile.setMiddleTile(true);
				}

				break;
			case 2:

				Tile collectibleTile = new Tile(collectible, x , y, TileID.COLLECTIBLE);

				allTiles.add(collectibleTile);
				if(i == MAPHALFWAY) {
					collectibleTile.setMiddleTile(true);
				}

				break;

			case 4:
			
				Tile reverseGravityTile = new Tile(reverseGravity, x, y, TileID.GRAVITY);

				allTiles.add(reverseGravityTile);
				if(i == MAPHALFWAY) {
					reverseGravityTile.setMiddleTile(true);
				}

				break;

			case 5:

				MovingObject enemyObject = new MovingObject(enemy, enemyReversed, x  * enemy.getWidth(), 
						y * enemy.getHeight());
				enemyObject.setJumping(true);
				enemies.add(enemyObject);

				break;
			default:
				break;
			}
		}
	}

	/**
	  * Similar to {@link Map#createWorld()} this laods a tile map. This is called whenever a tile with
	  * {@link Tile#isMiddleTile()} = true is removed from the game. Rather than set the position to the screen:
	  * x = x + screenWidth is used. This loads the tile map off screen so the player does not see the transition.
	  *  
	  */
	private void addPart() {
		Pixmap wall = Assets.getInstance().getWall();
		Pixmap hazard = Assets.getInstance().getHazard();
		Pixmap collectible = Assets.getInstance().getCollectible();
		Pixmap reverseGravity = Assets.getInstance().getReverseGravity();
		Pixmap enemy = Assets.getInstance().getEnemy();
		Pixmap enemyReversed = Assets.getInstance().getEnemyReversed();

		
		int[] tileArray = tileSet.getTileSet();

		int w = 40;
		int b = tileArray.length-1;
		
		for (int i = 0; i < tileArray.length; i++){
			int x = i % w;
			int y = (int) Math.floor(b / w);
			b--;
			switch (tileArray[i]) {
			case 0:

				Tile tile = new Tile(wall, (int) (x + screenWidth / wall.getWidth()), y, TileID.WALL);

				allTiles.add(tile);
				if (i == MAPHALFWAY) {
					tile.setMiddleTile(true);
				}

				break;

			case 1:

				Tile hazardTile = new Tile(hazard,  (int) (x + screenWidth / hazard.getWidth()), y, TileID.HAZARD);

				allTiles.add(hazardTile);
				if (i == MAPHALFWAY) {
					hazardTile.setMiddleTile(true);
				}
				break;

			case 2:

				Tile collectibleTile = new Tile(collectible, 
						(int) (x + screenWidth / collectible.getWidth()), y, TileID.COLLECTIBLE);

				allTiles.add(collectibleTile);
				if(i == MAPHALFWAY) {
					collectibleTile.setMiddleTile(true);
				}
				break;

			case 4:

				Tile reverseGravityTile = new Tile(reverseGravity, 
						(int) (x + screenWidth / reverseGravity.getWidth()), y, TileID.GRAVITY);

				allTiles.add(reverseGravityTile);
				if(i == MAPHALFWAY) {
					reverseGravityTile.setMiddleTile(true);
				}

				break;

			case 5:

				MovingObject enemyObject = new MovingObject(enemy, 
						enemyReversed, (x + screenWidth / enemy.getWidth()) * enemy.getWidth() , y * enemy.getHeight());
				enemyObject.setJumping(true);
				enemies.add(enemyObject);

				break;
			default:
				break;
			}
		}
	}

	/**
	  * Checks collisions between two objects based on bounding boxes. If object two
	  * is instance of {@link Player} then the player is dead. This methods prevents two enemies
	  * from overlapping with each other or checks if a player has hit and enemy.	
	  *   
	  */
	public Boolean checkCollisionBetweenObjects(MovingObject one, MovingObject two) {

		
		if(RectF.intersects(one.getBoundingBox(), two.getBoundingBox())){
			if(two instanceof Player) {
				two.setAlive(false);
			}
			
			if(Settings.getInstance().isSoundEnabled()) {
				Assets.getInstance().getHurt().play(2);
			}
			
			
			return true;
		}

		return false;
	}

	/**
	  * Iterates through {@link Map#allTiles} and {@link Map#enemies} and invokes their draw methods.	 	
	  *   
	  */
	public void drawWorld(Graphics g) {
		for(int i = 0; i < allTiles.size(); i++) {
			allTiles.get(i).Draw(g);
		}

		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
	}

	/**
	  * Calculates the overlap between two objects, on the y plane, as a float.
	  * This is used when a player collides with a wall to make sure they do not
	  * go through the wall/floor.It provides a float that can be added to a players
	  * y position so they are no longer intersecting the object.
	  * 
	  * @param one bounding box of object one.
	  * @param two bounding box of object two.	 	
	  *   
	  */
	private float calculateYOverlap(RectF one, RectF two) {
		float y11 = one.top;
		float y12 = one.bottom;

		float y21 = two.top;
		float y22 = two.bottom;

		return Math.max(0, Math.min(y12,y22) - Math.max(y11,y21));
	}

	/**
	  * Calculates the overlap between two objects, on the x plane, as a float.
	  * See {@link Map#calculateYOverlap(RectF, RectF)} for more details.	
	  *   
	  */
	private float calculateXOverlap(RectF one, RectF two) {
		float x11 = one.left;
		float x12 = one.right;

		float x21 = two.left;
		float x22 = two.right;

		return Math.max(0, Math.min(x12,x22) - Math.max(x11,x21));
	}
	
	/**
	  * Checks players collision with reverse gravity tiles. Iterates through
	  *  {@link Map#allTiles} and checks tile id, if the id = {@link TileID#GRAVITY}
	  *  and the tile is on screen, then the intersection is tested. If the objects intersect then
	  *  invoke {@link Settings#reverseGravity()}, remove tile from {@link Map#allTiles} so it is
	  *  not updated or drawn, and return. Reverse gravity is checked in the {@link Player#update(float)}
	  *  before a force is applied.	
	  *   
	  */
	public void checkPlayerCollisionWithReverseGravity(MovingObject player) {

		for (int i = 0; i < allTiles.size(); i++){
			Tile tile = allTiles.get(i);

			if(tile.isOnScreen(screenWidth) && tile.getID() == TileID.GRAVITY) {
				if(RectF.intersects(tile.getBoundingBox(), player.getBoundingBox())){
					Settings.getInstance().reverseGravity();
					allTiles.remove(tile);
					return;
				}
			}
		}
	}

	/**
	  * Checks players collision with floor tiles when gravity is reversed. Iterates through
	  *  {@link Map#allTiles} and checks tile id, if the id = {@link TileID#WALL}
	  *  and the tile is on screen, then the intersection is tested. If the objects intersect then:
	  *  
	  *  <pre>
	  *  
	  *  if(tile below player) {
	  *  	check intersection and move player up until not intersecting; 
	  *  	(uses {@link Map#calculateYOverlap(RectF, RectF)} and {@link Player#setDesiredPosition(PointF)})
	  *  	Set y velocity to zero (prevents gravity from pushing player through floor)
	  *  	Set {@link Player#isOnGround()} to true.
	  *  } else if(tile to right of player) {
	  *  	check intersection and move player up until not intersecting; 
	  *  	(uses {@link Map#calculateXOverlap(RectF, RectF)} and {@link Player#setDesiredPosition(PointF)})
	  *  } else if(tile above player) {
	  *  	check intersection and move player up until not intersecting; 
	  *  	(uses {@link Map#calculateYOverlap(RectF, RectF)} and {@link Player#setDesiredPosition(PointF)})
	  *  }
	  *  
	  *  </pre>
	  *   
	  *   Collisions with tiles to the right of the player are not tested because the world is moving to the left
	  *   (giving the impression of the player moving right) and there is no capability for the player to move left.
	  */
	private void checkFloorCollisionReversedGravity(MovingObject object) {
		for (int i = 0; i < allTiles.size(); i++){
			Tile tile = allTiles.get(i);

			if(tile.isOnScreen(screenWidth) && tile.getID() == TileID.WALL) {
				float tileX = tile.getBoundingBox().centerX();
				float tileY = tile.getBoundingBox().centerY();

				float playerX = object.getBoundingBox().centerX();
				float playerY = object.getBoundingBox().centerY();

				if(RectF.intersects(tile.getBoundingBox(), object.getBoundingBox())){

					if(tileY < playerY) { //below

						object.setDesiredPosition(new PointF(object.getDesiredPosition().x, 
								object.getDesiredPosition().y + 
								calculateYOverlap(object.getBoundingBox(), tile.getBoundingBox())));

						object.setVelocity(new PointF(object.getVelovity().x, 0f));
						object.setOnGround(true);

					}  else if (tileX > playerX) { //right
						object.setDesiredPosition(new PointF(object.getDesiredPosition().x +
								calculateXOverlap(object.getBoundingBox(), tile.getBoundingBox()), 
								object.getDesiredPosition().y));


					} else if (tileY > playerY) { //above
						object.setDesiredPosition(new PointF(object.getDesiredPosition().x, object.getDesiredPosition().y -
								calculateYOverlap(object.getBoundingBox(), tile.getBoundingBox())));

					}

				}
			}
		} 
	}

	/**
	  * Handles collisions with wall tiles in normal gravity. See {@link Map#checkFloorCollisionReversedGravity(MovingObject)}
	  * for more information.	 	
	  *   
	  */
	private void checkFloorCollisionNormalGravity(MovingObject object) {

		for (int i = 0; i < allTiles.size(); i++){
			Tile tile = allTiles.get(i);

			if(tile.isOnScreen(screenWidth) && tile.getID() == TileID.WALL) {
				float tileX = tile.getBoundingBox().centerX();
				float tileY = tile.getBoundingBox().centerY();

				float playerX = object.getBoundingBox().centerX();
				float playerY = object.getBoundingBox().centerY();
				
				if(RectF.intersects(tile.getBoundingBox(), object.getBoundingBox())){

					if(tileY > playerY) { 

						object.setDesiredPosition(new PointF(object.getDesiredPosition().x, 
								object.getDesiredPosition().y - 
								calculateYOverlap(object.getBoundingBox(), tile.getBoundingBox())));

						object.setVelocity(new PointF(object.getVelovity().x, 0f));
						object.setOnGround(true);

					} else if (tileX > playerX) { //right
						object.setDesiredPosition(new PointF(object.getDesiredPosition().x +
								calculateXOverlap(object.getBoundingBox(), tile.getBoundingBox()), 
								object.getDesiredPosition().y));

					} else if (tileY < playerY) { 
						object.setDesiredPosition(new PointF(object.getDesiredPosition().x, object.getDesiredPosition().y +
								calculateYOverlap(object.getBoundingBox(), tile.getBoundingBox())));

					}

				}
			}
		} 
	}
	
	/** 	
	  *  Invoked by {@link GamePlayScreen#update(float)}, calls {@link Map#checkFloorCollisionNormalGravity(MovingObject)}
	  *  or {@link Map#checkFloorCollisionReversedGravity(MovingObject)} based on {@link Settings#isGravityReversed()}. 
	  */
	public void checkObjectCollisionWithFloor(MovingObject object) {

		object.setOnGround(false);

		if(!Settings.getInstance().isGravityReversed()) {
			checkFloorCollisionNormalGravity(object);
		} else {
			checkFloorCollisionReversedGravity(object);
		}

		object.setPositionToDesired();

	}

	/**
	  * Checks players collision with hazards. Iterates through
	  *  {@link Map#allTiles} and checks tile id, if the id = {@link TileID#HAZARD}
	  *  and the tile is on screen, then the intersection is tested. If the objects intersect then
	  *  invoke {@link Player#setAlive(boolean)} is set to false.
	  *   
	  */
	public Boolean checkPlayerCollisionWithHazard(MovingObject player) {
		for (int i = 0; i < allTiles.size(); i++){
			Tile tile = allTiles.get(i);

			if(tile.isOnScreen(screenWidth) && tile.getID() == TileID.HAZARD){
				if(RectF.intersects(tile.getBoundingBox(), player.getBoundingBox())){
					player.setAlive(false);

					if(Settings.getInstance().isSoundEnabled()) {
						Assets.getInstance().getHurt().play(2);
					}

					player.setPositionToDesired();

					return true;
				}
			}

		}

		return false;

	}

	/**
	  * Moves the world to the left based on the constant Movement_Amount * deltaTime.
	  * Iterates through {@link Map#allTiles} and {@link Map#enemies} and decreases their
	  * positional x value. Checks if they are on screen and removes them from list if they are not. 
	  * Checks if {@link Tile#isMiddleTile()} is true when removing a tile. If it is, then the middle of the
	  * map is off screen and a new tile map is required to be loaded therefore {@link Map#addPart()} is called.  
	  */
	public void updateWorldLocation(float deltaTime, Score score) {	
		Boolean loadNew = false;

		float movementAmount = WORLDMOVEMENT * deltaTime;

		//tickTime += deltaTime;

		for (int i = 0; i < allTiles.size(); i++){
			allTiles.get(i).decreaseX(movementAmount);

			if(!allTiles.get(i).isOnScreen(screenWidth)) {
				if(allTiles.get(i).isMiddleTile()) {
					loadNew = true;
				}

				allTiles.remove(i);
			}
		}

		for(int i = 0; i < enemies.size(); i++) {
			MovingObject enemy = enemies.get(i);
			enemy.decreaseX(movementAmount);

			if (enemy.getPosition().x < 0) {
				enemies.remove(enemy);

			}

		}

		if(movementAmount > 1) {
			score.increaseScore(1);
		}

		if (loadNew) {
			addPart();
		}




	}

	/**
	  * Checks players collision with collectible tiles. Iterates through
	  *  {@link Map#allTiles} and checks tile id, if the id = {@link TileID#COLLECTIBLE}
	  *  and the tile is on screen, then the intersection is tested. If the objects intersect then
	  *  the tile is removed from {@link Map#allTiles} so it is
	  *  not updated or drawn, and players score is increased by 100. 
	  *   
	  */
	public void checkCollectibleCollision(Score score, MovingObject player) {
		for (int i = 0; i < allTiles.size(); i++) {
			Tile tile = allTiles.get(i);

			if(tile.isOnScreen(screenWidth) && tile.getID() == TileID.COLLECTIBLE) {
				if(RectF.intersects(tile.getBoundingBox(), player.getBoundingBox())){
					allTiles.remove(i);
					score.increaseScore(100);

					if(Settings.getInstance().isSoundEnabled())
						Assets.getInstance().getCollectibleSound().play(1);

					return;
				}
			}
		}
	}

	public ArrayList<MovingObject> getEnemies() {
		return enemies;
	}

	

	
}
