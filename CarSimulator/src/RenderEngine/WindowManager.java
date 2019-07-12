package RenderEngine;


import LibrarySupport.Sync;
import org.lwjgl.glfw.GLFWErrorCallback;

import java.awt.*;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.lwjgl.glfw.GLFW.*;

public class WindowManager {

//    error Callback GLFW
    private GLFWErrorCallback errorCallback;

//    windows Size declaration
    private static final int WIDTH = 1800;
    private static final int HEIGHT = 1000;

//    FPS
    public static final int FPS = 400;


//    Title
    private static final String TITLE = "Car Driving Simulator";

//    variable to calculate FPS
    private static double time;
    private static double processedTime = 0;
    private static double passedTime;
    private static double fpsCap = (double) 1 / FPS;

//    window
    private static Window window;

//    running checker
    private boolean running = false;

//=============================================

//    Constructor
    public WindowManager() {
    }

//    initialization
    public void init(){
//        Set error callbacks
        errorCallback = GLFWErrorCallback.createPrint();
        glfwSetErrorCallback(errorCallback);

//        initialize GLFW
        if(!glfwInit()){
            throw new IllegalStateException("Unable to initialize GLFW!");
        }

//        create Window
        window = new Window(WIDTH, HEIGHT, TITLE, true);

        time = getTime();

        running = true;
    }

//    destructor
    public void destroy(){
        window.destroy();
        glfwTerminate();
        errorCallback.free();
    }

//    update Window
    public void updateWindow(){
        if(isUpdateing()){
            window.update();
        }

    }

//   return delta
    public static double getFrameTimeSeconds(){
        return passedTime;
    }

    public static double getTime(){
        return (double) System.nanoTime() / 1000000000.0;
    }

//    open Window checker
    public boolean isRunning(){
        if(window.isClosing()) {
            running = false;
        }
        return running;
    }

//    WIDTH getter
    public static int getWIDTH() {
        return WIDTH;
    }

//    Height getter
    public static int getHEIGHT() {
        return HEIGHT;
    }

//    window getter
    public static Window getWindow() {
        return window;
    }

    public static boolean isUpdateing(){
        double nextTime = getTime();
        passedTime = nextTime - time;
        processedTime += passedTime;
        time = nextTime;

        while (processedTime > fpsCap){
            processedTime -= fpsCap;
            return true;
        }

        return false;
    }
}
