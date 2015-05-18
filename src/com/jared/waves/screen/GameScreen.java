package com.jared.waves.screen;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.jared.waves.Grid;

public class GameScreen implements Screen
{
	public static final float MIN_ZOOM = 1.0f, MAX_ZOOM = 2.0f;
	
	public static ArrayList<Disposable> content = new ArrayList<>();
	public static OrthographicCamera cam;
	public static Grid grid;
	private SpriteBatch batch;
	
	public static void loadAndSet() throws InterruptedException
	{
		Screen loadingScreen = new Screen()
		{
			private SpriteBatch batch;
			private BitmapFont font;
			
			@Override
			public void create()
			{
				Gdx.app.log("DEBUG", "LoadingScreen created");
				GameScreen.content.add(batch = new SpriteBatch());
				GameScreen.content.add(font = new BitmapFont());
			}

			@Override
			public void render()
			{
				String text = "Loading...";
				TextBounds bounds = font.getBounds(text);
				float x = Gdx.graphics.getWidth()/2 - bounds.width/2;
				float y = Gdx.graphics.getHeight()/2 + bounds.height/2;
				
				batch.begin();
				font.draw(batch, text, x, y);
				batch.end();
			}

			@Override
			public void resize(int width, int height) {}

			@Override
			public void dispose()
			{
				Gdx.app.log("DEBUG", "LoadingScreen disposed");
			}
		};
		
		Runnable load = () -> 
		{
			Gdx.app.log("Wait", "Loading");
			try {
				Thread.sleep(10000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			grid = new Grid(50,50);
			
			Runnable run = () ->
			{
				ScreenManager.setScreen(new GameScreen());
			};
			Gdx.app.postRunnable(run);
		};
		
		Thread loading = new Thread(load, "Loading-Game");
		loading.start();
		ScreenManager.setScreen(loadingScreen);
	}
	
	@Override
	public void create()
	{	
		cam = new OrthographicCamera(800, 600);
		cam.setToOrtho(false);
		content.add(batch = new SpriteBatch());
		Gdx.input.setInputProcessor(new InputHandler());
	}	
	
	@Override
	public void render()
	{
        clampCamera();        
        
        batch.setProjectionMatrix(cam.combined);
		batch.begin();
		batch.end();
	}
	
	@Override
	public void resize(int width, int height)
	{
		cam.viewportWidth = width;
		cam.viewportHeight = height;
		clampCamera();
	}

	@Override
	public void dispose()
	{
		Gdx.app.log("INFO", "GameScreen Disposed");
		for(Disposable d : content)
			d.dispose();
	}
	
	/**
	 * Ensures the camera doesn't go beyond the borders of the map.<br>
	 * Also ensures the camera is within the appropriate zoom range.
	 */
	public static void clampCamera()
	{
		//constrain the camera's movement
        cam.position.x = MathUtils.clamp(cam.position.x, 0, grid.getWidthPixels());
        cam.position.y = MathUtils.clamp(cam.position.y, 0, grid.getHeightPixels());
        cam.zoom = MathUtils.clamp(cam.zoom, MIN_ZOOM, MAX_ZOOM);
        cam.update();
	}

	private class InputHandler implements InputProcessor
	{
		private Thread draggedThread;
		private int button;
		
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
			this.button = button;
			
			if(button == Buttons.LEFT)
			{
				//stop sliding when the user clicks on the screen again
				if(draggedThread != null && draggedThread.isAlive())
					draggedThread.interrupt();
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
			float zoom = cam.zoom + amount / 10.0f;
			cam.zoom = zoom;
			clampCamera();
			return true;
		}
	}

		

}
