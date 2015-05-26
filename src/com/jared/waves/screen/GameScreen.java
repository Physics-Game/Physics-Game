package com.jared.waves.screen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.jared.waves.Level;
import com.jared.waves.units.barriers.Goal;
import com.jared.waves.units.barriers.Reflector;

public class GameScreen implements Screen
{
	public static final float MIN_ZOOM = 1.0f, MAX_ZOOM = 2.0f;

	public static ArrayList<Disposable> content = new ArrayList<>();
	public static OrthographicCamera cam;
	private static SpriteBatch batch = new SpriteBatch();
	public static boolean flagInitFire;
	public static Level[] levelArray;
	public static int levelOn = 0;
	
	@Override
	public void create()
	{	
		cam = new OrthographicCamera(800, 600);
		cam.setToOrtho(false);
		Gdx.input.setInputProcessor(new InputHandler());
		
		flagInitFire = false;
		
		JsonReader reader = new JsonReader();
		JsonValue root = reader.parse(new FileHandle("json/levelData.json"));
		JsonValue levels = root.get("levels"); 
		int amtLevels = root.getInt("levelAmt");
		levelArray = new Level[amtLevels];
		for(int i = 0; i < levelArray.length; i++)
			levelArray[i] = new Level();

		for(int level = 0; level < amtLevels; level++)
		{
			JsonValue indiLevel = levels.get(level);
			for(int barrierC = 0; barrierC < indiLevel.size; barrierC++)
			{
				JsonValue barrier = indiLevel.get(barrierC);

				int type = barrier.getInt("btype");
				int x = barrier.getInt("x");
				int y = barrier.getInt("y");
				int theta = 0;
				try{
					theta = barrier.getInt("ang");
				}catch (Exception e){
					System.err.println("There isn't any angle");
				}

				switch(type)
				{
					case 1:
					{
						levelArray[level].addBarrier(new Reflector(x,y,100,33,theta));
						break;
					}
					case 2:
					{
						break;
					}
					case 3:
					{
						break;
					}
					case 4:
					{
						Goal g = new Goal(x,y,100,33);
						levelArray[level].addBarrier(g);
						levelArray[level].createGoal(g);
						break;
					}
				}
			}
		}
	}	

	@Override
	public void render()
	{
		batch.setProjectionMatrix(cam.combined);
		
		if(levelOn < levelArray.length && !levelArray[levelOn].isDone())
		{
			batch.begin();
			
			Level l = levelArray[levelOn];
			
			l.initialDraw(batch);
			
			if(flagInitFire)
				l.draw(batch);
			
			l.checkForHits();
			
			batch.end();

		}
		else
		{
			flagInitFire = false;
			levelOn++;
			if(levelOn == levelArray.length)
			{
				ScreenManager.setScreen(new WinScreen());
				batch.dispose();
			}
			else
				ScreenManager.setScreen(new InBetweenScreen());				
		}
	}

	@Override
	public void resize(int width, int height)
	{
		
	}

	@Override
	public void dispose()
	{
		Gdx.app.log("INFO", "GameScreen Disposed");
		for(Disposable d : content)
			d.dispose();
	}

	private class InputHandler implements InputProcessor
	{
		@Override
		public boolean keyDown(int keycode)
		{
			return false;

		}

		@Override
		public boolean keyUp(int keycode)
		{
			return false;
		}

		@Override
		public boolean keyTyped(char character)
		{
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button)
		{
			if(button == Buttons.LEFT)
			{
				if(!flagInitFire)
				{
					levelArray[levelOn].initFire(screenX, Gdx.graphics.getHeight() - screenY);
					flagInitFire = true;
				}
				return true;
			}
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button)
		{
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer)
		{
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY)
		{
			return false;
		}

		@Override
		public boolean scrolled(int amount)
		{
			return false;
		}
	}
}