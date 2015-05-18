package com.jared.waves.units.barriers;

import com.badlogic.gdx.math.Rectangle;
import com.jared.waves.units.Wave;

public class Reflector implements Barrier
{
	private Rectangle hitbox;
	
	public Reflector(float x, float y, float width, float height)
	{
		hitbox = new Rectangle(x,y,width,height);
	}
	
	public boolean hits(Wave w)
	{
		return hitbox.contains(w.getVector());
	}
}
