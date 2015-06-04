package com.cmt3319.game;

import com.cmt3319.interfaces.Game;
import com.cmt3319.interfaces.Graphics;
import com.cmt3319.interfaces.Graphics.PixmapFormat;
import com.cmt3319.interfaces.Pixmap;
import com.cmt3319.interfaces.Screen;

/**
 * First screen loaded when game is run, loads all assets associated with project. 
 * Does not present anything to user but sets {@link MainMenuScreen} as the current screen when
 * it has finished loading. Interfaces with the setter methods in {@link Assets} and extends
 * {@link Screen}.
 * 
 * @author Robert Wells
 * @version 1.0
 *
 */
public class LoadingScreen extends Screen {
	
	public LoadingScreen(Game game) {
		super(game);
	}
	
	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		
		Assets.getInstance().setWall(g.newPixmap("wall.png", PixmapFormat.ARGB4444));
		Assets.getInstance().setHazard(g.newPixmap("hazard.png", PixmapFormat.ARGB4444));
		Assets.getInstance().setPlayer(g.newPixmap("player.png", PixmapFormat.ARGB4444));
		Assets.getInstance().setPlayerReversed(g.newPixmap("playerReverseGravity.png", PixmapFormat.ARGB4444));
		Assets.getInstance().setPlayerJumping(g.newPixmap("playerJumping.png", PixmapFormat.ARGB4444));
		Assets.getInstance().setPlayerJumpingReversed(g.newPixmap("playerJumpingReversed.png", PixmapFormat.ARGB4444));
		Assets.getInstance().setPlayerDead(g.newPixmap("playerDead.png", PixmapFormat.ARGB4444));
		Assets.getInstance().setCollectible(g.newPixmap("collectible.png", PixmapFormat.ARGB4444));
		Assets.getInstance().setEnemy(g.newPixmap("enemy.png", PixmapFormat.ARGB4444));
		Assets.getInstance().setEnemyReversed(g.newPixmap("enemyReversed.png", PixmapFormat.ARGB4444));
		Assets.getInstance().setNumbers(g.newPixmap("numbers.png", PixmapFormat.ARGB8888));
		Assets.getInstance().setCollectibleSound(game.getAudio().newSound("collectible.wav"));
		Assets.getInstance().setResume(g.newPixmap("resume.png", PixmapFormat.ARGB8888));
		Assets.getInstance().setReverseGravity(g.newPixmap("reverseGravity.png", PixmapFormat.ARGB4444));
		Assets.getInstance().setQuit(g.newPixmap("quit.png", PixmapFormat.ARGB8888));
		Assets.getInstance().setGameOver(g.newPixmap("gameOver.png", PixmapFormat.ARGB8888));
		Assets.getInstance().setRestart(g.newPixmap("restart.png", PixmapFormat.ARGB8888));
		Assets.getInstance().setTapToStart(g.newPixmap("tapToStart.png", PixmapFormat.ARGB8888));
		Assets.getInstance().setHelpScreenOne(g.newPixmap("helpScreenOne.png", PixmapFormat.RGB565));
		Assets.getInstance().setHelpScreenTwo(g.newPixmap("helpScreenTwo.png", PixmapFormat.RGB565));
		Assets.getInstance().setHelpScreenTwoHeading(g.newPixmap("helpScreenTwoHeading.png", PixmapFormat.RGB565));
		Assets.getInstance().setBackground(game.getAudio().newMusic("backgroundMusic.mp3"));
		Assets.getInstance().setJump(game.getAudio().newSound("jump.wav"));
		Assets.getInstance().setHurt(game.getAudio().newSound("hurt.wav"));
		
		Pixmap[] clouds = new Pixmap[]{g.newPixmap("cloudOne.png", PixmapFormat.ARGB4444),
				g.newPixmap("cloudTwo.png", PixmapFormat.ARGB4444),
				g.newPixmap("cloudThree.png", PixmapFormat.ARGB4444)};
		Assets.getInstance().setClouds(clouds);
		
		Assets.getInstance().setPlay(g.newPixmap("play.png", PixmapFormat.ARGB8888));
		Assets.getInstance().setHelp(g.newPixmap("help.png", PixmapFormat.ARGB8888));
		Assets.getInstance().setHighscore(g.newPixmap("highScore.png", PixmapFormat.ARGB8888));
		Assets.getInstance().setAudioOn(g.newPixmap("audioOn.png", PixmapFormat.ARGB8888));
		Assets.getInstance().setAudioOff(g.newPixmap("audioOff.png", PixmapFormat.ARGB8888));
		Assets.getInstance().setBackArrow(g.newPixmap("backArrow.png", PixmapFormat.ARGB8888));
		Assets.getInstance().setForwardArrow(g.newPixmap("forwardArrow.png", PixmapFormat.ARGB8888));
		Assets.getInstance().setPause(g.newPixmap("pause.png", PixmapFormat.ARGB8888));
		Assets.getInstance().setMenuSelect(game.getAudio().newSound("click.ogg"));
		Assets.getInstance().setTitle(g.newPixmap("title.png", PixmapFormat.ARGB8888));
		
		
		Settings.getInstance().load(game.getFileIO());
		
		game.setScreen(new MainMenuScreen(game));
		
		
		
	}

	@Override
	public void present(float deltaTime) {
		// TODO Auto-generated method stub
		
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
