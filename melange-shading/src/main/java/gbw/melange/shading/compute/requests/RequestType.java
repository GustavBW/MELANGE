package gbw.melange.shading.compute.requests;

public enum RequestType {
    /**
     * When scheduled, blocks the scheduling thread until the request is complete and the results ready.
     */
    NOW,
    /**
     * When scheduled, adds the request to the execution queue.
     */
    WHENEVER,
    /**
     * When scheduled, invokes the providers, but then does nothing.
     * Behaves as a {@link RequestType#NOW} when {@link ExecHandleNow#get()} is called.
     */
    LATER;
}
