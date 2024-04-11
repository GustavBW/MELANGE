module melange.shading {
    requires gdx;
    requires melange.common;
    requires org.lwjgl.opengl;
    requires org.apache.logging.log4j;
    requires spring.beans;
    requires spring.context;

    provides gbw.melange.common.shading.services.IShadingPipelineConfig with gbw.melange.shading.services.ShadingPipelineConfig;

    exports gbw.melange.shading.services to spring.beans, melange.core, melange.common;

    exports gbw.melange.shading;
    exports gbw.melange.shading.generative;
    exports gbw.melange.shading.generative.checker;
    exports gbw.melange.shading.generative.gradients;
    exports gbw.melange.shading.generative.voronoi;
    exports gbw.melange.shading.generative.noise;
    exports gbw.melange.shading.components;
}