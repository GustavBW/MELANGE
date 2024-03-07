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


@View
public class CanItFindThis{
    private static final Logger log = LoggerFactory.getLogger(CanItFindThis.class);
    public CanItFindThis(int hi){}

    @Autowired
    public CanItFindThis(IHomeScreen homeScreen){
    }
}















