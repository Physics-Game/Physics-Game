package com.jared.waves.screen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.jared.waves.PhysicsMain;
import com.jared.waves.widget.Button;

/**
 * The menu for the game.
 * @author Jason Carrete
 */
public class MainMenuScreen implements Screen
{
	public static ArrayList<Disposable> content = new ArrayList<>();
	
	private int width, height;
	private Button btnPlay;
	private SpriteBatch batch;
	
	@Override
	public void create()
	{
		content.add(batch = new SpriteBatch());
		Gdx.input.setInputProcessor(new InputHandler());
		
		Texture bg;
		content.add(bg = new Texture(PhysicsMain.ASSETPATH + "mainmenu/btn_bg.png"));
		Runnable btnPlayRunnable = () ->
		{
			GameScreen.loadAndSet();
		};
		btnPlay = new Button(400f - bg.getWidth()/2, 300f - bg.getHeight()/2, btnPlayRunnable, bg, "Play");
	}

	@Override
	public void render()
	{
		batch.begin();
		btnPlay.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height)
	{
		this.width = width;
		this.height = height;
	}

	@Override
	public void dispose()
	{
		for(Disposable d : content)
			d.dispose();
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
