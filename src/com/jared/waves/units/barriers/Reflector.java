package com.jared.waves.units.barriers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.jared.waves.PhysicsMain;
import com.jared.waves.screen.GameScreen;
import com.jared.waves.units.Wave;

public class Reflector implements Barrier
{
	private Rectangle hitbox;
	private Texture background;
	
	public Reflector(float x, float y, float width, float height)
	{
		hitbox = new Rectangle(x,y,width,height);
		GameScreen.content.add(background = new Texture(PhysicsMain.ASSETPATH + "sprites/barriers/reflector.png"));
	}

	@Override
	public boolean hits(Wave w)
	{
		return hitbox.contains(w.getVector());
	}
	
	@Override
	public void draw(SpriteBatch batch)
	{
		batch.draw(background, hitbox.x, hitbox.y, hitbox.width, hitbox.height);
	}
	

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}
}
