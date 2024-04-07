module melange.misc {
    requires melange.common;
    requires melange.core;
    requires org.apache.logging.log4j;
    requires gdx;
    requires melange.mesh;
    requires melange.shading.pre.atomization;
    requires spring.beans;
    requires melange.shading;

    exports gbw.melange.welcomeapp;
}