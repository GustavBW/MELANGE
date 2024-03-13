package gbw.melange.core.elementary;

import gbw.melange.common.elementary.space.IScreenSpace;
import gbw.melange.common.elementary.space.ISpaceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>ScreenSpaceProvider class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
@Service
public class ScreenSpaceProvider implements ISpaceProvider<IScreenSpace> {

    private final ISpaceRegistry registry;
    @Autowired
    /**
     * <p>Constructor for ScreenSpaceProvider.</p>
     *
     * @param registry a {@link gbw.melange.core.elementary.ISpaceRegistry} object
     */
    public ScreenSpaceProvider(ISpaceRegistry registry){
        this.registry = registry;
    }
    /** {@inheritDoc} */
    @Override
    public IScreenSpace getScreenSpace(Object forWhom) {
        if(forWhom == null){
            throw new RuntimeException("Someone tried to get an IScreenSpace instance from the ISpaceProvider, however, null was provided as the intended receiver, which is not currently supported.");
        }
        IScreenSpace instance = new ScreenSpace();
        registry.register(instance, forWhom);
        return instance;
    }
}
