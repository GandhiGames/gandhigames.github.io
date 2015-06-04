package com.cmt3319.game;

import java.util.List;

import android.graphics.Color;

import com.cmt3319.interfaces.Game;
import com.cmt3319.interfaces.Graphics;
import com.cmt3319.interfaces.Pixmap;
import com.cmt3319.interfaces.Screen;
import com.cmt3319.interfaces.Input.TouchEvent;

/**
 * Seconds help screen, provides information on items. Displays image 
 * on screen with information, and has once instances of {@link Arrow} for transitioning back to
 * {@link HelpScreenOne}. Extends {@link Screen}.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class HelpScreenTwo extends Screen  {
	
	private Arrow backArrow;
	private Pixmap image, heading;

	public HelpScreenTwo(Game game) {
		super(game);
		
		backArrow = new Arrow(game, true);
		
		image = Assets.getInstance().getHelpScreenTwo();
		heading = Assets.getInstance().getHelpScreenTwoHeading();
	}

	 /**
     * Registers touch events with
     * {@link HelpScreenTwo#backArrow}. Backwards arrow touch event loads
     * {@link HelpScreenOne}. 
     */
	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents(); 
		game.getInput().getKeyEvents();
		
		int len = touchEvents.size(); 
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i); 
			if (event.type == TouchEvent.TOUCH_UP) {
				if(backArrow.checkSelected(event)) {
					game.setScreen(new HelpScreenOne(game));
				}
			}
		}
		
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		
		g.clear(Color.WHITE);
		
		g.drawPixmap(image, 0, g.getHeight() / 2 - (image.getHeight()/2));
		
		g.drawPixmap(heading, 
				(g.getWidth() * 0.5f) - (heading.getWidth() / 2), 
				g.getHeight() * 0.05f);
		
	
		
		backArrow.present(g);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
