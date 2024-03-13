package gbw.melange.common.structures;

/**
 * Constant size linked list that can be pushed to any number of times, and simply looses the last value.
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class RollOverLinkedList<T> {
    private static class Link<T> {
        public Link<T> previous;
        public T value;
        public Link<T> next;
    }
    private Link<T> head;
    private Link<T> tail;
    /**
     * <p>Constructor for RollOverLinkedList.</p>
     *
     * @param size a int
     */
    public RollOverLinkedList(int size){
        head = new Link<>();
        Link<T> latest = head;
        for(int i = 0; i < size; i++){
            Link<T> next = new Link<>();
            latest.next = next;
            next.previous = latest;

            if(i == size - 1){
                tail = next;
            }
        }
    }

    /**
     * <p>push.</p>
     *
     * @param value a T object
     * @return a boolean
     */
    public boolean push(T value){
        boolean valueLost = tail.value != null;

        //Take out tail and make it head.
        tail.value = value;
        final Link<T> formerTail = tail;
        tail = formerTail.previous;
        tail.next = null;
        head.previous = formerTail;
        head = formerTail;


        return valueLost;
    }
}
