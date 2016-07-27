package com.nyhammer.newMON.graphics.shading.shaderPrograms;

import com.nyhammer.newMON.graphics.shading.ShaderProgram;
import com.nyhammer.newMON.math.matrix.Matrix4f;

/**
 * @since Version 0.1.0a
 * 
 * @author McFlyboy
 *
 */
public class MONShader extends ShaderProgram{
	private static final String shaderLocation = "/shaders/";
	private static final String vertexShaderFilename = shaderLocation + "MONVertexShader.vsh";
	private static final String fragmentShaderFilename = shaderLocation + "MONFragmentShader.fsh";
	private int transformationLocation;
	private int projectionLocation;
	private int viewLocation;
	public MONShader(){
		super(vertexShaderFilename, fragmentShaderFilename);
	}
	@Override
	protected void bindAttribs(){
		super.bindAttrib(0, "vertex");
		super.bindAttrib(1, "color");
	}
	@Override
	protected void getUniformLocations(){
		transformationLocation = super.getUniformLocation("transformation");
		projectionLocation = super.getUniformLocation("projection");
		viewLocation = super.getUniformLocation("view");
	}
	public void loadTransformation(Matrix4f transformation){
		super.loadMatrix4f(transformationLocation, transformation);
	}
	public void loadProjection(Matrix4f projection){
		super.loadMatrix4f(projectionLocation, projection);
	}
	public void loadView(Matrix4f view){
		super.loadMatrix4f(viewLocation, view);
	}
}