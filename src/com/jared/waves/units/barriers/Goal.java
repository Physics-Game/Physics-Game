package com.jared.waves.units.barriers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jared.waves.PhysicsMain;
import com.jared.waves.screen.GameScreen;
import com.jared.waves.units.Wave;

/**
 * The goal of the level
 * @author Jared Bass
 * @author Darian Atkinson
 */
public class Goal extends Barrier
{
	private final static int[] xs = {SpriteBatch.X1, SpriteBatch.X2, SpriteBatch.X3, SpriteBatch.X4};
	private Rectangle hitbox;
	private Texture background;
	private Sprite s;
	
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
		
		s = new Sprite(background);
		
		s.setX(x);
		s.setY(y);
	}
	
	/**
	 * Creates a goal based off of another goal
	 * @param g The goal to base the new goal off of
	 */
	public Goal(Goal g)
	{
		hitbox = new Rectangle(g.hitbox.x, g.hitbox.y, g.hitbox.width, g.hitbox.height);
		GameScreen.content.add(background = new Texture(PhysicsMain.ASSETPATH + "sprites/barriers/goal.png"));
		
		s = new Sprite(background);
		
		s.setX(g.hitbox.x);
		s.setY(g.hitbox.y);
	}
	
	/**
	 * Checks to see if the wave hits the goal
	 * @param wave The wave in the level
	 */
	@Override
	public boolean hits(Wave w)
	{
		Vector2[][] axesShape = {getAxes(w.getSprite()), getAxes(s)};
		
		for(int r = 0; r < axesShape.length; r++)
			for(int i = 0; i < axesShape[0].length; i++)
			{
				float[] x = project(w.getSprite(), axesShape[r][i]);
				float[] x2 = project(s, axesShape[r][i]);
				if(!((x[1] > x2[0] && x[0] < x2[1]) && (x2[1] > x[0] && x2[0] < x[1])))
					return false;
			}
		return true;
	}
	
	private float[] project(Sprite s, Vector2 axis) 
	{
		float min = Float.MAX_VALUE, max = -1*Float.MAX_VALUE;
		for(int i = 0; i < 4; i++)
		{
			float p = Math.abs(axis.dot(new Vector2(s.getVertices()[xs[i]], s.getVertices()[xs[i] + 1])));
			float p2 = axis.dot(new Vector2(s.getVertices()[xs[i]], s.getVertices()[xs[i] + 1]));
			
			if(p < min)
				min = p;
			else if(p > max)
				max = p;
			
			if(p2 < min)
				min = p2;
			else if(p2 > max)
				max = p2;

		}
		return new float[]{min, max};
	}

	private Vector2[] getAxes(Sprite s)
	{
		Vector2[] axes = new Vector2[4];
		
		for(int i = 0; i < axes.length; i++)
		{
			Vector2 p = new Vector2(s.getVertices()[xs[i]], s.getVertices()[xs[i] + 1]);
			Vector2 p2 = new Vector2(s.getVertices()[xs[i + 1 == axes.length ? 0 : i + 1]], s.getVertices()[xs[i + 1 == axes.length ? 0 : i + 1] + 1]);
			Vector2 edge = p.sub(p2);
			axes[i] = new Vector2(edge.y, -1*edge.x).nor();
		}
		
		return axes;
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