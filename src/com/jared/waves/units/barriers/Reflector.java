package com.jared.waves.units.barriers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jared.waves.PhysicsMain;
import com.jared.waves.screen.GameScreen;
import com.jared.waves.units.Wave;

public class Reflector implements Barrier
{
	//private Rectangle hitbox;
	private Sprite s;
	private float xMax, yMax;
	private Texture background;
	private double degrees;
	private float anglePerp;
	private boolean oneRotate;
	private Vector2 perpendicular;
	
	public Reflector(float x, float y, float width, float height, float ang)
	{
		//hitbox = new Rectangle(x,y,width,height);
		GameScreen.content.add(background = new Texture(PhysicsMain.ASSETPATH + "sprites/barriers/reflector.png"));
		s = new Sprite(background);
		
		oneRotate = false;
		
		s.setX(x);
		s.setY(y);
		xMax = width + x;
		yMax = height + y;
		
		degrees = ang;
		anglePerp = (90 + ang) % 360;
		perpendicular = new Vector2(1,0);
		perpendicular.setAngle(anglePerp);
		perpendicular.setLength(1);
	}

	public boolean hits(Wave w)
	{
		return (w.getX() >= s.getX() && w.getX() <= xMax && w.getY() >= s.getY() && w.getY() <= yMax);
	}
	
	public Wave reflect(Wave w)
	{
		Vector2 waveVector = w.getVector();
		
		System.out.println(perpendicular.angle(waveVector));
		
		w.rotateWave(perpendicular.angle(waveVector));
		
		return w;
	}
	
	@Override
	public void draw(SpriteBatch batch)
	{
		s.draw(batch);
		
		if(!oneRotate)
		{
			s.rotate((float) degrees);
			oneRotate = true;
		}
	}
	
	public double getChangedAngle()
	{
		return anglePerp;
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int barrierID() {
		return 1;
	}
}
