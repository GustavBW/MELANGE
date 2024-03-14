package gbw.melange.shading.iocache;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import gbw.melange.shading.IWrappedShader;
import gbw.melange.shading.errors.ShaderCompilationIssue;

import java.io.File;
import java.io.IOException;

public class DiskShaderCacheUtil {

    public static final String SHADER_CACHE_PATH = "./assets/system/generated/shaders";

    /**
     * Draw 2D shader to texture stored at {@link DiskShaderCacheUtil#SHADER_CACHE_PATH}
     * ToString is used to generate the filename, so make sure its unique.
     */
    public static Texture cacheOrUpdateExisting(IWrappedShader shader) throws ShaderCompilationIssue, IOException {
        if(!shader.isReady()){
            throw new ShaderCompilationIssue("Provided shader " + shader + " is not ready to be cached on disk");
        }
        init(); //Just a check

        final String derivedFileName = shader.shortName() + "@" + shader.hashCode();
        File existingCacheFile = new File(SHADER_CACHE_PATH + "/" + derivedFileName);
        Texture asTexture;

        if(!existingCacheFile.exists()){
            // Shader hasn't been cached yet, proceed with rendering
            asTexture = renderToTexture(shader, shader.getResolution(), shader.getResolution(), false);

            saveTextureToPNG(asTexture, derivedFileName);

            asTexture.dispose();

        } else {
            //TODO: FileHandle constructor has a warning about cross compatibility. Switch to com.badlogic.gdx.Files
            asTexture = new Texture(new FileHandle(existingCacheFile));

            if(asTexture.getWidth() != shader.getResolution()){
                //Re-render on resolution change
                asTexture = renderToTexture(shader, shader.getResolution(), shader.getResolution(), false);
            }

            saveTextureToPNG(asTexture, derivedFileName);

            asTexture.dispose();
        }

        return asTexture;
    }

    /**
     * Sets up folders and structure. Does nothing if called twice and is already checked and called on {@link DiskShaderCacheUtil#cacheOrUpdateExisting(IWrappedShader)}
     */
    public static void init(){
        // Ensure the cache directory exists
        File cacheDir = new File(SHADER_CACHE_PATH);
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
    }

    public static Texture renderToTexture(IWrappedShader shader, int resX, int resY, boolean depth) {
        FrameBuffer frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, resX, resY, depth);
        frameBuffer.begin();

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shader.bindResources();
        shader.getProgram().bind();

        FULL_SCREEN_QUAD.render(shader.getProgram(), GL20.GL_TRIANGLES);

        frameBuffer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        return frameBuffer.getColorBufferTexture();
    }

    /**
     * @param texture any
     * @param fileName without ".png" and preceding path
     */
    private static void saveTextureToPNG(Texture texture, String fileName) {
        if (!texture.getTextureData().isPrepared()) {
            texture.getTextureData().prepare();
        }
        Pixmap pixmap = texture.getTextureData().consumePixmap();
        PixmapIO.writePNG(Gdx.files.external(SHADER_CACHE_PATH + "/" + fileName), pixmap);
        pixmap.dispose();
    }

    private static final Mesh FULL_SCREEN_QUAD = new Mesh(
            true, 4, 6,
            VertexAttribute.Position(),
            VertexAttribute.TexCoords(0)
    );
    static {
        FULL_SCREEN_QUAD.setVertices(new float[] {
                -1f, -1f, 0, 0, 0,
                1f, -1f, 0, 1, 0,
                1f,  1f, 0, 1, 1,
                -1f,  1f, 0, 0, 1
        });
        FULL_SCREEN_QUAD.setIndices(new short[] {0, 1, 2, 2, 3, 0});
        //Crude, but should work
        Runtime.getRuntime().addShutdownHook(new Thread(FULL_SCREEN_QUAD::dispose));
    }

}
