package com.cmt3319.game;

import com.cmt3319.interfaces.Graphics;
import com.cmt3319.interfaces.Pixmap;

/**
 * Draws a cloud on screen and provides necessary methods to move across the screen, and to
 * check if it is still on screen. Instantiated and controlled by {@link CloudManager}.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class Cloud {
	private final int MOVEMENTAMOUNT = 180;
	
	private Pixmap image;
	private int x, y;
	
	public Cloud(Pixmap image, int x, int y) {
		this.image = image;
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics g) {
		g.drawPixmap(image, x, y);
	}
	
	 /**
	  * Moves cloud across screen by {@link Cloud#MOVEMENTAMOUNT} * deltaTime. Invoked
	  * by {@link CloudManager}.
	  * 
	  * @param deltaTime provides processor independent movement.
     */
	public void update(float deltaTime) {
		x-= MOVEMENTAMOUNT * deltaTime;
	}
	
	/**
	  * Moves cloud across screen by {@link Cloud#MOVEMENTAMOUNT} * deltaTime.
	  * 
	  * @return returns true if {@link Cloud#x} is less than screenWidth + the width of the image i.e. on screen.
    */
	public Boolean isOnScreen(float screenWidth) {
		return x < screenWidth + image.getWidth();
	}

}
