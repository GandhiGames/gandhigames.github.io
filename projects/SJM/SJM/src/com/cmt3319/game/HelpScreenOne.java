package com.cmt3319.game;

import java.util.List;

import android.graphics.Color;

import com.cmt3319.interfaces.Game;
import com.cmt3319.interfaces.Graphics;
import com.cmt3319.interfaces.Pixmap;
import com.cmt3319.interfaces.Screen;
import com.cmt3319.interfaces.Input.TouchEvent;

/**
 * First help screen, provides information on controls. Displays image 
 * on screen with information, and has two instances of {@link Arrow}, for transitioning back to
 * {@link MainMenuScreen} or forward to {@link HelpScreenTwo}. Extends {@link Screen}.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class HelpScreenOne extends Screen {
	
	
	private Arrow backArrow, forwardArrow;
	private Pixmap image;
	
	public HelpScreenOne(Game game) {
		super(game);
		

		image = Assets.getInstance().getHelpScreenOne();
	
		backArrow = new Arrow(game, true);
		

		forwardArrow = new Arrow(game, false);
		
	}
	
	 /**
     * Registers touch events with {@link HelpScreenOne#forwardArrow} and
     * {@link HelpScreenOne#backArrow}. Backwards arrow touch event loads
     * {@link MainMenuScreen}, forward arrow loads {@link HelpScreenTwo}.
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
					game.setScreen(new MainMenuScreen(game));
				}
			
				if(forwardArrow.checkSelected(event)) {
					game.setScreen(new HelpScreenTwo(game));
				}

			}
		}
		
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		
		g.clear(Color.rgb(75, 125, 250));
		

		
		g.drawPixmap(image, 0, 0);
		
		backArrow.present(g);
		
		forwardArrow.present(g);
		
		
		
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
