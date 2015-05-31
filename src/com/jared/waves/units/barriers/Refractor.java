package com.jared.waves.units.barriers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.jared.waves.PhysicsMain;
import com.jared.waves.screen.GameScreen;
import com.jared.waves.units.Wave;

public class Refractor extends Barrier 
{
	private float xMax, yMax, width , height;
	private Sprite s;
	private Texture background;
	private double degrees;
	private float anglePerp;
	private boolean oneRotate;
	private boolean deep;
	private Vector2 perpendicular;
	
	public Refractor(int x, int y, int width, int height, float ang, boolean isDeep)
	{
		if(isDeep)
			GameScreen.content.add(background = new Texture(PhysicsMain.ASSETPATH + "sprites/barriers/refractordeep.png"));
		else
			GameScreen.content.add(background = new Texture(PhysicsMain.ASSETPATH + "sprites/barriers/refractorshallow.png"));
		
		deep = isDeep;
		
		s = new Sprite(background);
		
		oneRotate = false;
		
		this.width = width;
		this.height = height;
		xMax = width + x;
		yMax = height + y;
		
		s.setX(x);
		s.setY(y);
		
		degrees = ang;
		anglePerp = (90 + ang) % 360;
		perpendicular = new Vector2(1,0);
		perpendicular.setAngle(anglePerp);
		perpendicular.setLength(1);
		
		used = false;
	}
	
	@Override
	public boolean hits(Wave w) 
	{
		return (w.getX() >= s.getX() - width/2 && w.getX() <= xMax && w.getY() >= s.getY() - height/2 && w.getY() <= yMax);
	}
	
	public void refract(Wave w)
	{
		if(!super.used)
		{
			Vector2 waveVector = w.getVector();
			
			if(deep)
			{
				if(waveVector.angle() > perpendicular.angle())
				{
					waveVector.setAngle(waveVector.angle() + 5);
					waveVector.setLength(waveVector.len() + 1.5f);
				}
				else
				{
					waveVector.setAngle(waveVector.angle() - 5);
					waveVector.setLength(waveVector.len() + 1.5f);
				}
			}
			else
			{
				if(waveVector.angle() > perpendicular.angle())
				{
					waveVector.setAngle(waveVector.angle() - 5);
					waveVector.setLength(waveVector.len() - 1.5f);
				}
				else
				{
					waveVector.setAngle(waveVector.angle() + 5);
					waveVector.setLength(waveVector.len() - 1.5f);
				}
			}
			
			used = true;
		}
	}

	@Override
	public void draw(SpriteBatch batch) 
	{
		s.draw(batch);
		
		if(!oneRotate)
		{
			s.rotate((float) degrees);
			oneRotate = true;
		}
	}

	@Override
	public double getChangedAngle() 
	{
		return 0;
	}

	@Override
	public int barrierID() 
	{
		if(deep)
			return 2;
		return 3;
	}

	@Override
	public void resetBackground() {
		// TODO Auto-generated method stub
		
	}

}
