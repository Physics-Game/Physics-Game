package com.jared.waves.screen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.utils.Disposable;
import com.jared.waves.PhysicsMain;
import com.jared.waves.widget.Button;

/**
 * The screen displayed inbetween levels or when the game is won
 * @author Jared Bass
 */
public class InBetweenScreen implements Screen
{
	private Button btnNextLevel, btnRedoLevel, btnMainMenu;
	public static ArrayList<Disposable> content = new ArrayList<Disposable>();
	private SpriteBatch batch;
	private BitmapFont font;
	
	/**
	 * Creates the screen and buttons on it
	 */
	@Override
	public void create() 
	{
		content.add(batch = new SpriteBatch());
		content.add(font = new BitmapFont());
		Gdx.input.setInputProcessor(new InputHandler());
		
		Texture bg;
		content.add(bg = new Texture(PhysicsMain.ASSETPATH + "background/btn_bg.png"));
		
		Runnable nextLevel = () ->
		{
			ScreenManager.setScreen(new GameScreen());
		};
		
		Runnable redo = () ->
		{
			GameScreen.levelOn--;
			ScreenManager.setScreen(new GameScreen());
		};
		
		Runnable mainMenu = () ->
		{
			GameScreen.levelOn = 0;
			ScreenManager.setScreen(new MainMenuScreen());
		};
	
		//Instantiates the buttons and places them on the screen with their contained runnables
		btnNextLevel = new Button(Gdx.graphics.getWidth() / 2 - bg.getWidth()/2, Gdx.graphics.getHeight() / 2 - bg.getHeight()/2 + 75, nextLevel, bg, "Next Level");
		btnRedoLevel = new Button(Gdx.graphics.getWidth() / 2 - bg.getWidth()/2, Gdx.graphics.getHeight() / 2 - bg.getHeight()/2 + 25, redo, bg, "Redo Level");
		btnMainMenu = new Button(Gdx.graphics.getWidth() / 2 - bg.getWidth()/2, Gdx.graphics.getHeight() / 2 - bg.getHeight()/2 - 20, mainMenu, bg, "Main Menu");	
	}

	/**
	 * Renders the applicable screen and buttons
	 */
	@Override
	public void render() 
	{
		batch.begin();
		
		//Draws the background
		batch.draw(new Texture(PhysicsMain.ASSETPATH + "background/mainBackground.png"), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		String text = "";
		
		//If the game is not over, display a generic inbetween screen
		if(GameScreen.levelOn != GameScreen.levelArray.length)
		{
			text = "You have beaten level " + GameScreen.levelOn + "!!";
			btnNextLevel.draw(batch);
			btnRedoLevel.draw(batch);
			btnMainMenu.draw(batch);
		}
		//Otherwise, display the victory screen
		else
		{
			text = "You have beaten The Wave Game!!";
			//btnRedoLevel.draw(batch);
			btnMainMenu.draw(batch);

			//Disposes of textures used
			while(GameScreen.textureContent.size() > 0)
			{
				Disposable d = GameScreen.textureContent.get(0);
				d.dispose();
				GameScreen.textureContent.remove(0);
			}
		}
		
		TextBounds bounds = font.getBounds(text);
		float x = Gdx.graphics.getWidth()/2 - bounds.width/2;
		float y = Gdx.graphics.getHeight()*2/3 + bounds.height/2 + 85;
		font.draw(batch, text, x, y);
		batch.end();
	}
	
	/**
	 * Disposes applicable objects to save memory usage
	 */
	@Override
	public void dispose()
	{
		//Empties array of content and releases sprites
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
			return false;
		}

		/**
		 * If a player clicks a button, perform the runnable
		 */
		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button)
		{
			if(button == Buttons.LEFT)
			{
				if(btnNextLevel.contains(screenX, screenY))
					btnNextLevel.handleClick(button);
				else if(btnRedoLevel.contains(screenX, screenY))
					btnRedoLevel.handleClick(button);
				else if(btnMainMenu.contains(screenX, screenY))
					btnMainMenu.handleClick(button);
				return true;
			}
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