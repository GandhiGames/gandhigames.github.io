package com.cmt3319.game;

import com.cmt3319.interfaces.Game;
import com.cmt3319.interfaces.Graphics;
import com.cmt3319.interfaces.Input.TouchEvent;
import com.cmt3319.interfaces.Pixmap;

/**
 * Places a selectable arrow on screen. Used to transition between screens. The arrow
 * can be placed in the bottom left or bottom right.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class Arrow {
	
	private Pixmap image;
	
	private int imageX, imageY;

	 /**
     * Constructor.
     * 
     * @param isBackArrow the position and arrow image used is based on the variable. If it is
     * a back arrow then the arrow is placed in the bottom left of the screen facng backwards. Forward
     * arrow is bottom right, facing forward.
     */
	public Arrow(Game game, boolean isBackArrow) {
		
		
		if(isBackArrow) {
			image = Assets.getInstance().getBackArrow();
			imageX = (int) (0 + (image.getWidth() * 0.5f));
			
		} else {
			image = Assets.getInstance().getForwardArrow();
			imageX = (int) (game.getGraphics().getWidth() - (image.getWidth() * 1.5f));
		}
		
		imageY = (int) (game.getGraphics().getHeight() - (image.getHeight() * 1.1f)); 
	}
	
	 /**
     * Invoked within the update method of screens with an arrow. Checks if arrow has been selected.
     * 
     * @param event the touch event
     * @return true if {@link TouchEvent} is within the bounds of the arrow.
     */
	public boolean checkSelected(TouchEvent event) {
		if (Assets.getInstance().inBounds(event, imageX, imageY, 
				image.getWidth(), image.getHeight())) {
			if(Settings.getInstance().isSoundEnabled()) {
				Assets.getInstance().getMenuSelect().play(2);
			}
			
			return true;
		}
		
		return false;
	}
	
	 
	/**
     * Draws arrow using positional variables {@link Arrow#imageX} and {@link Arrow#imageY}. These
     * are set in the constructors and depends on if the arrow is a forwards or backwards arrow.
     */
	public void present(Graphics g) {
		g.drawPixmap(image, imageX, imageY);
		
	}
	
	
	

}
