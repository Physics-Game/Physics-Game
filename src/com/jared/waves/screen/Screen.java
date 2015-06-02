package com.jared.waves.screen;

/**
 * Create a basis for all screens
 * @author Jared Bass
 */
public interface Screen
{
	public abstract void create();
	
	public abstract void render();
	
	public abstract void dispose();
}
