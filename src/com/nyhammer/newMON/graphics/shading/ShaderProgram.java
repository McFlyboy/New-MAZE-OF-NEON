package com.nyhammer.newMON.graphics.shading;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import com.nyhammer.newMON.math.matrix.Matrix4f;
import com.nyhammer.newMON.math.vector.Vector2f;
import com.nyhammer.newMON.math.vector.Vector3f;
import com.nyhammer.newMON.ui.GameWindow;
import com.nyhammer.newMON.util.ResourceLoader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

/**
 * @since Version 0.1.0a
 * 
 * @author McFlyboy
 *
 */
public abstract class ShaderProgram{
	private int vertexShaderID;
	private int fragmentShaderID;
	private int programID;
	private FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	public ShaderProgram(String vertexShaderFilename, String fragmentShaderFilename){
		vertexShaderID = loadShader(vertexShaderFilename, GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentShaderFilename, GL_FRAGMENT_SHADER);
		programID = glCreateProgram();
		glAttachShader(programID, vertexShaderID);
		glAttachShader(programID, fragmentShaderID);
		bindAttribs();
		glLinkProgram(programID);
		glValidateProgram(programID);
		getUniformLocations();
	}
	public void start(){
		glUseProgram(programID);
	}
	public static void stop(){
		glUseProgram(0);
	}
	protected void loadInt(int location, int i){
		glUniform1i(location, i);
	}
	protected void loadFloat(int location, float f){
		glUniform1f(location, f);
	}
	protected void loadBoolean(int location, boolean b){
		int i = GL_FALSE;
		if(b){
			i = GL_TRUE;
		}
		glUniform1i(location, i);
	}
	protected void loadVector2f(int location, Vector2f vec){
		glUniform2f(location, vec.x, vec.y);
	}
	protected void loadVector3f(int location, Vector3f vec){
		glUniform3f(location, vec.x, vec.y, vec.z);
	}
	protected void loadMatrix4f(int location, Matrix4f mat){
		mat.store(matrixBuffer);
		matrixBuffer.flip();
		glUniformMatrix4fv(location, false, matrixBuffer);
		matrixBuffer.clear();
	}
	protected abstract void getUniformLocations();
	protected int getUniformLocation(String name){
		return glGetUniformLocation(programID, name);
	}
	protected abstract void bindAttribs();
	protected void bindAttrib(int index, String name){
		glBindAttribLocation(programID, index, name);
	}
	private int loadShader(String filepath, int type){
		StringBuilder sourceCode = ResourceLoader.loadShaderCode(filepath);
		int shaderID = glCreateShader(type);
		glShaderSource(shaderID, sourceCode);
		glCompileShader(shaderID);
		if(glGetShaderi(shaderID, GL_COMPILE_STATUS) != GL_TRUE){
			System.err.println("Error in shader-file: " + filepath);
			System.err.println(glGetShaderInfoLog(shaderID, 500));
			GameWindow.close();
		}
		return shaderID;
	}
	public void dispose(){
		glDetachShader(programID, vertexShaderID);
		glDetachShader(programID, fragmentShaderID);
		glDeleteShader(vertexShaderID);
		glDeleteShader(fragmentShaderID);
		glDeleteProgram(programID);
	}
}