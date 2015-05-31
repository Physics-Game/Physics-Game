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
	private final static int[] xs = {SpriteBatch.X1, SpriteBatch.X2, SpriteBatch.X3, SpriteBatch.X4};
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
		Vector2[][] axesShape = {getAxes(w.getSprite()), getAxes(s)};
		
		for(int r = 0; r < axesShape.length; r++)
			for(int i = 0; i < axesShape[0].length; i++)
			{
				float[] x = project(w.getSprite(), axesShape[r][i]);
System.out.println("Compared To: \n");
				float[] x2 = project(s, axesShape[r][i]);
System.out.println("=======================> ");			
				//if(!((x[1] > x2[0] && x[0] < x2[1]) && (x2[1] > x[0] && x2[0] < x[1])))
				if(!((x[1] > x2[0] && x[0] < x2[1]) && (x2[1] > x[0] && x2[0] < x[1])))
				{
					System.out.print(false + "\n\n");
					return false;
				}
System.out.print(true + "\n\n");
			}
		
		System.out.print("It collided\n\n");
		return true;
	}
	
	private float[] project(Sprite s, Vector2 axis) 
	{
		float min = Float.MAX_VALUE, max = -1*Float.MAX_VALUE;
		for(int i = 0; i < 4; i++)
		{
			float p = Math.abs(axis.dot(new Vector2(s.getVertices()[xs[i]], s.getVertices()[xs[i] + 1])));
			float p2 = axis.dot(new Vector2(s.getVertices()[xs[i]], s.getVertices()[xs[i] + 1]));
			
			if(p < min)
				min = p;
			else if(p > max)
				max = p;
			
			if(p2 < min)
				min = p2;
			else if(p2 > max)
				max = p2;

		}
		System.out.println("Max: " + max + "\nMin: " + min + "\n");
		return new float[]{min, max};
	}

	public void reflect(Wave w)
	{
		if(!super.used)
		{
			Vector2 waveVector = w.getVector();
			
			float thetaR = (float) (2 * degrees - waveVector.angle());
		
			w.rotateWave(thetaR);
			
			used = true;
			
			s.setTexture(new Texture(PhysicsMain.ASSETPATH + "sprites/barriers/reflectorUsed.png"));
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
	
	private Vector2[] getAxes(Sprite s)
	{
		Vector2[] axes = new Vector2[4];
		
		for(int i = 0; i < axes.length; i++)
		{
			Vector2 p = new Vector2(s.getVertices()[xs[i]], s.getVertices()[xs[i] + 1]);
			Vector2 p2 = new Vector2(s.getVertices()[xs[i + 1 == axes.length ? 0 : i + 1]], s.getVertices()[xs[i + 1 == axes.length ? 0 : i + 1] + 1]);
			
System.out.println(p);
System.out.println(p2 + "\n---------------");
			
			Vector2 edge = p.sub(p2);
			axes[i] = new Vector2(edge.y, -1*edge.x).nor();
		}
		
		return axes;
	}
}