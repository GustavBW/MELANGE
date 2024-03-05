package gbw.melange.common.elementary.space;

import gbw.melange.core.annotations.Space;
import org.springframework.context.annotation.Scope;

/**
 * This space will attempt to resolve all IElement constraints within the current screen space
 */
@Scope("prototype")
public interface IScreenSpace extends ISpace {
}
