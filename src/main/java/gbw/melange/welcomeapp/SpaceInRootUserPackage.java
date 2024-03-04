package gbw.melange.welcomeapp;

import gbw.melange.common.MelangeApplication;
import gbw.melange.common.annotations.Space;
import gbw.melange.welcomeapp.discovery.CanItFindThis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Space
public class SpaceInRootUserPackage {

    private static final Logger log = LoggerFactory.getLogger(SpaceInRootUserPackage.class);
    @Autowired
    public SpaceInRootUserPackage(CanItFindThis lemmeHaveIt){
        log.info("Got it: " + lemmeHaveIt);
    }
}
