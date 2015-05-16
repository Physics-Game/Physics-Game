package com.jared.waves;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class PhysicsMain implements ApplicationListener 
{
	public static final String ASSETPATH = "com/jared/waves/assets";
	
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Waves";
		cfg.width = 800;
		cfg.height = 600;
		cfg.addIcon(ASSETPATH + "icon-128x.png", FileType.Classpath);
		cfg.addIcon(ASSETPATH + "icon-32x.png", FileType.Classpath);
		cfg.addIcon(ASSETPATH + "icon-16x.png", FileType.Classpath);
		new LwjglApplication(new PhysicsMain(), cfg);
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

}
