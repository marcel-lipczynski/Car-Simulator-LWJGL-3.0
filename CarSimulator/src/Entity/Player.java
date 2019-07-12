package Entity;

import GUI.GuiRenderer;
import GUI.GuiTexture;
import LibrarySupport.Keyboard;
import LibrarySupport.Vector2f;
import LibrarySupport.Vector3f;
import Models.TexturedModel;
import RenderEngine.Loader;
import RenderEngine.WindowManager;
import Terrain.Terrain;
import org.lwjgl.glfw.GLFWKeyCallback;

import java.util.ArrayList;
import java.util.List;

import static RenderEngine.WindowManager.getWindow;
import static org.lwjgl.glfw.GLFW.*;


public class Player extends Entity{

    private static final float MAX_SPEED = 90;
    private static final float TURN_SPEED = 50;
    private static final float GRAVITY = -50;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardSpeed = 0;

    private int endurance;

    private boolean crashed = false;

    private boolean gameOver = false;

    private GLFWKeyCallback keyCallback;

    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale, 10);
        glfwSetKeyCallback(getWindow().getId(), keyCallback = new Keyboard());
        this.endurance = 3;

    }

    public void move(Terrain terrain){
        if(!crashed) {
            checkInputs();
//        System.out.println(position);
            if(currentSpeed == 0){
                currentTurnSpeed = 0;
            }
            super.increaseRotation(0, (float) (currentTurnSpeed * WindowManager.getFrameTimeSeconds()), 0);
            float distance = (float) (currentSpeed * WindowManager.getFrameTimeSeconds());
            float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
            float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
            super.increasePosition(dx, 0, dz);
            upwardSpeed += GRAVITY * WindowManager.getFrameTimeSeconds();
            super.increasePosition(0, (float) (upwardSpeed * WindowManager.getFrameTimeSeconds()), 0);

            float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
            if (super.getPosition().y < terrainHeight) {
                upwardSpeed = 0;
                super.getPosition().y = terrainHeight;
            }
            System.out.println(position);
        }else{
//          After crash game will wait for enter
            if(Keyboard.isKeyDown(GLFW_KEY_ENTER)){
                this.crashed = false;
                this.currentSpeed = 0;
                this.endurance--;
                super.position= new Vector3f(150, 5, -240);
                super.setRotY(180);
                if(endurance < 1){
                    this.gameOver = true;
                }
            }
        }
    }

    private void checkInputs(){
//        move forward and backward
        if(Keyboard.isKeyDown(GLFW_KEY_W)){
            if(currentSpeed < MAX_SPEED) {
                this.currentSpeed += MAX_SPEED / 110;
            }
        }else if(Keyboard.isKeyDown(GLFW_KEY_S)){
            if(currentSpeed > -MAX_SPEED/3) {
                this.currentSpeed -= MAX_SPEED / 110;
            }
        }else {
//            if W/S buttons arent pressed speed will be going to 0
            if(currentSpeed > MAX_SPEED/190){
                this.currentSpeed -= MAX_SPEED/190;
            }else if(currentSpeed < -MAX_SPEED/190){
                this.currentSpeed += MAX_SPEED/190;
            }else {
                this.currentSpeed = 0;
            }
        }
//          turn Right/Left, but only if speed > 0
        if(Keyboard.isKeyDown(GLFW_KEY_D)){
            this.currentTurnSpeed = -TURN_SPEED;
        }else if(Keyboard.isKeyDown(GLFW_KEY_A)){
            this.currentTurnSpeed = TURN_SPEED;
        }else {
            this.currentTurnSpeed = 0;
        }
    }

//    crash detection
    public boolean checkCrash(Entity entity){
        if(position.x > 1600 || position.x < 0 || position.z > 0 || position.z < -1600){
            crashed = true;
        }
        if(entity.getType() == 0 &&  position.x <= entity.getPosition().x + 7 && position.x >= entity.getPosition().x -7 &&
                position.z  <= entity.getPosition().z + 7 && position.z >= entity.getPosition().z - 7 ){
            crashed = true;
        }
        if(entity.getType() == 1 &&  position.x <= entity.getPosition().x + 10 && position.x >= entity.getPosition().x -10 &&
            position.z  <= entity.getPosition().z + 10 && position.z >= entity.getPosition().z - 10 ){
            crashed = true;
        }
        if(entity.getType() == 2 &&  position.x <= entity.getPosition().x + 35 && position.x >= entity.getPosition().x -35 &&
            position.z  <= entity.getPosition().z + 40 && position.z >= entity.getPosition().z - 40 ){
            crashed = true;
        }
        if(entity.getType() == 3 &&  position.x <= entity.getPosition().x +39 && position.x >= entity.getPosition().x -39 &&
            position.z  <= entity.getPosition().z + 42 && position.z >= entity.getPosition().z - 42 ){
            crashed = true;
        }
        if(entity.getType() == 4 &&  position.x <= entity.getPosition().x + 30 && position.x >= entity.getPosition().x -30 &&
            position.z  <= entity.getPosition().z + 42 && position.z >= entity.getPosition().z - 42 ){
            crashed = true;
        }
        if(entity.getType() == 5 &&  position.x <= entity.getPosition().x + 40 && position.x >= entity.getPosition().x -40 &&
            position.z  <= entity.getPosition().z + 42 && position.z >= entity.getPosition().z - 42 ){
            crashed = true;
        }
        if(entity.getType() == 6 &&  position.x <= entity.getPosition().x + 37 && position.x >= entity.getPosition().x -37 &&
            position.z  <= entity.getPosition().z + 43 && position.z >= entity.getPosition().z - 43 ){
            crashed = true;
        }
        return crashed;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getEndurance() {
        return endurance;
    }
}


