package gbw.melange.welcomeapp;

import gbw.melange.core.MelangeApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class WelcomeExample {

    private static final Logger log = LoggerFactory.getLogger(WelcomeExample.class);

    public static void main(String[] args) throws Exception {
        MelangeApplication.run(WelcomeExample.class);
    }

}
