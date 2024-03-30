package gbw.melange.shading.graph.nodes;

public record GraphNodeParams(boolean retainFBO, boolean retainResult, boolean useCaching) {
    public static final GraphNodeParams DEFAULT = new GraphNodeParams(true, true,true);
}
