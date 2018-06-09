//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import edu.princeton.cs.algs4.Queue;
import java.util.Arrays;


public final class  Board
{
    private final int[][] points;
    //pos of blank
    private int posOfBlank;
    private final int manhattanScore;
    private final int N;


    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
    {
        N = blocks.length;
        points = new int[N][N];

        for (int row = 0; row < blocks.length; ++row)
            for (int col = 0; col < blocks[row].length; ++col) {
                if (blocks[row][col] == 0) posOfBlank = N * row + col;
                points[row][col] = blocks[row][col];

            }


        manhattanScore = calcManhattan(blocks);

    }


    public int manhattan() // sum of Manhattan distances between blocks and goal
    {
        return manhattanScore;
    }

    private int calcManhattan(int[][] blocks)
    {
        int sum = 0;
        int start, end;
        int[][] goal = new int[N][N];
        //Goal board with each square in place to compute Manhattan
        int value = 1;
        for (int row = 0; row < N; ++row)
            for (int col = 0; col < N; ++col)
                goal[row][col] = value++;

        //Set last square == blank
        goal[N - 1][N - 1] = 0;

        int blankX = posOfBlank / N;
        int blankY = posOfBlank % N;

        int[] pts = new int[2];
        for (int row = 0; row < N; ++row)
            for (int col = 0; col < N; ++col) {
                start = blocks[row][col];
                end = goal[row][col];
                if ((row != blankX || col != blankY) && start != end) {
                    goalPosition(start, pts);
                    sum += (Math.abs(row - pts[0]) + Math.abs(col - pts[1]));
                }

            }

        return sum;
    }

    private void goalPosition(int values, int[] pts)
    {
        if (values % N == 0) {   //if val is a multiple of N then row == val/N - 1
            pts[0] = (values / N - 1);
            //and col is always N - 1
            pts[1] = (N - 1);
        } else {
            // else row == val/N
            pts[0] = (values / N);
            //and col == remainder of val/N - 1
            pts[1] = (values % N - 1);
        }
    }


    public Iterable<Board> neighbors()     // all neighboring boards
    {
        Queue<Board> boardQueue = new Queue<>();

        final int POSX = posOfBlank / N;
        final int POSY = posOfBlank % N;


        //if neighbor above
        if (POSX - 1 >= 0)
            boardQueue.enqueue(new Board(swap(POSX - 1, POSY, POSX, POSY)));


        //if neighbor below
        if (POSX + 1 < N)
            boardQueue.enqueue(new Board(swap(POSX + 1, POSY, POSX, POSY)));


        //if neighbor to right
        if (POSY + 1 < N)
            boardQueue.enqueue(new Board(swap(POSX, POSY + 1, POSX, POSY)));


        //if neighbor to left
        if (POSY - 1 >= 0)
            boardQueue.enqueue(new Board(swap(POSX, POSY - 1, POSX, POSY)));

        return boardQueue;
    }

    private int[][] swap(int x, int y, int x2, int y2)
    {
        int[][] cpyPts = new int[N][N];

        for (int row = 0; row < N; row++)
            cpyPts[row] = Arrays.copyOf(points[row], points[row].length);

        int tmp = cpyPts[x2][y2];
        cpyPts[x2][y2] = cpyPts[x][y];
        cpyPts[x][y] = tmp;
        return cpyPts;
    }

    // (where blocks[i][j] = block in row i, column j)
    public int dimension()               // board dimension n
    {
        return N;
    }

    public int hamming()                   // number of blocks out of place
    {
        int sum = 0;
        int[][] goal = new int[N][N];

        //Goal board with each square in place to compute Hamming
        int value = 1;
        for (int row = 0; row < N; ++row)
            for (int col = 0; col < N; ++col)
                goal[row][col] = value++;

        goal[N - 1][N - 1] = 0;

        int blankX = posOfBlank / N;
        int blankY = posOfBlank % N;

        for (int row = 0; row < N; ++row)
            for (int col = 0; col < N; ++col)
                if ((row != blankX || col != blankY) && points[row][col] != goal[row][col])
                    sum += 1;

        return sum;

    }


    public boolean isGoal()                // is this board the goal board?
    {
        return (hamming() == 0);
    }

    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        Board twin = null;
        boolean found = false;

        for (int i = 0; i < N && !found; ++i)
            for (int j = 1; j < N && !found; ++j)
                if (points[i][j - 1] != 0 && points[i][j] != 0) {
                    twin = new Board(swap(i, j - 1, i, j));
                    found = true;
                }


        return twin;
    }


    public boolean equals(Object other)        // does this board equal y?
    {
        if (this == other) return true;
        if (other == null) return false;
        if (this.getClass() != other.getClass()) return false;

        Board that = (Board) other;
        if (N != that.N) return false;

        for (int i = 0; i < N; ++i)
            for (int j = 0; j < N; j++)
                if (points[i][j] != that.points[i][j])
                    return false;

        return true;
    }


    public String toString()               // string representation of this board (in the output format specified below)
    {
        StringBuilder brdString = new StringBuilder();
        brdString.append("\n" + N + "\n");

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                brdString.append(points[i][j] + " ");
            brdString.append("\n");
        }


        return brdString.toString();
    }

    public static void main(String[] args)  // unit tests (not graded)
    {
        /*File file = new File(args[0]);
        Scanner fileInp = new Scanner(file);
        int n = fileInp.nextInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = fileInp.nextInt();
        Board board = new Board(tiles);
        Board board2 = new Board(tiles);

        System.out.print("Board: " + board);
        System.out.print("Twin: " + board.twin());
        //System.out.printf("Hamming: %d%n", board.hamming());
        System.out.printf("Manhattan: %d%n", board.manhattan());

        System.out.printf("Twin Manhattan: %d%n",board2.manhattan());
       // System.out.printf("Twin Hamming: %d%n", board2.hamming());
       *//* System.out.print(board + ((board.equals(board2)) ? " equals " : "does not equal ") + board2);
        System.out.print(board.twin());*//*

        *//*for (Board nbr : board.neighbors()) {
            System.out.print(nbr);
            System.out.printf("Manhattan Score: %d%n", nbr.manhattanScore);

        }*/

    }
}
