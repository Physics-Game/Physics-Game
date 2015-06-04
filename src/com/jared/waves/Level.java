package com.jared.waves;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jared.waves.screen.GameScreen;
import com.jared.waves.screen.InBetweenScreen;
import com.jared.waves.screen.Screen;
import com.jared.waves.screen.ScreenManager;
import com.jared.waves.units.Wave;
import com.jared.waves.units.barriers.Barrier;
import com.jared.waves.units.barriers.Goal;
import com.jared.waves.units.barriers.Reflector;
import com.jared.waves.units.barriers.Refractor;

public class Level 
{
	private ArrayList<Barrier> barriers;
	private Texture background;
	private Wave wave;
	private Goal goal;
	
	public Level()
	{
		background = new Texture(PhysicsMain.ASSETPATH + "background/levelBackground.png");
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
	
	public void initFire(float x, float y)
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
		
		if(wave.getX() > Gdx.graphics.getWidth() || wave.getY() > Gdx.graphics.getHeight() || wave.getY() < 0 || wave.getX() < 0)
			reset();
	}
	
	public void levelWasBeaten()
	{
		GameScreen.levelOn++;
		reset();
		
		//Display the applicable inbetween screen
		ScreenManager.setScreen(new InBetweenScreen());	
	}
	
	private void reset() 
	{
		wave = new Wave();
		GameScreen.flagInitFire = false;
		for(int i = 0; i < barriers.size(); i++)
		{
			barriers.get(i).used = false;
			barriers.get(i).resetBackground();
		}

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
	
	public Wave getWave()
	{
		return wave;
	}
	
	public ArrayList<Barrier> getBarriers()
	{
		return barriers;
	}
	
	public void checkForHits()
	{
		int i = wave.hits(barriers);
		
		if(i >= 0)
		{
			if(barriers.get(i).barrierID() == 1)
			{
				((Reflector)barriers.get(i)).reflect(wave);	
			}
			else
			{
				if((barriers.get(i).barrierID() < 4))
				{
					((Refractor)barriers.get(i)).refract(wave);
				}
			}			
		}		
	}
}