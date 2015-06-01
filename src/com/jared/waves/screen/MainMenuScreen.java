package com.jared.waves.screen;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.utils.Disposable;
import com.jared.waves.PhysicsMain;
import com.jared.waves.widget.Button;

/**
 * The menu for the game.
 * @author Darian Atkinson 
 * @author Jared Bass
 */
public class MainMenuScreen implements Screen
{
	public static ArrayList<Disposable> content = new ArrayList<>();
	
	private Button btnPlay, btnHowToPlay, btnExit;
	private SpriteBatch batch;
	private BitmapFont font;
	
	@Override
	public void create()
	{
		content.add(font = new BitmapFont());
		batch = new SpriteBatch();
		Gdx.input.setInputProcessor(new InputHandler());
		
		Texture bg;
		content.add(bg = new Texture(PhysicsMain.ASSETPATH + "background/btn_bg.png"));
		Runnable btnPlayRunnable = () ->
		{
			ScreenManager.setScreen(new GameScreen());
			batch.dispose();
		};
		btnPlay = new Button(Gdx.graphics.getWidth() / 2 - bg.getWidth()/2, Gdx.graphics.getHeight() / 2 - bg.getHeight()/2 + 75, btnPlayRunnable, bg, "Play");
		
		Runnable btnHowToPlayRunnable = () ->
		{
			try {
				ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "How To Play.txt");
				pb.start();
				Gdx.app.log("Note", "Launching How to Play text file");
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
		
		btnHowToPlay = new Button(Gdx.graphics.getWidth() / 2 - bg.getWidth()/2, Gdx.graphics.getHeight()/2 - bg.getHeight()/2 + 25, btnHowToPlayRunnable, bg, "How To Play");
		
		Runnable exitGame = () ->
		{
			Gdx.app.log("INFO", "Game Closed");
			Gdx.app.exit();
		};

		btnExit = new Button(Gdx.graphics.getWidth() / 2 - bg.getWidth()/2, Gdx.graphics.getHeight() / 2 - bg.getHeight()/2 - 20, exitGame, bg, "Exit Game");
	}

	@Override
	public void render()
	{
		String text = "The Wave Game";
		TextBounds bounds = font.getBounds(text);
		float x = Gdx.graphics.getWidth()/2 - bounds.width/2;
		float y = Gdx.graphics.getHeight()*2/3 + bounds.height/2 + 85;
		batch.begin();
		batch.draw(new Texture(PhysicsMain.ASSETPATH + "background/mainBackground.png"), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		font.draw(batch, text, x, y);
		btnPlay.draw(batch);
		btnHowToPlay.draw(batch);
		btnExit.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height)
	{
		
	}

	@Override
	public void dispose()
	{
		while(content.size() > 0)
		{
			Disposable d = content.get(0);
			d.dispose();
			content.remove(0);
		}
		Gdx.app.log("INFO", "MainMenu Disposed");
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
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button)
		{
			if(button == Buttons.LEFT)
			{
				if(btnPlay.contains(screenX, screenY))
					btnPlay.handleClick(button);
				else if(btnHowToPlay.contains(screenX, screenY))
					btnHowToPlay.handleClick(button);
				else if(btnExit.contains(screenX, screenY))
					btnExit.handleClick(button);
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