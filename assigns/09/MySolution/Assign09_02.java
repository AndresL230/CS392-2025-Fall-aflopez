import Library.FnList.*;
import Library.LnStrm.*;
import Library.FnTuple.*;
import Library.FnGtree.*;

public class Assign09_02 {
    // HX-2025-12-02:
    // Please use Warnsdorf's rule to
    // search for knight's tours on a chess board
    // of dimension (chessBoardSize x chessBoardSize)
    // Your search should be based on the PFirstEnumerate
    // (See Code/FnGtree/FnGtreeSUtil.java)

    static class KnightTourNode implements FnGtree<FnList<FnTupl2<Integer,Integer>>> {
	private FnList<FnTupl2<Integer,Integer>> path;
	private boolean[][] visited;
	private int boardSize;
	private int priority;

	public KnightTourNode(int boardSize) {
	    this.boardSize = boardSize;
	    this.path = new FnList<>();
	    this.visited = new boolean[boardSize][boardSize];
	    this.priority = 0;
	}

	public KnightTourNode(FnList<FnTupl2<Integer,Integer>> path, boolean[][] visited, int boardSize, int priority) {
	    this.path = path;
	    this.visited = visited;
	    this.boardSize = boardSize;
	    this.priority = priority;
	}

	public FnList<FnTupl2<Integer,Integer>> value() {
	    return this.path;
	}

	public int priority() {
	    return this.priority;
	}

	public FnList<FnGtree<FnList<FnTupl2<Integer,Integer>>>> children() {
	    if (this.path.length() == this.boardSize * this.boardSize) {
		return new FnList<>();
	    }

	    int row, col;
	    if (this.path.nilq()) {
		row = 0;
		col = 0;
	    } else {
		FnTupl2<Integer,Integer> lastPos = this.path.hd();
		row = lastPos.getSub0();
		col = lastPos.getSub1();
	    }

	    int[][] knightMoves = {{-2,-1},{-2,1},{-1,-2},{-1,2},{1,-2},{1,2},{2,-1},{2,1}};
	    FnList<FnGtree<FnList<FnTupl2<Integer,Integer>>>> result = new FnList<>();

	    for (int[] move : knightMoves) {
		int newRow = row + move[0];
		int newCol = col + move[1];

		if (isValid(newRow, newCol)) {
		    boolean[][] newVisited = copyArray(this.visited);
		    newVisited[newRow][newCol] = true;

		    FnTupl2<Integer,Integer> newPos = new FnTupl2<>(newRow, newCol);
		    FnList<FnTupl2<Integer,Integer>> newPath = new FnList<>(newPos, this.path);

		    int warnsdorfPriority = calculateWarnsdorfPriority(newRow, newCol, newVisited);

		    KnightTourNode child = new KnightTourNode(newPath, newVisited, this.boardSize, warnsdorfPriority);
		    result = new FnList<>(child, result);
		}
	    }

	    return result;
	}

	private boolean isValid(int row, int col) {
	    return row >= 0 && row < boardSize && col >= 0 && col < boardSize && !visited[row][col];
	}

	private int calculateWarnsdorfPriority(int row, int col, boolean[][] vis) {
	    int[][] knightMoves = {{-2,-1},{-2,1},{-1,-2},{-1,2},{1,-2},{1,2},{2,-1},{2,1}};
	    int count = 0;
	    for (int[] move : knightMoves) {
		int newRow = row + move[0];
		int newCol = col + move[1];
		if (newRow >= 0 && newRow < boardSize && newCol >= 0 && newCol < boardSize && !vis[newRow][newCol]) {
		    count++;
		}
	    }
	    return count;
	}

	private boolean[][] copyArray(boolean[][] arr) {
	    boolean[][] copy = new boolean[arr.length][arr[0].length];
	    for (int i = 0; i < arr.length; i++) {
		for (int j = 0; j < arr[i].length; j++) {
		    copy[i][j] = arr[i][j];
		}
	    }
	    return copy;
	}
    }

    public static
	LnStrm<FnList<FnTupl2<Integer,Integer>>>
	genKnightsTours(int chessBoardSize) {
	// I expect you to find some knight's tours for
	// a board of dimension 8x8; there will be bonus
	// points for handling larger boards.

	boolean[][] initialVisited = new boolean[chessBoardSize][chessBoardSize];
	initialVisited[0][0] = true;
	FnTupl2<Integer,Integer> startPos = new FnTupl2<>(0, 0);
	FnList<FnTupl2<Integer,Integer>> startPath = new FnList<>(startPos, new FnList<>());
	KnightTourNode actualRoot = new KnightTourNode(startPath, initialVisited, chessBoardSize, 0);

	LnStrm<FnList<FnTupl2<Integer,Integer>>> allPaths = FnGtreeSUtil.PFirstEnumerate(actualRoot);

	return allPaths.filter0((path) -> path.length() == chessBoardSize * chessBoardSize);
    }

    public static void main(String[] args) {
	System.out.println("Testing Knight's Tours for 5x5 board:");
	LnStrm<FnList<FnTupl2<Integer,Integer>>> tours = genKnightsTours(5);

	final int[] count = {0};
	tours.foritm0((tour) -> {
	    if (count[0] < 3) {
		System.out.print("Tour " + (count[0] + 1) + ": ");
		tour.System$out$print();
		System.out.println();
		count[0]++;
	    }
	});

	if (count[0] == 0) {
	    System.out.println("No complete tours found for 5x5");
	}

	System.out.println("\nTesting Knight's Tours for 8x8 board (limited output):");
	LnStrm<FnList<FnTupl2<Integer,Integer>>> tours8 = genKnightsTours(8);

	final int[] count8 = {0};
	tours8.foritm0((tour) -> {
	    if (count8[0] < 1) {
		System.out.println("Found tour of length: " + tour.length());
		count8[0]++;
	    }
	});

	if (count8[0] == 0) {
	    System.out.println("No complete tours found for 8x8");
	}
    }
}
