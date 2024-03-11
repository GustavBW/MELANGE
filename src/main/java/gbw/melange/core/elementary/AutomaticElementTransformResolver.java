package gbw.melange.core.elementary;

import gbw.melange.common.elementary.types.IConstrainedElement;
import gbw.melange.common.elementary.types.IElement;
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

    public void resolve(){
        ElementTransformAccess transformAccess = new ElementTransformAccess();
        //Pass 1: Evenly distribute everything, ignore SizingPolicy


        //Pass 2: Backwards propegate from all SizingPolicy.fitContent
    }

    /**
     * Resolve only this element and elements attached to it
     */
    public void resolveFrom(IConstrainedElement element){

    }

}
