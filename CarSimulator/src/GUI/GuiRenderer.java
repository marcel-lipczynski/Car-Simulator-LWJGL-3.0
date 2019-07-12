package GUI;

import LibrarySupport.Maths;
import LibrarySupport.Matrix4f;
import Models.RawModel;
import RenderEngine.Loader;
import org.lwjgl.opengl.GL20C;

import java.util.List;

import static org.lwjgl.opengl.GL15C.*;
import static org.lwjgl.opengl.GL30C.*;

public class GuiRenderer {

    private final RawModel quad;
    private GuiShader shader;

    public GuiRenderer(Loader loader){
        float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
        quad = loader.loadToVAO(positions, 2);
        shader = new GuiShader();
    }

//    render list of GuiElements
    public void render(List<GuiTexture> guis){
        shader.start();
        glBindVertexArray(quad.getVaoID());
        glEnableVertexAttribArray(0);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//        glDisable(GL_DEPTH_TEST);
        for(GuiTexture gui: guis){
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, gui.getTexture());
            Matrix4f matrix = Maths.createTransformationMatrix(gui.getPosition(), gui.getScale() );
            shader.loadTransformation(matrix);
            glDrawArrays(GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
        }

//        glEnable(GL_DEPTH_TEST);
        glDisable(GL_BLEND);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
        shader.stop();
    }

//    public void renderOne(GuiTexture gui){
//        shader.start();
//        glBindVertexArray(quad.getVaoID());
//        glEnableVertexAttribArray(0);
//        glEnable(GL_BLEND);
//        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//        glActiveTexture(GL_TEXTURE0);
//        glBindTexture(GL_TEXTURE_2D, gui.getTexture());
//        Matrix4f matrix = Maths.createTransformationMatrix(gui.getPosition(), gui.getScale());
//        shader.loadTransformation(matrix);
//        glDrawArrays(GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
//
//        glDisable(GL_BLEND);
//        glDisableVertexAttribArray(0);
//        glBindVertexArray(0);
//        shader.stop();
//    }

    public void cleanUp(){
        shader.cleanUp();
    }
}
