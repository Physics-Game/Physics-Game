package com.jared.waves.screen;

/**
 * 
 * @author Jason Carrete
 */
public interface Screen
{
	public abstract void create();
	
	public abstract void render();
	
	public abstract void resize(int width, int height);
	
	public abstract void dispose();
}
