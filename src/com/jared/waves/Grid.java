package com.jared.waves;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jared.waves.screen.GameScreen;

public class Grid
{
	private Tile[][] grid;
	
	public Grid(int rows, int cols)
	{
		grid = new Tile[rows][cols];
	}
	
	public void draw(SpriteBatch batch)
	{ 
		final float zoom = GameScreen.cam.zoom;
		float x = GameScreen.cam.position.x - GameScreen.cam.viewportWidth*zoom/2;
		float y = GameScreen.cam.position.y - GameScreen.cam.viewportHeight*zoom/2;
		float width = GameScreen.cam.viewportWidth*zoom;
		float height = GameScreen.cam.viewportHeight*zoom;
	}
	
	/**
	 * @return The number of rows of tiles in the grid.
	 */
	public int rows()
	{
		return grid.length;
	}
	
	/**
	 * @return The number of columns of tiles in the grid.
	 */
	public int cols()
	{
		return grid[0].length;
	}
	
	public float getWidthPixels()
	{
		return cols() * Tile.WIDTH;
	}
	
	public float getHeightPixels()
	{
		return rows() * Tile.HEIGHT;
	}
}
