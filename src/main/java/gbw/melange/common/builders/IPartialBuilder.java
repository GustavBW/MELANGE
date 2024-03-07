package gbw.melange.common.builders;

public interface IPartialBuilder<T extends IBuilder<?>> {
    /**
     * Applies the sub-configuration to the parent builder, and returns the parent builder.
     */
    T apply();
}
