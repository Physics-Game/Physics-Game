package com.jared.waves.units.barriers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.jared.waves.PhysicsMain;
import com.jared.waves.screen.GameScreen;
import com.jared.waves.units.Wave;

public class Goal extends Barrier
{
	private Rectangle hitbox;
	private Texture background;
	
	/**
	 * Creates the barriers sprite and hitbox
	 * @param x The x position of the goal
	 * @param y The y position of the goal
	 * @param width Width of the barrier
	 * @param height Height of the barrier
	 */
	public Goal(float x, float y, float width, float height)
	{
		hitbox = new Rectangle(x,y,width,height);
		background = new Texture(PhysicsMain.ASSETPATH + "sprites/barriers/goal.png");
		GameScreen.textureContent.add(background);
	}
	
	/**
	 * Creates a goal based off of another goal
	 * @param g The goal to base the new goal off of
	 */
	public Goal(Goal g)
	{
		hitbox = new Rectangle(g.hitbox.x, g.hitbox.y, g.hitbox.width, g.hitbox.height);
		GameScreen.content.add(background = new Texture(PhysicsMain.ASSETPATH + "sprites/barriers/goal.png"));
	}
	
	/**
	 * Checks to see if the wave hits the goal
	 * @param wave The wave in the level
	 */
	@Override
	public boolean hits(Wave wave)
	{
		Sprite waveS = wave.getSprite();
		return hitbox.contains(waveS.getX(), waveS.getY());
	}
	
	/**
	 * Draws the goal
	 */
	@Override
	public void draw(SpriteBatch batch)
	{
		batch.draw(background, hitbox.x, hitbox.y, hitbox.width, hitbox.height);
	}

	@Override
	public double getChangedAngle() 
	{
		return 0;
	}

	/**
	 * Returns the goal's ID
	 */
	@Override
	public int barrierID() 
	{
		return 4;
	}

	/**
	 * Resets the sprite
	 */
	@Override
	public void resetBackground() 
	{
		//The goal does not need to be reset, as it will only be hit once and then the level ends
	}
}
