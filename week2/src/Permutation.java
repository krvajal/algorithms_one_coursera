import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

/**
 * Created by carvajal on 1/13/17.
 */
public class Permutation {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();

        String [] words = StdIn.readAllStrings();
        for(String word: words){
            randomizedQueue.enqueue(word);
        }

        Iterator<String> iterator = randomizedQueue.iterator();
        int count = 0;
        while (count < k) {
            StdOut.println(iterator.next());
            count++;
        }

    }
}
