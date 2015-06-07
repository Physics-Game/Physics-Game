package com.jared.waves.units.barriers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jared.waves.units.Wave;

/**
 * Represents a barrier shown on screen
 * @author Jared Bass
 */
public abstract class Barrier
{
	public boolean used;
	
	/**
	 * Checks for wave contact with barrier
	 * @param wave A wave that is to be compared to the barrier
	 * @return true if wave hits barrier, false otherwise
	 */
	public abstract boolean hits(Wave wave);
	
	/**
	 * Draws the barrier
	 * @param batch The batch to be drawn to
	 */
	public abstract void draw(SpriteBatch batch);
	
	public abstract double getChangedAngle();
	
	/**
	 * Resets the background of the barrier once the level resets
	 */
	public abstract void resetBackground();
	
	/**
	 * @return The ID number of the barrier
	 */
	public abstract int barrierID();
}
