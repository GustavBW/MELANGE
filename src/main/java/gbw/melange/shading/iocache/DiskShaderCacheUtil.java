package gbw.melange.shading.iocache;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import gbw.melange.shading.IWrappedShader;
import gbw.melange.shading.errors.ShaderCompilationIssue;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class DiskShaderCacheUtil {

    public static final String SHADER_CACHE_PATH = "./assets/system/generated/shaders";

    /**
     * Draw 2D shader to texture stored at {@link DiskShaderCacheUtil#SHADER_CACHE_PATH}
     * ToString is used to generate the filename, so make sure its unique.
     */
    public static Texture cacheOrUpdateExisting(IWrappedShader shader) throws Exception {

        init(); //Just a check

        //TODO: Hashcode might depend on code content of shader, assure a swapped out shader returns the same name for consistency
        final String derivedFileName = shader.shortName() + "@" + shader.hashCode();
        File existingCacheFile = new File(SHADER_CACHE_PATH + "/" + derivedFileName);
        FrameBuffer resultingFBO;
        Texture toReturn;

        if(!existingCacheFile.exists()){
            // Shader hasn't been cached yet, proceed with rendering
            resultingFBO = renderToFBO(shader, shader.getResolution(), shader.getResolution(), false);

            saveFBOtoPNG(resultingFBO, derivedFileName);

            toReturn = resultingFBO.getColorBufferTexture();

            resultingFBO.dispose();
        } else {
            //TODO: FileHandle constructor has a warning about cross compatibility. Switch to com.badlogic.gdx.Files
             toReturn = new Texture(new FileHandle(existingCacheFile));

            if(toReturn.getWidth() != shader.getResolution()){
                //Re-render on resolution change
                resultingFBO = renderToFBO(shader, shader.getResolution(), shader.getResolution(), false);

                saveFBOtoPNG(resultingFBO, derivedFileName);

                toReturn = resultingFBO.getColorBufferTexture();

                resultingFBO.dispose();
            }

        }

        return toReturn;
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

    /**
     *
     * @param shader - not disposed
     * @param resX - width
     * @param resY - height
     * @param depth - has depth
     */
    public static FrameBuffer renderToFBO(IWrappedShader shader, int resX, int resY, boolean depth) {
        FrameBuffer frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, resX, resY, depth);
        frameBuffer.begin();

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shader.applyBindings();
        shader.getProgram().bind();

        FULL_SCREEN_QUAD.render(shader.getProgram(), GL20.GL_TRIANGLES);

        frameBuffer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        return frameBuffer;
    }

    /**
     * @param fbo any, not disposed
     * @param fileName without ".png" and preceding path
     */
    private static void saveFBOtoPNG(FrameBuffer fbo, String fileName) {
        Pixmap pixmap = getPixmapFromFrameBuffer(fbo);
        PixmapIO.writePNG(Gdx.files.external(SHADER_CACHE_PATH + "/" + fileName), pixmap);
        pixmap.dispose();
    }

    /**
     * @param frameBuffer not disposed
     */
    public static Pixmap getPixmapFromFrameBuffer(FrameBuffer frameBuffer) {
        // Bind the framebuffer
        frameBuffer.bind();

        int width = frameBuffer.getWidth();
        int height = frameBuffer.getHeight();

        final Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        ByteBuffer pixels = pixmap.getPixels();

        // Read the pixels from the framebuffer
        Gdx.gl.glPixelStorei(GL20.GL_PACK_ALIGNMENT, 1);
        Gdx.gl.glReadPixels(0, 0, width, height, GL20.GL_RGBA, GL20.GL_UNSIGNED_BYTE, pixels);

        // Unbind the framebuffer to return to default
        FrameBuffer.unbind();

        return pixmap; // Return the correctly oriented pixmap
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
