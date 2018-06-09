import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public final class Solver
{
    private SearchNode goalNode;
    private final int fnlMoves;
    private Stack<Board> path;

    private static class SearchNode implements Comparable<SearchNode>
    {
        Board board;
        SearchNode prevNode;
        int moves;
        int priority;

        SearchNode(Board board)
        {

            this.board = board;
            prevNode = null;
            moves = 0;
            priority = board.manhattan();
        }

        @Override
        public int compareTo(SearchNode that)
        {
            if (that == null) throw new NullPointerException();
            /*int priorityA = board.manhattan() + moves;
            int priorityB = that.board.manhattan() + moves;*/
            if (priority < that.priority) return -1;
            if (priority > that.priority) return 1;
            return 0;
        }
    }

    public Solver(Board initial) // find a solution to the initial board (using the A* algorithm)
    {
        if (initial == null) throw new IllegalArgumentException("Null board.");
        MinPQ<SearchNode> minPQ = new MinPQ<>();
        minPQ.insert(new SearchNode(initial));

        MinPQ<SearchNode> minPQTwin = new MinPQ<>();
        minPQTwin.insert(new SearchNode(initial.twin()));

        SearchNode minNode = minPQ.delMin();
        SearchNode minTwnNode = minPQTwin.delMin();

        while (!minNode.board.isGoal() && !minTwnNode.board.isGoal()) {
            for (Board main : minNode.board.neighbors()) {

                if (minNode.prevNode == null || !main.equals(minNode.prevNode.board)) {
                    SearchNode current = new SearchNode(main);
                    current.prevNode = minNode;
                    current.moves = minNode.moves + 1;
                    current.priority += current.moves;
                    minPQ.insert(current);
                }

            }//for

            for (Board twn : minTwnNode.board.neighbors()) {
                if (minTwnNode.prevNode == null || !twn.equals(minNode.prevNode.board)) {
                    SearchNode current = new SearchNode(twn);
                    current.prevNode = minTwnNode;
                    current.moves = minTwnNode.moves + 1;
                    current.priority += current.moves;
                    minPQTwin.insert(current);
                }

            }//for

            minNode = minPQ.delMin();
            minTwnNode = minPQTwin.delMin();

        }//while

        if (minNode.board.isGoal()) {
            goalNode = minNode;
            fnlMoves = minNode.moves;
            path = getPath();
        }
        else {
            goalNode = null;
            path = null;
            fnlMoves = -1;
        }
    }


    public boolean isSolvable()            // is the initial board solvable?
    {
        return (goalNode != null);
    }

    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        return fnlMoves;
    }

    private Stack<Board> getPath()
    {

        Stack<Board> solution = new Stack<>();
        solution.push(goalNode.board);

        while (goalNode.prevNode != null) {
            goalNode = goalNode.prevNode;
            solution.push(goalNode.board);
        }

        return solution;
    }

    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        return path;
    }

    public static void main(String[] args) // solve a slider puzzle (given below)
    {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());

            for (Board board : solver.solution())
                StdOut.println(board);
        }

    }
}
