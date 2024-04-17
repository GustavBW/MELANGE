module melange.core {
    //User-side entry-point
    exports gbw.melange.core.app;

    //Spring stuff
    opens gbw.melange.core.elementary to spring.beans, spring.core;
    opens gbw.melange.core.interactions to spring.beans, spring.core;

    //Melange modules
    requires melange.common;
    requires melange.shading;
    requires melange.mesh;
    requires melange.dev;
    requires melange.events;
    requires melange.elements;
    //External libs
    requires gdx;
    requires gdx.backend.lwjgl3;
    requires org.apache.logging.log4j;
    requires spring.context;
    requires spring.core;
    requires spring.beans;
    requires org.reflections;
}