package com.nyhammer.newMON.math.matrix;

import com.nyhammer.newMON.math.Transformation;
import com.nyhammer.newMON.math.vector.Vector3f;

/**
 * @since Version 0.1.0a
 * 
 * @author McFlyboy
 *
 */
public class MatrixUtil{
	public static Matrix4f createTransformation(Transformation transformation){
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(transformation.position, matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(transformation.angle.x), new Vector3f(1, 0, 0), matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(transformation.angle.y), new Vector3f(0, 1, 0), matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(transformation.angle.z), new Vector3f(0, 0, 1), matrix, matrix);
		Matrix4f.scale(transformation.scale, matrix, matrix);
		return matrix;
	}
	public static Matrix4f createProjectionMatrix(float aspectRatio, float fov, float nearPlane, float farPlane){
		Matrix4f matrix = new Matrix4f();
		matrix.setPerspective(aspectRatio, fov, nearPlane, farPlane);
		return matrix;
	}
	public static Matrix4f createViewMatrix(Transformation transformation){
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(Vector3f.negate(transformation.position), matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(-transformation.angle.x), new Vector3f(1, 0, 0), matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(-transformation.angle.y), new Vector3f(0, 1, 0), matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(-transformation.angle.z), new Vector3f(0, 0, 1), matrix, matrix);
		return matrix;
	}
}