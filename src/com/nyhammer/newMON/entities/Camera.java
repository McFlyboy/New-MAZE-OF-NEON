package com.nyhammer.newMON.entities;

import com.nyhammer.newMON.PlayerControls;
import com.nyhammer.newMON.input.Controller;
import com.nyhammer.newMON.input.Keyboard;
import com.nyhammer.newMON.input.Mouse;
import com.nyhammer.newMON.math.vector.Vector3f;

/**
 * @since Version 0.1.0a
 * 
 * @author McFlyboy
 *
 */
public class Camera extends Entity{
	private float walkSpeed = 3f;
	private float rotationSpeed = 1f / 16f;
	private float controllerRotationSpeed = 2000f;
	public Vector3f viewPosition = new Vector3f();
	public Camera(){
		super();
	}
	public void move(float delta){
		Vector3f deltaPosition = new Vector3f();
		Vector3f deltaAngle = new Vector3f();
		if(PlayerControls.gameFocused){
			if(Keyboard.isKeyDown(Keyboard.KEY_W)){
				deltaPosition.z++;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_S)){
				deltaPosition.z--;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_A)){
				deltaPosition.x--;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_D)){
				deltaPosition.x++;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_Q) | Controller.isButtonDown(Controller.BUTTON_X)){
				deltaPosition.y--;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_E) | Controller.isButtonDown(Controller.BUTTON_A)){
				deltaPosition.y++;
			}
			if(Keyboard.isKeyPressed(Keyboard.KEY_R) | Controller.isButtonPressed(Controller.BUTTON_Y)){
				transformation.position.y = 0.5f;
			}
			deltaAngle.y = Mouse.getDXpos();
			deltaAngle.x = Mouse.getDYpos();
			float rx = Controller.getAxisState(Controller.AXIS_RX);
			float ry = Controller.getAxisState(Controller.AXIS_RY);
			if(rx != 0f){
				deltaAngle.y = rx * controllerRotationSpeed * delta;
			}
			if(ry != 0f){
				deltaAngle.x = -ry * controllerRotationSpeed * delta;
			}
			deltaAngle.mul(rotationSpeed);
			transformation.angle.add(deltaAngle);
			if(transformation.angle.x < -90){
				transformation.angle.x = -90;
			}
			if(transformation.angle.x > 90){
				transformation.angle.x = 90;
			}
			deltaPosition.normalize();
			float lx = Controller.getAxisState(Controller.AXIS_LX);
			float ly = Controller.getAxisState(Controller.AXIS_LY);
			if(lx != 0f){
				deltaPosition.x = lx;
			}
			if(ly != 0f){
				deltaPosition.z = ly;
			}
			deltaPosition.mul(walkSpeed * delta);
			Vector3f rotatedDeltaPosition = Vector3f.rotate(deltaPosition, new Vector3f(0, 1, 0), transformation.angle.y);
			transformation.position.add(new Vector3f(rotatedDeltaPosition.x, rotatedDeltaPosition.y, rotatedDeltaPosition.z));
		}
		viewPosition = transformation.position;
		viewPosition = Vector3f.rotate(viewPosition, new Vector3f(0, 0, 1), -transformation.angle.z);
		viewPosition = Vector3f.rotate(viewPosition, new Vector3f(0, 1, 0), -transformation.angle.y);
		viewPosition = Vector3f.rotate(viewPosition, new Vector3f(1, 0, 0), -transformation.angle.x);
	}
}