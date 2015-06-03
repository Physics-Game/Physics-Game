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
		background = new Texture(PhysicsMain.ASSETPATH + "sprites/barriers/reflector.png");
		GameScreen.textureContent.add(background);
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
				float[] x2 = project(s, axesShape[r][i]);
				if(!((x[1] > x2[0] && x[0] < x2[1]) && (x2[1] > x[0] && x2[0] < x[1])))
					return false;
			}
		
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
		return new float[]{min, max};
	}

	public void reflect(Wave w)
	{
		int side = getSide(w);
		
		if(!super.used)
		{
			Vector2 waveVector = w.getVector();
			
			float thetaR = (float) (2 * (degrees + 90 * side)- waveVector.angle());
		
			w.rotateWave(thetaR);
			
			used = true;
			
			s.setTexture(new Texture(PhysicsMain.ASSETPATH + "sprites/barriers/reflectorUsed.png"));
		}
	}
	
	private int getSide(Wave w) 
	{
		Sprite s;
		
		for(int i = 0; i < 4; i++)
		{
			this.s.rotate((float)-degrees);
			
			if(i == 0)
				s = new Sprite(background, (int)this.s.getVertices()[xs[i]] - 5, (int)this.s.getVertices()[xs[i] + 1], 5, (int)this.s.getHeight());
			else if(i == 1)
				s = new Sprite(background, (int)this.s.getVertices()[xs[i]], (int)this.s.getVertices()[xs[i] + 1] - 5, (int)this.s.getWidth(), 5);
			else if(i == 2)
				s = new Sprite(background, (int)this.s.getVertices()[xs[i]] + 5, (int)this.s.getVertices()[xs[i] + 1], 5, (int)this.s.getHeight());
			else
				s = new Sprite(background, (int)this.s.getVertices()[xs[i]], (int)this.s.getVertices()[xs[i] + 1] + 5, (int)this.s.getWidth(), 5);

			s.rotate((float)degrees);
			this.s.rotate((float)degrees);

			System.out.println();
			System.out.println(s.getX() + " " + s.getY());
			System.out.println(s.getVertices()[xs[0]] + " " + s.getVertices()[xs[0] + 1]);
			System.out.println(s.getVertices()[xs[1]] + " " + s.getVertices()[xs[1] + 1]);
			System.out.println(s.getVertices()[xs[2]] + " " + s.getVertices()[xs[2] + 1]);
			System.out.println(s.getVertices()[xs[3]] + " " + s.getVertices()[xs[3] + 1]);
			
			if(hits(w, s))
			{
				System.out.println(i);
				return i + 1;
			}
		}
		return 0;
	}

	public boolean hits(Wave w, Sprite s)
	{
		Vector2[][] axesShape = {getAxes(w.getSprite()), getAxes(s)};
		
		for(int r = 0; r < axesShape.length; r++)
			for(int i = 0; i < axesShape[0].length; i++)
			{
				float[] x = project(w.getSprite(), axesShape[r][i]);
				float[] x2 = project(s, axesShape[r][i]);
				if(!((x[1] > x2[0] && x[0] < x2[1]) && (x2[1] > x[0] && x2[0] < x[1])))
					return false;
			}
		
		return true;
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
			Vector2 edge = p.sub(p2);
			axes[i] = new Vector2(edge.y, -1*edge.x).nor();
		}
		
		return axes;
	}
}