package gbw.melange.welcomeapp;

import gbw.melange.common.MelangeApplication;
import gbw.melange.welcomeapp.discovery.CanItFindThis;

public class WelcomeExample {

    public static void main(String[] args) {
        new CanItFindThis();
        new SpaceInRootUserPackage();
        MelangeApplication.run(WelcomeExample.class);
    }

    public WelcomeExample(){
        System.out.println("[WE] Constructor hit");
    }

}
