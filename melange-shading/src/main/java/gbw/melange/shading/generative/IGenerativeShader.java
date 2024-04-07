package gbw.melange.shading.generative;

import gbw.melange.shading.IManagedShader;
import gbw.melange.shading.components.VertexShader;

/**
 * A generative shader produces information
 */
public interface IGenerativeShader<T extends IManagedShader<T>> extends IManagedShader<T> {


    T copy();
    T copyAs(String newLocalName);


}
