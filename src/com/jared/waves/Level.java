package com.jared.waves;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jared.waves.screen.GameScreen;
import com.jared.waves.screen.Screen;
import com.jared.waves.units.Wave;
import com.jared.waves.units.barriers.Barrier;
import com.jared.waves.units.barriers.Goal;

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
		wave = new Wave(new Sprite(new Texture(PhysicsMain.ASSETPATH + "sprites/wave.png")));
	}
	
	public void createGoal(Goal g)
	{
		goal = new Goal(g);
	}
	
	public void addBarrier(Barrier b)
	{
		barriers.add(b);
	}
	
	public void initFire(Screen b, float x, float y)
	{
		wave.rotateWave((float)Math.toDegrees(Math.atan(Math.abs(y/(double)x))));
		translateWave();
		Gdx.app.log("DEBUG", "FIRE");
		Gdx.app.log("DEBUG", "Mouse x: " + x + ", Mouse y: " + y);
		Gdx.app.log("DEBUG", "Angle: " + (float)Math.toDegrees(Math.atan(y/(double)x)));
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
		translateWave();
		
		if(wave.getX() > Gdx.graphics.getWidth() || wave.getY() > Gdx.graphics.getHeight())
			reset();
	}
	
	private void reset() 
	{
		wave = new Wave(new Sprite(new Texture(PhysicsMain.ASSETPATH + "sprites/wave.png")));
		GameScreen.flagInitFire = false;
		Gdx.app.log("DEBUG", "Level Reset");
	}

	public boolean isDone()
	{
		if(wave.hitsObject(goal))
			return true;
		return false;
	}
	
	public void translateWave()
	{
		wave.translate();  
	}
}