package gbw.melange.welcomeapp;

import gbw.melange.core.MelangeApplication;
import org.springframework.beans.factory.annotation.Autowired;

public class WelcomeExample {

    public static void main(String[] args) throws Exception {
        MelangeApplication.run(WelcomeExample.class);
    }

    @Autowired
    public WelcomeExample(SpaceInRootUserPackage thisShouldWork){
        System.out.println("[WE] Constructor hit");
    }

}
