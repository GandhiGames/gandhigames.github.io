package com.cmt3319.game;

import android.graphics.PointF;
import android.graphics.RectF;

import com.cmt3319.game.Tile.TileID;
import com.cmt3319.interfaces.Graphics;
import com.cmt3319.interfaces.Pixmap;

/**
 * Represents the player and enemies in the game. Provides physics based movement 
 * (see {@link MovingObject#update(float)} and a
 * draw method.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class MovingObject {
	private final float MAX_GRAVITY = 400, MAX_JUMP = 300, MAX_JUMP_CUT_OFF = 120f;

	
	protected Pixmap image;
	private Pixmap reversedImage;
	
	protected PointF position, desiredPosition;

	private PointF velocity;
		
	private Boolean isOnGround, isMovingForward, isJumping, isAlive;
	
	public float distanceMoved;
	
	private float gravity, jump, jumpCutOff;
	
	public MovingObject(float x, float y) {
		isAlive = true;
		
		
		distanceMoved = 0f;
		
		isOnGround = false;
		isMovingForward = true;
		isJumping= false;
		

		position = new PointF(x, y);
		velocity = new PointF(0,0);
		desiredPosition = new PointF(0,0);

	}
	
	public MovingObject(Pixmap image, Pixmap reversedImage, float x, float y) {
		isAlive = true;
		
		distanceMoved = 0f;
		
		isOnGround = false;
		isMovingForward = true;
		isJumping= false;
		
		this.image = image;
		this.reversedImage = reversedImage;
		
		position = new PointF(x, y);
		velocity = new PointF(0,0);
		desiredPosition = new PointF(0,0);

	}
	
	 /**
     * Updates position of object based on gravity and jumpforce.{@link MovingObject#checkGravity()}
     * is called first. After which:
     * 
     * <pre>
     * Create PointF with gravity force as its y value;
     * Create gravityStep by multiplying by deltaTime; 
     * 
     * Add gravityStep to velocity;
     * 
     * Create new PointF with jump force as its y value;
     * 
     * if(player is jumping and is on ground) {
     * 		add jumping force to velocity;
     * } else {
     * 		if(gravity is not reversed) {
     * 			if(player is not jumping and velocity.y > jumpcutoff) {
     * 				set velocity y value to equal jumpCutOff;
     * 			} 
     * 		} else {
     * 			if(player is not jumping and velocity.y < jumpCutOff) {
     * 				set velocity y value to equal jumpCutOff;
     * 			}
     * 		}
     * }
     * 
     * Truncate force to minimum/maximum;
     * 
     * Multiple velocity by delta time and add to players desired position;
     * </pre>
     * 
     * A desired position is used because if the player is intersecting with a wall the players desired position
     * is fixed by {@link Map#checkObjectCollisionWithFloor(MovingObject)} before setting the payers position
     * to equal desired position.
     */
	public void update(float deltaTime) {

		checkGravity();
		
		PointF gravityForce = new PointF(0, gravity);
		PointF gravStep = new PointF(gravityForce.x * deltaTime, gravityForce.y * deltaTime);
		
		//PointF forward = new PointF(-200, 0);
		//PointF forwardStep = new PointF(forward.x * deltaTime, forward.y * deltaTime);
		
		velocity = new PointF((velocity.x + gravStep.x) * 0.9f, velocity.y + gravStep.y);

		PointF jumpForce = new PointF(0, jump);

	    if(isJumping && isOnGround) {
	    	velocity = new PointF(velocity.x + jumpForce.x, velocity.y + jumpForce.y);
	  
	    } else {
	    	if(!Settings.getInstance().isGravityReversed()) {
		    	if (!isJumping && velocity.y > jumpCutOff) {	    
		    		velocity = new PointF(velocity.x, jumpCutOff);
		    	}
	    	} else {
	    		if (!isJumping && velocity.y < jumpCutOff) {	    
		    		velocity = new PointF(velocity.x, jumpCutOff);
		    	}
	    	}
	    }
	    
	    //if(isMovingForward) {
	    //	velocity = new PointF(velocity.x + forwardStep.x, velocity.y + forwardStep.y);
	    //}
	    
	    PointF minMovement = new PointF(0, -450);
	    PointF maxMovement = new PointF(0, 450);
	    
	    velocity = new PointF(0, 
	    		clamp(velocity.y, minMovement.y, maxMovement.y));
	    
	    PointF velocityStep = new PointF(0, velocity.y * deltaTime);
	    		
	    this.desiredPosition = new PointF(position.x, position.y - velocityStep.y);

	}
	
	public void draw(Graphics g) {
		if(Settings.getInstance().isGravityReversed()) {
			g.drawPixmap(reversedImage, position.x, position.y);
		} else {
			g.drawPixmap(image, position.x, position.y);
		}
		
	}
	
	private float clamp(float val, float min, float max) {
	    return Math.max(min, Math.min(max, val));
	}
	
	public RectF getBoundingBox() {
		return new RectF(desiredPosition.x, 
				desiredPosition.y, desiredPosition.x + image.getWidth(), 
				desiredPosition.y + image.getHeight());		
	}

	public PointF getDesiredPosition() {
		return desiredPosition;
	}

	public void setOnGround(Boolean ground) {
		isOnGround = ground;
	}
	
	public Boolean isOnGround() {
		return isOnGround;
	}
	
	public void setDesiredPosition(PointF desired) {
		desiredPosition = desired;
	}
	
	public void setPositionToDesired(float screenWidth) {
		if(desiredPosition.x <  screenWidth * 0.7f) {
			distanceMoved += desiredPosition.x - (screenWidth * 0.7f);
			desiredPosition.x = screenWidth * 0.7f;
		}
		
		this.position = desiredPosition;
	}
	
	public void setPositionToDesired() {
		this.position = desiredPosition;
	}
	
	/**
	  * Invoked by {@link MovingObject#update(float)} before force is applied. Checks if
	  * gravity is reversed in {@link Settings#isGravityReversed()}:
	  * 
	  * <pre>
	  * if(gravity is not reversed) {
	  * 	gravity force = -Max_gravity constant;
	  * 	jump = minus MAX_JUMP constant;
			jumpCutOff = minus MAX_JUMP_CUT_OFF constant;
	  * } else {
	  * 	gravity = MAX_GRAVITY constant;
			jump =  minus MAX_JUMP;
			jumpCutOff = minus MAX_JUMP_CUT_OFF;
	  * }
	  * 
	  * </pre>	 
	  *   
	  */
	public void checkGravity() {
		if(!Settings.getInstance().isGravityReversed()) {
			//set to normal
			gravity = -MAX_GRAVITY;
			jump = MAX_JUMP;
			jumpCutOff = MAX_JUMP_CUT_OFF;
		} else {
			//set to reversed
			gravity = MAX_GRAVITY;
			jump = -MAX_JUMP;
			jumpCutOff = -MAX_JUMP_CUT_OFF;
		}
		
		
	}

	public float getDistanceMoved(){
		return distanceMoved;
	}
	
	public void setDistanceMoved(float distance){
		distanceMoved = distance;
	}
	
	public void setVelocity(PointF velocity) {
		this.velocity = velocity;
	}
	
	public PointF getVelovity() {
		return velocity;
	}
	
	public void setJumping(Boolean jumping) {
		isJumping = jumping;
	}
	
	public void setMoving(Boolean moving) {
		isMovingForward = moving;
	}

	public PointF getPosition() {
		return position;
	}
	
	public Boolean isMoving() {
		return isMovingForward;
	}


	public void decreaseX(float amount) {
		position.x -= amount;
	}

	public void increaseX(float amount) {
		position.x += amount;
		
	}
	
	public void setAlive(boolean alive) {
		isAlive = alive;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public boolean isJumping() {
		return isJumping;
	}
	
}
