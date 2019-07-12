package RenderEngine;

import Entity.Entity;
import LibrarySupport.Maths;
import LibrarySupport.Matrix4f;
import LibrarySupport.Vector3f;
import Models.RawModel;
import Models.TexturedModel;
import Shaders.TerrainShader;
import Terrain.Terrain;
import Textures.ModelTexture;
import Textures.TerrainTexturePack;

import java.util.List;

import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL13C.*;
import static org.lwjgl.opengl.GL20C.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20C.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;

public class TerrainRenderer {

    private TerrainShader shader;

    public TerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix){
        this.shader = shader;
        shader.start();
        shader.loadProjeectionMatrix(projectionMatrix);
        shader.connectTextureUnits();
        shader.stop();
    }

    public void render(List<Terrain> terrains){
        for(Terrain terrain : terrains){
            prepareTerrain(terrain);
            loadModelMatrix(terrain);
            glDrawElements(GL_TRIANGLES, terrain.getModel().getVertexCount(), GL_UNSIGNED_INT, 0);

            unbindTextureModel();
        }
    }

    private void prepareTerrain(Terrain terrain){
        RawModel rawModel = terrain.getModel();
        glBindVertexArray(rawModel.getVaoID());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        bindTextures(terrain);
        shader.loadShineVariables(1, 0); //shine!!!! didn't add

    }

//    active 0-4 texture buffers and bind texture
    private void bindTextures(Terrain terrain){
        TerrainTexturePack terrainTexturePack = terrain.getTexturePack();

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, terrainTexturePack.getBackgroundTexture().getTextureID());

        glActiveTexture(GL_TEXTURE1);
        glBindTexture(GL_TEXTURE_2D, terrainTexturePack.getrTexture().getTextureID());

        glActiveTexture(GL_TEXTURE2);
        glBindTexture(GL_TEXTURE_2D, terrainTexturePack.getgTexture().getTextureID());

        glActiveTexture(GL_TEXTURE3);
        glBindTexture(GL_TEXTURE_2D, terrainTexturePack.getbTexture().getTextureID());

        glActiveTexture(GL_TEXTURE4);
        glBindTexture(GL_TEXTURE_2D, terrain.getBlendMap().getTextureID());
    }

    private void unbindTextureModel(){
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);

        glBindVertexArray(0);
    }

    private void loadModelMatrix(Terrain terrain){
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(
                new Vector3f(terrain.getX(),0,terrain.getZ()),0, 0, 0,1);

        shader.loadTransformationMatrix(transformationMatrix);
    }
}
