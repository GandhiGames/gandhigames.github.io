package com.cmt3319.game;

import android.graphics.RectF;

import com.cmt3319.interfaces.Graphics;
import com.cmt3319.interfaces.Pixmap;


/**
 * Represents a tile on screen. Holds the tiles id {@link Tile#tileID} i.e. its type,
 * the tiles image, and its location on screen. Provides methods to draw tiles (invoked by {@link Map}),
 * check if a tile is on screen (used to remove unnecessary tiles), and to check if a tile is in the middle. 
 * This is used to determine when to load a new tilemap i.e. when the middle tile is off screen. 
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class Tile {
	public enum TileID {WALL, HAZARD, COLLECTIBLE, GRAVITY}
	
	private Pixmap image;
	
	private int x, y;
	
	private Boolean middleTile;
	
	private TileID tileID;
	 
	public Tile(Pixmap image, int x, int y, TileID id) {
		middleTile = false;
		this.image = image;
		this.x = x * image.getWidth();
		this.y = y * image.getHeight();
		
		this.tileID = id;
	}
	
	public void Draw(Graphics g) {
		g.drawPixmap(image, x, y);
	}

	public RectF getBoundingBox() {
		return new RectF(x, y, x + image.getWidth(), y + image.getHeight());
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getX() {
		return x;
	}
	
	
	public void decreaseX(float amount) {
		x-= (int) amount;
	}
	
	public Boolean isOnScreen(float screenWidth) {
		return x > 0 - image.getWidth();
	}
	
	public Boolean isTimeToRemove(float screenWidth) {
		return x > (screenWidth * 2);
	}

	public Boolean isMiddleTile() {
		return middleTile;
	}

	public void setMiddleTile(Boolean middleTile) {
		this.middleTile = middleTile;
	}
	
	public TileID getID(){
		return tileID;
	}
	
}
