/**
 * Created by carvajal on 1/5/17.
 */
public abstract class QuickUnionUF {


    protected int[] id;

    public QuickUnionUF(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++) id[i] = i;


    }

    protected int root(int i) {
        while (i != id[i]) i = id[i];
        return i;
    }

    public boolean connected(int p, int q){

        return  root(p) == root(q);
    }
    public  void union(int p, int q){
        int i = root(p);
        int j = root(q);
        //set root of i-root to q root
        id[i] = j;
    }


}
