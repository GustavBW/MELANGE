package gbw.melange.core.elementary;

import gbw.melange.common.elementary.contraints.IComputedTransforms;
import gbw.melange.common.elementary.types.IConstrainedElement;
import gbw.melange.elements.ElementTransformAccess;

import java.util.*;

/**
 * Algorithm overview: <br/>
 * - Establishes a many-rooted tree structure on {@link gbw.melange.core.elementary.AutomaticElementTransformResolver#load(List)} <br/>
 * - Then, depth first traverses all subtrees equally distributing them into a square grid <br/>
 * - For each grid, constraints.attachingAnchor is consulted to determine the most fitting cell for the element <br/>
 * - The last most element to be given a cell is given the remaining. <br/>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class AutomaticElementTransformResolver implements IAETR {

    public static class ElementSubTree {
        public IConstrainedElement root;
        public List<ElementSubTree> children = new ArrayList<>();
        public ElementSubTree(IConstrainedElement element){
            this.root = element;
        }
    }

    private final List<ElementSubTree> roots = new ArrayList<>();

    /**
     * <p>resolve.</p>
     */
    public void resolve() {
        if(roots.isEmpty()) return;

        ElementTransformAccess transformAccess = new ElementTransformAccess();
        int nRoots = roots.size();
        double nRootSq = Math.sqrt(nRoots);
        int nRows = (int) Math.ceil(nRootSq);
        int nCols = nRows;

        double rootWidth = 1.0 / nRootSq;
        double rootHeight = 1.0 / nRootSq;

        for (int i = 0; i < nRoots; i++) {

            int row = i / nCols;
            int col = i % nCols;

            double xOffset = (col * rootWidth * 2);
            double yOffset = (row * rootHeight * 2);

            resolveSubTree(transformAccess, roots.get(i), rootWidth, rootHeight, xOffset, yOffset);
        }
    }

    private void resolveSubTree(ElementTransformAccess transformAccess, ElementSubTree subTree, double parentWidth, double parentHeight, double parentOffsetX, double parentOffsetY) {
        IConstrainedElement parent = subTree.root;
        List<ElementSubTree> children = subTree.children;

        System.out.println("Resolved to w: " + parentWidth + "\t\th: " + parentHeight + "\t\toffX: " + parentOffsetX + "\t\toffY: " + parentOffsetY);

        // Apply translation and scale to the current root of the subtree
        transformAccess.setTranslation(parent, parentOffsetX, parentOffsetY, 0);
        transformAccess.setScale(parent, parentWidth, parentHeight, 1);

        if (children == null || children.isEmpty()) {
            return;
        }

        //Adjust according to padding for children
        double padding = parent.getConstraints().getPadding();
        parentOffsetX += padding;
        parentOffsetY += padding;
        parentWidth -= padding * 2;
        parentHeight -= padding * 2;

        double sqrtChildren = Math.sqrt(children.size());
        int nRows = (int) Math.ceil(sqrtChildren);
        int nCols = (int) Math.ceil(sqrtChildren); // For a square grid, but this can be adjusted

        // The scale for each child is based on the division of space into rows and columns
        double childScaleX = parentWidth / nCols;
        double childScaleY = parentHeight / nRows;

        for (int i = 0; i < children.size(); i++) {
            int row = i / nCols; // Determine current row
            int col = i % nCols; // Determine current column

            // Calculate offsets for children based on their row and column in the grid
            double childOffsetX = parentOffsetX + col * childScaleX;
            double childOffsetY = parentOffsetY + row * childScaleY;

            // Recursively adjust the subtree for each child
            resolveSubTree(transformAccess, children.get(i), childScaleX, childScaleY, childOffsetX, childOffsetY);
        }
    }

    /**
     * {@inheritDoc}
     *
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

    //To avoid GC during runtime
    private final Queue<ElementSubTree> searchQueue = new LinkedList<>();

    /**
     * Uses BFS to search for the ElementSubTree containing the given element as its root.
     *
     * @param targetElement The IConstrainedElement to search for.
     * @return The ElementSubTree that contains the targetElement as its root, or null if not found.
     */
    public ElementSubTree findSubTreeBFS(IConstrainedElement targetElement) {
        searchQueue.clear();
        searchQueue.addAll(roots);

        while (!searchQueue.isEmpty()) {
            ElementSubTree currentSubTree = searchQueue.poll();

            // Check if the current element is the target
            if (currentSubTree.root.equals(targetElement)) {
                return currentSubTree;
            }

            // If not, add all children of the current element to the queue
            searchQueue.addAll(currentSubTree.children);
        }

        return null; // Target element not found
    }


    /** {@inheritDoc} */
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
    }
}
