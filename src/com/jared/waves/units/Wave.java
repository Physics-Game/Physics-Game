package com.jared.waves.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.jared.waves.PhysicsMain;
import com.jared.waves.screen.GameScreen;
import com.jared.waves.units.barriers.Barrier;

public class Wave
{
	private Vector2 wave;
	private Sprite s;
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
	
	public Wave(Sprite s)
	{
		this.s = s;
		wave = new Vector2();
		wave.setLength(1);
		wave.setAngle(0);
		x = 0;
		y = 0;
	}
	
	public void rotateWave(float degrees)
	{
		wave.setAngle(degrees);
		s.setRotation(degrees);
	}
	
	public boolean hitsObject(Barrier b)
	{
		return b.hits(s);
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
	
	public void translate()
	{
		System.out.println(wave.angle());
		
		float xTrans = (float)(Math.cos(Math.toRadians((double)wave.angle())));
		float yTrans = (float)(Math.sin(Math.toRadians((double)wave.angle())));

		System.out.println(xTrans);
		System.out.println(yTrans);
		
		s.translate(xTrans, yTrans);
		
		x = s.getX();
		y = s.getY();
	}
	
	public void draw(SpriteBatch batch)
	{
		s.draw(batch);
		Gdx.app.log("DEBUG", "X: " + x + ", Y: " + y );
	}
}
