module melange.mesh {
    requires gdx;
    requires org.apache.logging.log4j;
    requires melange.common;
    requires org.jetbrains.annotations;
    requires spring.context;
    requires spring.beans;

    exports gbw.melange.mesh.services;

    exports gbw.melange.mesh.constants;
    exports gbw.melange.mesh.modifiers;
    exports gbw.melange.mesh;

    provides gbw.melange.common.mesh.services.IMeshPipelineConfig with gbw.melange.mesh.services.MeshPipelineConfig;
}