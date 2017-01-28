/**
 * Created by carvajal on 1/18/17.
 */
public class MergeBU extends MergeSort {


    public static void sort(Comparable[] a) {
        int N = a.length;
        aux = new Comparable[N];

        // sz is the size of the subarray
        for (int sz = 1; sz < N; sz += sz) {
            for (int lo = 0; lo < N - sz; lo += sz + sz) {
                merge(a, aux, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, N - 1));
            }

        }
    }
}
