package com.nyhammer.newMON;

import static org.lwjgl.glfw.GLFW.*;

import com.nyhammer.newMON.ui.GameWindow;

/**
 * @since Version 0.0.1a
 * 
 * @author McFlyboy
 *
 */
public class Timer{
	private static double lastFrameTime;
	private static double lastFpsTime;
	private static int fpsCount;
	private static int fps;
	public static double getTime(){
		return glfwGetTime();
	}
	public static int getFPS(){
		return fps;
	}
	public static void init(){
		double time = getTime();
		lastFrameTime = time;
		lastFpsTime = time;
	}
	public static float getDelta(){
		double time = getTime();
		float delta = (float)(time - lastFrameTime);
		lastFrameTime = time;
		return delta;
	}
	public static void updateFPS(){
		if(getTime() - lastFpsTime > 1.0){
			fps = fpsCount;
			GameWindow.setTitle(String.format("%s | (FPS: %d)", Main.TITLE, fps));
			fpsCount = 0;
			lastFpsTime++;
		}
		fpsCount++;
	}
}