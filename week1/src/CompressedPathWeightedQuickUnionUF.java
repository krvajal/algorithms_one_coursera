/**
 * Created by carvajal on 1/5/17.
 */
public class CompressedPathWeightedQuickUnionUF extends WheightedQuickUnionUF {

    public CompressedPathWeightedQuickUnionUF(int N) {
        super(N);


    }

    @Override
    protected int root(int i) {
        while (id[i] != i) {
            id[i] = id[id[i]]; //flattening the tree a little bit
            i = id[i];
        }
        return i;
    }

}
