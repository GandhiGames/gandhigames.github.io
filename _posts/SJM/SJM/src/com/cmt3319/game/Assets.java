package com.cmt3319.game;

import com.cmt3319.interfaces.Pixmap;
import com.cmt3319.interfaces.Sound;
import com.cmt3319.interfaces.Music;
import com.cmt3319.interfaces.Input.TouchEvent;

/**
 * Singleton used to store all assets used in game, including images and audio.
 * Provides getters and setters for accessibility.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class Assets {

	private Pixmap wall;
	private Pixmap hazard;
	private Pixmap player;
	private Pixmap playerReversed;
	private Pixmap playerJumping;
	private Pixmap playerJumpingReversed;
	private Pixmap playerDead;
	private Pixmap reverseGravity;
	private Pixmap collectible;
	private Pixmap enemy;
	private Pixmap enemyReversed;
	private Pixmap pause;
	private Pixmap gameOver;
	private Pixmap numbers;
	private Pixmap resume;
	private Pixmap quit;
	private Pixmap[] clouds = new Pixmap[3];
	private Pixmap restart;
	private Sound jump;
	private Sound collectibleSound;
	private Sound hurt;
	private Pixmap tapToStart;
	
	private Pixmap helpScreenOne;
	private Pixmap helpScreenTwo;
	private Pixmap helpScreenTwoHeading;
	private Pixmap title;
	private Pixmap menuBackground;
	private Pixmap play;
	private Pixmap highscore;
	private Pixmap help;
	private Pixmap audioOn;
	private Pixmap audioOff;
	private Pixmap backArrow;
	private Pixmap forwardArrow;
	
	
	private Sound menuSelect;

	private Music background;
	
	private static Assets instance = null;
	 
	protected Assets() {
		 
	}
	 
	 /**
     * Static method, provides access to an instance
     * of this class using the singleton design pattern.
     * 
     * @return returns an instance of Assets.
     */
	public static Assets getInstance() {
		if(instance == null) {
	         instance = new Assets();
	    }
	    return instance;
	}

	public Pixmap getWall() {
		return wall;
	}

	public void setWall(Pixmap wall) {
		this.wall = wall;
	}

	public Pixmap getHazard() {
		return hazard;
	}

	public void setHazard(Pixmap hazard) {
		this.hazard = hazard;
	}

	public Pixmap getPlayer() {
		return player;
	}

	public void setPlayer(Pixmap player) {
		this.player = player;
	}

	public Pixmap getPause() {
		return pause;
	}

	public void setPause(Pixmap pause) {
		this.pause = pause;
	}

	public Pixmap getGameOver() {
		return gameOver;
	}

	public void setGameOver(Pixmap gameOver) {
		this.gameOver = gameOver;
	}

	public Pixmap getMenuBackground() {
		return menuBackground;
	}

	public void setMenuBackground(Pixmap menuBackground) {
		this.menuBackground = menuBackground;
	}

	public Pixmap getPlay() {
		return play;
	}

	public void setPlay(Pixmap play) {
		this.play = play;
	}

	public Sound getMenuSelect() {
		return menuSelect;
	}

	public void setMenuSelect(Sound menuSelect) {
		this.menuSelect = menuSelect;
	}

	public Sound getJump() {
		return jump;
	}

	public void setJump(Sound jump) {
		this.jump = jump;
	}

	public Music getBackground() {
		return background;
	}

	public void setBackground(Music background) {
		this.background = background;
	}


	public Pixmap getCollectible() {
		return collectible;
	}

	public void setCollectible(Pixmap collectible) {
		this.collectible = collectible;
	}

	public Sound getCollectibleSound() {
		return collectibleSound;
	}

	public void setCollectibleSound(Sound collectibleSound) {
		this.collectibleSound = collectibleSound;
	}

	public Pixmap getNumbers() {
		return numbers;
	}

	public void setNumbers(Pixmap numbers) {
		this.numbers = numbers;
	}

	public Pixmap getHighscore() {
		return highscore;
	}

	public void setHighscore(Pixmap highscore) {
		this.highscore = highscore;
	}

	public Pixmap getHelp() {
		return help;
	}

	public void setHelp(Pixmap help) {
		this.help = help;
	}

	public Pixmap getAudioOn() {
		return audioOn;
	}

	public void setAudioOn(Pixmap audioOn) {
		this.audioOn = audioOn;
	}

	public Pixmap getAudioOff() {
		return audioOff;
	}

	public void setAudioOff(Pixmap audioOff) {
		this.audioOff = audioOff;
	}

	public Pixmap getBackArrow() {
		return backArrow;
	}

	public void setBackArrow(Pixmap backArrow) {
		this.backArrow = backArrow;
	}
	
	public boolean inBounds(TouchEvent event, int x, int y, int width, int height) { 
		if(event.x > x && event.x < x + width - 1 &&
			event.y > y && event.y < y + height - 1) 
			return true;
		else
			return false;

	}

	public Pixmap getResume() {
		return resume;
	}

	public void setResume(Pixmap resume) {
		this.resume = resume;
	}

	public Pixmap getQuit() {
		return quit;
	}

	public void setQuit(Pixmap quit) {
		this.quit = quit;
	}

	public Sound getHurt() {
		return hurt;
	}

	public void setHurt(Sound hurt) {
		this.hurt = hurt;
	}

	public Pixmap getRestart() {
		return restart;
	}

	public void setRestart(Pixmap restart) {
		this.restart = restart;
	}

	public Pixmap getReverseGravity() {
		return reverseGravity;
	}

	public void setReverseGravity(Pixmap reverseGravity) {
		this.reverseGravity = reverseGravity;
	}

	public Pixmap getEnemy() {
		return enemy;
	}

	public void setEnemy(Pixmap enemy) {
		this.enemy = enemy;
	}

	public Pixmap getPlayerJumping() {
		return playerJumping;
	}

	public void setPlayerJumping(Pixmap playerJumping) {
		this.playerJumping = playerJumping;
	}

	public Pixmap getPlayerDead() {
		return playerDead;
	}

	public void setPlayerDead(Pixmap playerDead) {
		this.playerDead = playerDead;
	}

	public Pixmap getPlayerReversed() {
		return playerReversed;
	}

	public void setPlayerReversed(Pixmap playerReversed) {
		this.playerReversed = playerReversed;
	}

	public Pixmap getPlayerJumpingReversed() {
		return playerJumpingReversed;
	}

	public void setPlayerJumpingReversed(Pixmap playerJumpingReversed) {
		this.playerJumpingReversed = playerJumpingReversed;
	}

	public Pixmap getEnemyReversed() {
		return enemyReversed;
	}

	public void setEnemyReversed(Pixmap enemyReversed) {
		this.enemyReversed = enemyReversed;
	}

	public Pixmap[] getClouds() {
		return clouds;
	}

	public void setClouds(Pixmap[] clouds) {
		this.clouds = clouds;
	}

	public Pixmap getTitle() {
		return title;
	}

	public void setTitle(Pixmap title) {
		this.title = title;
	}

	public Pixmap getTapToStart() {
		return tapToStart;
	}

	public void setTapToStart(Pixmap tapToStart) {
		this.tapToStart = tapToStart;
	}

	public Pixmap getHelpScreenOne() {
		return helpScreenOne;
	}

	public void setHelpScreenOne(Pixmap helpScreenOne) {
		this.helpScreenOne = helpScreenOne;
	}

	public Pixmap getHelpScreenTwo() {
		return helpScreenTwo;
	}

	public void setHelpScreenTwo(Pixmap helpScreenTwo) {
		this.helpScreenTwo = helpScreenTwo;
	}

	public Pixmap getForwardArrow() {
		return forwardArrow;
	}

	public void setForwardArrow(Pixmap forwardArrow) {
		this.forwardArrow = forwardArrow;
	}

	public Pixmap getHelpScreenTwoHeading() {
		return helpScreenTwoHeading;
	}

	public void setHelpScreenTwoHeading(Pixmap helpScreenTwoHeading) {
		this.helpScreenTwoHeading = helpScreenTwoHeading;
	}
	
	
	
	
	
	
}
