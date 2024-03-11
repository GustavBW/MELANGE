package gbw.melange.core.elementary;

import gbw.melange.common.elementary.types.IConstrainedElement;

import java.util.List;

/**
 * I-Automatic-Element-Transform-Resolver
 */
public interface IAETR {
    void load(List<? extends IConstrainedElement> elements);
    void resolve();
    void resolveFrom(IConstrainedElement element);
}
