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
 * @since Version 0.1.0a
 * 
 * @author McFlyboy
 *
 */
public class Main{
	public static final int VERSION_MAJOR = 0;
	public static final int VERSION_MINOR = 1;
	public static final int VERSION_REVISION = 0;
	public static final int VERSION_SUB_REVISION = 0;
	public static final String PRE_VERSION_SUFFIX = "a";
	public static final String TITLE = "New MAZE OF NEON - Version " + getVersion();
	private ModelEntity cube;
	private ModelEntity pointZeroPlane;
	private MONShader shader;
	private Camera camera;
	public static final float FOV = 70f;
	public static final float NEAR_PLANE = 0.1f;
	public static final float FAR_PLANE = 1000f;
	public static boolean gameFocused = false;
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
			cube = new ModelEntity();
			cube.model.bind();
			cube.model.setFaces(new int[]{
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
			cube.model.addAttrib(0, 3, new float[]{
					-0.5f, -0.5f, -0.5f,
					0.5f, -0.5f, -0.5f,
					0.5f, 0.5f, -0.5f,
					-0.5f, 0.5f, -0.5f,
					-0.5f, -0.5f, 0.5f,
					0.5f, -0.5f, 0.5f,
					0.5f, 0.5f, 0.5f,
					-0.5f, 0.5f, 0.5f
			});
			cube.model.addAttrib(1, 3, new float[]{
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
			cube.transformation.position.z = 7f;
			pointZeroPlane = new ModelEntity(cube.model);
			pointZeroPlane.transformation.scale.y = 0.05f;
			camera = new Camera();
			camera.transformation.position.y = 0.5f;
			Render.setCamera(camera);
			shader = new MONShader();
			Render.setShader(shader);
			Render.setProjectionMatrix(GameWindow.getWidth(), GameWindow.getHeight(), FOV, NEAR_PLANE, FAR_PLANE);
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
		if(Mouse.getButtonState(Mouse.BUTTON_LEFT)){
			Mouse.getDXpos();
			Mouse.getDYpos();
			gameFocused = true;
			Mouse.setCursorMode(Mouse.CURSOR_MODE_DISABLED);
		}
		if(GameWindow.isFocused() == false){
			gameFocused = false;
			Mouse.setCursorMode(Mouse.CURSOR_MODE_NORMAL);
		}
		cube.transformation.angle.x += 25f * delta;
		cube.transformation.angle.y += 50f * delta;
		cube.transformation.angle.z += 12.5f * delta;
		camera.move(delta);
	}
	private void render(){
		Render.clear();
		Render.render(cube);
		Render.render(pointZeroPlane);
		GameWindow.update();
	}
	private void stop(){
		MONShader.stop();
		shader.dispose();
		cube.model.dispose();
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