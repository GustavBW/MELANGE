package gbw.melange.elements.styling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import gbw.melange.common.elementary.styling.BevelConfig;
import gbw.melange.common.gl.GLDrawStyle;
import gbw.melange.common.elementary.styling.IElementStyleDefinition;
import gbw.melange.common.elementary.styling.IReferenceStyleDefinition;
import gbw.melange.shading.generative.IWrappedShader;
import gbw.melange.shading.generative.WrappedShader;
import gbw.melange.shading.postprocessing.IPostProcessShader;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>ElementStyleDefinition class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class ElementStyleDefinition implements IElementStyleDefinition {

    private IWrappedShader backgroundShader = WrappedShader.DEFAULT;
    private IWrappedShader borderShader = WrappedShader.DEFAULT;
    private Texture backgroundImage = new Texture(Gdx.files.internal("assets/system/fallback/errors/stderr.jpg"));
    private GLDrawStyle backgroundDrawStyle = GLDrawStyle.TRIANGLES;
    private GLDrawStyle borderDrawStyle = GLDrawStyle.LINE_LOOP;
    private BevelConfig bevelConfig = BevelConfig.DEFAULT;
    private final List<IPostProcessShader> postProcesses = new ArrayList<>();

    /**
     * <p>Constructor for ElementStyleDefinition.</p>
     *
     * @param def a {@link gbw.melange.common.elementary.styling.IReferenceStyleDefinition} object
     */
    public ElementStyleDefinition(IReferenceStyleDefinition def){
        if(def.backgroundShader() != null) this.backgroundShader = def.backgroundShader();
        if(def.borderShader() != null) this.borderShader = def.borderShader();
        if(def.backgroundDrawStyle() != GLDrawStyle.INVALID) this.backgroundDrawStyle = def.backgroundDrawStyle();
        if(def.borderDrawStyle() != GLDrawStyle.INVALID) this.borderDrawStyle = def.borderDrawStyle();
        if(def.borderBevel() != null) this.bevelConfig = def.borderBevel();
        if(def.postProcesses() != null) this.postProcesses.addAll(def.postProcesses());
    }

    /** {@inheritDoc} */
    @Override
    public IWrappedShader getBackgroundShader() {
        return backgroundShader;
    }

    /** {@inheritDoc} */
    @Override
    public IWrappedShader getBorderShader() {
        return borderShader;
    }

    /** {@inheritDoc} */
    @Override
    public GLDrawStyle getBackgroundDrawStyle() {
        return backgroundDrawStyle;
    }

    /** {@inheritDoc} */
    @Override
    public GLDrawStyle getBorderDrawStyle() {
        return borderDrawStyle;
    }

    /** {@inheritDoc} */
    @Override
    public BevelConfig getBorderBevel() {
        return bevelConfig;
    }

    /** {@inheritDoc} */
    @Override
    public List<IPostProcessShader> getPostProcesses() {
        return postProcesses;
    }

    /** {@inheritDoc} */
    @Override
    public void dispose(){
        if(backgroundShader != null){
            backgroundShader.getProgram().dispose();
        }
        if(borderShader != null){
            borderShader.getProgram().dispose();
        }
    }
}
