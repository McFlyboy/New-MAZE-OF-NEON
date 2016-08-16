package com.nyhammer.newMON;

import com.nyhammer.newMON.entities.Camera;
import com.nyhammer.newMON.entities.ModelEntity;
import com.nyhammer.newMON.graphics.Render;
import com.nyhammer.newMON.graphics.shading.shaderPrograms.MONShader;
import com.nyhammer.newMON.input.Controller;
import com.nyhammer.newMON.input.Keyboard;
import com.nyhammer.newMON.input.Mouse;
import com.nyhammer.newMON.ui.GameWindow;
import com.nyhammer.newMON.util.ResourceLoader;

/**
 * @since Version 0.1.0a
 * 
 * @author McFlyboy
 *
 */
public class Main{
	public static final int VERSION_MAJOR = 0;
	public static final int VERSION_MINOR = 1;
	public static final int VERSION_REVISION = 1;
	public static final int VERSION_SUB_REVISION = 0;
	public static final String PRE_VERSION_SUFFIX = "a";
	public static final String TITLE = "New MAZE OF NEON - Version " + getVersion();
	private MONShader shader;
	private Camera camera;
	private ModelEntity testLevel;
	private ModelEntity finish;
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
			GameWindow.setVSync(false);
			Render.setClearColor(0f, 0.3f, 0.6f, 1f);
			Render.enableDepthTest(true);
			Keyboard.create();
			Mouse.create();
			Timer.init();
			testLevel = new ModelEntity(ResourceLoader.loadOBJModel("levels/Test-maze.obj"));
			finish = new ModelEntity(ResourceLoader.loadOBJModel("Finish-F.obj"));
			finish.transformation.position.x = 6.5f;
			finish.transformation.position.y = 0.35f;
			finish.transformation.position.z = 6.5f;
			finish.transformation.scale.mul(0.5f);
			camera = new Camera();
			camera.transformation.position.x = 6.5f;
			camera.transformation.position.y = 0.5f;
			camera.transformation.position.z = -6.5f;
			Render.setCamera(camera);
			shader = new MONShader();
			Render.setShader(shader);
			Render.setProjectionMatrix(GameWindow.getWidth(), GameWindow.getHeight(), FOV, NEAR_PLANE, FAR_PLANE);
			run();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			stop();
		}
	}
	private void run(){
		final double targetFrameTime = 1.0 / GameWindow.getMonitorRefreshRate();
		double unprocessedTime = 0;
		while(!GameWindow.windowShouldClose()){
			boolean renderReady = false;
			float delta = Timer.getDelta();
			if(unprocessedTime >= 0){
				renderReady = true;
				unprocessedTime -= targetFrameTime;
			}
			update(delta);
			unprocessedTime += delta;
			if(renderReady){
				render();
				Timer.updateFPS();
			}
			else{
				try{
					Thread.sleep(1);
				}
				catch(InterruptedException e){
					e.printStackTrace();
					GameWindow.close();
				}
			}
		}
		stop();
	}
	private void update(float delta){
		if(Controller.isPresent()){
			Controller.updateButtonState();
			Controller.updateAxisStates();
		}
		if(Keyboard.getKeyState(Keyboard.KEY_ESCAPE) == Keyboard.KEY_PRESSED){
			GameWindow.close();
		}
		if(Keyboard.getKeyState(Keyboard.KEY_F11) == Keyboard.KEY_PRESSED){
			if(!GameWindow.isFullscreen()){
				GameWindow.setFullscreen(true);
			}
			else{
				GameWindow.setFullscreen(false);
			}
		}
		if(Keyboard.getKeyState(Keyboard.KEY_V) == Keyboard.KEY_PRESSED){
			if(!GameWindow.isVSync()){
				GameWindow.setVSync(true);
			}
			else{
				GameWindow.setVSync(false);
			}
		}
		if(Keyboard.getKeyState(Keyboard.KEY_F) == Keyboard.KEY_PRESSED){
			if(!Render.isWireframe()){
				Render.setWireframe(true);
			}
			else{
				Render.setWireframe(false);
			}
		}
		if(Mouse.getButtonState(Mouse.BUTTON_LEFT) == Mouse.BUTTON_PRESSED && !gameFocused){
			Mouse.getDXpos();
			Mouse.getDYpos();
			gameFocused = true;
			Mouse.setCursorMode(Mouse.CURSOR_MODE_DISABLED);
		}
		if(GameWindow.isFocused() == false){
			gameFocused = false;
			Mouse.setCursorMode(Mouse.CURSOR_MODE_NORMAL);
		}
		finish.transformation.angle.y += 50f * delta;
		finish.transformation.position.y += (float)(Math.sin(Timer.getTime()) * delta / 8.0);
		camera.move(delta);
	}
	private void render(){
		Render.clear();
		Render.render(testLevel);
		Render.render(finish);
		GameWindow.update();
	}
	private void stop(){
		MONShader.stop();
		shader.dispose();
		testLevel.model.dispose();
		finish.model.dispose();
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