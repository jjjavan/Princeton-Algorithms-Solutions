public class MinPQ<Key extends Comparable<Key>>
{
    private Key[] pq;
    private int n = 0;

    public MinPQ(int max)
    {
        pq = (Key[]) new Object[max + 1];
    }

    public boolean isEmpty()
    {
        return n == 0;
    }

    public int size()
    {
        return n;
    }

    public void insert(Key v)
    {
        pq[++n] = v;
        swim(n);
    }


    public Key delMin()
    {
        Key min = pq[1];
        exch(1, n--);
        pq[n+1] = null;// Avoid loitering
        sink(1);
        return min;
    }

    private boolean greater(int i,int j)
    {
       return pq[i].compareTo(pq[j]) > 0;

    }

    private void exch(int i, int j)
    {
        Key tmp = pq[i];
        pq[i] = pq[j];
        pq[j] = pq[i];

    }

    private void swim(int k)
    {
        while (k > 1 && greater(k/2, k))
        {
            exch(k/2, k);
            k = k/2;
        }
    }

    private void sink(int k)
    {
        while (2*k <= n )
        {
            int j = 2*k;
            if (j < n && greater(j, j+1)) ++j;
            if (!greater(j,k)) break;
            exch(k,j);
            k = j;
        }
    }
}
