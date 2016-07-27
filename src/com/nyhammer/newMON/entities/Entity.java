package com.nyhammer.newMON.entities;

import com.nyhammer.newMON.math.Transformation;

/**
 * @since Version 0.1.0a
 * 
 * @author McFlyboy
 *
 */
public class Entity{
	public Transformation transformation;
	public Entity(){
		transformation = new Transformation();
	}
	public Entity(Transformation transformation){
		this.transformation = transformation;
	}
}