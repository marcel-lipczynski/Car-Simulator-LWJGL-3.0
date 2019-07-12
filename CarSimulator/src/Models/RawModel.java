package Models;

/*
    Raw Model class, prepare 3d model to render.
*/
public class RawModel {

//    Vertex Arrays Object and vertices counter
    private int vaoID;
    private int vertexCount;

    public RawModel(int vaoID, int vertexCount){
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
    }

    public int getVaoID(){
        return vaoID;
    }

    public int getVertexCount(){
        return vertexCount;
    }
}
