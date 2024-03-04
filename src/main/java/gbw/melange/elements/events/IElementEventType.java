package gbw.melange.elements.events;


public enum IElementEventType {
    /**
     * The moment that caused this event may affect other elements meaning further investigation and propegating updates is needed.<br/>
     * Events that match this type: ElementMoveEvent
     */
    CASCADING,
    /**
     * The outcome of this event cannot, and may not ever, have cascading effects on other elements.<br/>
     * Events that match this type: ElementStyleChange
     */
    CONTAINED;
}
