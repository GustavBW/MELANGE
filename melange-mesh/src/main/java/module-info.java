module melange.mesh {
    requires gdx;
    requires org.apache.logging.log4j;
    requires melange.common;
    requires spring.context;
    requires spring.beans;

    provides gbw.melange.common.mesh.services.IMeshPipelineConfig with gbw.melange.mesh.services.MeshPipelineConfig;
    exports gbw.melange.mesh.services to spring.beans, melange.core;

    exports gbw.melange.mesh.constants;
    exports gbw.melange.mesh.modifiers;
    exports gbw.melange.mesh;

}