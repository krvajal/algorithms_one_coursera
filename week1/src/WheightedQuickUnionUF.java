/**
 * Created by carvajal on 1/5/17.
 */
public class WheightedQuickUnionUF extends QuickUnionUF {

    private int [] sz;
    public WheightedQuickUnionUF(int N){
        super(N);
        sz = new int[N];
        for(int i = 0; i < N; i++) sz[i] = 1;
    }

    @Override
    public  void union(int p, int q){

        int  i = root(p);
        int  j = root(q);
        if(i == j) return;
        if(sz[i] < sz[j]) {
            //p is in smaller tree, so
            //set root to bigger tree root
            id[i] = j;
            sz[j] += sz[i];
        }else{
            id[j] = i;
            sz[i] += sz[j];
        }

    }

}
