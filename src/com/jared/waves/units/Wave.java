package com.jared.waves.units;

import com.badlogic.gdx.math.Vector2;
import com.jared.waves.units.barriers.Barrier;

public class Wave
{
	private Vector2 wave;
	
	public Wave()
	{
		wave = new Vector2();
	}
	
	public Wave(float x, float y)
	{
		wave = new Vector2(x, y);
	}
	
	public void rotateWave(float degrees)
	{
		wave.setAngle(degrees);
	}
	
	public void translateWave()
	{
		wave.add(wave.x/wave.len(), wave.y/wave.len());
	}
	
	public boolean hitsObject(Barrier b)
	{
		return b.hits(this);
	}
	
	public Vector2 getVector()
	{
		return wave;
	}
}
