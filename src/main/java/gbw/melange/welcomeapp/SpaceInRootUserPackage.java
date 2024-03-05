package gbw.melange.welcomeapp;

import gbw.melange.common.builders.IElementBuilder;
import gbw.melange.common.elementary.IElementRenderer;
import gbw.melange.common.elementary.ISpace;
import gbw.melange.welcomeapp.discovery.CanItFindThis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class SpaceInRootUserPackage implements ISpace {

    private static final Logger log = LoggerFactory.getLogger(SpaceInRootUserPackage.class);
    @Autowired
    public SpaceInRootUserPackage(CanItFindThis lemmeHaveIt) {
        log.info("Got it: " + lemmeHaveIt);
    }

    @Override
    public IElementBuilder createElement() {
        return null;
    }

    @Override
    public void render(IElementRenderer renderer) {

    }

    @Override
    public void dispose() {

    }
}
