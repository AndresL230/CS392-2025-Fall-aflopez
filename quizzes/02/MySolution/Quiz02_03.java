//
// HX-2025-11-20: 50 points
// Some "hard" Sudoku puzzles can be
// found here: https://sudoku.com/hard/.
// This question is similar to Assign07_02.
// You are asked to use DFirstEnumerate and BFirstEnumerate
// in FnGtree to solve Sudoku puzzles. Your solution
// should be able to solve "hard" Sudoku puzzles effectively.
//
import Library.FnList.*;
import Library.LnStrm.*;
import Library.FnGtree.*;
//
class Sudoku {
    int[][] board;

    Sudoku() {
	    board = new int[9][9];
    }

    Sudoku(int[][] b) {
        board = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = b[i][j];
            }
        }
    }

    boolean isValid(int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num || board[i][col] == num)
                return false;
        }

        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[boxRow + i][boxCol + j] == num)
                    return false;
            }
        }
        return true;
    }

    boolean isSolved() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0)
                    return false;
            }
        }
        return true;
    }

    int[] findEmpty() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0)
                    return new int[]{i, j};
            }
        }
        return null;
    }

    void print() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}

public class Quiz02_03 {
    public LnStrm<Sudoku> Soduku_dfs_solve(Sudoku puzzle) {
        FnGtree<Sudoku> tree = buildTree(puzzle);
        return FnGtreeSUtil.DFirstEnumerate(tree).filter0(s -> s.isSolved());
    }

    public LnStrm<Sudoku> Soduku_bfs_solve(Sudoku puzzle) {
        FnGtree<Sudoku> tree = buildTree(puzzle);
        return FnGtreeSUtil.BFirstEnumerate(tree).filter0(s -> s.isSolved());
    }

    private FnGtree<Sudoku> buildTree(Sudoku current) {
        return new FnGtree<Sudoku>() {
            public Sudoku value() {
            return current;
            }
            public FnList<FnGtree<Sudoku>> children() {
            if (current.isSolved()) {
                return new FnList<FnGtree<Sudoku>>();
            }
            int[] empty = current.findEmpty();
            if (empty == null) {
                return new FnList<FnGtree<Sudoku>>();
            }
            int row = empty[0];
            int col = empty[1];
		FnList<FnGtree<Sudoku>> kids = new FnList<FnGtree<Sudoku>>();
		for (int num = 1; num <= 9; num++) {
		    if (current.isValid(row, col, num)) {
			Sudoku next = new Sudoku(current.board);
			next.board[row][col] = num;
			kids = new FnList<FnGtree<Sudoku>>(buildTree(next), kids);
		    }
		}
		return kids;
	    }
	    };
    }
//
    public static void main (String[] args) {
	// Please add minimal testing code for Sudoku_dfs_solve
	// Please add minimal testing code for Sudoku_bfs_solve
        int[][] puzzle = {
            {5,3,0,0,7,0,0,0,0},
            {6,0,0,1,9,5,0,0,0},
            {0,9,8,0,0,0,0,6,0},
            {8,0,0,0,6,0,0,0,3},
            {4,0,0,8,0,3,0,0,1},
            {7,0,0,0,2,0,0,0,6},
            {0,6,0,0,0,0,2,8,0},
            {0,0,0,4,1,9,0,0,5},
            {0,0,0,0,8,0,0,7,9}
        };
        Sudoku s = new Sudoku(puzzle);
        Quiz02_03 solver = new Quiz02_03();
        LnStrm<Sudoku> solutions = solver.Soduku_dfs_solve(s);
        solutions.foritm0(sol -> {
            sol.print();
            System.out.println();
        });
        return /*void*/;
    }
//
}
