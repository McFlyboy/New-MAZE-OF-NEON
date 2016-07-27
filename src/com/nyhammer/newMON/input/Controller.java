package com.nyhammer.newMON.input;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/**
 * @since Version 0.1.0a
 * 
 * @author McFlyboy
 *
 */
public class Controller{
	public static final int BUTTON_A = 0;
	public static final int BUTTON_B = 1;
	public static final int BUTTON_X = 2;
	public static final int BUTTON_Y = 3;
	public static final int BUTTON_LB = 4;
	public static final int BUTTON_RB = 5;
	public static final int BUTTON_BACK = 6;
	public static final int BUTTON_START = 7;
	public static final int BUTTON_LS = 8;
	public static final int BUTTON_RS = 9;
	public static final int BUTTON_DPAD_UP = 10;
	public static final int BUTTON_DPAD_RIGHT = 11;
	public static final int BUTTON_DPAD_DOWN = 12;
	public static final int BUTTON_DPAD_LEFT = 13;
	public static final int AXIS_LX = 0;
	public static final int AXIS_LY = 1;
	public static final int AXIS_RX = 2;
	public static final int AXIS_RY = 3;
	public static final int AXIS_LT = 4;
	public static final int AXIS_RT = 5;
	private static boolean[] buttons = new boolean[14];
	private static float[] axes = new float[6];
	private static float innerThreshold = 0.2f;
	private static float outerThreshold = 0.8f;
	public static String getName(){
		return glfwGetJoystickName(GLFW_JOYSTICK_1);
	}
	public static boolean isPresent(){
		return glfwJoystickPresent(GLFW_JOYSTICK_1);
	}
	public static boolean getButtonState(int buttonID){
		return buttons[buttonID];
	}
	public static float getAxisState(int axisID){
		return axes[axisID];
	}
	public static float getInnerThreshold(){
		return innerThreshold;
	}
	public static void setInnerThreshold(float innerThreshold){
		Controller.innerThreshold = innerThreshold;
	}
	public static float getOuterThreshold(){
		return outerThreshold;
	}
	public static void setOuterThreshold(float outerThreshold){
		Controller.outerThreshold = outerThreshold;
	}
	public static void updateButtonState(){
		ByteBuffer buttonStates = glfwGetJoystickButtons(GLFW_JOYSTICK_1);
		if(buttonStates == null){
			return;
		}
		int i = 0;
		while(buttonStates.hasRemaining()){
			int state = buttonStates.get();
			if(state != GLFW_RELEASE){
				buttons[i] = true;
			}
			else{
				buttons[i] = false;
			}
			i++;
		}
	}
	public static void updateAxisStates(){
		FloatBuffer axisStates = glfwGetJoystickAxes(GLFW_JOYSTICK_1);
		if(axisStates == null){
			return;
		}
		int i = 0;
		while(axisStates.hasRemaining()){
			float state = axisStates.get();
			if(i < 4){
				if(Math.abs(state) < innerThreshold){
					state = 0f;
				}
				if(state > outerThreshold){
					state = 1;
				}
				if(state < -outerThreshold){
					state = -1;
				}
			}
			axes[i] = state;
			i++;
		}
	}
}