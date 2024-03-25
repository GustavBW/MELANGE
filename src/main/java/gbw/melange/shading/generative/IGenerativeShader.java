package gbw.melange.shading.generative;

import gbw.melange.shading.IManagedShader;

/**
 * A generative shader produces information
 */
public interface IGenerativeShader<T extends IManagedShader<T>> extends IManagedShader<T> {


    T copy();
    T copyAs(String newLocalName);

    //TODO: Add optional vertex shader stuff

}
