package com.cmt3319.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.cmt3319.interfaces.FileIO;

/**
 * Holds a number of settings for the game including: if sound is enabled,
 * high score, and if gravity is reversed. 
 * Also enables saving and loading of these settings to/from file. 
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class Settings {
	private String fileName = ".platformer";
	private boolean isSoundEnabled = true;
	private int highScore;
	private boolean isGravityReversed = false;
	
	private static Settings instance = null;
	 
	protected Settings() {
		 
	}
	 
	public static Settings getInstance() {
		if(instance == null) {
	         instance = new Settings();
	    }
	    return instance;
	}

	private void setDefaults() {
		setHighScore(0);
		setSoundEnabled(true);
	}

	public void load(FileIO file){
		BufferedReader in = null;
		
		try{
			in = new BufferedReader(new InputStreamReader(file.readFile(fileName)));
			setSoundEnabled(Boolean.parseBoolean(in.readLine()));
			setHighScore(Integer.parseInt(in.readLine()));
		} catch(IOException ioe) {
			setDefaults();
		} catch(NumberFormatException nfe) {
			setDefaults();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ioe) {
				
			}
		}
	}
	
	public void save(FileIO file) {
		BufferedWriter out = null;
		
		try {
			out = new BufferedWriter(new OutputStreamWriter(file.writeFile(fileName)));
			
			out.write(Boolean.toString(isSoundEnabled()));
			out.write(Integer.toString(getHighScore()));
			
		} catch (IOException ioe) {
			
		} finally {
			try {
				if (out != null) {
					out.close();
				} 
			} catch (IOException ioe) {
					
			}
		}
		
	}
	
	public boolean isSoundEnabled() {
		return isSoundEnabled;
	}

	public void setSoundEnabled(boolean soundEnabled) {
		this.isSoundEnabled = soundEnabled;
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		if (highScore > this.highScore)
			this.highScore = highScore;
	}

	public boolean isGravityReversed() {
		return isGravityReversed;
	}
	
	public void setGravityReversed(Boolean reversed) {
		isGravityReversed = reversed;
	}

	public void reverseGravity() {
		isGravityReversed = !isGravityReversed;
	}
	
	
	
	

}
