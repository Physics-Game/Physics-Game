package com.jared.waves;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jared.waves.screen.GameScreen;
import com.jared.waves.screen.InBetweenScreen;
import com.jared.waves.screen.Screen;
import com.jared.waves.screen.ScreenManager;
import com.jared.waves.units.Wave;
import com.jared.waves.units.barriers.Barrier;
import com.jared.waves.units.barriers.Goal;
import com.jared.waves.units.barriers.Reflector;
import com.jared.waves.units.barriers.Refractor;

/**
 * Level of the game
 * @author Jared Bass
 */
public class Level 
{
	//List of barriers that represent the content within the level
	private ArrayList<Barrier> barriers;
	private Texture background;
	private Wave wave;
	private Goal goal;
	
	/***
	 * Constructs level and instantiates the levels wave and goal, the only two objects guarenteed to exits
	 */
	public Level()
	{
		background = new Texture(PhysicsMain.ASSETPATH + "background/levelBackground.png");
		barriers = new ArrayList<Barrier>();
		wave = new Wave();
	}
	
	/**
	 * Separately assigns the goal instance variable
	 * @param g Goal of the level
	 */
	public void createGoal(Goal g)
	{
		goal = new Goal(g);
	}
	
	/**
	 * Public method to add barrier to level
	 * @param b Barrier to be added
	 */
	public void addBarrier(Barrier b)
	{
		barriers.add(b);
	}
	
	/**
	 * Initially fires the wave
	 * @param x X position clicked
	 * @param y Y position clicked
	 */
	public void initFire(float x, float y)
	{
		//Rotates the wave the correct measure
		wave.rotateWave((float)Math.toDegrees(Math.atan(Math.abs(y/(double)x))));
		//Fires the wave by translating its vector
		translateWave();
	}
	
	/**
	 * Draws the basic parts of the level
	 */
	public void initialDraw(SpriteBatch batch)
	{
		//Draws the background
		batch.draw(background, 0, 0);
		
		//Draws the barriers
		for(int i = 0; i < barriers.size(); i++)
			barriers.get(i).draw(batch);
		
		//Draws the wave
		wave.draw(batch);
	}
	
	/**
	 * Draws parts of level that can change
	 */
	public void draw(SpriteBatch batch)
	{	
		//Moves the wave
		translateWave();
		
		//If the wave leaves the screen, reset the level
		if(wave.getX() > Gdx.graphics.getWidth() || wave.getY() > Gdx.graphics.getHeight() || wave.getY() < 0 || wave.getX() < 0)
			reset();
	}
	
	/**
	 * Prepares the level to be changed once the level is beaten
	 */
	public void levelWasBeaten()
	{
		//Increments the level counter
		GameScreen.levelOn++;
		//Resets the level
		reset();
		
		//Displays the applicable inbetween screen
		ScreenManager.setScreen(new InBetweenScreen());	
	}
	
	/**
	 * Resets the various parts of the level for the circumstances when it is needed
	 */
	private void reset() 
	{
		//Recreates the wave which resets its position
		wave = new Wave();
		//Allows the wave to be fired again
		GameScreen.flagInitFire = false;
		//Allows each barrier to be used again and resets its background to show that
		for(int i = 0; i < barriers.size(); i++)
		{
			barriers.get(i).used = false;
			barriers.get(i).resetBackground();
		}
	}

	/**
	 * Checks to see if the level is done
	 * @return Whether the level was beaten
	 */
	public boolean isDone()
	{
		//If the wave hit the goal, the player beat the level
		if(wave.hitsObject(goal))
			return true;
		
		//Otherwhise, they haven't
		return false;
	}
	
	/**
	 * Moves the wave by translating its vector
	 */
	public void translateWave()
	{
		wave.translate();  
	}
	
	/**
	 * Accesses the wave used by the level
	 * @return The level's wave
	 */
	public Wave getWave()
	{
		return wave;
	}
	
	/**
	 * Accesses the list of barriers within the level
	 * @return The barriers contained within the level
	 */
	public ArrayList<Barrier> getBarriers()
	{
		return barriers;
	}
	
	/**
	 * Checks the level to see if the wave has hit any barrier
	 */
	public void checkForHits()
	{
		//The wave checks to see if it has hit anything and returns the location in the list of the object hit
		int i = wave.hits(barriers);
		
		//If the wave hit something, check to see what it hit
		if(i >= 0)
		{
			if(barriers.get(i).barrierID() == 1)//Reflector
			{
				//Casts the barrier to a Reflector and calls the reflect method
				((Reflector)barriers.get(i)).reflect(wave);	
			}
			else
			{
				if((barriers.get(i).barrierID() < 4))//Refractor
				{
					//Casts the barrier to a Refractor and calls the refract method
					((Refractor)barriers.get(i)).refract(wave);
				}
			}			
		}		
	}
}