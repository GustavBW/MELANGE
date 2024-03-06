package gbw.melange.welcomeapp;

import gbw.melange.common.annotations.View;
import gbw.melange.common.elementary.space.IScreenSpace;
import gbw.melange.welcomeapp.processors.IHomeScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
@View( layer = View.HOME_SCREEN )
public class HomeScreen implements IHomeScreen {
    private static final Logger log = LoggerFactory.getLogger(HomeScreen.class);
    @Autowired
    public HomeScreen(IScreenSpace screenSpace) {
        log.info("Got it: " + screenSpace);
        screenSpace.createElement()
                .build();
    }



}
