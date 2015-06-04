package com.cmt3319.game;

import com.cmt3319.interfaces.Screen;
import com.cmt3319.implementation.AndroidGame;

/**
 * Entry point into the game. Loads {@link LoadingScreen}. Extends {@link AndroidGame}.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class PlatformGame extends AndroidGame{
	
	@Override
	public Screen getStartScreen() {
		return new LoadingScreen(this);
	}
	
}
