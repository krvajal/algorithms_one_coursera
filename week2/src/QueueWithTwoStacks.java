/**
 * Created by carvajal on 1/11/17.
 */
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class QueueWithTwoStacks<Item> {
    private Stack<Item> s1;
    private Stack<Item> s2;
    public QueueWithTwoStacks(){
        s1 = new Stack<Item>();
        s2 = new Stack<Item>();

    }

    public void enqueue(Item item){


        s1.push(item);
    }
    public Item dequeue(){
        if(s2.isEmpty()) {
            while (!s1.isEmpty())
                s2.push(s1.pop());
        }
        return s2.pop();
    }

    public static void main(String[] args) {
        QueueWithTwoStacks<Integer> queue = new QueueWithTwoStacks<>();
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(23);
        StdOut.println(queue.dequeue());
        StdOut.println(queue.dequeue());
        queue.enqueue(10);
        StdOut.println(queue.dequeue());
        StdOut.println(queue.dequeue());

    }
}
