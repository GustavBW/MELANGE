module melange.example {

    requires melange.common;
    requires melange.core;
    requires melange.mesh;
    requires melange.shading;

    requires org.apache.logging.log4j;
    requires spring.beans;
    requires gdx;

    opens gbw.melange.example to spring.beans;
}