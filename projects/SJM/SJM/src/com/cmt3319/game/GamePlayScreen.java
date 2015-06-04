package com.cmt3319.game;

import java.util.List;
import android.graphics.Color;
import com.cmt3319.interfaces.Game;
import com.cmt3319.interfaces.Graphics;
import com.cmt3319.interfaces.Pixmap;
import com.cmt3319.interfaces.Screen;
import com.cmt3319.interfaces.Input.TouchEvent;

/**
 * Runs and updates game play screen. Includes drawing and updating {@link Map},
 * drawing and updating {@link Player}, drawing and updating {@link Score}, running {@link CloudManager},
 * accepting user input i.e. jump and stop running, and handling pausing and quiting of game using 
 * a game state manager. Extends {@link Screen}.
 *
 * The game state manager has ready, running, paused and gameover states. In the running state the game
 * is being played and the map and player input accepted and updated. The ready state waits for the user
 * to tap on the screen once and then it transistions to the running state. The paused state occurs when the user
 * taps on the pause image whilst in the running state. This provides resume and quit options. Resume goes back to
 * running and quit loads {@link MainMenuScreen}.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class GamePlayScreen extends Screen {
	
	enum GameState {
		Ready,
		Running,
		Paused,
		GameOver
	}
	
	GameState state = GameState.Ready;
	
	private Player player;
	
	private Game game;
	
	private int screenWidth, screenHeight;
	
	private Score score;
	
	private Map map;
	
	private Pixmap pause, resume, quit, gameOver, restart, tapToStart;
	
	private int pauseX, pauseY, resumeX, resumeY, quitX, quitY,
		gameoverX, gameoverY, restartX, restartY;
	
	private CloudManager cloudManager;
	
	 /**
	  * Initialises {@link GamePlayScreen#score}, {@link GamePlayScreen#player}, 
	  * and {@link GamePlayScreen#cloudManager}.Starts background music.
	  */
	public GamePlayScreen(Game game) {
		super(game);
		
		score = new Score();
		
		this.game = game;
		
		screenWidth = game.getGraphics().getWidth();
		screenHeight = game.getGraphics().getHeight();
	
		player = new Player(screenWidth * 0.3f, screenHeight * 0.6f);
	
		map = new Map(screenWidth);
		
		pause = Assets.getInstance().getPause();
		pauseX = 0;
		pauseY = 0;
		
		resume =  Assets.getInstance().getResume();
		resumeX = screenWidth / 2  - (resume.getWidth()/2);
		resumeY = (int) (game.getGraphics().getHeight() * 0.5f);
		
		quit =  Assets.getInstance().getQuit();
		quitX = game.getGraphics().getWidth() / 2 - (quit.getWidth() / 2);
		quitY = (int) (game.getGraphics().getHeight() * 0.7f);
		
		gameOver = Assets.getInstance().getGameOver();
		gameoverX = screenWidth / 2 - (gameOver.getWidth() / 2);
		gameoverY = (int) (screenHeight * 0.3f) - (gameOver.getHeight() / 2);
		
		restart = Assets.getInstance().getRestart();
		restartX = screenWidth / 2 - (restart.getWidth() / 2);
		restartY = (int) (screenHeight * 0.5f);
		
		cloudManager = new CloudManager(screenWidth, screenHeight);
		
		tapToStart = Assets.getInstance().getTapToStart();
		
		Settings.getInstance().setGravityReversed(false);
		
		if(Settings.getInstance().isSoundEnabled())
			Assets.getInstance().getBackground().play();
		
		
	}
	
	 /**
	  * Calls other methods depending on the game state:
	  * <pre>
	  * if(state = ready)
	  * 	invoke {@link GamePlayScreen#updateReady(List)};
	  * else if(state = running)
	  * 	invoke {@link GamePlayScreen#updateRunning(List, float)};
	  * else if(state = paused)
	  * 	invoke {@link GamePlayScreen#updatePause(List)};
	  * else
	  * 	invoke {@link GamePlayScreen#updateGameOver(List)};
	  * 
	  * </pre>
	  * 
	  * The methods are passed a list of touchevents.
	  */
	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();

		if(state== GameState.Ready) {
			updateReady(touchEvents);
		} else if(state == GameState.Running) {
			updateRunning(touchEvents, deltaTime);
		} else if(state == GameState.Paused) {
			updatePause(touchEvents);
		} else if(state == GameState.GameOver) {
			updateGameOver(touchEvents);
		}
	}
	
	
	private void updateReady(List<TouchEvent> touchEvents) {		
		if(touchEvents.size() > 0)
			state = GameState.Running;
	}
	
	private void gameOver() {
		score.saveHighScore();
		state = GameState.GameOver;
	}
	
	 /**
	  * Checks if player is on screen. Used by {@link GamePlayScreen#updateRunning(List, float)}.
	  * 
	  * @return returns true if player is not on screen.
	  */
	private Boolean CheckOutOfBounds() {
		
		return player.getPosition().y > game.getGraphics().getHeight()  ||
				player.getPosition().y < 0 || player.getPosition().x > 
				(game.getGraphics().getWidth() + 10)
				|| player.getPosition().x < 0 -10;
	}
	
	 /**
	  * The main update method for game play:
	  * 
	  * <pre>
	  * 
	  * if({@link GamePlayScreen#CheckOutOfBounds()})
	  * 	set player alive = false;
	  * 	set game state = Game Over;
	  * 	return;
	  * 
	  * if({@link Player#isMoving()()}
	  * 	update world location ({@link Map#updateWorldLocation(float, Score)})
	  * 	update cloud manager ({@link CloudManager#update(float)})
	  * 
	  * update player ({@link Player#update(float)})
	  * 
	  * if(player collides with hazard {@link Map#checkPlayerCollisionWithHazard(MovingObject)})
	  * 	set state to game over;
	  * 	return;
	  * 
	  * Check player collision with reverse gravity; ({@link Map#checkPlayerCollisionWithReverseGravity(MovingObject)})
	  * 
	  * Check player collision with floor/wall; ({@link Map#checkObjectCollisionWithFloor(MovingObject)})
	  * 
	  * Check player collision with collectibles; ({@link Map#checkCollectibleCollision(Score, MovingObject)})
	  * 
	  * for(each enemy in {@link Map#getEnemies()}) {
	  * 	update enemy; ({@link MovingObject#update(float)}
	  * 	check collision with floor; 
	  * 	if(enemy collides with player({@link Map#checkCollisionBetweenObjects(MovingObject, MovingObject)}) {
	  * 		set state to game over;
	  * 		return;
	  * 	}
	  * }
	  * 
	  * Check for input by calling {@link GamePlayScreen#updateRunningInput(List)};
	  * 
	  * </pre>
	  */
	private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
		if(CheckOutOfBounds()) {
			player.setAlive(false);
			gameOver();
			return;
		}
		
		if(player.isMoving()) {
			map.updateWorldLocation(deltaTime, score);
			cloudManager.update(deltaTime);
		}
		
		player.update(deltaTime);
		
		if(map.checkPlayerCollisionWithHazard(player)) {
			gameOver();
			return;
		}
		
		
		map.checkPlayerCollisionWithReverseGravity(player);
		
		map.checkObjectCollisionWithFloor(player);
		
		map.checkCollectibleCollision(score, player);
		
		for (int i = 0; i < map.getEnemies().size(); i++) {
			MovingObject enemy = map.getEnemies().get(i);
		
			enemy.update(deltaTime);
			map.checkObjectCollisionWithFloor(enemy);
			
			if(map.checkCollisionBetweenObjects(enemy, player)) {
				gameOver();
				return;
			}
		}
	
		updateRunningInput(touchEvents);		
	}
	

	 /**
	  * Handles input when game is running in this order:
	  * 
	  * <pre>
	  * for each event:
	  * 	if(event = touch down) {
	  * 		if(paused image has been pressed) {
	  * 			Set game state to paused;
	  * 			return;
	  * 		}
	  * 
	  * 		if(right side of screen pressed) {
	  * 			make player jump; ({@link Player#setJumping(Boolean)})
	  * 		}
	  * 
	  * 		if(left side of screen pressed) {
	  * 			set player moving to false; ({@link Player#setMoving(Boolean)})
	  * 		}
	  * 
	  * 	}
	  * 
	  * 	if(event = touch up) {
	  * 		if(right side of screen had touch removed) {
	  * 			stop player jumping;
	  * 		}
	  * 
	  * 		if(left side of screen had touch removed) {
	  * 			set player moving to true;
	  * 		}
	  * 	}
	  * </pre>
	  */
	private void updateRunningInput(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		
		for(int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			
			if(event.type == TouchEvent.TOUCH_DOWN) {
				if(Assets.getInstance().inBounds(event, pauseX, pauseY, pause.getWidth(), pause.getHeight())) {
					if(Settings.getInstance().isSoundEnabled()) {
						Assets.getInstance().getMenuSelect().play(2);
					}
					state = GameState.Paused;
					return;
				} 
				
				if(event.x >= game.getGraphics().getWidth() / 2) {
					player.setJumping(true);
					player.setMoving(true);
					
				} 
				
				if(event.x < game.getGraphics().getWidth() / 2) {
					player.setMoving(false);
					//player.setJumping(false);
				}
			} 
			
			if (event.type == TouchEvent.TOUCH_UP) {
				
				if(event.x >= game.getGraphics().getWidth() / 2) {
					player.setJumping(false);
					player.setMoving(true);
				} 
				if(event.x < game.getGraphics().getWidth() / 2) {
					player.setMoving(true);
					//player.setJumping(false);
				}
			}
			
		}
	}

	 /**
	  * Checks if resume or quit images are pressed. Resume sets game state to running, and
	  * quit sets {@link MainMenuScreen} as current screen.
	  * 
	  * @param touchEvents passed from {@link GamePlayScreen#update(float)}
	  */
	private void updatePause(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		
		for(int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			
			if(event.type == TouchEvent.TOUCH_UP) {
				if(Assets.getInstance().inBounds(event, resumeX, resumeY, resume.getWidth(), resume.getHeight())) {
					state = GameState.Running;
				} else if(Assets.getInstance().inBounds(event, quitX, quitY, quit.getWidth(), quit.getHeight())) {
					score.saveHighScore();
					game.setScreen(new MainMenuScreen(game));
					return;
				}
			}
		}
	}
	
	 /**
	  * Checks if restart or quit images are pressed. Restart loads a new {@link GamePlayScreen} and sets gravity
	  * to normal, and
	  * quit sets {@link MainMenuScreen} as current screen.
	  * 
	  * @param touchEvents passed from {@link GamePlayScreen#update(float)}
	  */
	private void updateGameOver(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		
		for(int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			
			if(event.type == TouchEvent.TOUCH_UP) {
				if(Assets.getInstance().inBounds(event, quitX, quitY, quit.getWidth(), quit.getHeight())) {
					Settings.getInstance().setGravityReversed(false);
					game.setScreen(new MainMenuScreen(game));
					return;
				} else if(Assets.getInstance().inBounds(event, restartX, restartY, restart.getWidth(), 
						restart.getHeight())) {
					game.setScreen(new GamePlayScreen(game));
					return;
				}
			}
		}
	}
	
	 /**
	  * Invokes {@link CloudManager#draw(Graphics)}, {@link Map#drawWorld(Graphics)},
	  * {@link Player#draw(Graphics)}, and {@link Score#draw(Graphics)} regardless of
	  * game state. Draws "taptostart" image if game state equals ready, draws pause
	  * menu if game state equals running, draws resume and quit images if game state equals paused,
	  * and draws restart and quit images if state equals game over.
	  */
	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		g.clear(Color.rgb(75, 125, 250));
		
		cloudManager.draw(g);
		
		map.drawWorld(g);
		
		player.draw(g);
	
		score.present(g);
		
		if(state == GameState.Ready) {
			g.drawPixmap(tapToStart, screenWidth / 2 - (tapToStart.getWidth() / 2), 
					screenHeight / 2 - (tapToStart.getHeight() / 2));
		}
		
		if(state == GameState.Running) {
			g.drawPixmap(pause, pauseX, pauseY);
		}
		
		if(state == GameState.Paused) {
			g.drawPixmap(resume, resumeX, resumeY);
			g.drawPixmap(quit, quitX, quitY);
		} 
		
		if(state == GameState.GameOver) {
		
			g.drawPixmap(gameOver, gameoverX, gameoverY);
			g.drawPixmap(restart, restartX, restartY);
			g.drawPixmap(quit, quitX, quitY);
		}
		
	}

	@Override
	public void pause() {
		if(state == GameState.Running) {
			state = GameState.Paused;
		} else if(state ==GameState.GameOver) {
			Settings.getInstance().save(game.getFileIO());
		}
		
		
		if(Settings.getInstance().isSoundEnabled())
			Assets.getInstance().getBackground().pause();
		
	}

	@Override
	public void resume() {
		if(Settings.getInstance().isSoundEnabled())
			Assets.getInstance().getBackground().play();
		
	}

	@Override
	public void dispose() {
		if(Settings.getInstance().isSoundEnabled())
			Assets.getInstance().getBackground().stop();
		
	}
	
	

}
