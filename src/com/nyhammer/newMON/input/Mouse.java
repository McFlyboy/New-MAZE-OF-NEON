package com.nyhammer.newMON.input;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWCursorEnterCallback;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

import com.nyhammer.newMON.ui.GameWindow;

/**
 * @since Version 0.0.1a
 * 
 * @author McFlyboy
 *
 */
public class Mouse{
	/** Mouse buttons*/
	public static final int
		BUTTON_1      = 0,
		BUTTON_2      = 1,
		BUTTON_3      = 2,
		BUTTON_4      = 3,
		BUTTON_5      = 4,
		BUTTON_6      = 5,
		BUTTON_7      = 6,
		BUTTON_8      = 7,
		BUTTON_LAST   = BUTTON_8,
		BUTTON_LEFT   = BUTTON_1,
		BUTTON_RIGHT  = BUTTON_2,
		BUTTON_MIDDLE = BUTTON_3;
	/** Cursor mode. */
	public static final int
		CURSOR_NORMAL   = 0x34001,
		CURSOR_HIDDEN   = 0x34002,
		CURSOR_DISABLED = 0x34003;
	private static GLFWMouseButtonCallback buttonCallback;
	private static GLFWCursorPosCallback positionCallback;
	private static GLFWCursorEnterCallback enterCallback;
	private static GLFWScrollCallback scrollCallback;
	private static boolean[] buttons = new boolean[20];
	private static int xpos, ypos, lastXPos, lastYPos;
	private static boolean entered;
	private static int scrollXOffset, scrollYOffset;
	public static int getXpos(){
		return xpos;
	}
	public static int getYpos(){
		return ypos;
	}
	public static int getDXpos(){
		int dxpos = xpos - lastXPos;
		lastXPos = xpos;
		return dxpos;
	}
	public static int getDYpos(){
		int dypos = ypos - lastYPos;
		lastYPos = ypos;
		return dypos;
	}
	public static boolean getButtonState(int buttonID){
		return buttons[buttonID];
	}
	public static boolean isEntered(){
		return entered;
	}
	public static int getScrollXOffset(){
		int xoffset = scrollXOffset;
		scrollXOffset = 0;
		return xoffset;
	}
	public static int getScrollYOffset(){
		int yoffset = scrollYOffset;
		scrollYOffset = 0;
		return yoffset;
	}
	public static void create(){
		glfwSetMouseButtonCallback(GameWindow.getWindowID(), buttonCallback = new GLFWMouseButtonCallback(){
			@Override
			public void invoke(long window, int button, int action, int mods){
				buttons[button] = action != GLFW_RELEASE;
			}
		});
		glfwSetCursorPosCallback(GameWindow.getWindowID(), positionCallback = new GLFWCursorPosCallback(){
			@Override
			public void invoke(long window, double xpos, double ypos){
				Mouse.xpos = (int)xpos;
				Mouse.ypos = (int)ypos;
			}
		});
		glfwSetCursorEnterCallback(GameWindow.getWindowID(), enterCallback = new GLFWCursorEnterCallback(){
			@Override
			public void invoke(long window, boolean entered){
				Mouse.entered = entered;
				Mouse.lastXPos = xpos;
				Mouse.lastYPos = ypos;
			}
		});
		glfwSetScrollCallback(GameWindow.getWindowID(), scrollCallback = new GLFWScrollCallback(){
			@Override
			public void invoke(long window, double xoffset, double yoffset){
				Mouse.scrollXOffset = (int)xoffset;
				Mouse.scrollYOffset = (int)yoffset;
			}
		});
	}
	public static void reset(){
		for(int i = 0; i < buttons.length; i++){
			buttons[i] = false;
		}
	}
	public static void setCursorMode(int cursorMode){
		glfwSetInputMode(GameWindow.getWindowID(), GLFW_CURSOR, cursorMode);
	}
	public static void destroy(){
		buttonCallback.free();
		positionCallback.free();
		enterCallback.free();
		scrollCallback.free();
	}
}