package com.cmt3319.game;

import java.util.List;

import android.graphics.Color;

import com.cmt3319.interfaces.Game;
import com.cmt3319.interfaces.Graphics;
import com.cmt3319.interfaces.Input.TouchEvent;
import com.cmt3319.interfaces.Pixmap;
import com.cmt3319.interfaces.Screen;

/**
 * Presents users high score loaded from {@link Settings#getHighScore()}.
 * Has once instances of {@link Arrow} for transitioning back to
 * {@link MainMenuScreen}. Extends {@link Screen}.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class HighscoreScreen extends Screen{
	
	private Arrow arrow;
	private Pixmap title;
	
	public HighscoreScreen(Game game){
		super(game);
		
		arrow = new Arrow(game, true);
		
		title = Assets.getInstance().getHighscore();
	}

	 /**
     * Registers touch events with {@link HighscoreScreen#arrow}. 
     * which loads
     * {@link MainMenuScreen} when pressed.
     */
	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents(); 
		game.getInput().getKeyEvents();
		
		int len = touchEvents.size(); 
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i); 
			
			if(arrow.checkSelected(event)) {
				game.setScreen(new MainMenuScreen(game));
			}
		}
		
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		
		g.clear(Color.rgb(75, 125, 250));
		
		arrow.present(g);
		
		drawText(g);
		
		g.drawPixmap(title, g.getWidth() / 2 - (int)(title.getWidth() / 2), 
				(g.getHeight() * 0.15f));
		
		
	}

	private void drawText(Graphics g){

		String stScore = ""+ Settings.getInstance().getHighScore();
		
		int x = g.getWidth() / 2 - stScore.length() * 20 /2;
		int y = (int) (g.getHeight() * 0.5f);
		
		
		int len = stScore.length();
		
		for(int i = 0; i < len; i++) {
			char character = stScore.charAt(i);
			
			if(character == ' ') {
				x+= 20;
				continue;
			}
			
			int srcX = 0;
			

				
				if(character == ' ') {
					x+= 20;
					continue;
				}
				

				int srcWidth = 27;
				
				if(character == '0') {
					srcX = 260;

				} else if (character == '1') {
					srcX = 0;
					srcWidth = 31;
				}else if (character == '2') {
					srcX = 31;
					srcWidth = 29;
				}else if (character == '3') {
					srcX = 63;
					
				}else if (character == '4') {
					srcX = 88;
					srcWidth = 32;
				}else if (character == '5') {
					srcX = 121;
					srcWidth = 28;
				}else if (character == '6') {
					srcX = 150;
					srcWidth = 24;
				}else if (character == '7') {
					srcX = 176;
					srcWidth = 30;
				}else if (character == '8') {
					srcX = 208;

				}else if (character == '9') {
					srcX = 234;
					srcWidth = 25;
				}
			

			g.drawPixmap(Assets.getInstance().getNumbers(), x, y, srcX, 0, srcWidth, 64);
			
			
			x+= (srcWidth +1);
			
		}
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
