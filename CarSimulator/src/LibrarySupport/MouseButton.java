package LibrarySupport;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import static org.lwjgl.glfw.GLFW.*;

/*
    MouseButtonClick Handler
*/

public class MouseButton extends GLFWMouseButtonCallback {

    private static long windowID;
    private static int button;
    private static int action;
    private static int mods;

    public void invoke(long windowID, int button, int action, int mods) {
        this.windowID = windowID;
        this.button = button;
        this.action = action;
        this.mods = mods;
    }

    public static boolean isLeftButtonDown() {
        if(button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS) {
            return true;
        }
        return false;
    }

    public static boolean isRightButtonDown() {
        if(button == GLFW_MOUSE_BUTTON_RIGHT && action == GLFW_PRESS) {
            return true;
        }
        return false;
    }

}
