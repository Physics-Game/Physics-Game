package com.jared.waves.widget;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Represents properties a barrier has on the screen
 * @author Jason Carrete
 * @author Alex Berman
 */
public interface Widget
{
	/**
	 * Checks if the point is within this Widget.
	 * @param x X-position of the point. (assuming origin is at top-left)
	 * @param y Y-position of the point. (assuming origin is at top-left)
	 * @return <tt>true</tt> if the point is within this Widget, otherwise <tt>false</tt>.
	 */
	boolean contains(float screenX, float screenY);
	
	/**
	 * Handles what happens when this Widget is clicked.
	 * @param x X-position of the point. (assuming origin is at top-left)
	 * @param y Y-position of the point. (assuming origin is at top-left)
	 * @return <tt>true</tt> if the click was handled successfully by this Widget, otherwise <tt>false</tt>.
	 */
	boolean handleClick(float screenX, float screenY, int button);
	
	/**
	 * Handles what happens when the mouse enters this Widget.
	 * @param x X-position of the point. (assuming origin is at top-left)
	 * @param y Y-position of the point. (assuming origin is at top-left)
	 * @return <tt>true</tt> if mouseEntered() was handled successfully, otherwise <tt>false</tt>.
	 */
	boolean mouseEntered(float screenX, float screenY);
	
	/**
	 * Handles what happens when the mouse exits this Widget.
	 * @param x X-position of the point. (assuming origin is at top-left)
	 * @param y Y-position of the point. (assuming origin is at top-left)
	 * @return <tt>true</tt> if mouseExited() was handled successfully, otherwise <tt>false</tt>.
	 */
	boolean mouseExited(float screenX, float screenY);
	
	/**
	 * Draws this Widget and its subcomponents with the specified SpriteBatch.
	 */
	void draw(SpriteBatch batch);
	
	/**
	 * Updates this Widget's position based on the new width and height of the screen.
	 * @param width New width of the screen.
	 * @param height New height of the screen.
	 */
	void resize(int width, int height);
}
