package com.jared.waves.units.barriers;

public class Axis 
{
	private float theta, alpha;
	
	public Axis(float theta)
	{
		this.theta = theta;
		alpha = 0;
	}
	
	public float[] rotatePoints(float x, float y)
	{
		float r = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		
		return new float[]{(float) (r * Math.cos(alpha)), (float) (r * Math.sin(alpha))};
	}
	
	public void setAlpha(float phi)
	{
		
		alpha = Math.abs(theta + phi);
	}
}