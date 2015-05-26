package com.jared.waves.units.barriers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
	 * @param w A wave that is to be compared to the barrier
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
	 * Updates this barrier's position based on the new width and height of the screen.
	 * @param width New width of the screen.
	 * @param height New height of the screen.
	 */
	public abstract void resize(int width, int height);
	
	public abstract void resetBackground();
	
	public abstract int barrierID();
}
