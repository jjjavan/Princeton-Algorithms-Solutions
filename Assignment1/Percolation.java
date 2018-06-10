import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{
    private boolean[][] grid;
    private final WeightedQuickUnionUF unionUF;
    private int total;
    private final int n;
    private int xyTo1D(int row, int col)
    {
        return row*n + col; //Point P = row + col*N

    }// set the root by subtracting 1 from each coordinate, multiplying n * x(row) and adding y(col)
    public Percolation(int n)
    {
        if(n <= 0)
            throw new IllegalArgumentException("Grid must be greater than zero. You entered " + n + "\n");
       grid = new boolean[n][n];
       unionUF = new WeightedQuickUnionUF((n*n)+2);///add virtual top and virtual bottom
       this.n = n;
       for(int i = 0; i < n; ++i)
           for(int j = 0; j < n; ++j)
               grid[i][j] = false; //init grid to closed
        final int VTP = n*n;
        final int VBTM = n*n+1;
        for (int i = 0; i < n; ++i)
        {
            unionUF.union(VTP,xyTo1D(0,i));
            unionUF.union(VBTM,xyTo1D(n-1,i));
        }
    } // create n-by-n grid, with all sites blocked
    public void open(int row, int col)
    {
        validate(row,col);//validate row and col are between 0 and N-1
        //convert row and col to array indices
        int x = row-1, y = col-1;
      if(isOpen(row,col)) return;
       grid[x][y] = true;
        //try and connect four points adjacent to pointP
        if (x > 0  && isOpen(row-1,col))
            unionUF.union(xyTo1D(x-1,y),xyTo1D(x,y));
        if (x < n-1 && isOpen(row+1,col))
            unionUF.union(xyTo1D(x+1,y),xyTo1D(x,y));
        if (y > 0 && isOpen(row,col-1))
            unionUF.union(xyTo1D(x,y-1), xyTo1D(x,y));
        if (y < n-1 && isOpen(row,col+1))
            unionUF.union(xyTo1D(x,y+1), xyTo1D(x,y));
        ++total;
    }// open site (row, col) if it is not open already, create components from adjacent sites(up to 4)
    public boolean isOpen(int row, int col)
    {
        validate(row,col);
        return grid[row-1][col-1];
    }// is site (row, col) open?
    public boolean isFull(int row, int col)
    {
        validate(row,col);
        if (!isOpen(row,col)) return false;
        return unionUF.connected(n*n,xyTo1D(row-1,col-1));
    }// is site (row, col) full?
    public int numberOfOpenSites()
    {
        return total;
    }
    // number of open sites
    public boolean percolates()
    {
        if (numberOfOpenSites() == 0) return false;//prevents corner cases when n==1 || n==2
        final int VTP = n*n, VBTM = n*n+1;
        return unionUF.connected(VTP,VBTM);
    }// does the system percolate?
    private void validate(int row, int col)
    {
        if(!(row >= 1 && row <= n) || !(col >= 1 && col <= n))
            throw new IllegalArgumentException("Row and column must be >= 1 and <= n. You entered " + row
                                                + " for row and " + col + " for column.\n");
    }// were valid values entered?
}

