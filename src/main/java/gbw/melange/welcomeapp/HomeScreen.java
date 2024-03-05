package gbw.melange.welcomeapp;

import gbw.melange.common.elementary.space.IScreenSpace;
import gbw.melange.welcomeapp.processors.IHomeScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class HomeScreen implements IHomeScreen {

    @Autowired
    public HomeScreen(IScreenSpace screenSpace) {
        log.info("Got it: " + screenSpace);
    }

    private static final Logger log = LoggerFactory.getLogger(HomeScreen.class);

}
