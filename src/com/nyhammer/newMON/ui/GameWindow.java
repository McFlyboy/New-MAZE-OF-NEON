package com.nyhammer.newMON.ui;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowFocusCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;

import com.nyhammer.newMON.Main;
import com.nyhammer.newMON.graphics.Render;

/**
 * @since Version 0.1.0a
 * 
 * @author McFlyboy
 *
 */
public class GameWindow{
	private static long windowID;
	private static GLFWWindowSizeCallback sizeCallback;
	private static GLFWFramebufferSizeCallback frameBufferSizeCallback;
	private static GLFWWindowFocusCallback focusCallback;
	private static int width, height;
	private static boolean focused;
	public static long getWindowID(){
		return windowID;
	}
	public static int getWidth(){
		return width;
	}
	public static int getHeight(){
		return height;
	}
	public static boolean windowShouldClose(){
		return glfwWindowShouldClose(windowID);
	}
	public static boolean isFocused(){
		return focused;
	}
	public static void create(int width, int height, String title, boolean fullscreen){
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		long monitor = NULL;
		if(fullscreen){
			monitor = glfwGetPrimaryMonitor();
		}
		windowID = glfwCreateWindow(width, height, title, monitor, NULL);
		if(windowID == NULL){
			throw new RuntimeException("Could not create the window!");
		}
		GameWindow.width = width;
		GameWindow.height = height;
		glfwSetWindowSizeCallback(windowID, sizeCallback = new GLFWWindowSizeCallback(){
			@Override
			public void invoke(long window, int width, int height){
				GameWindow.width = width;
				GameWindow.height = height;
			}
		});
		glfwSetWindowFocusCallback(windowID, focusCallback = new GLFWWindowFocusCallback(){
			@Override
			public void invoke(long window, boolean focused){
				GameWindow.focused = focused;
			}
		});
		center();
		glfwMakeContextCurrent(windowID);
		glfwShowWindow(windowID);
		GL.createCapabilities();
		glfwSetFramebufferSizeCallback(windowID, frameBufferSizeCallback = new GLFWFramebufferSizeCallback(){
			@Override
			public void invoke(long window, int width, int height){
				glViewport(0, 0, width, height);
				Render.setProjectionMatrix(width, height, Main.FOV, Main.NEAR_PLANE, Main.FAR_PLANE);
			}
		});
	}
	public static void center(){
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(windowID, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
	}
	public static void setSize(int width, int height){
		glfwSetWindowSize(windowID, width, height);
	}
	public static void setTitle(String title){
		glfwSetWindowTitle(windowID, title);
	}
	public static void setVSync(boolean vsync){
		int interval = 0;
		if(vsync){
			interval = 1;
		}
		glfwSwapInterval(interval);
	}
	public static void update(){
		glfwSwapBuffers(windowID);
		glfwPollEvents();
	}
	public static void close(){
		glfwSetWindowShouldClose(windowID, true);
	}
	public static void destroy(){
		sizeCallback.free();
		frameBufferSizeCallback.free();
		focusCallback.free();
		glfwDestroyWindow(windowID);
	}
}