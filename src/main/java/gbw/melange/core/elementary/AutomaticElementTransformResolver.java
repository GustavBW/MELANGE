package gbw.melange.core.elementary;

import gbw.melange.common.elementary.types.IElement;

import java.util.*;

public class AutomaticElementTransformResolver {

    public static class ElementSubTree {
        public IElement<?> root;
        public List<ElementSubTree> children = new ArrayList<>();
        public ElementSubTree(IElement<?> element){
            this.root = element;
        }
    }

    private final List<ElementSubTree> roots = new ArrayList<>();
    private double avgRootElementInitialSize = 1;

    public AutomaticElementTransformResolver(List<IElement<?>> elements) {
        if(elements.isEmpty()) return;

        Map<IElement<?>, ElementSubTree> elementToNodeMap = new HashMap<>();

        // First pass: create nodes for all elements and map them
        for (IElement<?> element : elements) {
            ElementSubTree node = new ElementSubTree(element);
            elementToNodeMap.put(element, node);
        }

        // Second pass: establish parent-child relationships
        for (IElement<?> element : elements) {
            IElement<?> parent = element.getConstraints().getAttachedTo();
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

    public void resolve(ElementSubTree root){

    }

    public List<ElementSubTree> getRoots() {
        return roots;
    }
}
