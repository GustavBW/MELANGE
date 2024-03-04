package gbw.melange.welcomeapp;

import gbw.melange.core.MelangeApplication;
import gbw.melange.welcomeapp.processors.SpttgTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class WelcomeExample {
    private static final Logger log = LoggerFactory.getLogger(SpttgTest.class);
    public static void main(String[] args) throws Exception {
        MelangeApplication.run(WelcomeExample.class);
    }

    @Autowired
    public WelcomeExample(SpaceInRootUserPackage thisShouldWork){
        log.info("[WE] Constructor hit, injection is: " + thisShouldWork);
    }

}
