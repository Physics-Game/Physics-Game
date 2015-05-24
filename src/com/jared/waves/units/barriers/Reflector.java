package com.jared.waves.units.barriers;

import java.awt.Rectangle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.jared.waves.PhysicsMain;
import com.jared.waves.screen.GameScreen;
import com.jared.waves.units.Wave;

public class Reflector implements Barrier
{
	private Rectangle hitbox;
	private Sprite s;
	private Texture background;
	private double degrees;
	private float anglePerp;
	private boolean oneRotate;
	private Vector2 perpendicular;
	private boolean usedReflect;
	
	public Reflector(int x, int y, int width, int height, float ang)
	{
		hitbox = new Rectangle(x,y,width,height);
		GameScreen.content.add(background = new Texture(PhysicsMain.ASSETPATH + "sprites/barriers/reflector.png"));
		s = new Sprite(background);
		
		oneRotate = false;
		
		s.setX(x);
		s.setY(y);
		
		degrees = ang;
		anglePerp = (90 + ang) % 360;
		perpendicular = new Vector2(1,0);
		perpendicular.setAngle(anglePerp);
		perpendicular.setLength(1);
		
		usedReflect = false;
	}

	public boolean hits(Wave w)
	{
		return hitbox.intersects(new Rectangle(0, 0, (int)w.getX(),(int)w.getY())) && (Math.abs(w.getX() - hitbox.x) < hitbox.width) && (Math.abs(w.getY() - hitbox.y) < hitbox.height);
	}
	
	public Wave reflect(Wave w)
	{/*
		if(!usedReflect)
		{
			Vector2 waveVector = w.getVector();
			float angle = waveVector.angle();
			//angle += degrees;
			float thetaI = perpendicular.angle(waveVector);
			if(thetaI > 90)
				thetaI = 180 - thetaI;
			waveVector.rotate(angle);
			w.rotateWave(180 - Math.abs(perpendicular.angle(waveVector)));
			//take angle wave is being shot and add the tilt angle. that is angle of incidence relative to the normal
			
			System.out.println(w.getX() + " " + w.getY());
			
			//System.out.println(perpendicular.angle(waveVector));
			
			usedReflect = true;
		}
		
		*/
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