package gbw.melange.welcomeapp.discovery;

import gbw.melange.common.annotations.View;
import gbw.melange.common.builders.IElementBuilder;
import gbw.melange.common.elementary.IElementRenderer;
import gbw.melange.common.elementary.space.ISpace;
import gbw.melange.welcomeapp.HomeScreen;
import gbw.melange.welcomeapp.processors.IHomeScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * <p>CanItFindThis class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
@View
public class CanItFindThis{
    private static final Logger log = LoggerFactory.getLogger(CanItFindThis.class);
    /**
     * <p>Constructor for CanItFindThis.</p>
     *
     * @param hi a int
     */
    public CanItFindThis(int hi){}

    @Autowired
    /**
     * <p>Constructor for CanItFindThis.</p>
     *
     * @param homeScreen a {@link gbw.melange.welcomeapp.processors.IHomeScreen} object
     */
    public CanItFindThis(IHomeScreen homeScreen){
    }
}















