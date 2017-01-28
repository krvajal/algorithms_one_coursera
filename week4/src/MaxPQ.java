/**
 * Created by carvajal on 1/20/17.
 */
public class MaxPQ<Key extends Comparable<Key>> {

    private Key[] pq;
    private int N = 0;

    public MaxPQ(int maxN) {

        pq = (Key[]) new Comparable[maxN + 1];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void insert(Key key) {
        pq[N++] = key;
        swim(N);
    }

    private void swim(int n) {

        while (n > 1 && less(n / 2, n)) {

            exch(n / 2, n);
            n = n / 2;
        }

    }

    private boolean less(int i, int n) {
        return pq[i].compareTo(pq[n]) < 0;
    }

    public Key delMax() {

        Key max = pq[1];
        exch(1, N--);
        pq[N + 1] = null;
        sink(1);
        return max;
    }

    // top-down reheapify (sink) implementation
    private void sink(int k) {

        while (2 * k <= N) {
            int j = 2 * k;
            if (k < N && less(j, j + 1)) j++;
            if (!less(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

    private void exch(int i, int j) {
        if(i == j) return;
        Key tmp = pq[i];
        pq[i] = pq[j];
        pq[j] = tmp;
    }

}
