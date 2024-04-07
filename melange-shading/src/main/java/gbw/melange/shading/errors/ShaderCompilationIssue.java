package gbw.melange.shading.errors;

/**
 * <p>ShaderCompilationIssue class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class ShaderCompilationIssue extends Exception {
    /**
     * <p>Constructor for ShaderCompilationIssue.</p>
     *
     * @param msg a {@link java.lang.String} object
     */
    public ShaderCompilationIssue(String msg){
        super(msg);
    }
}
