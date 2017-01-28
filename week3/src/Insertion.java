import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

/**
 * Created by carvajal on 1/18/17.
 */
public class Insertion {

    public  static void sort(Comparable [] a){
        int N = a.length;
        for(int i = 0; i < N; i++){

            for(int  j = i; j > 0 && less(a[j],a[j-1]); j--){
                exch(a, j, j-1);
            }
        }
    }

    private static void exch(Comparable[] a, int j, int i) {
        Comparable aux = a[j];
        a[j] = a[i];
        a[i] = aux;
    }

    private static boolean less(Comparable comparable, Comparable comparable1) {
        return comparable.compareTo(comparable1) < 0;
    }

    public static void main(String[] args) {
        Integer [] elements  = {8,5,2};
        Insertion.sort(elements);
        for(Integer e: elements){
            StdOut.println(e);
        }

    }
}
