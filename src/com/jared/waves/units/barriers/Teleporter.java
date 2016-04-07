package com.jared.waves.units.barriers;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.jared.waves.PhysicsMain;
import com.jared.waves.screen.GameScreen;
import com.jared.waves.units.Wave;

/**
 * A refractor that changes a wave's speed and angle
 * @author Jared Bass
 * @author Darian Atkinson
 */
public class Teleporter extends Barrier 
{
	private final static int[] xs = {SpriteBatch.X1, SpriteBatch.X2, SpriteBatch.X3, SpriteBatch.X4};
	private Sprite s, s2;
	private Texture background;
	private double degrees;
	private float anglePerp;
	private boolean oneRotate;
	private Vector2 perpendicular;
	private final float index = 10, speedIndex = 1.5f;
	
	public Teleporter(int x, int y, int x1, int y1, int width, int height, float ang)
	{
		background = new Texture(PhysicsMain.ASSETPATH + "sprites/barriers/teleporter.png");
		
		GameScreen.textureContent.add(background);
		
		s = new Sprite(background);
		s2 = new Sprite(background);
		
		oneRotate = false;
		
		s.setX(x);
		s.setY(y);
		
		s2.setX(x1);
		s2.setY(y1);
		
		degrees = ang;
		anglePerp = (90 + ang) % 360;
		perpendicular = new Vector2(1,0);
		perpendicular.setAngle(anglePerp);
		perpendicular.setLength(1);
		
		used = false;
	}
	
	/**
	 * Uses the Separating Axis Theorem (SAT) to detect collisions
	 * @param Wave w
	 * @return boolean didItHit
	 */
	@Override
	public boolean hits(Wave w)
	{
		Vector2[][] axesShape = {getAxes(w.getSprite()), getAxes(s)};
		
		for(int r = 0; r < axesShape.length; r++)
			for(int i = 0; i < axesShape[0].length; i++)
			{
				float[] x = project(w.getSprite(), axesShape[r][i]);
				float[] x2 = project(s, axesShape[r][i]);
				if(!((x[1] > x2[0] && x[0] < x2[1]) || (x2[1] > x[0] && x2[0] < x[1])))
					return false;
			}
		return true;
	}
	
	/**
	 * Approximately changes the wave angle through an infinitely large medium
	 * @param Wave w
	 */
	public void Teleport(Wave w)
	{
		Vector2 waveVector = w.getVector();
		
		if(!super.used)
		{
			w.setX(s.getX() - waveVector.x + s2.getX());
			w.setY(s.getY() - waveVector.y + s2.getY());
			
		}
		
		System.out.println(w.getX());
		System.out.println(w.getY());
	}
	
	@Override
	public void draw(SpriteBatch batch) 
	{		
		s.draw(batch);
		s2.draw(batch);
		
		if(!oneRotate)
		{
			s.rotate((float) degrees);
			oneRotate = true;
		}
	}

	public double getNewPos() 
	{
		return 0;
	}

	@Override
	public int barrierID() 
	{
		return 5;
	}

	@Override
	public void resetBackground() {
	}

	/**
	 * Gets the axes to be tested in SAT
	 * @param Sprite s
	 * @return Vector2[] axes
	 */
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
	
	/**
	 * Projects each point vetor on the axes to be tested in SAT
	 * @param Sprite s
	 * @param Vector2 axis
	 * @return float[] projections
	 */
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

	@Override
	public double getChangedAngle() {
		// TODO Auto-generated method stub
		return 0;
	}
}