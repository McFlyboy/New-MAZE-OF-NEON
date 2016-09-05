package com.nyhammer.newMON;

import com.nyhammer.newMON.entities.Camera;
import com.nyhammer.newMON.graphics.Render;
import com.nyhammer.newMON.input.Controller;
import com.nyhammer.newMON.input.Keyboard;
import com.nyhammer.newMON.input.Mouse;
import com.nyhammer.newMON.ui.GameWindow;

public class PlayerControls{
	public static boolean gameFocused = false;
	public static void update(){
		if(Controller.isPresent()){
			Controller.updateButtonState();
			Controller.updateAxisStates();
		}
		if(Keyboard.isKeyPressed(Keyboard.KEY_ESCAPE) | Controller.isButtonPressed(Controller.BUTTON_START)){
			GameWindow.close();
		}
		if(Keyboard.isKeyPressed(Keyboard.KEY_F11) | Controller.isButtonPressed(Controller.BUTTON_BACK)){
			if(!GameWindow.isFullscreen()){
				GameWindow.setFullscreen(true);
			}
			else{
				GameWindow.setFullscreen(false);
			}
		}
		if(Keyboard.isKeyPressed(Keyboard.KEY_V) | Controller.isButtonPressed(Controller.BUTTON_RB)){
			if(!GameWindow.isVSync()){
				GameWindow.setVSync(true);
			}
			else{
				GameWindow.setVSync(false);
			}
		}
		if(Keyboard.isKeyPressed(Keyboard.KEY_F) | Controller.isButtonPressed(Controller.BUTTON_LB)){
			if(!Render.isWireframe()){
				Render.setWireframe(true);
			}
			else{
				Render.setWireframe(false);
			}
		}
		if(Mouse.isButtonPressed(Mouse.BUTTON_LEFT) && !gameFocused){
			Mouse.getDXpos();
			Mouse.getDYpos();
			gameFocused = true;
			Mouse.setCursorMode(Mouse.CURSOR_MODE_DISABLED);
		}
		if(GameWindow.isFocused() == false){
			gameFocused = false;
			Mouse.setCursorMode(Mouse.CURSOR_MODE_NORMAL);
		}
	}
	public static void updateCamera(Camera camera, float delta){
		camera.move(delta);
	}
}