package com.jared.waves.screen;

import java.io.File;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.utils.Disposable;
import com.jared.waves.PhysicsMain;
import com.jared.waves.widget.Button;

/**
 * The menu for the game.
 * @author Darian Atkinson and Jared Bass
 */
public class MainMenuScreen implements Screen
{
	public static ArrayList<Disposable> content = new ArrayList<>();
	
	private int width, height;
	private Button btnPlay;
	private SpriteBatch batch;
	private BitmapFont font;
	
	@Override
	public void create()
	{
		content.add(batch = new SpriteBatch());
		content.add(font = new BitmapFont());
		Gdx.input.setInputProcessor(new InputHandler());
		
		Texture bg;
		content.add(bg = new Texture(PhysicsMain.ASSETPATH + "mainmenu/btn_bg.png"));
		Runnable btnPlayRunnable = () ->
		{
			try {
				GameScreen.loadAndSet();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		btnPlay = new Button(400f - bg.getWidth()/2, 300f - bg.getHeight()/2, btnPlayRunnable, bg, "Play");
	}

	@Override
	public void render()
	{
		String text = "The Wave Game";
		TextBounds bounds = font.getBounds(text);
		float x = Gdx.graphics.getWidth()/2 - bounds.width/2;
		float y = Gdx.graphics.getHeight()*2/3 + bounds.height/2;
		Texture t = new Texture(PhysicsMain.ASSETPATH + "mainbackground.png");
		batch.begin();
		batch.draw(t, 0, 0);
		font.draw(batch, text, x, y);
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
