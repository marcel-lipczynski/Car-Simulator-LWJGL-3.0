package LibrarySupport;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWScrollCallback;

/*
    MouseScroll Handler
*/
public class MouseScroll extends GLFWScrollCallback {

    private static float currentDy;
    private static boolean scrolled = false;

    public void invoke(long windowID, double dx, double dy) {
        currentDy = (float) dy;
        scrolled = true;
    }

    public static float getDy() {
        if(scrolled){
            scrolled = false;
            return currentDy;
        }
        return 0;
    }
}
