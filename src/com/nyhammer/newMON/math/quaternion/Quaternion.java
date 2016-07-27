package com.nyhammer.newMON.math.quaternion;

import com.nyhammer.newMON.math.vector.Vector3f;

/**
 * @since Version 0.1.0a
 * 
 * @author McFlyboy
 *
 */
public class Quaternion{
	public float x, y, z, w;
	public Quaternion(){
		x = 0f;
		y = 0f;
		z = 0f;
		w = 0f;
	}
	public Quaternion(float x, float y, float z, float w){
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	public float abs(){
		return (float)Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2) + Math.pow(w, 2));
	}
	public static Quaternion normalize(Quaternion src){
		float length = src.abs();
		if(length == 0){
			return new Quaternion();
		}
		return new Quaternion(src.x / length, src.y / length, src.z / length, src.w / length);
	}
	public void normalize(){
		float length = abs();
		if(length == 0){
			return;
		}
		x /= length;
		y /= length;
		z /= length;
		w /= length;
	}
	public static Quaternion conjugate(Quaternion src){
		return new Quaternion(-src.x, -src.y, -src.z, src.w);
	}
	public void conjugate(){
		x *= -1;
		y *= -1;
		z *= -1;
	}
	public static Quaternion mul(Quaternion src0, Quaternion src1){
		Quaternion dest = new Quaternion();
		dest.w = src0.w * src1.w - src0.x * src1.x - src0.y * src1.y - src0.z * src1.z;
		dest.x = src0.x * src1.w + src0.w * src1.x + src0.y * src1.z - src0.z * src1.y;
		dest.y = src0.y * src1.w + src0.w * src1.y + src0.z * src1.x - src0.x * src1.z;
		dest.z = src0.z * src1.w + src0.w * src1.z + src0.x * src1.y - src0.y * src1.x;
		return dest;
	}
	public void mul(Quaternion src){
		float w = this.w * src.w - this.x * src.x - this.y * src.y - this.z * src.z;
		float x = this.x * src.w + this.w * src.x + this.y * src.z - this.z * src.y;
		float y = this.y * src.w + this.w * src.y + this.z * src.x - this.x * src.z;
		float z = this.z * src.w + this.w * src.z + this.x * src.y - this.y * src.x;
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	public static Quaternion mul(Quaternion q, Vector3f v){
		Quaternion dest = new Quaternion();
		dest.w = -q.x * v.x - q.y * v.y - q.z * v.z;
		dest.x = q.w * v.x + q.y * v.z - q.z * v.y;
		dest.y = q.w * v.y + q.z * v.x - q.x * v.z;
		dest.z = q.w * v.z + q.x * v.y - q.y * v.x;
		return dest;
	}
	public void mul(Vector3f vec){
		float w = -this.x * vec.x - this.y * vec.y - this.z * vec.z;
		float x = this.w * vec.x + this.y * vec.z - this.z * vec.y;
		float y = this.w * vec.y + this.z * vec.x - this.x * vec.z;
		float z = this.w * vec.z + this.x * vec.y - this.y * vec.x;
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	@Override
	public String toString(){
		return String.format("(%f, %f, %f, %f)", x, y, z, w);
	}
}