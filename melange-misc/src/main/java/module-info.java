module melange.misc {
    requires melange.common;
    requires melange.core;
    requires org.apache.logging.log4j;
    requires gdx;
    requires melange.mesh;
    requires melange.shading.pre.atomization;
    requires spring.beans;
    exports gbw.melange.rules;
    exports gbw.melange.welcomeapp;
}