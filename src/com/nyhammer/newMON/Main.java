package com.nyhammer.newMON;

import com.nyhammer.newMON.entities.Camera;
import com.nyhammer.newMON.entities.ModelEntity;
import com.nyhammer.newMON.graphics.Model;
import com.nyhammer.newMON.graphics.Render;
import com.nyhammer.newMON.graphics.shading.shaderPrograms.MONShader;
import com.nyhammer.newMON.input.Controller;
import com.nyhammer.newMON.input.Keyboard;
import com.nyhammer.newMON.input.Mouse;
import com.nyhammer.newMON.ui.GameWindow;

/**
 * @since Version 0.0.1a
 * 
 * @author McFlyboy
 *
 */
public class Main{
	public static final int VERSION_MAJOR = 0;
	public static final int VERSION_MINOR = 0;
	public static final int VERSION_REVISION = 1;
	public static final int VERSION_SUB_REVISION = 0;
	public static final String PRE_VERSION_SUFFIX = "a";
	public static final String TITLE = "New MAZE OF NEON - Version " + getVersion();
	private ModelEntity entity;
	private ModelEntity zero;
	private MONShader shader;
	private Camera camera;
	public static float fov = 70f;
	public static float nearPlane = 0.1f;
	public static float farPlane = 1000f;
	public static String getVersion(){
		StringBuilder version = new StringBuilder();
		version.append(VERSION_MAJOR + "." + VERSION_MINOR + "." + VERSION_REVISION);
		String subRevision = String.format("%02d", VERSION_SUB_REVISION);
		if(!subRevision.equals("00")){
			version.append("_" + subRevision);
		}
		version.append(PRE_VERSION_SUFFIX);
		return version.toString();
	}
	private void start(){
		try{
			Framework.init();
			GameWindow.create(800, 600, TITLE, false);
			GameWindow.setVSync(true);
			Render.setClearColor(0f, 0.3f, 0.6f, 1f);
			Render.enableDepthTest(true);
			Keyboard.create();
			Mouse.create();
			Timer.init();
			entity = new ModelEntity();
			entity.model.bind();
			entity.model.setFaces(new int[]{
					0, 1, 2,
					0, 2, 3,
					4, 5, 6,
					4, 6, 7,
					0, 1, 5,
					0, 5, 4,
					0, 4, 7,
					0, 7, 3,
					1, 5, 6,
					1, 6, 2,
					3, 2, 6,
					3, 6, 7
			});
			entity.model.addAttrib(0, 3, new float[]{
					-0.5f, -0.5f, -0.5f,
					0.5f, -0.5f, -0.5f,
					0.5f, 0.5f, -0.5f,
					-0.5f, 0.5f, -0.5f,
					-0.5f, -0.5f, 0.5f,
					0.5f, -0.5f, 0.5f,
					0.5f, 0.5f, 0.5f,
					-0.5f, 0.5f, 0.5f
			});
			entity.model.addAttrib(1, 3, new float[]{
					0f, 0f, 1f,
					0f, 1f, 1f,
					1f, 1f, 0f,
					1f, 0f, 0f,
					1f, 1f, 0f,
					1f, 0f, 0f,
					0f, 0f, 1f,
					0f, 1f, 1f
			});
			Model.unbind();
			entity.transformation.position.z = 7f;
			zero = new ModelEntity(entity.model);
			zero.transformation.scale.y = 0.05f;
			camera = new Camera();
			camera.transformation.position.y = 0.75f;
			shader = new MONShader();
			Render.setShader(shader);
			Render.setProjectionMatrix(GameWindow.getWidth(), GameWindow.getHeight(), fov, nearPlane, farPlane);
			run();
		}
		finally{
			stop();
		}
	}
	private void run(){
		while(!GameWindow.windowShouldClose()){
			update(Timer.getDelta());
			render();
			Timer.updateFPS();
		}
		stop();
	}
	private void update(float delta){
		if(Controller.isPresent()){
			Controller.updateButtonState();
			Controller.updateAxisStates();
		}
		if(Keyboard.getKeyState(Keyboard.KEY_ESCAPE)){
			GameWindow.close();
		}
		entity.transformation.angle.x += 25f * delta;
		entity.transformation.angle.y += 50f * delta;
		entity.transformation.angle.z += 12.5f * delta;
		camera.move(delta);
	}
	private void render(){
		Render.clear();
		Render.render(entity, camera);
		Render.render(zero, camera);
		GameWindow.update();
	}
	private void stop(){
		MONShader.stop();
		shader.dispose();
		entity.model.dispose();
		Mouse.destroy();
		Keyboard.destroy();
		GameWindow.destroy();
		Framework.terminate();
		System.exit(0);
	}
	public static void main(String[] args){
		new Main().start();
	}
}