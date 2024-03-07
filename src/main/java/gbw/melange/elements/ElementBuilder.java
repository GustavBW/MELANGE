package gbw.melange.elements;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.common.builders.IElementBuilder;
import gbw.melange.common.elementary.*;
import gbw.melange.common.elementary.space.ISpace;
import gbw.melange.common.hooks.OnInit;
import gbw.melange.elements.constraints.ReferenceConstraintDefinition;
import gbw.melange.elements.styling.ReferenceStyleDefinition;
import gbw.melange.shading.FragmentShader;
import gbw.melange.shading.VertexShader;
import org.springframework.lang.NonNull;

/**
 * @param <T> the type of result from the given OnInit, if any.
 */
public class ElementBuilder<T> implements IElementBuilder<T> {

    //Allowed to be null:
    private IElement elementAttachedTo;
    private final ISpace parentSpace;
    private OnInit<T> onInit;

    //Not allowed to be null, but is null to detect changes.
    //There might be some about of unnecessary copying going on here, but before Rules are established its hard to know how careful to be in terms of deep copies / shallow copies
    private IElementStyleDefinition referenceStyling = null;
    private IElementConstraints referenceConstraints = null;
    private Anchor selfAnchor = null;
    private Anchor attachingAnchor = null;
    private double borderWidth = -1;
    private double padding = -1;

    //Styling
    private ShaderProgram backgroundShader = null;
    private ShaderProgram borderShader = null;
    private int backgroundDrawStyle = -1;
    private int borderDrawStyle = -1;
    private Mesh mesh;

    public ElementBuilder(@NonNull ISpace parentSpace){
        this.parentSpace = parentSpace;
    }

    @Override
    public IElement build() {
        ReferenceConstraintDefinition refConDef = loadRefConDef();
        ReferenceStyleDefinition refStyDef = loadRefStyDef();

        if(onInit != null){
            IInitElement element = new OnInitElement<>(
                    mesh,
                    onInit,
                    elementAttachedTo,
                    refStyDef,
                    refConDef
            );
            parentSpace.addOnInitElement(element);
            return element;
        } else {
            IPureElement element = new PureElement(
                    mesh,
                    elementAttachedTo,
                    refStyDef,
                    refConDef
            );
            parentSpace.addPureElement(element);
            return element;
        }
    }

    private ReferenceStyleDefinition loadRefStyDef(){
        ReferenceStyleDefinition refStyDef;
        if(referenceStyling != null){
            refStyDef = new ReferenceStyleDefinition(referenceStyling);
        }else{
            refStyDef = new ReferenceStyleDefinition();
        }
        if(backgroundShader != null){
            refStyDef.backgroundShader = backgroundShader;
        }
        if(borderShader != null){
            refStyDef.borderShader = borderShader;
        }
        if(backgroundDrawStyle != -1){
            refStyDef.backgroundDrawStyle = backgroundDrawStyle;
        }
        if(borderDrawStyle != -1){
            refStyDef.borderDrawStyle = borderDrawStyle;
        }
        return refStyDef;
    }

    private ReferenceConstraintDefinition loadRefConDef() {
        ReferenceConstraintDefinition refConDef;
        if(referenceConstraints != null){
            refConDef = new ReferenceConstraintDefinition(referenceConstraints);
        }else{
            refConDef = new ReferenceConstraintDefinition();
        }
        if(selfAnchor != null){
            refConDef.selfAnchor = selfAnchor;
        }
        if(attachingAnchor != null){
            refConDef.attachingAnchor = attachingAnchor;
        }
        if(borderWidth != -1){
            refConDef.borderWidth = borderWidth;
        }
        if(padding != -1){
            refConDef.padding = padding;
        }
        return refConDef;
    }

    @Override
    public IElementBuilder<T> stylingsFrom(IElement element) {
        this.referenceStyling = element.getStylings();
        return this;
    }

    @Override
    public IElementBuilder<T> constraintsFrom(IElement element) {
        this.referenceConstraints = element.getConstraints();
        return this;
    }

    @Override
    public IElementBuilder<T> setSelfAnchor(ElementAnchoring anchor) {
        this.selfAnchor = anchor.anchor;
        return this;
    }

    @Override
    public IElementBuilder<T> setSelfAnchor(Anchor anchor) {
        this.selfAnchor = anchor;
        return this;
    }

    @Override
    public IElementBuilder<T> setAttachingAnchor(ElementAnchoring anchor) {
        this.attachingAnchor = anchor.anchor;
        return this;
    }

    @Override
    public IElementBuilder<T> setAttachingAnchor(Anchor anchor) {
        this.attachingAnchor = anchor;
        return this;
    }

    @Override
    public IElementBuilder<T> attachTo(IElement element) {
        this.elementAttachedTo = element;
        return this;
    }

    @Override
    public IElementBuilder<T> setBackgroundColor(FragmentShader fragment) {
        return setBackgroundColor(new ShaderProgram(VertexShader.DEFAULT, fragment.code()));
    }

    @Override
    public IElementBuilder<T> setBorderColor(FragmentShader fragment) {
        return setBorderColor(new ShaderProgram(VertexShader.DEFAULT, fragment.code()));
    }

    @Override
    public IElementBuilder<T> setBackgroundColor(ShaderProgram shader) {
        this.backgroundShader = shader;
        return this;
    }

    @Override
    public IElementBuilder<T> setBorderColor(ShaderProgram shader) {
        this.borderShader = shader;
        return this;
    }

    @Override
    public IElementBuilder<T> setPadding(double percent) {
        padding = percent;
        return this;
    }

    @Override
    public IElementBuilder<T> setBorderWidth(double percent) {
        borderWidth = percent;
        return this;
    }

    @Override
    public IElementBuilder<T> setMesh(Mesh mesh) {
        this.mesh = mesh.copy(true);
        return this;
    }

    @Override
    public IElementBuilder<T> setBackgroundDrawStyle(int style) {
        this.backgroundDrawStyle = style;
        return this;
    }

    @Override
    public IElementBuilder<T> setBorderDrawStyle(int style) {
        this.borderDrawStyle = style;
        return this;
    }

    @Override
    public IElementBuilder<T> onInit(OnInit<T> onInit) {
        this.onInit = onInit;
        return this;
    }
}
