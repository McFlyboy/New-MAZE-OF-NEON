package com.nyhammer.newMON.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.nyhammer.newMON.graphics.Model;
import com.nyhammer.newMON.math.vector.Vector3f;
import com.nyhammer.newMON.ui.GameWindow;

/**
 * @since Version 0.1.1a
 * 
 * @author McFlyboy
 *
 */
public class ResourceLoader{
	private static final String shaderLocation = "/shaders/";
	private static final String modelLocation = "/models/";
	private static Random random = new Random();
	public static StringBuilder loadShaderCode(String filepath){
		StringBuilder sourceCode = new StringBuilder();
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(Class.class.getResourceAsStream(shaderLocation + filepath)));
			String line;
			while((line = reader.readLine()) != null){
				sourceCode.append(line).append("\n");
			}
			reader.close();
		}
		catch(FileNotFoundException e){
			System.err.println("Could not locate the file: " + shaderLocation + filepath);
			e.printStackTrace();
			GameWindow.close();
		}
		catch(IOException e){
			System.err.println("Could not read the file: " + shaderLocation + filepath);
			e.printStackTrace();
			GameWindow.close();
		}
		return sourceCode;
	}
	/**Returns a model with preset colors in random positions for now...*/
	public static Model loadOBJModel(String filepath){
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector3f> colors = new ArrayList<Vector3f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		List<Integer> faces = new ArrayList<Integer>();
		float[] vertexArray = null;
		float[] colorArray = null;
		float[] normalArray = null;
		int[] faceArray = null;
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(Class.class.getResourceAsStream(modelLocation + filepath)));
			String line;
			String[] currentLine = null;
			while(true){
				line = reader.readLine();
				currentLine = line.split(" ");
				if(currentLine[0].equals("v")){
					Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					vertices.add(vertex);
				}
				if(currentLine[0].equals("vn")){
					Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					normals.add(normal);
				}
				if(currentLine[0].equals("f")){
					colors.add(new Vector3f(0, 0, 0));
					colors.add(new Vector3f(0, 0, 1));
					colors.add(new Vector3f(0, 1, 0));
					colors.add(new Vector3f(0, 1, 1));
					colors.add(new Vector3f(1, 0, 0));
					colors.add(new Vector3f(1, 0, 1));
					colors.add(new Vector3f(1, 1, 0));
					colors.add(new Vector3f(1, 1, 1));
					colorArray = new float[vertices.size() * 3];
					normalArray = new float[vertices.size() * 3];
					break;
				}
			}
			while(line != null){
				if(!currentLine[0].equals("f")){
					line = reader.readLine();
					continue;
				}
				currentLine = line.split(" ");
				String[] vertex1 = currentLine[1].split("/");
				String[] vertex2 = currentLine[2].split("/");
				String[] vertex3 = currentLine[3].split("/");
				processVertex(vertex1, faces, colors, normals, colorArray, normalArray);
				processVertex(vertex2, faces, colors, normals, colorArray, normalArray);
				processVertex(vertex3, faces, colors, normals, colorArray, normalArray);
				line = reader.readLine();
			}
			reader.close();
		}
		catch(FileNotFoundException e){
			System.err.println("Could not locate the file: " + modelLocation + filepath);
			e.printStackTrace();
			GameWindow.close();
		}
		catch(IOException e){
			System.err.println("Could not read the file: " + modelLocation + filepath);
			e.printStackTrace();
			GameWindow.close();
		}
		vertexArray = new float[vertices.size() * 3];
		faceArray = new int[faces.size()];
		int vertexPointer = 0;
		for(Vector3f vertex : vertices){
			vertexArray[vertexPointer++] = vertex.x;
			vertexArray[vertexPointer++] = vertex.y;
			vertexArray[vertexPointer++] = vertex.z;
		}
		for(int i = 0; i < faces.size(); i++){
			faceArray[i] = faces.get(i);
		}
		Model model = new Model();
		model.bind();
		model.setFaces(faceArray);
		model.addAttrib(0, 3, vertexArray);
		model.addAttrib(1, 3, colorArray);
		Model.unbind();
		return model;
	}
	private static void processVertex(String[] vertexData, List<Integer> faces, List<Vector3f> colors, List<Vector3f> normals, float[] colorArray, float[] normalArray){
		int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
		faces.add(currentVertexPointer);
		Vector3f currentColor = colors.get(random.nextInt(colors.size()));
		colorArray[currentVertexPointer * 3] = currentColor.x;
		colorArray[currentVertexPointer * 3 + 1] = currentColor.y;
		colorArray[currentVertexPointer * 3 + 2] = currentColor.z;
		Vector3f currentNormal = normals.get(Integer.parseInt(vertexData[2]) - 1);
		normalArray[currentVertexPointer * 3] = currentNormal.x;
		normalArray[currentVertexPointer * 3 + 1] = currentNormal.y;
		normalArray[currentVertexPointer * 3 + 2] = currentNormal.z;
	}
}