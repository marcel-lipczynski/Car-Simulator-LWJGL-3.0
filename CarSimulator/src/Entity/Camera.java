package Entity;

import LibrarySupport.*;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;


import static RenderEngine.WindowManager.getWindow;
import static org.lwjgl.glfw.GLFW.*;


public class Camera {

    private float distanceFromPlayer = 30;
    private float angleAroundPlayer = 0;

    private Vector3f position = new Vector3f(0,0,0);

    private float pitch = 0;
    private float yaw = 0;
    private float roll;

    private Player player;

    private GLFWScrollCallback scrollCallback;
    private GLFWCursorPosCallback cursorPosCallback;
    private GLFWMouseButtonCallback mouseButton;

    public Camera(Player player){

        glfwSetMouseButtonCallback(getWindow().getId(), mouseButton = new MouseButton());
        glfwSetScrollCallback(getWindow().getId(), scrollCallback = new MouseScroll());
        glfwSetCursorPosCallback(getWindow().getId(), cursorPosCallback = new MousePosition());

        this.player = player;
    }

    public void move(){
        calculateZoom();
        calculatePitch();
        calculateAngleAroundPlayer();


        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();

        calculateCameraPosition(horizontalDistance, verticalDistance);

        this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
    }


    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }

    private void calculateCameraPosition(float horizontalDistance, float verticalDistance){
        float theta = player.getRotY() + angleAroundPlayer;
        float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
        position.x = player.getPosition().x - offsetX;
        position.z = player.getPosition().z - offsetZ;
        position.y = player.getPosition().y + verticalDistance + 10;

    }

//  Zoom camera position after mouse scroll
    private void calculateZoom(){
        float zoomLevel = MouseScroll.getDy() * 0.2f;
        if(distanceFromPlayer-zoomLevel > 2 && distanceFromPlayer-zoomLevel < 100) {
            distanceFromPlayer -= zoomLevel;
        }
    }

//    Up/Down camera Move with right press
    private void calculatePitch(){
        if(MouseButton.isRightButtonDown()){
            float pitchChange = (float) (MousePosition.getDy());
            if(pitchChange < 0){
                pitch += 0.5;
            }else {
                pitch -= 0.5;
            }
        }
    }

//    Left/Right move with left button press
    private void calculateAngleAroundPlayer(){
        if(MouseButton.isLeftButtonDown()){
            float angleChange = (float)(MousePosition.getDx());
            if(angleChange > 0){
                angleAroundPlayer += 0.5;
            } else {
                angleAroundPlayer -= 0.5;
            }

        }
    }

    private float calculateHorizontalDistance(){
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance(){
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }
}
