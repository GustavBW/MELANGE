package gbw.melange.spttg.shadertemplating;

import gbw.melange.common.builders.IShaderTemplateBuilder;
import gbw.melange.common.elementary.IElement;

public class ShaderTemplateBuilder implements IShaderTemplateBuilder {

    public static ShaderTemplateBuilder gradient(IElement element){
        return new ShaderTemplateBuilder();
    }

}
