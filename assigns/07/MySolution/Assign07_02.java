import Library.LnStrm.*;
import Library.FnList.*;
import static Library.FnList.FnListSUtil.*;
import Library.FnGtree.*;

class UnsupportedOpr
    extends RuntimeException {
    String opr;
    public UnsupportedOpr(String opr) {
	this.opr = opr;
    }
}

abstract class Term {
    public String tag = "Term";
    public abstract double eval();
    public abstract String toString();
    // eval() returns the value of the term
}

class TermInt extends Term {
    public int val;
    public TermInt(int val) {
	this.tag = "TermInt"; this.val = val;
    }
    public double eval() { return val; }
    public String toString() { return String.valueOf(val); }
}

class TermOpr extends Term {
    public String opr;
    public Term arg1, arg2;
    public TermOpr(String opr0, Term arg1, Term arg2) {
	this.tag = "TermOpr";
	this.opr = opr0; this.arg1 = arg1; this.arg2 = arg2;
    }
    public double eval() {
	switch (opr) {
	  case "+":
	      return arg1.eval() + arg2.eval();
	  case "-":
	      return arg1.eval() - arg2.eval();
	  case "*":
	      return arg1.eval() * arg2.eval();
	  case "/":
	      return arg1.eval() / arg2.eval();
	}
	throw new UnsupportedOpr(opr);
    }
    public String toString() {
	return "(" + arg1.toString() + " " + opr + " " + arg2.toString() + ")";
    }
}

// Helper class to represent a state in the game tree
class GameState implements FnGtree<FnList<Term>> {
    private FnList<Term> terms;

    public GameState(FnList<Term> terms) {
	this.terms = terms;
    }

    public FnList<Term> value() {
	return terms;
    }

    public FnList<FnGtree<FnList<Term>>> children() {
	// Generate all possible next states by combining two terms
	return generateChildren(terms);
    }

    private static FnList<FnGtree<FnList<Term>>> generateChildren(FnList<Term> terms) {
	if (terms.length() <= 1) {
	    return nil();
	}

	FnList<FnGtree<FnList<Term>>> result = nil();

	// For each pair of terms
	FnList<Term> terms_copy = terms;
	int i = 0;
	while (!nilq(terms_copy)) {
	    Term t1 = terms_copy.hd();
	    FnList<Term> rest1 = terms_copy.tl();

	    FnList<Term> rest1_copy = rest1;
	    int j = i + 1;
	    while (!nilq(rest1_copy)) {
		Term t2 = rest1_copy.hd();

		// Create list without t1 and t2
		FnList<Term> remaining = removeTwo(terms, i, j);

		// For each operator, create new state
		String[] operators = {"+", "-", "*", "/"};
		for (String op : operators) {
		    // Try t1 op t2
		    Term newTerm = new TermOpr(op, t1, t2);
		    FnList<Term> newTerms = cons(newTerm, remaining);
		    result = cons(new GameState(newTerms), result);

		    // For non-commutative operators, also try t2 op t1
		    if (op.equals("-") || op.equals("/")) {
			Term newTerm2 = new TermOpr(op, t2, t1);
			FnList<Term> newTerms2 = cons(newTerm2, remaining);
			result = cons(new GameState(newTerms2), result);
		    }
		}

		rest1_copy = rest1_copy.tl();
		j++;
	    }

	    terms_copy = terms_copy.tl();
	    i++;
	}

	return result;
    }

    private static FnList<Term> removeTwo(FnList<Term> list, int idx1, int idx2) {
	FnList<Term> result = nil();
	int i = 0;
	while (!nilq(list)) {
	    if (i != idx1 && i != idx2) {
		result = cons(list.hd(), result);
	    }
	    list = list.tl();
	    i++;
	}
	return reverse(result);
    }
}

public class Assign07_02 {
//
    public LnStrm<Term> GameOf24_bfs_solve
	(int n1, int n2, int n3, int n4) {
	// Create initial state with 4 numbers
	FnList<Term> initial = cons(new TermInt(n1),
				    cons(new TermInt(n2),
					 cons(new TermInt(n3),
					      cons(new TermInt(n4), nil()))));

	GameState root = new GameState(initial);

	// Use BFirstEnumerate to traverse the game tree
	LnStrm<FnList<Term>> states = Assign07_01.BFirstEnumerate(root);

	// Filter for states with single term that equals 24
	LnStrm<FnList<Term>> solutions = states.filter0(
	    termList -> {
		if (termList.length() == 1) {
		    double val = termList.hd().eval();
		    return Math.abs(val - 24.0) < 0.0001;
		}
		return false;
	    }
	);

	// Extract the single term from each solution
	return LnStrmSUtil.map0(solutions, termList -> termList.hd());
    }

    public LnStrm<Term> GameOf24_dfs_solve
	(int n1, int n2, int n3, int n4) {
	// Create initial state with 4 numbers
	FnList<Term> initial = cons(new TermInt(n1),
				    cons(new TermInt(n2),
					 cons(new TermInt(n3),
					      cons(new TermInt(n4), nil()))));

	GameState root = new GameState(initial);

	// Use DFirstEnumerate to traverse the game tree
	LnStrm<FnList<Term>> states = Assign07_01.DFirstEnumerate(root);

	// Filter for states with single term that equals 24
	LnStrm<FnList<Term>> solutions = states.filter0(
	    termList -> {
		if (termList.length() == 1) {
		    double val = termList.hd().eval();
		    return Math.abs(val - 24.0) < 0.0001;
		}
		return false;
	    }
	);

	// Extract the single term from each solution
	return LnStrmSUtil.map0(solutions, termList -> termList.hd());
    }
//
    // Testing code for GameOf24_bfs_solve
    public static void test_bfs() {
	System.out.println("=== Testing BFS Solution ===");
	Assign07_02 game = new Assign07_02();

	// Test case: 3, 3, 8, 8 -> (8 / (3 - 8/3)) = 24
	System.out.println("Testing with: 3, 3, 8, 8");
	LnStrm<Term> solutions_bfs = game.GameOf24_bfs_solve(3, 3, 8, 8);

	final int[] count = {0};
	solutions_bfs.foritm0(term -> {
	    count[0]++;
	    if (count[0] <= 5) {
		System.out.println("Solution " + count[0] + ": " + term.toString() + " = " + term.eval());
	    }
	});
	System.out.println("Total BFS solutions found: " + count[0]);
    }

    // Testing code for GameOf24_dfs_solve
    public static void test_dfs() {
	System.out.println("\n=== Testing DFS Solution ===");
	Assign07_02 game = new Assign07_02();

	// Test case: 3, 3, 8, 8
	System.out.println("Testing with: 3, 3, 8, 8");
	LnStrm<Term> solutions_dfs = game.GameOf24_dfs_solve(3, 3, 8, 8);

	final int[] count = {0};
	solutions_dfs.foritm0(term -> {
	    count[0]++;
	    if (count[0] <= 5) {
		System.out.println("Solution " + count[0] + ": " + term.toString() + " = " + term.eval());
	    }
	});
	System.out.println("Total DFS solutions found: " + count[0]);
    }

    public static void main(String[] args) {
	test_bfs();
	test_dfs();
    }
//
} // end of [public class Assign07_02{...}]
