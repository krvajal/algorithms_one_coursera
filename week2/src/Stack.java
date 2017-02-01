import java.util.Iterator;
import java.util.ListIterator;

/**
 * Created by carvajal on 1/11/17.
 */
public class Stack<Item> implements Iterable<Item> {

    private class Node{
        Item item;
        Node next;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();

    }

    private class ListIterator implements Iterator<Item>{

        private  Node current = null;
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}
