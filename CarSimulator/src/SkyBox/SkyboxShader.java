package SkyBox;

import Entity.Camera;
import LibrarySupport.Maths;
import LibrarySupport.Matrix4f;
import LibrarySupport.Vector3f;
import RenderEngine.WindowManager;
import Shaders.ShaderProgram;

public class SkyboxShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src/SkyBox/skyboxVertexShader";
    private static final String FRAGMENT_FILE = "src/SkyBox/skyboxFragmentShader";

    private static final float ROTATE_SPEED = 1f;

    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_fogColour;

    private float rotation = 0;

    public SkyboxShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    public void loadProjectionMatrix(Matrix4f matrix){
        super.loadMatrix(location_projectionMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera){
        Matrix4f matrix = Maths.createViewMatrix(camera);
        matrix.m30 = 0;
        matrix.m31 = 0;
        matrix.m32 = 0;
        rotation += ROTATE_SPEED * WindowManager.getFrameTimeSeconds();
        Matrix4f.rotate((float) Math.toRadians(rotation), new Vector3f(0,1,0), matrix, matrix);
        super.loadMatrix(location_viewMatrix, matrix);
    }

    @Override
    protected void getAllUniformLocations() {
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_fogColour = super.getUniformLocation("fogColour");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

    public void loadFogColour(float r, float g, float b){
        super.loadVector(location_fogColour, new Vector3f(r,g,b));
    }

}
