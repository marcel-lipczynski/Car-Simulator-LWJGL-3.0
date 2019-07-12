package LibrarySupport;

import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;
import static org.lwjgl.stb.STBImage.*;


/*
    Create Texture from file and store information about it.
*/
public class Texture {

    private final int id;

    private int width;

    private int height;

//    constructor
    public Texture(){
        id = glGenTextures();
    }


    public void bind(){
        glBindTexture(GL_TEXTURE_2D, id);
    }


    public void setParameter(int name, int value) {
        glTexParameteri(GL_TEXTURE_2D, name, value);
    }

//    upload
    public void uploadData(int width, int height, ByteBuffer data) {
        uploadData(GL_RGBA8, width, height, GL_RGBA, data);
    }

//    upload
    public void uploadData(int internalFormat, int width, int height, int format, ByteBuffer data) {
        glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, GL_UNSIGNED_BYTE, data);
    }

//    delete LibrarySupport
    public void delete() {
        glDeleteTextures(id);
    }

//    width getter
    public int getWidth() {
        return width;
    }

//    width setter
    public void setWidth(int width) {
        if (width > 0) {
            this.width = width;
        }
    }

//    Height getter
    public int getHeight() {
        return height;
    }

//    Height setter
    public void setHeight(int height) {
        if (height > 0) {
            this.height = height;
        }
    }

//    get texture ID
    public int getId() {
        return id;
    }

//    create LibrarySupport in our Memory
    public static Texture createTexture(int width, int height, ByteBuffer data) {
        Texture texture = new Texture();
        texture.setWidth(width);
        texture.setHeight(height);

        texture.bind();

        texture.setParameter(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
        texture.setParameter(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
        texture.setParameter(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        texture.setParameter(GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        texture.uploadData(GL_RGBA8, width, height, GL_RGBA, data);

        return texture;
    }

//    Load LibrarySupport from fileName
    public static Texture loadTexture(String fileName) {
        String path = ("res/" +fileName + ".png");
        ByteBuffer image;
        int width, height;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            /* Prepare image buffers */
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            /* Load image */
            stbi_set_flip_vertically_on_load(false);
            image = stbi_load(path, w, h, comp, 4);
            if (image == null) {
                throw new RuntimeException("Failed to load a texture file!"
                        + System.lineSeparator() + stbi_failure_reason());
            }

            /* Get width and height of image */
            width = w.get();
            height = h.get();
        }

        return createTexture(width, height, image);
    }
}
