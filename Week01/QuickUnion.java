public class QuickUnion {
    private int[] id;
    private int[] size;
    public QuickUnion(int N){
        id = new int[N];
        size = new int[N];
        for(int i = 0; i < N; i++) {
            id[i] = i;
            size[i] = 1;
        }
    }

    private int root(int i){
        while(id[i] != i)
            i = id[i];
        return i;
    }

    public boolean connected(int p, int q){
        return root(p) == root(q);
    }

    public void union(int p, int q){
        int i = root(p);
        int j = root(q);
        if(i == j) return;
        if(size[i] > size[j]){
            id[j] = i;
            size[i] += size[j];
        }
        else {
            id[i] = j;
            size[j] += size[i];
        }
    }
}
