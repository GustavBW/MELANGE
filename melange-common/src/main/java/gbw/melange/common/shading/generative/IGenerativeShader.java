package gbw.melange.common.shading.generative;

import gbw.melange.common.shading.IManagedShader;

/**
 * A generative shader produces information
 */
public interface IGenerativeShader<T extends IManagedShader<T>> extends IManagedShader<T> {


    T copy();
    T copyAs(String newLocalName);


}
