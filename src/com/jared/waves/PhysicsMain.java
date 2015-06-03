package com.jared.waves;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.jared.waves.screen.MainMenuScreen;
import com.jared.waves.screen.ScreenManager;

public class PhysicsMain implements ApplicationListener 
{
	public static final String ASSETPATH = "com/jared/waves/assets/";
	
	public static void main(String[] args)
	{
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Waves";
		cfg.width = 960;
		cfg.height = 720;
		cfg.addIcon(ASSETPATH + "icon-128.png", FileType.Classpath);
		cfg.addIcon(ASSETPATH + "icon-32.png", FileType.Classpath);
		cfg.addIcon(ASSETPATH + "icon-16.png", FileType.Classpath);
		new LwjglApplication(new PhysicsMain(), cfg);
	}

	@Override
	public void create()
	{
		ScreenManager.setScreen(new MainMenuScreen());
	}

	@Override
	public void resize(int width, int height)
	{
		
	}

	@Override
	public void render()
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(ScreenManager.getScreen() != null)
			ScreenManager.getScreen().render();
	}

	@Override
	public void dispose()
	{
		if(ScreenManager.getScreen() != null)
			ScreenManager.getScreen().dispose();
	}
	
	@Override
	public void pause()
	{
		
	}

	@Override
	public void resume()
	{
		
	}
}
