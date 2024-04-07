module melange.elements {
    requires gdx;
    requires melange.mesh;
    requires melange.common;
    requires melange.events;
    requires spring.core;
    requires melange.shading.pre.atomization;
    requires melange.misc;
    requires org.apache.logging.log4j;

    exports gbw.melange.elements;
    exports gbw.melange.elements.problematic;
}