package com.cmt3319.game;

import java.util.List;

import android.graphics.Color;

import com.cmt3319.interfaces.Game;
import com.cmt3319.interfaces.Graphics;
import com.cmt3319.interfaces.Input.TouchEvent;
import com.cmt3319.interfaces.Pixmap;
import com.cmt3319.interfaces.Screen;

/**
 * First visible screen, provides users with a number of options including:
 * play game (loads {@link GamePlayScreen}, High scores (loads {@link HighscoreScreen}),
 * and help (loads {@link HelpScreenOne}). Provides ability to toggle audio by invoking
 * {@link Settings#setSoundEnabled(boolean)}. Uses {@link CloudManager} and an instance of
 * {@link Player} to provide an interactive background.
 * 
 * Extends {@link Screen}.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class MainMenuScreen extends Screen{
	
	private Pixmap play, highscore, help, audio, title;
	
	private int playX, playY, highscoreX, highscoreY, helpX, helpY,
	audioX, audioY, titleX, titleY;
	
	private CloudManager cloudManager;
	
	private Player player;
	
	private float screenWidth;
	
	 /**
	  * Initialises play, highscore, help, title, and audio on/off images and positions.
	  * Audio image is dependent on {@link Settings#isSoundEnabled()}.Initialises 
	  * {@link CloudManager} and {@link Player}.
	  */
	public MainMenuScreen(Game game){
		super(game);
		
		Graphics g = game.getGraphics();
		
		play = Assets.getInstance().getPlay();
		playX = g.getWidth() / 2 - (play.getWidth() /2);
		playY = (int) (g.getHeight() * 0.4f - (play.getHeight() /2));
		
		highscore = Assets.getInstance().getHighscore();
		highscoreX = g.getWidth() / 2 - (highscore.getWidth() /2);
		highscoreY = (int) (g.getHeight() * 0.6f - (highscore.getHeight() /2));
		
		help = Assets.getInstance().getHelp();
		helpX = g.getWidth() / 2 - (help.getWidth() /2);
		helpY = (int) (g.getHeight() * 0.8f - (help.getHeight() /2));
		
		title = Assets.getInstance().getTitle();
		titleX = g.getWidth() / 2 - (title.getWidth() /2);
		titleY = (int) (g.getHeight() * 0.2f - (title.getHeight() /2));
		
		if(Settings.getInstance().isSoundEnabled())
			audio = Assets.getInstance().getAudioOn();
		else
			audio = Assets.getInstance().getAudioOff();
		
		screenWidth = game.getGraphics().getWidth();
		
		audioX = (int) (screenWidth - (audio.getWidth() * 1.05f));
		audioY = (int) (game.getGraphics().getHeight() - (audio.getHeight() * 1.05f));
		
		
		int height = game.getGraphics().getHeight();
		
		cloudManager = new CloudManager(screenWidth, height);
		
		player = new Player(0, game.getGraphics().getHeight() * 0.91f);
		player.setOnGround(true);
		player.setMoving(true);
		
	}
	
	private void playSelectedSound() {
		if(Settings.getInstance().isSoundEnabled()){
			Assets.getInstance().getMenuSelect().play(2);
		}
	}

	 /**
	  * Updates cloud manager {@link CloudManager#update(float)}, which moves clouds across screen,
	  * updates player {@link Player#update(float)}, which also moves player across screen, checks if player
	  * is still visible, and if it isn't, it sets player back to start position. Lastly handles touch events
	  * for play, high scores, help screen, and audio on/off.
     */
	@Override
	public void update(float deltaTime) {

		cloudManager.update(deltaTime);
		player.update(deltaTime);
		player.increaseX(80 * deltaTime);
		
		if(!player.isOnScreen(screenWidth)) {
			player.setPositionX(10);
		}
		
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		
		int len = touchEvents.size();
		
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			
			if(event.type == TouchEvent.TOUCH_UP) {
				if(Assets.getInstance().inBounds(event, playX, playY, play.getWidth(), play.getHeight())) {
					playSelectedSound();
					
					game.setScreen(new GamePlayScreen(game));
				} else if (Assets.getInstance().inBounds(event, highscoreX, highscoreY, highscore.getWidth(), highscore.getHeight())) {
					playSelectedSound();
					
					game.setScreen(new HighscoreScreen(game));
				} else if (Assets.getInstance().inBounds(event, helpX, helpY, help.getWidth(), help.getHeight())) {
					playSelectedSound();
					
					game.setScreen(new HelpScreenOne(game));
					
				} else if (Assets.getInstance().inBounds(event, audioX, audioY, audio.getWidth(), audio.getHeight())) {
					
					
					if(Settings.getInstance().isSoundEnabled()) {
						Settings.getInstance().setSoundEnabled(false);
						audio = Assets.getInstance().getAudioOff();
					} else {
						Settings.getInstance().setSoundEnabled(true);
						audio = Assets.getInstance().getAudioOn();
						Assets.getInstance().getMenuSelect().play(2);
						
					}
					
				}
			}
		}
		
	}
	
	

	

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		
		g.clear(Color.rgb(75, 125, 250));
		
		cloudManager.draw(g);
		
		player.draw(g);
		
		g.drawPixmap(play, playX, playY);

		g.drawPixmap(highscore, highscoreX, highscoreY);
		
		g.drawPixmap(help, helpX, helpY);
		
		g.drawPixmap(audio, audioX, audioY);
		
		g.drawPixmap(title, titleX, titleY);
		


	}

	@Override
	public void pause() {
		Settings.getInstance().save(game.getFileIO());
		
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
