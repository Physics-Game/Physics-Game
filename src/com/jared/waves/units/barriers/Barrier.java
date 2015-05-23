package com.jared.waves.units.barriers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jared.waves.units.Wave;

/**
 * Represents a barrier shown on screen
 * @author Jared Bass
 */
public interface Barrier
{
	/**
	 * Checks for wave contact with barrier
	 * @param w A wave that is to be compared to the barrier
	 * @return true if wave hits barrier, false otherwise
	 */
	public boolean hits(Wave wave);
	
	/**
	 * Draws the barrier
	 * @param batch The batch to be drawn to
	 */
	void draw(SpriteBatch batch);
	
	public double getChangedAngle();
	
	/**
	 * Updates this barrier's position based on the new width and height of the screen.
	 * @param width New width of the screen.
	 * @param height New height of the screen.
	 */
	void resize(int width, int height);
	
	public int barrierID();
}
