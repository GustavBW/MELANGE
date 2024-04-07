module melange.shading {
    requires gdx;
    requires melange.common;
    requires org.lwjgl.opengl;
    requires org.jetbrains.annotations;
    requires org.apache.logging.log4j;
    requires spring.beans;
    requires spring.context;


    exports gbw.melange.shading;
    exports gbw.melange.shading.generative;
    exports gbw.melange.shading.generative.checker;
    exports gbw.melange.shading.generative.gradients;
    exports gbw.melange.shading.generative.voronoi;
    exports gbw.melange.shading.generative.noise;
    exports gbw.melange.shading.services;

    exports gbw.melange.shading.components;


}