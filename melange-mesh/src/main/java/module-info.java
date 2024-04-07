module melange.mesh {
    requires gdx;
    requires org.apache.logging.log4j;
    requires melange.common;
    requires org.jetbrains.annotations;
    requires spring.context;

    exports gbw.melange.mesh.services;
    exports gbw.melange.common.mesh.errors;

    opens gbw.melange.mesh.services to spring.beans;
}