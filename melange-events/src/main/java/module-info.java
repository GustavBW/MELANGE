module melange.events {
    requires spring.context;
    requires melange.common;

    exports gbw.melange.events.observability.filters;
    exports gbw.melange.events.observability;
    exports gbw.melange.events;
}