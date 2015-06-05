package com.jared.waves.screen;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
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
import com.jared.waves.units.barriers.Refractor;

/**
 * The screen holding the game itself
 * @author Jared Bass
 * @author Darian Atkinson
 */
public class GameScreen implements Screen
{
	public static ArrayList<Disposable> content = new ArrayList<Disposable>();
	public static ArrayList<Disposable> textureContent = new ArrayList<Disposable>();
	public static OrthographicCamera cam;
	private SpriteBatch batch;
	public static boolean flagInitFire;
	public static Level[] levelArray;
	public static int levelOn = 0;
	
	/**
	 * Reads in the json file with level data and creates the levels for the game
	 */
	public static void createLevels()
	{
		JsonReader reader = new JsonReader();
		JsonValue root = reader.parse(new FileHandle("json/levelData.json"));
		JsonValue levels = root.get("levels"); 
		int amtLevels = root.getInt("levelAmt");
		
		//Instantiates the level array and the levels contained within
		levelArray = new Level[amtLevels];
		for(int i = 0; i < levelArray.length; i++)
			levelArray[i] = new Level();

		//Goes through each level in the json file and pulls out each barrier and creates it according to barrier type
		for(int level = 0; level < amtLevels; level++)
		{
			JsonValue indiLevel = levels.get(level);
			for(int barrierC = 0; barrierC < indiLevel.size; barrierC++)
			{
				JsonValue barrier = indiLevel.get(barrierC);

				int type = barrier.getInt("btype");
				int x = barrier.getInt("x");
				int y = barrier.getInt("y");
				int theta = barrier.getInt("ang");

				//Determines the type of barrier and uses the appropriate constructor
				switch(type)
				{
					case 1:
					{
						levelArray[level].addBarrier(new Reflector(x,y,100,33,theta));
						break;
					}
					case 2:
					{
						levelArray[level].addBarrier(new Refractor(x,y,100,33,theta,true));
						break;
					}
					case 3:
					{
						levelArray[level].addBarrier(new Refractor(x,y,100,33,theta,false));
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
	
	/**
	 * Creates the screen the level is displayed on
	 */
	@Override
	public void create()
	{	
		//Sets up basic properties of the window
		content.add(batch = new SpriteBatch());
		cam = new OrthographicCamera(800, 600);
		cam.setToOrtho(false);
		Gdx.input.setInputProcessor(new InputHandler());
		
		flagInitFire = false;		
	}	

	/**
	 * Renders the level using each objects given draw method
	 */
	@Override
	public void render()
	{
		batch.setProjectionMatrix(cam.combined);
		Level l = levelArray[levelOn];
		
		//If the level is not done, draw it
		if(levelOn < levelArray.length && !levelArray[levelOn].isDone())
		{
			batch.begin();
			
			//Draws basic level features
			l.initialDraw(batch);
			
			//If the wave has been fired, draw its translation
			if(flagInitFire)
				l.draw(batch);
			
			//Check to see if the wave has hit any barriers
			l.checkForHits();
			
			batch.end();

		}
		else //The player has won the level
		{
			//Reset whether the wave has been fired, and increase the level
			l.levelWasBeaten();			
		}
	}

	/**
	 * Disposes applicable objects to save memory usage
	 */
	@Override
	public void dispose()
	{
		//Empties array of content frees memory
		while(content.size() > 0)
		{
			Disposable d = content.get(0);
			d.dispose();
			content.remove(0);
		}
	}

	/**
	 * Inner class that handles input
	 */
	private class InputHandler implements InputProcessor
	{
		@Override
		public boolean keyDown(int keycode)
		{
			if(keycode == Keys.M)
				levelArray[levelOn].levelWasBeaten();
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

		/**
		 * If the player has left clicked, fire the wave
		 */
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