package Shaders;


import LibrarySupport.Matrix4f;
import LibrarySupport.Vector2f;
import LibrarySupport.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL15C.*;
import static org.lwjgl.opengl.GL20C.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30C.*;


import java.lang.Object.*;
public abstract class ShaderProgram {

    private int programID;
    private int vertexShaderID;
    private int fragmentShaderID;

    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

//    constructor
    public ShaderProgram(String vertexFile, String fragmentFile){
        vertexShaderID = loadShader(vertexFile, GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(fragmentFile, GL_FRAGMENT_SHADER);
        programID = glCreateProgram();
        glAttachShader(programID, vertexShaderID);
        glAttachShader(programID, fragmentShaderID);
        bindAttributes();
        glLinkProgram(programID);
        glValidateProgram(programID);
        getAllUniformLocations();
    }

//    program starts
    public void start(){
        glUseProgram(programID);
    }

//    program stops
    public void stop(){
        glUseProgram(0);
    }

//    clean
    public void cleanUp(){
        stop();
        glDetachShader(programID, vertexShaderID);
        glDetachShader(programID, fragmentShaderID);
        glDeleteShader(vertexShaderID);
        glDeleteShader(fragmentShaderID);
        glDeleteProgram(programID);
    }

    protected abstract void bindAttributes();

    protected void bindAttribute(int attribute, String variableName){
        glBindAttribLocation(programID, attribute, variableName);
    }

//    Shaders loader
    private static int loadShader(String file, int type){

        StringBuilder shaderSource = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine()) != null){
                shaderSource.append(line).append("\n");
            }
            reader.close();
        }catch (IOException e){
            System.err.println("Couldn't read file.");
            e.printStackTrace();
            System.exit(-1);
        }

        int shaderID = glCreateShader(type);
        glShaderSource(shaderID, shaderSource);
        glCompileShader(shaderID);
        if(glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE){
            System.out.println(glGetShaderInfoLog(shaderID, 500));
            System.err.println("Couldn't compile shader.");
            System.exit(-1);
        }

        return shaderID;
    }


    protected abstract void getAllUniformLocations();

    protected void loadFloat(int location, float value){
        glUniform1f(location, value);
    }

    protected void loadVector(int location, Vector3f vector){
        glUniform3f(location,vector.x, vector.y, vector.z);
    }

    protected void load2DVector(int location, Vector2f vector){
        glUniform2f(location,vector.x, vector.y);
    }

    protected void loadBoolean(int location, boolean value){
        float toLoad = 0;
        if(value){
            toLoad = 1;
        }
        glUniform1f(location, toLoad);
    }

    protected void loadMatrix(int location, Matrix4f matrix){
        matrix.store(matrixBuffer);
        matrixBuffer.flip();
        glUniformMatrix4fv(location, false, matrixBuffer);
    }

    protected int getUniformLocation(String uniformName){
        return glGetUniformLocation(programID,uniformName);
    }

    protected void loadInt(int location, int value){
        glUniform1i(location,value);
    }

}