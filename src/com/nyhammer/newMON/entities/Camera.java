package com.nyhammer.newMON.entities;

import com.nyhammer.newMON.Main;
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
	private float rotationSpeed = 7.5f;
	public Vector3f viewPosition = new Vector3f();
	public Camera(){
		super();
	}
	public void move(float delta){
		Vector3f deltaPosition = new Vector3f();
		Vector3f deltaAngle = new Vector3f();
		if(Main.gameFocused){
			if(Keyboard.getKeyState(Keyboard.KEY_W)){
				deltaPosition.z++;
			}
			if(Keyboard.getKeyState(Keyboard.KEY_S)){
				deltaPosition.z--;
			}
			if(Keyboard.getKeyState(Keyboard.KEY_A)){
				deltaPosition.x--;
			}
			if(Keyboard.getKeyState(Keyboard.KEY_D)){
				deltaPosition.x++;
			}
			if(Keyboard.getKeyState(Keyboard.KEY_Q)){
				deltaPosition.y--;
			}
			if(Keyboard.getKeyState(Keyboard.KEY_E)){
				deltaPosition.y++;
			}
			if(Keyboard.getKeyState(Keyboard.KEY_R)){
				transformation.position.y = 0.5f;
			}
			deltaAngle.y = Mouse.getDXpos();
			deltaAngle.x = Mouse.getDYpos();
		}
		deltaAngle.mul(rotationSpeed * delta);
		transformation.angle.add(deltaAngle);
		if(transformation.angle.x < -90){
			transformation.angle.x = -90;
		}
		if(transformation.angle.x > 90){
			transformation.angle.x = 90;
		}
		deltaPosition.normalize();
		deltaPosition.mul(walkSpeed * delta);
		Vector3f rotatedDeltaPosition = Vector3f.rotate(deltaPosition, new Vector3f(0, 1, 0), transformation.angle.y);
		transformation.position.add(new Vector3f(rotatedDeltaPosition.x, rotatedDeltaPosition.y, rotatedDeltaPosition.z));
		viewPosition = transformation.position;
		viewPosition = Vector3f.rotate(viewPosition, new Vector3f(0, 0, 1), -transformation.angle.z);
		viewPosition = Vector3f.rotate(viewPosition, new Vector3f(0, 1, 0), -transformation.angle.y);
		viewPosition = Vector3f.rotate(viewPosition, new Vector3f(1, 0, 0), -transformation.angle.x);
	}
}