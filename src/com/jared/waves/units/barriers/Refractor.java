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
	private final static int[] xs = {SpriteBatch.X1, SpriteBatch.X2, SpriteBatch.X3, SpriteBatch.X4};
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

	
}
