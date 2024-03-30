package gbw.melange.shading.graph.nodes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import gbw.melange.common.gl.GLDrawStyle;
import gbw.melange.shading.generative.IGenerativeShader;
import gbw.melange.shading.iocache.GraphIOCache;

/**
 * This shader node is a leaf of the tree that is the entire shader graph and generates data. It might be a voronoi, noise,
 * gradient, checker... anything really. The point is it samples from nothing else and doesn't expect any inputs.
 */
public class GenerativeShaderNode implements IShaderNode {

    private IShaderNode next;
    private FrameBuffer internalFBO =  new FrameBuffer(Pixmap.Format.RGBA8888, 512, 512, true);
    private Texture latestResult;
    private IGenerativeShader<?> shader;
    private final GraphIOCache ioCache;
    private final GraphNodeParams params;

    public GenerativeShaderNode(GraphIOCache ioCache){
        this(ioCache, GraphNodeParams.DEFAULT);
    }
    public GenerativeShaderNode(GraphIOCache ioCache, GraphNodeParams params){
        this.ioCache = ioCache;
        this.params = params;
    }

    public Texture getLatest(){
        if(latestResult == null){
            latestResult = ioCache.readEntry(this);
        }
        return latestResult;
    }

    public void update(Mesh renderBasis){
        //this draw
        FrameBuffer finalFBO = drawToFBO(internalFBO, renderBasis);
        latestResult = finalFBO.getColorBufferTexture();
        //chain cache entry update
        ioCache.updateEntry(this, finalFBO);
        //next update... etc etc

    }

    public FrameBuffer drawToFBO(FrameBuffer buffer, Mesh renderBasis){
        buffer.begin();

        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);

        shader.getProgram().bind();
        shader.applyBindings();

        renderBasis.render(shader.getProgram(), GLDrawStyle.TRIANGLES.glValue);

        buffer.end();

        return buffer;
    }
}
