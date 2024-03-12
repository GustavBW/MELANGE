package gbw.melange.core.elementary;

import com.badlogic.gdx.math.Vector3;
import gbw.melange.common.elementary.IComputedTransforms;
import gbw.melange.common.elementary.types.IConstrainedElement;
import gbw.melange.elements.ElementTransformAccess;

import java.util.*;

public class AutomaticElementTransformResolver implements IAETR {

    public static class ElementSubTree {
        public IConstrainedElement root;
        public List<ElementSubTree> children = new ArrayList<>();
        public ElementSubTree(IConstrainedElement element){
            this.root = element;
        }
    }

    private final List<ElementSubTree> roots = new ArrayList<>();
    private double avgRootElementInitialSize = 1;

    public void resolve() {
        ElementTransformAccess transformAccess = new ElementTransformAccess();
        avgRootElementInitialSize = 1.0 / roots.size();

        for (int i = 0; i < roots.size(); i++) {
            double rootScale = 1.0 / roots.size();
            resolveSubTree(transformAccess, roots.get(i), i * rootScale, i * rootScale, i * rootScale, i * rootScale);
        }
    }

    private void resolveSubTree(ElementTransformAccess transformAccess, ElementSubTree subTree, double scaleX, double scaleY, double xOffset, double yOffset) {
        transformAccess.setTranslation(subTree.root, xOffset, yOffset, 0);
        transformAccess.setScale(subTree.root, scaleX, scaleY, 1);

        List<ElementSubTree> children = subTree.children;
        if(children == null || children.isEmpty()){
            return;
        }
        double childScaleX = scaleX / children.size();
        double childScaleY = scaleY / children.size();

        for(int i = 0; i < children.size(); i++){
            resolveSubTree(transformAccess, children.get(i), childScaleX, childScaleY, i * childScaleX, i * childScaleY);
        }
    }

    /**
     * Resolve only this element and elements attached to it
     */
    public void resolveFrom(IConstrainedElement element){
        ElementSubTree subTree = findSubTreeBFS(element);
        if(subTree == null) return;

        ElementTransformAccess transformAccess = new ElementTransformAccess();
        IComputedTransforms computed = subTree.root.computed();
        double rootOffsetX = computed.getPositionX();
        double rootOffsetY = computed.getPositionY();
        double rootScaleX = computed.getWidth();
        double rootScaleY = computed.getHeight();

        resolveSubTree(transformAccess, findSubTreeBFS(element), rootScaleX, rootScaleY, rootOffsetX, rootOffsetY);
    }

    /**
     * Uses BFS to search for the ElementSubTree containing the given element as its root.
     *
     * @param targetElement The IConstrainedElement to search for.
     * @return The ElementSubTree that contains the targetElement as its root, or null if not found.
     */
    public ElementSubTree findSubTreeBFS(IConstrainedElement targetElement) {
        Queue<ElementSubTree> queue = new LinkedList<>();

        // Add all roots to the queue to start the search
        queue.addAll(roots);

        while (!queue.isEmpty()) {
            ElementSubTree currentSubTree = queue.poll();

            // Check if the current element is the target
            if (currentSubTree.root.equals(targetElement)) {
                return currentSubTree;
            }

            // If not, add all children of the current element to the queue
            queue.addAll(currentSubTree.children);
        }

        return null; // Target element not found
    }


    public void load(List<? extends IConstrainedElement> elements){
        if(elements.isEmpty()) return;

        roots.clear();

        Map<IConstrainedElement, ElementSubTree> elementToNodeMap = new HashMap<>();

        // First pass: create nodes for all elements and map them
        for (IConstrainedElement element : elements) {
            ElementSubTree node = new ElementSubTree(element);
            elementToNodeMap.put(element, node);
        }

        // Second pass: establish parent-child relationships
        for (IConstrainedElement element : elements) {
            IConstrainedElement parent = element.getConstraints().getAttachedTo();
            ElementSubTree currentNode = elementToNodeMap.get(element);

            if (parent != null) {
                ElementSubTree parentNode = elementToNodeMap.get(parent);
                if (parentNode != null) {
                    parentNode.children.add(currentNode);
                }
            } else {
                // This is a root node
                roots.add(currentNode);
            }
        }
        avgRootElementInitialSize = 1.0 / roots.size();
    }
}
