/**
 * Created by carvajal on 1/5/17.
 */
public class LinkedStackOfStrings {

    private  Node first  = null;

    private class Node{

        // Size information
        // 16 bytes (object overhead)
        // 8 bytes (inner class overhead)
        // 8 bytes (reference to string)
        // 8 bytes (reference to node)
        // total: 40 bytes per stack node
        String item;
        Node next;
    }

    /**
     * Returns true is the stack have
     * zero elements
     */
    public boolean isEmpty(){

        return first == null;
    }

    /**
     * Add an item in top of the stack
     *
     * @param item the string to insert
     */
    public void push(String item){
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
    }

    /**
     * Returns the most recently added element
     * and removes it from the stack
     * @return
     */
    public String pop(){
        String item = first.item;
        first = first.next;
        return  item;
    }

}
