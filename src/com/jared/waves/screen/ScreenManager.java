package com.jared.waves.screen;

/**
 * Class to manage the screens and display the correct one
 * @author Jared Bass
 */
public class ScreenManager
{
	private static Screen currentScreen;
	
	public static void setScreen(Screen screen)
	{
		if(currentScreen != null)
			currentScreen.dispose();
		currentScreen = screen;
		currentScreen.create();
	}
	
	public static Screen getScreen()
	{
		return currentScreen;
	}
}
