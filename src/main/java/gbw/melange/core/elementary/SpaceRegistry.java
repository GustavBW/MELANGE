package gbw.melange.core.elementary;

import gbw.melange.common.annotations.View;
import gbw.melange.common.elementary.space.ISpace;
import gbw.melange.common.elementary.space.ISpaceRegistry;
import gbw.melange.common.elementary.space.SpaceLayerEntry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpaceRegistry implements ISpaceRegistry {


    private Map<Class<?>, List<SpaceLayerEntry>> registry = new HashMap<>();

    @Override
    public void register(ISpace space, Object object) {
        register0(space, object.getClass());
    }

    @Override
    public void register(ISpace space, Class<?> clazz) {
        register0(space, clazz);
    }

    @Override
    public Map<Class<?>, List<SpaceLayerEntry>> get() {
        return registry;
    }

    private void register0(ISpace space, Class<?> clazz) {
        View viewAnnotation = clazz.getAnnotation(View.class);
        SpaceLayerEntry entry;
        if(viewAnnotation == null){
            entry = new SpaceLayerEntry(space, View.DEFAULT);
        }else{
            entry = new SpaceLayerEntry(space, viewAnnotation.layer());
        }
        registry.computeIfAbsent(clazz, k -> new ArrayList<>()).add(entry);
    }
}
