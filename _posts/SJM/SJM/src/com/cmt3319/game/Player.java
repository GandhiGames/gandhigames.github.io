package com.cmt3319.game;

import android.graphics.RectF;

import com.cmt3319.interfaces.Graphics;
import com.cmt3319.interfaces.Pixmap;

/**
 * Extends {@link MovingObject} and introduces animation. Switches between a number of images
 * depending on if the payer is moving, jumping, dead, or gravity is reversed. {@link Player#draw(Graphics)}
 * animates character before calling {@link MovingObject#draw(Graphics)}.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class Player extends MovingObject{
	private final float ANIMATIONSPEED = 0.2f;
	
	private final int  PLAYERWIDTH = 20;
	
	private float changeImage;
	
	private Pixmap jumpImage;
	
	public Player(float x, float y) {
		super(x, y);
		
		changeImage = 0f;
	}
	
	public void update(float deltaTime) {
		if(isMoving())
			changeImage += deltaTime;
		
		if(isJumping() && isOnGround() && Settings.getInstance().isSoundEnabled())
    		Assets.getInstance().getJump().play(1);
		
		super.update(deltaTime);
	}
	
	/**
	  * Alternates between images based on an Animation variable to give the impression that the character
	  * is moving. Changes image if player jumps, is dead, or gravity is reversed. 
	  *   
	  */
	public void draw(Graphics g) {

		if(Settings.getInstance().isGravityReversed()) {
			image = Assets.getInstance().getPlayerReversed();
			jumpImage = Assets.getInstance().getPlayerJumpingReversed();
		} else {
			image = Assets.getInstance().getPlayer();
			jumpImage = Assets.getInstance().getPlayerJumping();
		}
		
		if(!isAlive()) {
			g.drawPixmap(Assets.getInstance().getPlayerDead(), position.x, position.y);
		} else if(!isOnGround()) {
			g.drawPixmap(jumpImage, position.x, position.y);
		} else if(isMoving()){
			if (changeImage > ANIMATIONSPEED * 3) {
				g.drawPixmap(image, (int) position.x, (int)position.y, 78, 0, PLAYERWIDTH, image.getHeight());
				if(changeImage > ANIMATIONSPEED * 4) {
					changeImage = 0f;
				}
			} else if (changeImage > ANIMATIONSPEED * 2) {
				g.drawPixmap(image, (int) position.x, (int)position.y, 26, 0, PLAYERWIDTH, image.getHeight());
				
			} else if(changeImage > ANIMATIONSPEED) {
				g.drawPixmap(image, (int) position.x, (int)position.y, 78, 0, PLAYERWIDTH, image.getHeight());
	
			}  else {
				g.drawPixmap(image, (int)position.x, (int)position.y, 0, 0, PLAYERWIDTH, image.getHeight());
			}
		} else {
			g.drawPixmap(image, (int) position.x, (int)position.y, 78, 0, PLAYERWIDTH, image.getHeight());
		}
		
	
	
	}
	
	public RectF getBoundingBox() {		
		return new RectF(desiredPosition.x, 
				desiredPosition.y, desiredPosition.x + PLAYERWIDTH, 
				desiredPosition.y + image.getHeight());

	}
	

	public boolean isOnScreen(float screenWidth) {
		return position.x < screenWidth * 1.05f;
	}

	public void setPositionX(int x) {
		position.x = x;
	}
	
	public float getPositionX() {
		return position.x;
	}
}
