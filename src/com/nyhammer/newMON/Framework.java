package com.nyhammer.newMON;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.*;

/**
 * @since Version 0.1.0a
 * 
 * @author McFlyboy
 *
 */
public class Framework{
	private static GLFWErrorCallback errorCallback;
	public static String getLWJGLVersion(){
		return Version.getVersion();
	}
	public static void init(){
		glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
		if(!glfwInit()){
			throw new IllegalStateException("Could not initialize GLFW form LWJGL!");
		}
	}
	public static void terminate(){
		glfwTerminate();
		errorCallback.free();
	}
}