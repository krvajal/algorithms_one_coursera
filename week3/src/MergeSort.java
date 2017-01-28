import org.jetbrains.annotations.Contract;
import  edu.princeton.cs.algs4.Insertion;
/**
 * Created by carvajal on 1/18/17.
 */

public class MergeSort {

    private  static int CUTOFF = 5;
    protected   static Comparable [] aux;
    protected static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {

        // check if already sorted
        if(!less(a[mid + 1],a [mid])) return;

        assert isSorted(a, lo, mid); // precondition a[lo..hi] sorted
        assert isSorted(a, mid + 1, hi); // precondition a[lo..hi] sorted

        // copy values
        for (int k = 0; k <= hi; k++) {
            aux[k] = a[k];

        }
        int i = lo;
        int j = mid + 1;
        // merge
        for (int k = lo; k <= hi; k++) {

            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (less(aux[j], aux[i])) aux[k] = aux[j++];
            else aux[k] = aux[i++];
        }
        assert isSorted(a, lo, hi); // postcondition: a[lo..hi] sorted
    }

    protected static  boolean less(Comparable a , Comparable b){

        return  a.compareTo(b) < 0;
    }

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if(hi <= lo +   CUTOFF - 1){
            Insertion.sort(a, lo, hi);
            return;
        }
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux,lo, mid, hi);
    }

    public  static void sort(Comparable [] a){
        aux = new Comparable[a.length];
        sort(a, aux,0, a.length - 1);
    }
    @Contract(pure = true)
    public static boolean isSorted(Comparable[] a, int lo, int hi) {

        for (int k = lo + 1; k <= hi; k++) {
            if (!less(a[k - 1],a[k])) return false;
        }
        return true;
    }


}
