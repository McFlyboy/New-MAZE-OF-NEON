package com.nyhammer.newMON.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import com.nyhammer.newMON.entities.ModelEntity;
import com.nyhammer.newMON.graphics.shading.shaderPrograms.MONShader;
import com.nyhammer.newMON.entities.Camera;
import com.nyhammer.newMON.math.Transformation;
import com.nyhammer.newMON.math.matrix.Matrix4f;
import com.nyhammer.newMON.math.matrix.MatrixUtil;

/**
 * @since Version 0.1.0a
 * 
 * @author McFlyboy
 *
 */
public class Render{
	private static Camera camera;
	private static MONShader shader;
	private static Matrix4f projection;
	private static boolean wireframe;
	public static boolean isWireframe(){
		return wireframe;
	}
	public static void setCamera(Camera camera){
		Render.camera = camera;
	}
	public static void setShader(MONShader shader){
		Render.shader = shader;
		shader.start();
	}
	public static void setProjectionMatrix(int windowWidth, int windowHeight, float fov, float nearPlane, float farPlane){
		projection = MatrixUtil.createProjectionMatrix((float)(windowWidth) / (float)(windowHeight), fov, nearPlane, farPlane);
	}
	public static void clear(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	public static void setClearColor(float red, float green, float blue, float alpha){
		glClearColor(red, green, blue, alpha);
	}
	public static void enableDepthTest(boolean depthTest){
		if(depthTest){
			glEnable(GL_DEPTH_TEST);
		}
		else{
			glDisable(GL_DEPTH_TEST);
		}
	}
	public static void setWireframe(boolean wireframe){
		Render.wireframe = wireframe;
		int mode = GL_FILL;
		if(wireframe){
			mode = GL_LINE;
		}
		glPolygonMode(GL_FRONT_AND_BACK, mode);
	}
	public static void render(ModelEntity entity){
		entity.model.bind();
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		shader.loadProjection(projection);
		shader.loadView(MatrixUtil.createViewMatrix(new Transformation(camera.viewPosition, camera.transformation.angle, camera.transformation.scale)));
		shader.loadTransformation(MatrixUtil.createTransformation(entity.transformation));
		glDrawElements(GL_TRIANGLES, entity.model.getIndexCount(), GL_UNSIGNED_INT, 0L);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		Model.unbind();
	}
}