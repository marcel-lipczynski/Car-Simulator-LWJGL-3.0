package RenderEngine;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;

import static org.lwjgl.glfw.GLFW.*;

public class Window {

    private final long id;

    private boolean vSync;

//    constructor, to create window with specified size and title.
    public Window(int width, int height, CharSequence title, boolean vSync){
        this.vSync = vSync;

//        Create temporary window for getting the available OpenGl version
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        long temp = glfwCreateWindow(1,1,"",0,0);
        glfwMakeContextCurrent(temp);
        GL.createCapabilities();
        GLCapabilities caps = GL.getCapabilities();
        glfwDestroyWindow(temp);

//        Reset and set window hints
        glfwDefaultWindowHints();
        if(caps.OpenGL32){
            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        }else if(caps.OpenGL21){
            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 2);
            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
        }else{
            throw new RuntimeException("Neither OpenGL 3.2 nor OpenGL 2.1 is "
                    + "supported, you may want to update your graphic driver.");
        }
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

//        Create application window with specified OpenGL context
        id = glfwCreateWindow(width, height,title,0,0);
        if(id == 0){
            glfwTerminate();
            throw new RuntimeException("Failed to create GLFW Window!");
        }

//        Center window on screen
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(id,
                (vidMode.width() - width) /2,
                (vidMode.height() - height) / 2);

//        create OpenGL context
        glfwMakeContextCurrent(id);
        GL.createCapabilities();

//        version sync
        if (vSync) {
            glfwSwapInterval(1);
        }
    }

//    closing checker
    public boolean isClosing(){
        return glfwWindowShouldClose(id);
    }

//    Title setter
    public void setTitle(CharSequence title){
        glfwSetWindowTitle(id, title);
    }

//    update screen
    public void update(){
        glfwSwapBuffers(id);
        glfwPollEvents();
    }

//    destroys the window
    public void destroy(){
        glfwDestroyWindow(id);
    }

//    vSync setter
    public void setVSync(boolean vSync){
        this.vSync = vSync;

        if(vSync){
            glfwSwapInterval(1);
        }else{
            glfwSwapInterval(0);
        }
    }

//    check if vSync is enabled
    public boolean isVSyncEnabled(){
        return this.vSync;
    }

//    window ID getter
    public long getId() {
        return id;
    }
}
