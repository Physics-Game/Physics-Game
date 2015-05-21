package com.jared.waves.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.jared.waves.PhysicsMain;
import com.jared.waves.screen.GameScreen;
import com.jared.waves.units.barriers.Barrier;

public class Wave
{
	private static Vector2 wave;
	private Texture texture;
	private float x, y;
	
	public Wave()
	{
		wave = new Vector2();
		wave.setLength(1);
		x = 0;
		y = 0;
		GameScreen.content.add(texture = new Texture(PhysicsMain.ASSETPATH + "sprites/wave.png"));
	}	
	
	public Wave(float xPos, float yPos)
	{
		wave = new Vector2(xPos, yPos);
		x = xPos;
		y = yPos;
		GameScreen.content.add(texture = new Texture(PhysicsMain.ASSETPATH + "sprites/wave.png"));
	}
	
	public void rotateWave(float degrees)
	{
		wave.setAngle(degrees);
	}
	
	public boolean hitsObject(Barrier b)
	{
		return b.hits(this);
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
	
	public void draw(SpriteBatch batch)
	{
		batch.draw(texture, x, y);
		Gdx.app.log("DEBUG", "X: " + x + ", Y: " + y );
	}
}
