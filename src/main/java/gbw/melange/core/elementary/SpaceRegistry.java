package gbw.melange.core.elementary;

import gbw.melange.common.annotations.View;
import gbw.melange.common.elementary.space.ISpace;
import gbw.melange.common.elementary.space.SpaceLayerEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>SpaceRegistry class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class SpaceRegistry implements ISpaceRegistry {
    private final Map<Class<?>, List<SpaceLayerEntry>> registry = new HashMap<>();

    /** {@inheritDoc} */
    @Override
    public void register(ISpace space, Object object) {
        register0(space, object.getClass());
    }

    /** {@inheritDoc} */
    @Override
    public void register(ISpace space, Class<?> clazz) {
        register0(space, clazz);
    }

    /** {@inheritDoc} */
    @Override
    public Map<Class<?>, List<SpaceLayerEntry>> get() {
        return registry;
    }

    private void register0(ISpace space, Class<?> clazz) {
        View viewAnnotation = clazz.getAnnotation(View.class);
        int layer;
        View.FocusPolicy focusPolicy;
        if(viewAnnotation == null){
            layer = View.DEFAULT_LAYER;
            focusPolicy = View.DEFAULT_FOCUS_POLICY;
        }else{
            layer = viewAnnotation.layer();
            focusPolicy = viewAnnotation.focusPolicy();
        }
        registry.computeIfAbsent(clazz, k -> new ArrayList<>()).add(new SpaceLayerEntry(space, layer, focusPolicy));
    }
}
