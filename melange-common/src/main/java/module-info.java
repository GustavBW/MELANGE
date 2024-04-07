module melange.common {

    requires spring.context;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;
    requires gdx;
    requires spring.core;
    requires org.lwjgl.opengl;

    exports gbw.melange.common;
    exports gbw.melange.common.events;
    exports gbw.melange.common.events.interactions;
    exports gbw.melange.common.events.observability;
    exports gbw.melange.common.events.observability.filters;

    exports gbw.melange.common.hooks;

    exports gbw.melange.common.elementary.space;
    exports gbw.melange.common.elementary.types;
    exports gbw.melange.common.elementary.contraints;
    exports gbw.melange.common.elementary;
    exports gbw.melange.common.elementary.styling;
    exports gbw.melange.common.elementary.rules;

    exports gbw.melange.common.errors;
    exports gbw.melange.common.builders;
    exports gbw.melange.common.navigation;
    exports gbw.melange.common.annotations;
    exports gbw.melange.common.rules;

    exports gbw.melange.common.shading;
    exports gbw.melange.common.shading.errors;
    exports gbw.melange.common.shading.constants;
    exports gbw.melange.common.shading.components;
    exports gbw.melange.common.shading.services;
    exports gbw.melange.common.shading.generative.voronoi;
    exports gbw.melange.common.shading.generative.noise;
    exports gbw.melange.common.shading.generative;
    exports gbw.melange.common.shading.generative.checker;
    exports gbw.melange.common.shading.generative.gradients;
    exports gbw.melange.common.shading.postprocess;

    exports gbw.melange.common.mesh;
    exports gbw.melange.common.mesh.modifiers;
    exports gbw.melange.common.mesh.services;
    exports gbw.melange.common.mesh.formatting.slicing;
    exports gbw.melange.common.mesh.constants;
    exports gbw.melange.common.mesh.formatting.providers;
    exports gbw.melange.common.mesh.formatting;
    exports gbw.melange.common.mesh.errors;

}