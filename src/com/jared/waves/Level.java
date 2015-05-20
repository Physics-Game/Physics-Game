package com.jared.waves;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jared.waves.units.Wave;
import com.jared.waves.units.barriers.Barrier;
import com.jared.waves.units.barriers.Goal;
import com.jared.waves.units.barriers.Reflector;

public class Level 
{
	private ArrayList<Barrier> barriers;
	private Texture background;
	private Wave wave;
	private Goal goal;
	
	public Level()
	{
		background = new Texture(PhysicsMain.ASSETPATH + "levelBackground.png");
		barriers = new ArrayList<Barrier>();
		wave = new Wave();
	}
	
	public void createGoal(Goal g)
	{
		goal = new Goal(g);
	}
	
	public void addBarrier(Barrier b)
	{
		barriers.add(b);
	}
	
	public void initialDraw(SpriteBatch batch)
	{
		batch.draw(background, 0, 0);
		for(int i = 0; i < barriers.size(); i++)
			barriers.get(i).draw(batch);
		wave.draw(batch);
	}
	
	public void draw(SpriteBatch batch)
	{
		boolean flag = false;
		
		for(Barrier r : barriers)
			if(r.hits(wave))
			{
				wave.rotateWave((float)((Reflector)r).getChangedAngle());
				flag = true;
			}
		if(!flag)
			wave.translateWave();
	}
	
	public boolean isDone()
	{
		if(wave.hitsObject(goal))
			return true;
		return false;
	}
}
