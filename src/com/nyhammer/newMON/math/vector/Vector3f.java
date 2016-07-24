package com.nyhammer.newMON.math.vector;

/**
 * @since Version 0.0.1a
 * 
 * @author McFlyboy
 *
 */
public class Vector3f{
	public float x, y, z;
	public Vector3f(){
		x = 0f;
		y = 0f;
		z = 0f;
	}
	public Vector3f(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public static Vector3f add(Vector3f vec0, Vector3f vec1){
		return new Vector3f(vec0.x + vec1.x, vec0.y + vec1.y, vec0.z + vec1.z);
	}
	public static void add(Vector3f vec0, Vector3f vec1, Vector3f dest){
		dest.x = vec0.x + vec1.x;
		dest.y = vec0.y + vec1.y;
		dest.z = vec0.z + vec1.z;
	}
	public void add(Vector3f vec){
		x += vec.x;
		y += vec.y;
		z += vec.z;
	}
	public static Vector3f sub(Vector3f vec0, Vector3f vec1){
		return new Vector3f(vec0.x - vec1.x, vec0.y - vec1.y, vec0.z - vec1.z);
	}
	public static void sub(Vector3f vec0, Vector3f vec1, Vector3f dest){
		dest.x = vec0.x - vec1.x;
		dest.y = vec0.y - vec1.y;
		dest.z = vec0.z - vec1.z;
	}
	public void sub(Vector3f vec){
		x -= vec.x;
		y -= vec.y;
		z -= vec.z;
	}
	public static Vector3f mul(Vector3f vec, float scalar){
		return new Vector3f(vec.x * scalar, vec.y * scalar, vec.z * scalar);
	}
	public static void mul(Vector3f vec, float scalar, Vector3f dest){
		dest.x = vec.x * scalar;
		dest.y = vec.y * scalar;
		dest.z = vec.z * scalar;
	}
	public void mul(float scalar){
		x *= scalar;
		y *= scalar;
		z *= scalar;
	}
	public static Vector3f div(Vector3f vec, float scalar){
		if(scalar == 0f){
			return new Vector3f();
		}
		return new Vector3f(vec.x / scalar, vec.y / scalar, vec.z / scalar);
	}
	public static void div(Vector3f vec, float scalar, Vector3f dest){
		if(scalar == 0f){
			dest.x = 0f;
			dest.y = 0f;
			dest.z = 0f;
		}
		else{
			dest.x = vec.x / scalar;
			dest.y = vec.y / scalar;
			dest.z = vec.z / scalar;
		}
	}
	public void div(float scalar){
		if(scalar == 0f){
			x = 0f;
			y = 0f;
			z = 0f;
		}
		else{
			x /= scalar;
			y /= scalar;
			z /= scalar;
		}
	}
	public static float dot(Vector3f vec0, Vector3f vec1){
		return vec0.x * vec1.x + vec0.y * vec1.y + vec0.z * vec1.z;
	}
	public static Vector3f cross(Vector3f vec0, Vector3f vec1){
		return new Vector3f(vec0.y * vec1.z - vec0.z * vec1.y, vec0.z * vec1.x - vec0.x * vec1.z, vec0.x * vec1.y - vec0.y * vec1.x);
	}
	public static void cross(Vector3f vec0, Vector3f vec1, Vector3f dest){
		dest.x = vec0.y * vec1.z - vec0.z * vec1.y;
		dest.y = vec0.z * vec1.x - vec0.x * vec1.z;
		dest.z = vec0.x * vec1.y - vec0.y * vec1.x;
	}
	public void cross(Vector3f vec){
		x = y * vec.z - z * vec.y;
		y = z * vec.x - x * vec.z;
		z = x * vec.y - y * vec.x;
	}
	public float abs(){
		return (float)Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}
	public static Vector3f normalize(Vector3f vec){
		float length = vec.abs();
		if(length == 0f){
			return new Vector3f();
		}
		return new Vector3f(vec.x / length, vec.y / length, vec.z / length);
	}
	public static void normalize(Vector3f vec, Vector3f dest){
		float length = vec.abs();
		if(length == 0f){
			dest.x = 0f;
			dest.y = 0f;
			dest.z = 0f;
		}
		else{
			dest.x = vec.x / length;
			dest.y = vec.y / length;
			dest.z = vec.z / length;
		}
	}
	public void normalize(){
		float length = abs();
		if(length == 0f){
			x = 0f;
			y = 0f;
			z = 0f;
		}
		else{
			x /= length;
			y /= length;
			z /= length;
		}
	}
	public static Vector3f negate(Vector3f vec){
		return new Vector3f(-vec.x, -vec.y, -vec.z);
	}
	public static void negate(Vector3f vec, Vector3f dest){
		dest.x = -vec.x;
		dest.y = -vec.y;
		dest.z = -vec.z;
	}
	public void negate(){
		x *= -1f;
		y *= -1f;
		z *= -1f;
	}
	public void reset(){
		x = 0;
		y = 0;
		z = 0;
	}
	@Override
	public String toString(){
		return String.format("[%f, %f, %f]", x, y, z);
	}
}