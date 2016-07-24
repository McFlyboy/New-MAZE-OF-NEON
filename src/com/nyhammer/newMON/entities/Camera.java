package com.nyhammer.newMON.entities;

import com.nyhammer.newMON.input.Keyboard;
import com.nyhammer.newMON.math.vector.Vector2f;
import com.nyhammer.newMON.math.vector.Vector3f;

/**
 * @since Version 0.0.1a
 * 
 * @author McFlyboy
 *
 */
public class Camera extends Entity{
	private static final float WALK_SPEED = 3f;
	private static final float ROTATION_SPEED = 50f;
	private Vector3f cameraAngle = new Vector3f();
	public Camera(){
		super();
	}
	public void move(float delta){
		Vector2f deltaPosition = new Vector2f();
		if(Keyboard.getKeyState(Keyboard.KEY_LEFT)){
			cameraAngle.y += ROTATION_SPEED * delta;
		}
		if(Keyboard.getKeyState(Keyboard.KEY_RIGHT)){
			cameraAngle.y -= ROTATION_SPEED * delta;
		}
		if(Keyboard.getKeyState(Keyboard.KEY_UP)){
			transformation.angle.x--;
		}
		if(Keyboard.getKeyState(Keyboard.KEY_DOWN)){
			transformation.angle.x++;
		}
		if(Keyboard.getKeyState(Keyboard.KEY_W)){
			deltaPosition.y++;
		}
		if(Keyboard.getKeyState(Keyboard.KEY_S)){
			deltaPosition.y--;
		}
		if(Keyboard.getKeyState(Keyboard.KEY_A)){
			deltaPosition.x--;
		}
		if(Keyboard.getKeyState(Keyboard.KEY_D)){
			deltaPosition.x++;
		}
		
		deltaPosition.normalize();
		deltaPosition.mul(WALK_SPEED * delta);
		Vector2f rotatedDeltaPosition = Vector2f.rotate(deltaPosition, cameraAngle.y);
		transformation.position.x += rotatedDeltaPosition.x;
		transformation.position.z += rotatedDeltaPosition.y;
	}
}