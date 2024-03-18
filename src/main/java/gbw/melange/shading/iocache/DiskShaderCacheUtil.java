package gbw.melange.shading.iocache;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import gbw.melange.shading.constants.GLShaderAttr;
import gbw.melange.shading.IWrappedShader;
import org.jetbrains.annotations.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.ByteBuffer;

public class DiskShaderCacheUtil implements Disposable {
    private static final Logger log = LogManager.getLogger();

    public static final String SHADER_CACHE_PATH = "./assets/system/generated/textures";
    private final Matrix4 unitMatrix = new Matrix4();
    private int hits = 0;
    private final Mesh screenQuad;

    public DiskShaderCacheUtil(){
        screenQuad = new Mesh(
                true, 4, 6,
                VertexAttribute.Position(),
                VertexAttribute.TexCoords(0)
        );
        screenQuad.setVertices(new float[] {
                -1f, -1f, 0, 0, 1,
                 1f, -1f, 0, 1, 1,
                 1f,  1f, 0, 1, 0,
                -1f,  1f, 0, 0, 0
        });
        screenQuad.setIndices(new short[] {0, 1, 2, 2, 3, 0});
    }

    /**
     * Draw 2D shader to texture stored at {@link DiskShaderCacheUtil#SHADER_CACHE_PATH}
     * ToString is used to generate the filename, so make sure its unique.
     *
     * @return Returns the file handle to the image file which was just generated
     */
    public FileHandle cacheOrUpdateExisting(@NotNull IWrappedShader shader) throws GdxRuntimeException {

        init(); //Just a check

        //TODO: Hashcode might depend on code content of shader, assure a swapped out shader returns the same name for consistency
        final String derivedFileName = shader.shortName() + "@" + shader.hashCode();
        File existingCacheFile = new File(SHADER_CACHE_PATH + "/" + derivedFileName);
        FrameBuffer resultingFBO;
        FileHandle toReturn;

        if(!existingCacheFile.exists()){
            // Shader hasn't been cached yet, proceed with rendering
            resultingFBO = renderToFBO(shader, shader.getResolution(), shader.getResolution(), false);

            toReturn = saveFBOtoPNG(resultingFBO, derivedFileName);

            resultingFBO.dispose();
        } else {
            hits++;
            toReturn = Gdx.files.local(getExactLocationOf(derivedFileName));
            Texture existing = new Texture(toReturn);

            if(existing.getWidth() != shader.getResolution() || existing.getHeight() != shader.getResolution()){
                //Re-render on resolution change
                resultingFBO = renderToFBO(shader, shader.getResolution(), shader.getResolution(), false);

                toReturn = saveFBOtoPNG(resultingFBO, derivedFileName);

                resultingFBO.dispose();
            }
        }

        return toReturn;
    }
    public int getHits(){
        return hits;
    }

    /**
     * Sets up folders and structure. Does nothing if called twice and is already checked and called on {@link DiskShaderCacheUtil#cacheOrUpdateExisting(IWrappedShader)}
     */
    public void init(){
        // Ensure the cache directory exists
        File cacheDir = new File(SHADER_CACHE_PATH);
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();

            try(BufferedWriter bw = new BufferedWriter(new FileWriter("./assets/system/generated/readme.md"))){
                String generatedReadmeDisclaimer = """
                    # Don't panic. \n
                    ### This directory and its sub-directories are strictly for system use and interfering with it manually is a really bad idea. \n
                    Certain services and utilities allow you to interfere safely, so feel free to investigate how you may use these resources.
                    """;
                bw.write(generatedReadmeDisclaimer);
            }catch (Exception e) {
                log.warn("Error occurred during DiskShaderCache.init():");
                log.warn(e.getMessage());
            }
        }
    }

    public void clearCache(){
        FileHandle texturesDir = Gdx.files.local(SHADER_CACHE_PATH);
        texturesDir.deleteDirectory();
    }

    public FrameBuffer renderToFBO(IWrappedShader shader, int resX, int resY, boolean depth) {
        FrameBuffer frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, resX, resY, depth);
        frameBuffer.begin();

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        shader.getProgram().bind();
        shader.applyBindings();
        shader.getProgram().setUniformMatrix(GLShaderAttr.MATRIX.glValue(), unitMatrix);

        screenQuad.render(shader.getProgram(), GL30.GL_TRIANGLES);

        frameBuffer.end();

        Gdx.gl.glDisable(GL30.GL_BLEND);

        return frameBuffer;
    }

    /**
     * @param fbo any, not disposed
     * @param fileName without ".png" and preceding path
     */
    private FileHandle saveFBOtoPNG(FrameBuffer fbo, String fileName) {
        Pixmap pixmap = getPixmapFromFrameBuffer(fbo);
        FileHandle handle = Gdx.files.local(getExactLocationOf(fileName));
        PixmapIO.writePNG(handle, pixmap);
        pixmap.dispose();
        return handle;
    }

    private String getExactLocationOf(String fileName){
        return SHADER_CACHE_PATH + "/" + fileName + ".png";
    }

    /**
     * @param frameBuffer not disposed
     */
    public Pixmap getPixmapFromFrameBuffer(FrameBuffer frameBuffer) {
        // Bind the framebuffer
        frameBuffer.bind();

        int width = frameBuffer.getWidth();
        int height = frameBuffer.getHeight();

        final Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        ByteBuffer pixels = pixmap.getPixels();

        // Read the pixels from the framebuffer
        Gdx.gl.glPixelStorei(GL30.GL_PACK_ALIGNMENT, 1);
        Gdx.gl.glReadPixels(0, 0, width, height, GL30.GL_RGBA, GL30.GL_UNSIGNED_BYTE, pixels);

        // Unbind the framebuffer to return to default
        FrameBuffer.unbind();

        return pixmap; // Return the correctly oriented pixmap
    }


    @Override
    public void dispose() {
        screenQuad.dispose();
    }
}
