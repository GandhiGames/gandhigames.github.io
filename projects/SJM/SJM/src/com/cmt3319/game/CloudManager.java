package com.cmt3319.game;

import java.util.ArrayList;
import java.util.Random;

import com.cmt3319.interfaces.Graphics;
import com.cmt3319.interfaces.Pixmap;

/**
 * Manages a list of {@link Cloud}. Adds clouds based on {@link CloudManager#TIMETOADDCLOUD}, 
 * updates clouds position using {@link Cloud#update(float)}},
 * and removes clouds when they are off screen.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class CloudManager {
	private final float TIMETOADDCLOUD = 3f;
	
	private float timeSoFar;
	
	private Random rand;
	
	private ArrayList<Cloud> clouds;
	private float screenWidth, screenHeight;
	
	public CloudManager(float screenWidth, float screenHeight) {
		clouds = new ArrayList<Cloud>();
		
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		
		timeSoFar = 0f;

		rand = new Random();
		
		initialise();
	}
	
	 /**
	  * Adds two clouds to {@link CloudManager#clouds} at the beginning of the game. Invoked in constructor.
	  */
	private void initialise() {
		Pixmap image = Assets.getInstance().getClouds()[0];
		
		clouds.add(new Cloud(image, (int) (screenWidth / 2), (int) (screenHeight * 0.3f)));
		
		clouds.add(new Cloud(image, (int) (screenWidth *0.3f), (int) (screenHeight * 0.1f)));
	}
	
	 /**
	  * Removes clouds when they are off screen by invoking {@link Cloud#isOnScreen(float)}
	  * for each cloud in list.
	  */
	private void removeClouds() {
		for(int i = 0; i < clouds.size(); i++) {
			if(!clouds.get(i).isOnScreen(screenWidth)) {
				clouds.remove(i);
			}
		}
	}
	
	 /**
	  * Checks if it is time to add a new cloud.
	  * 
	  * @param deltaTime is added to a variable. If this variable is greater then
	  * TIMETOADDCLOUD constant then a new cloud is added to the ArrayList. 
	  */
	private void checkForNewCloud(float deltaTime) {
		timeSoFar += deltaTime;
		
		if(timeSoFar > TIMETOADDCLOUD) {
			int y = (int) (rand.nextInt((int)screenHeight / 2) + 30);
			int chosenCloud = rand.nextInt(3);
			
			Pixmap image = Assets.getInstance().getClouds()[0];
			
			switch(chosenCloud) {
				case 0: break;
				case 1: 
					image = Assets.getInstance().getClouds()[1];
					break;
				case 2: 
					image = Assets.getInstance().getClouds()[2];
					break;
			}
			
			clouds.add(new Cloud(image, (int)screenWidth + 30, y));
			timeSoFar = 0f;
		}
		
	}
	
	public void draw(Graphics g) {
		for(int i = 0; i < clouds.size(); i++) {
			clouds.get(i).draw(g);
		}
	}
	
	 /**
	  * Invokes {@link Cloud#update(float)} for each cloud, which moves them across the screen. 
	  * Calls removeClouds() and checkForNewClouds().
	  */
	public void update(float deltaTime) {
		for(int i = 0; i < clouds.size(); i++) {
			clouds.get(i).update(deltaTime);
		}
		
		removeClouds();
		checkForNewCloud(deltaTime);
	}
}
