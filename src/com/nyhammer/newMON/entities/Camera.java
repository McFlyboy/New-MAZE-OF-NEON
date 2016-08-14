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
	private float rotationSpeed = 1f / 16f;
	public Vector3f viewPosition = new Vector3f();
	private boolean[] WSADQE = new boolean[6];
	public Camera(){
		super();
	}
	public void move(float delta){
		Vector3f deltaPosition = new Vector3f();
		Vector3f deltaAngle = new Vector3f();
		if(Main.gameFocused){
			int keyState = Keyboard.getKeyState(Keyboard.KEY_W);
			if(keyState == Keyboard.KEY_PRESSED){
				WSADQE[0] = true;
			}
			if(keyState == Keyboard.KEY_RELEASED){
				WSADQE[0] = false;
			}
			keyState = Keyboard.getKeyState(Keyboard.KEY_S);
			if(keyState == Keyboard.KEY_PRESSED){
				WSADQE[1] = true;
			}
			if(keyState == Keyboard.KEY_RELEASED){
				WSADQE[1] = false;
			}
			keyState = Keyboard.getKeyState(Keyboard.KEY_A);
			if(keyState == Keyboard.KEY_PRESSED){
				WSADQE[2] = true;
			}
			if(keyState == Keyboard.KEY_RELEASED){
				WSADQE[2] = false;
			}
			keyState = Keyboard.getKeyState(Keyboard.KEY_D);
			if(keyState == Keyboard.KEY_PRESSED){
				WSADQE[3] = true;
			}
			if(keyState == Keyboard.KEY_RELEASED){
				WSADQE[3] = false;
			}
			keyState = Keyboard.getKeyState(Keyboard.KEY_Q);
			if(keyState == Keyboard.KEY_PRESSED){
				WSADQE[4] = true;
			}
			if(keyState == Keyboard.KEY_RELEASED){
				WSADQE[4] = false;
			}
			keyState = Keyboard.getKeyState(Keyboard.KEY_E);
			if(keyState == Keyboard.KEY_PRESSED){
				WSADQE[5] = true;
			}
			if(keyState == Keyboard.KEY_RELEASED){
				WSADQE[5] = false;
			}
			if(WSADQE[0]){
				deltaPosition.z++;
			}
			if(WSADQE[1]){
				deltaPosition.z--;
			}
			if(WSADQE[2]){
				deltaPosition.x--;
			}
			if(WSADQE[3]){
				deltaPosition.x++;
			}
			if(WSADQE[4]){
				deltaPosition.y--;
			}
			if(WSADQE[5]){
				deltaPosition.y++;
			}
			if(Keyboard.getKeyState(Keyboard.KEY_R) == Keyboard.KEY_PRESSED){
				transformation.position.y = 0.5f;
			}
			deltaAngle.y = Mouse.getDXpos();
			deltaAngle.x = Mouse.getDYpos();
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
		deltaPosition.mul(walkSpeed * delta);
		Vector3f rotatedDeltaPosition = Vector3f.rotate(deltaPosition, new Vector3f(0, 1, 0), transformation.angle.y);
		transformation.position.add(new Vector3f(rotatedDeltaPosition.x, rotatedDeltaPosition.y, rotatedDeltaPosition.z));
		viewPosition = transformation.position;
		viewPosition = Vector3f.rotate(viewPosition, new Vector3f(0, 0, 1), -transformation.angle.z);
		viewPosition = Vector3f.rotate(viewPosition, new Vector3f(0, 1, 0), -transformation.angle.y);
		viewPosition = Vector3f.rotate(viewPosition, new Vector3f(1, 0, 0), -transformation.angle.x);
	}
}