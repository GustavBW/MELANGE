module melange.elements {
    requires gdx;
    requires melange.mesh;
    requires melange.common;
    requires melange.events;
    requires spring.core;
    requires org.apache.logging.log4j;
    requires melange.shading;

    exports gbw.melange.elements;
    exports gbw.melange.elements.problematic;
}