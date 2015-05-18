package com.jared.waves;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jared.waves.units.Wave;
import com.jared.waves.units.barriers.Barrier;

public class Level 
{
	private ArrayList<Barrier> barriers;
	Grid levelGrid;
	Wave wave;
	
	public Level()
	{
		levelGrid = new Grid(50,50);
		barriers = new ArrayList<Barrier>();
		wave = new Wave();
	}
	
	public void addBarrier(Barrier b)
	{
		barriers.add(b);
	}
		
	public void draw(SpriteBatch batch)
	{
		for(int i = 0; i < barriers.size(); i++)
			barriers.get(i).draw(batch);
		
		wave.draw(batch);
	}
}
