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

public class Wave
{
	private Vector2 wave;
	private Sprite s;
	private Texture texture;
	private float x, y;
	
	public Wave()
	{
		wave = new Vector2(5, 0);
		wave.setLength(5);
		x = 0;
		y = 0;
		GameScreen.content.add(texture = new Texture(PhysicsMain.ASSETPATH + "sprites/wave2.png"));
		s = new Sprite(new Texture(PhysicsMain.ASSETPATH + "sprites/wave2.png"));
	}	
	
	public Wave(float xPos, float yPos)
	{
		wave = new Vector2(xPos, yPos);
		x = xPos;
		y = yPos;
		GameScreen.content.add(texture = new Texture(PhysicsMain.ASSETPATH + "sprites/wave2.png"));
	}
	
	public void rotateWave(float degrees)
	{
		wave.setAngle(degrees);
		s.setRotation(degrees);
	}
	
	public boolean hitsObject(Barrier b)
	{
		return b.hits(this);
	}
	
	public int hits(ArrayList<Barrier> barriers)
	{
		String thing = "";
		
		for(int i = 0; i < barriers.size(); i++)
		{
			Barrier b = barriers.get(i);
			if(b.hits(this))
				thing = b.getClass().getSimpleName();
			
			if(thing.equals("Reflector"))
				return i;
			else 
				if(thing.equals("Refractor"))
					return i;
		}
		
		return -1;
	}
	
	public Vector2 getVector()
	{
		return wave;
	}
	
	public void setVector(Vector2 v)
	{
		wave = new Vector2(v);
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
	
	public void translate()
	{
		float xTrans = (float)(wave.len() * Math.cos(Math.toRadians((double)wave.angle())));
		float yTrans = (float)(wave.len() * Math.sin(Math.toRadians((double)wave.angle())));

		s.translate(xTrans, yTrans);
		
		x = s.getX();
		y = s.getY();
	}
	
	public void draw(SpriteBatch batch)
	{
		s.draw(batch);
	}
}