package com.nyhammer.newMON.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;

/**
 * @since Version 0.1.0a
 * 
 * @author McFlyboy
 *
 */
public class Model{
	private int vaoID;
	private int faceVboID;
	private List<Integer> vboIDs = new ArrayList<Integer>();
	private int indexCount;
	public Model(){
		vaoID = glGenVertexArrays();
	}
	public int getVaoID(){
		return vaoID;
	}
	public int getIndexCount(){
		return indexCount;
	}
	public void bind(){
		glBindVertexArray(vaoID);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, faceVboID);
	}
	public static void unbind(){
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}
	public void setFaces(int[] data){
		faceVboID = glGenBuffers();
		IntBuffer dataBuffer = createIntBuffer(data);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, faceVboID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, dataBuffer, GL_STATIC_DRAW);
		indexCount = data.length;
	}
	public void addAttrib(int index, int size, float[] data){
		int vboID = glGenBuffers();
		vboIDs.add(vboID);
		FloatBuffer dataBuffer = createFloatBuffer(data);
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, dataBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0L);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	private FloatBuffer createFloatBuffer(float[] data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	private IntBuffer createIntBuffer(int[] data){
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	public void dispose(){
		for(int vboID : vboIDs){
			glDeleteBuffers(vboID);
		}
		glDeleteBuffers(faceVboID);
		glDeleteVertexArrays(vaoID);
	}
}