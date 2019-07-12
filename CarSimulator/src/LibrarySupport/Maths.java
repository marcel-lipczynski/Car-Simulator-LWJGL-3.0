package LibrarySupport;

import Entity.Camera;

public class Maths {

//    calculate Terrain height
    public static float bayrionicCentric(Vector3f v1, Vector3f v2, Vector3f v3, Vector2f pos){
        float det = (v2.z - v3.z) * (v1.x - v3.x) + (v3.x - v2.x) * (v1.z - v3.z);
        float l1 = ((v2.z - v3.z) * (pos.x - v3.x) + (v3.x - v2.x) * (pos.y - v3.z)) / det;
        float l2 = ((v3.z - v1.z) * (pos.x - v3.x) + (v1.x - v3.x) * (pos.y - v3.z)) / det;
        float l3 = 1.0f - l1 - l2;
        return l1 * v1.y + l2 * v2.y + l3 * v3.y;
    }

//    create transformation Matrix
    public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale){
        Matrix4f matrix = new Matrix4f();

        matrix.identity();
        Matrix4f.translate( translation,matrix, matrix);
        Matrix4f.rotate((float)Math.toRadians(rx), new Vector3f(1,0,0), matrix, matrix);
        Matrix4f.rotate((float)Math.toRadians(ry), new Vector3f(0,1,0), matrix, matrix);
        Matrix4f.rotate((float)Math.toRadians(rz), new Vector3f(0,0,1), matrix, matrix);
        Matrix4f.scale( matrix, new Vector3f(scale,scale,scale), matrix);
        return matrix;
    }

//    create View Matrix
    public static Matrix4f createViewMatrix(Camera camera){
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.identity();
        Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1,0,0), viewMatrix, viewMatrix);
        Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0,1,0), viewMatrix, viewMatrix);
        Matrix4f.rotate((float)Math.toRadians(camera.getRoll()), new Vector3f(0,0,1), viewMatrix, viewMatrix);
        Vector3f cameraPos = camera.getPosition();
        Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        Matrix4f.translate( negativeCameraPos,viewMatrix, viewMatrix);
        return viewMatrix;
    }

//   Transformation Matrix for 2D elements
    public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.identity();
        Matrix4f.translate(translation, matrix, matrix);
        Matrix4f.scale( matrix, new Vector3f(scale.x, scale.y, 1f),matrix);
        return matrix;
    }


}
