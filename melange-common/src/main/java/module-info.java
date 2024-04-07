module melange.common {

    requires spring.context;
    requires melange.mesh;
    requires melange.shading.pre.atomization;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;
    requires gdx;
    requires spring.core;

    exports gbw.melange.common;
    exports gbw.melange.common.events;
    exports gbw.melange.common.events.interactions;
    exports gbw.melange.common.hooks;
    exports gbw.melange.common.elementary.space;
    exports gbw.melange.common.errors;
    exports gbw.melange.common.elementary.types;
    exports gbw.melange.common.events.observability;
    exports gbw.melange.common.events.observability.filters;
    exports gbw.melange.common.elementary.contraints;
    exports gbw.melange.common.builders;
    exports gbw.melange.common.elementary;
    exports gbw.melange.common.navigation;
    exports gbw.melange.common.annotations;
    exports gbw.melange.common.elementary.styling;
    exports gbw.melange.common.elementary.rules;
    exports gbw.melange.common.rules;

}