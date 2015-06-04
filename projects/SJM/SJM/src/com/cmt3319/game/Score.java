package com.cmt3319.game;

import com.cmt3319.interfaces.Graphics;

/**
 * Draws score on screen during game play. {@link GamePlayScreen} invokes {@link Score#increaseScore(int)}
 * when player is moving or collects a coin. It is also allows a score to be saved by invoking 
 * {@link Settings#setHighScore(int)}.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class Score {
	
	private int score;
	
	
	public Score() {
		score = 0;
	}
	
	public void increaseScore(int amount) {
		score += amount;
	}
	
	public void present(Graphics g) {
		
		String stScore = ""+ score;
		
		int x = (int) ((g.getWidth() * 0.8f) - stScore.length() * 15);
		int y = (int) (g.getHeight() * 0.05f);
		
		int len = stScore.length();
		
		for(int i = 0; i < len; i++) {
			char character = stScore.charAt(i);
			
			if(character == ' ') {
				x+= 20;
				continue;
			}
			
			int srcX = 0;
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
			
			
			x+= (srcWidth + 1);
			
		}
		
	}
	
	public void saveHighScore(){
		Settings.getInstance().setHighScore(score);
	}

}
