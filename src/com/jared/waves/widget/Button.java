package com.jared.waves.widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.jared.waves.screen.GameScreen;

/**
 * A button which runs a runnable when its handle click method is called.
 * @author Jared Bass
 */	
public class Button
{
	public Rectangle hitbox;
	protected Texture buttonTexture;
	protected BitmapFont font;
	protected String text;
	protected Runnable action;

	/**
	 * @param x X-position of the point. (assuming origin is at bottom-left)
	 * @param y Y-position of the point. (assuming origin is at bottom-left)
	 * @param action Function that the button should execute when clicked.
	 * @param bg Background Texture of this Button.
	 * @param text Text that goes on the button.
	 */
	public Button(float x, float y, Runnable action, Texture bg, Object text)
	{
		GameScreen.content.add(buttonTexture = bg);
		font = new BitmapFont();
		GameScreen.content.add(font);
		this.text = text.toString();
		hitbox = new Rectangle(x, y, bg.getWidth(), bg.getHeight());
		this.action = action;
	}
	
	/**
	 * Updates the text of this button.
	 * @return The old text of the button.
	 */
	public String setText(String newText)
	{
		String oldText = text;
		text = newText;
		return oldText;
	}
	
	/**
	 * Checks if the point is within the button
	 * @param x X-position of the point. (assuming origin is top-left)
	 * @param y Y-position of the point. (assuming origin is top-left)
	 * @return <tt>true</tt> if the Point is within the Button, otherwise <tt>false</tt>.
	 */
	public boolean contains(float screenX, float screenY)
	{
		return hitbox.contains(screenX, Gdx.graphics.getHeight() - screenY); 
	}
	
	/**
	 * Handles the buttons click by running the passed runnable if its on a left click
	 */
	public boolean handleClick(int button)
	{
		if(button == Buttons.LEFT)
		{
			action.run();
			return true;
		}
		return false;
	}
	
	/**
	 * Draws the button
	 */
	public void draw(SpriteBatch batch)
	{
		batch.draw(buttonTexture, hitbox.x, hitbox.y);
		
		if(text != null)
		{
			//center the text in the middle of the button
			float textWidth = font.getBounds(text, 0, text.length()).width;
			float textHeight = font.getBounds(text, 0, text.length()).height;
			float x = hitbox.x + (hitbox.width/2 - textWidth/2);
			float y = (hitbox.y + textHeight) + (hitbox.height/2 - textHeight/2);
			
			font.draw(batch, text, x, y);
		}
	}
}
