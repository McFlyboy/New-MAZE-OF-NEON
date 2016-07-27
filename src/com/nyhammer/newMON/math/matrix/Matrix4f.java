package com.nyhammer.newMON.math.matrix;

import java.nio.FloatBuffer;

import com.nyhammer.newMON.math.vector.Vector3f;

/**
 * @since Version 0.1.0a
 * 
 * @author McFlyboy
 *
 */
public class Matrix4f{
	public float m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33;
	public void reset(){
		m00 = 0f;
		m01 = 0f;
		m02 = 0f;
		m03 = 0f;
		m10 = 0f;
		m11 = 0f;
		m12 = 0f;
		m13 = 0f;
		m20 = 0f;
		m21 = 0f;
		m22 = 0f;
		m23 = 0f;
		m30 = 0f;
		m31 = 0f;
		m32 = 0f;
		m33 = 0f;
	}
	public void setIdentity(){
		m00 = 1f;
		m01 = 0f;
		m02 = 0f;
		m03 = 0f;
		m10 = 0f;
		m11 = 1f;
		m12 = 0f;
		m13 = 0f;
		m20 = 0f;
		m21 = 0f;
		m22 = 1f;
		m23 = 0f;
		m30 = 0f;
		m31 = 0f;
		m32 = 0f;
		m33 = 1f;
	}
	public static void rotate(float angle, Vector3f axis, Matrix4f mat, Matrix4f dest){
		float sin = (float)(Math.sin(angle));
		float cos = (float)(Math.cos(angle));
		float oneMinusCos = 1.0f - cos;
		float xy = axis.x * axis.y;
		float yz = axis.y * axis.z;
		float xz = axis.x * axis.z;
		float xSin = axis.x * sin;
		float ySin = axis.y * sin;
		float zSin = axis.z * sin;
		float f00 = axis.x * axis.x * oneMinusCos + cos;
		float f01 = xy * oneMinusCos + zSin;
		float f02 = xz * oneMinusCos - ySin;
		float f10 = xy * oneMinusCos - zSin;
		float f11 = axis.y * axis.y * oneMinusCos + cos;
		float f12 = yz * oneMinusCos + xSin;
		float f20 = xz * oneMinusCos + ySin;
		float f21 = yz * oneMinusCos - xSin;
		float f22 = axis.z * axis.z * oneMinusCos + cos;
		float t00 = mat.m00 * f00 + mat.m10 * f01 + mat.m20 * f02;
		float t01 = mat.m01 * f00 + mat.m11 * f01 + mat.m21 * f02;
		float t02 = mat.m02 * f00 + mat.m12 * f01 + mat.m22 * f02;
		float t03 = mat.m03 * f00 + mat.m13 * f01 + mat.m23 * f02;
		float t10 = mat.m00 * f10 + mat.m10 * f11 + mat.m20 * f12;
		float t11 = mat.m01 * f10 + mat.m11 * f11 + mat.m21 * f12;
		float t12 = mat.m02 * f10 + mat.m12 * f11 + mat.m22 * f12;
		float t13 = mat.m03 * f10 + mat.m13 * f11 + mat.m23 * f12;
		dest.m20 = mat.m00 * f20 + mat.m10 * f21 + mat.m20 * f22;
		dest.m21 = mat.m01 * f20 + mat.m11 * f21 + mat.m21 * f22;
		dest.m22 = mat.m02 * f20 + mat.m12 * f21 + mat.m22 * f22;
		dest.m23 = mat.m03 * f20 + mat.m13 * f21 + mat.m23 * f22;
		dest.m00 = t00;
		dest.m01 = t01;
		dest.m02 = t02;
		dest.m03 = t03;
		dest.m10 = t10;
		dest.m11 = t11;
		dest.m12 = t12;
		dest.m13 = t13;
	}
	public static void scale(Vector3f scale, Matrix4f mat, Matrix4f dest){
		dest.m00 = mat.m00 * scale.x;
		dest.m01 = mat.m01 * scale.x;
		dest.m02 = mat.m02 * scale.x;
		dest.m03 = mat.m03 * scale.x;
		dest.m10 = mat.m10 * scale.y;
		dest.m11 = mat.m11 * scale.y;
		dest.m12 = mat.m12 * scale.y;
		dest.m13 = mat.m13 * scale.y;
		dest.m20 = mat.m20 * scale.z;
		dest.m21 = mat.m21 * scale.z;
		dest.m22 = mat.m22 * scale.z;
		dest.m23 = mat.m23 * scale.z;
	}
	public static void translate(Vector3f translation, Matrix4f mat, Matrix4f dest){
		dest.m30 = mat.m30 + translation.x;
		dest.m31 = mat.m31 + translation.y;
		dest.m32 = mat.m32 + translation.z;
	}
	public void setPerspective(float aspectRatio, float fov, float nearPlane, float farPlane){
		float scale = (float)Math.tan(Math.toRadians(fov / 2f));
		float frustumLength = nearPlane - farPlane;
		m00 = 1f / (scale * aspectRatio);
		m11 = 1f / scale;
		m22 = (-nearPlane - farPlane) / frustumLength;
		m23 = 1;
		m32 = 2 * farPlane * nearPlane / frustumLength;
		m33 = 0;
	}
	@Override
	public String toString(){
		StringBuilder string = new StringBuilder();
		string.append(m00 + ", ");
		string.append(m10 + ", ");
		string.append(m20 + ", ");
		string.append(m30 + ",\n");
		string.append(m01 + ", ");
		string.append(m11 + ", ");
		string.append(m21 + ", ");
		string.append(m31 + ",\n");
		string.append(m02 + ", ");
		string.append(m12 + ", ");
		string.append(m22 + ", ");
		string.append(m32 + ",\n");
		string.append(m03 + ", ");
		string.append(m13 + ", ");
		string.append(m23 + ", ");
		string.append(m33 + ",\n");
		return string.toString();
	}
	public void store(FloatBuffer buffer){
		buffer.put(m00);
		buffer.put(m01);
		buffer.put(m02);
		buffer.put(m03);
		buffer.put(m10);
		buffer.put(m11);
		buffer.put(m12);
		buffer.put(m13);
		buffer.put(m20);
		buffer.put(m21);
		buffer.put(m22);
		buffer.put(m23);
		buffer.put(m30);
		buffer.put(m31);
		buffer.put(m32);
		buffer.put(m33);
	}
}