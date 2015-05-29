package com.jared.waves.units.barriers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.jared.waves.PhysicsMain;
import com.jared.waves.screen.GameScreen;
import com.jared.waves.units.Wave;

public class Reflector extends Barrier
{
	private float xMax, yMax, width , height;
	private Sprite s;
	private Texture background;
	private double degrees;
	private float anglePerp;
	private boolean oneRotate;
	private Vector2 perpendicular;
	
	public Reflector(int x, int y, int width, int height, float ang)
	{	
		GameScreen.content.add(background = new Texture(PhysicsMain.ASSETPATH + "sprites/barriers/reflector.png"));
		s = new Sprite(background);
		
		oneRotate = false;
		
		this.width = width;
				this.height = height;
				xMax = width + x;
				yMax = height + y;
		
		s.setX(x);
		s.setY(y);
		
		degrees = ang;
		anglePerp = (90 + ang) % 360;
		perpendicular = new Vector2(1,0);
		perpendicular.setAngle(anglePerp);
		perpendicular.setLength(1);
		
		used = false;
	}

	public boolean hits(Wave w)
	{
		return (w.getX() >= s.getX() - width/2 && w.getX() <= xMax && w.getY() >= s.getY() - height/2 && w.getY() <= yMax);
	}
	
	public void reflect(Wave w)
	{
		if(!super.used)
		{
			Vector2 waveVector = w.getVector();
			
			float thetaR = (float) (2 * degrees - waveVector.angle());
		
			w.rotateWave(thetaR);
			
			used = true;
			
			s.setTexture(new Texture(PhysicsMain.ASSETPATH + "sprites/barriers/reflector Used.png"));
		}
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

	public Sprite getSprite()
	{
		return s;
	}
	
	@Override
	public int barrierID() {
		return 1;
	}

	@Override
	public void resetBackground() {
		s.setTexture(background);
	}
}