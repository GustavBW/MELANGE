package gbw.melange.shading.generative;

import gbw.melange.shading.IManagedShader;
import gbw.melange.shading.ManagedShader;
import gbw.melange.shading.components.FragmentShader;
import gbw.melange.shading.components.VertexShader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public abstract class GenerativeShader<T extends IManagedShader<T>> extends ManagedShader<T> implements IGenerativeShader<T> {
    private static final Logger log = LogManager.getLogger();
    public GenerativeShader(String localName, VertexShader vertex, FragmentShader fragment, boolean isStatic) {
        super(localName, vertex, fragment, isStatic);
    }

    @Override
    public T copy(){
        T child = copyChild();
        try{
            child.compile();
        }catch (Exception partiallyIgnored){
            log.warn("Compiling a new copy of " + child.getLocalName() + " failed: " + partiallyIgnored.getMessage());
        }
        return child;
    }
    protected abstract T copyChild();
    @Override
    public T copyAs(String newLocalName){
        T child = copyChildAs(newLocalName);
        try{
            child.compile();
        }catch (Exception partiallyIgnored){
            log.warn("Compiling a new copy of " + child.getLocalName() + " as: " + newLocalName + " failed: " + partiallyIgnored.getMessage());
        }
        return child;
    }
    protected abstract T copyChildAs(String newLocalName);
}
