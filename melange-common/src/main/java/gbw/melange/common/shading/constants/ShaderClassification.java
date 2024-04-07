package gbw.melange.common.shading.constants;

/**
 * For caching purposes
 */
public enum ShaderClassification {
    /**
     * More computationally heavy than a texture sampler or Unknown.
     * If static, this shader should be cached.
     */
    COMPLEX(1f),
    /**
     * Requires nothing but a bound texture and/or texCoords. It makes no sense to cache this, as the exchanged texture shader is as complex
     */
    PURE_SAMPLER(.5f),
    /**
     * Constant color requiring no bound resources or the like. It makes no sense to cache this, as the exchanged texture sampling shader is more complex
     * texCoords is allowed.
     */
    SIMPLE(0f);

    public final float abstractValRep;
    ShaderClassification(float abstractValRep){
        this.abstractValRep = abstractValRep;
    }
}
