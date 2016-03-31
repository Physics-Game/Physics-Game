package com.jared.waves.units;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.jared.waves.PhysicsMain;
import com.jared.waves.screen.GameScreen;
import com.jared.waves.units.barriers.Barrier;

/**
 * Wave that moves along the screen
 * @author Jared Bass
 * @author Darian Atkinson
 */
public class Wave
{
	private Vector2 wave;
	private Sprite s;
	private Texture texture;
	private float x, y;
	
	/**
	 * Constructs a wave as a vector with origin at the bottom left corner
	 */
	public Wave()
	{
		wave = new Vector2(8, 0);
		x = 0;
		y = 0;
		GameScreen.content.add(texture = new Texture(PhysicsMain.ASSETPATH + "sprites/wave3.png"));
		s = new Sprite(new Texture(PhysicsMain.ASSETPATH + "sprites/wave3.png"));
	}	
	
	/**
	 * Rotates the wave
	 * @param degrees The degree you want to rotate the wave to
	 */
	public void rotateWave(float degrees)
	{
		wave.setAngle(degrees);
		s.setRotation(degrees);
	}
	
	/**
	 * Checks if the wave hits the barrier
	 * @param b The barrier being checked
	 * @return True if the wave has hit the barrier, false otherwise
	 */
	public boolean hitsObject(Barrier b)
	{
		return b.hits(this);
	}
	
	/**
	 * Checks every barrier in a level and if the wave has hit it
	 * @param barriers The level's list of barriers
	 * @return The index of the barrier hit, -1 otherwise
	 */
	public int hits(ArrayList<Barrier> barriers)
	{
		String className = "";
		
		for(int i = 0; i < barriers.size(); i++)
		{
			Barrier b = barriers.get(i);
			
			if(b.hits(this))
				className = b.getClass().getSimpleName();
			
			if(className.equals("Reflector"))
				return i;
			else if(className.equals("Refractor"))
				return i;
			else if(className.equals("Goal"))
				return i;
		}
		return -1;
	}
	
	public Vector2 getVector()
	{
		return wave;
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public Sprite getSprite()
	{
		return s;
	}
	
	/**
	 * Moves the wave by moving the sprite the distance of the vector at the wave's angle
	 */
	public void translate()
	{
		float xTrans = (float)(wave.len() * Math.cos(Math.toRadians((double)wave.angle())));
		float yTrans = (float)(wave.len() * Math.sin(Math.toRadians((double)wave.angle())));

		s.translate(xTrans, yTrans);
		
		x = s.getX();
		y = s.getY();
	}
	
	/**
	 * Draws the wave's sprite
	*/
	public void draw(SpriteBatch batch)
	{
		s.draw(batch);
	}
}