package com.nyhammer.newMON.math.vector;

/**
 * @since Version 0.1.0a
 * 
 * @author McFlyboy
 *
 */
public class Vector2f{
	public float x, y;
	public Vector2f(){
		x = 0f;
		y = 0f;
	}
	public Vector2f(float x, float y){
		this.x = x;
		this.y = y;
	}
	public static Vector2f add(Vector2f vec0, Vector2f vec1){
		return new Vector2f(vec0.x + vec1.x, vec0.y + vec1.y);
	}
	public static void add(Vector2f vec0, Vector2f vec1, Vector2f dest){
		dest.x = vec0.x + vec1.x;
		dest.y = vec0.y + vec1.y;
	}
	public void add(Vector2f vec){
		x += vec.x;
		y += vec.y;
	}
	public static Vector2f sub(Vector2f vec0, Vector2f vec1){
		return new Vector2f(vec0.x - vec1.x, vec0.y - vec1.y);
	}
	public static void sub(Vector2f vec0, Vector2f vec1, Vector2f dest){
		dest.x = vec0.x - vec1.x;
		dest.y = vec0.y - vec1.y;
	}
	public void sub(Vector2f vec){
		x -= vec.x;
		y -= vec.y;
	}
	public static Vector2f mul(Vector2f vec, float scalar){
		return new Vector2f(vec.x * scalar, vec.y * scalar);
	}
	public static void mul(Vector2f vec, float scalar, Vector2f dest){
		dest.x = vec.x * scalar;
		dest.y = vec.y * scalar;
	}
	public void mul(float scalar){
		x *= scalar;
		y *= scalar;
	}
	public static Vector2f div(Vector2f vec, float scalar){
		if(scalar == 0f){
			return new Vector2f();
		}
		return new Vector2f(vec.x / scalar, vec.y / scalar);
	}
	public static void div(Vector2f vec, float scalar, Vector2f dest){
		if(scalar == 0f){
			dest.x = 0f;
			dest.y = 0f;
		}
		else{
			dest.x = vec.x / scalar;
			dest.y = vec.y / scalar;
		}
	}
	public void div(float scalar){
		if(scalar == 0f){
			x = 0f;
			y = 0f;
		}
		else{
			x /= scalar;
			y /= scalar;
		}
	}
	public static Vector2f rotate(Vector2f vec, float angle){
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);
		return new Vector2f((float)(vec.x * cos - vec.y * sin), (float)(vec.x * sin + vec.y * cos));
	}
	public static void rotate(Vector2f vec, float angle, Vector2f dest){
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);
		dest.x = (float)(vec.x * cos - vec.y * sin);
		dest.y = (float)(vec.x * sin + vec.y * cos);
	}
	public void rotate(float angle){
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);
		float x = (float)(this.x * cos - this.y * sin);
		float y = (float)(this.x * sin + this.y * cos);
		this.x = x;
		this.y = y;
	}
	public static float dot(Vector2f vec0, Vector2f vec1){
		return vec0.x * vec1.x + vec0.y * vec1.y;
	}
	public float abs(){
		return (float)Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	public static Vector2f normalize(Vector2f vec){
		float length = vec.abs();
		if(length == 0f){
			return new Vector2f();
		}
		return new Vector2f(vec.x / length, vec.y / length);
	}
	public static void normalize(Vector2f vec, Vector2f dest){
		float length = vec.abs();
		if(length == 0f){
			dest.x = 0f;
			dest.y = 0f;
		}
		else{
			dest.x = vec.x / length;
			dest.y = vec.y / length;
		}
	}
	public void normalize(){
		float length = abs();
		if(length == 0f){
			x = 0f;
			y = 0f;
		}
		else{
			x /= length;
			y /= length;
		}
	}
	public static Vector2f negate(Vector2f vec){
		return new Vector2f(-vec.x, -vec.y);
	}
	public static void negate(Vector2f vec, Vector2f dest){
		dest.x = -vec.x;
		dest.y = -vec.y;
	}
	public void negate(){
		x *= -1f;
		y *= -1f;
	}
	public void reset(){
		x = 0;
		y = 0;
	}
	@Override
	public String toString(){
		return String.format("[%f, %f]", x, y);
	}
}