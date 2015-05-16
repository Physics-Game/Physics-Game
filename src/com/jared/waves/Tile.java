package com.jared.waves;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Represents a Hex on the map.
 * @author Jason Carrete
 */
public class Tile
{	
	private static Tile selectedTile;
	public static final float WIDTH = 50f, HEIGHT = 50f;

	public Tile()
	{
		
	}
	
	public void draw(SpriteBatch batch)
	{
		
	}
	
	public static Tile getSelectedTile()
	{
		return selectedTile;
	}

	public void handleClick(int button)
	{
		if(button == Buttons.LEFT)
		{
			selectedTile = this;
		}
		else 
			if(button == Buttons.RIGHT)
			{
				
			}
	}	
}
