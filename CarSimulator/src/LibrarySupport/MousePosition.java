package LibrarySupport;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import static EngineTester.MainGameLoop.window;

/*
    MousePosition Handler
*/
public class MousePosition extends GLFWCursorPosCallback {

    private static double dy;
    private static double dx;

    public void invoke(long windowID, double dx, double dy) {
        this.dx = dx;
        this.dy = dy;

    }

    public static double getDx() {
        if(window.getWIDTH()/2 < dx){
            return -dx;
        }
        return dx;
    }

    public static double getDy() {
        if(window.getHEIGHT()/2 < dy){
            return -dy;
        }
        return dy;
    }
}
