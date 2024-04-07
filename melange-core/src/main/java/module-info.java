module melange.core {

    exports gbw.melange.core.app;

    requires melange.common;
    requires melange.shading;
    requires melange.mesh;
    requires melange.dev;
    requires melange.events;
    requires melange.elements;

    requires gdx;
    requires gdx.backend.lwjgl3;
    requires org.apache.logging.log4j;
    requires spring.context;
    requires spring.core;
    requires spring.beans;
    requires org.reflections;
}