package com.nyhammer.newMON.entities;

import com.nyhammer.newMON.graphics.Model;
import com.nyhammer.newMON.math.Transformation;

/**
 * @since Version 0.1.0a
 * 
 * @author McFlyboy
 *
 */
public class ModelEntity extends Entity{
	public Model model;
	public ModelEntity(){
		super();
		model = new Model();
	}
	public ModelEntity(Transformation transformation, Model model){
		super(transformation);
		this.model = model;
	}
	public ModelEntity(Transformation transformation){
		super(transformation);
		model = new Model();
	}
	public ModelEntity(Model model){
		super();
		this.model = model;
	}
}