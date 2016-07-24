package com.nyhammer.newMON.math;

import com.nyhammer.newMON.math.vector.Vector3f;

/**
 * @since Version 0.0.1a
 * 
 * @author McFlyboy
 *
 */
public class Transformation{
	public Vector3f position;
	public Vector3f angle;
	public Vector3f scale;
	public Transformation(){
		position = new Vector3f();
		angle = new Vector3f();
		scale = new Vector3f(1f, 1f, 1f);
	}
	public Transformation(Vector3f position, Vector3f angle, Vector3f scale){
		this.position = position;
		this.angle = angle;
		this.scale = scale;
	}
}