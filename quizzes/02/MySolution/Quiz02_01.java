//
// HX-2025-11-19: 50 points
//
// This question tests your understanding
// of recursion and time analysis involving
// recursion.
// Given a sequence xs, a subsequence of xs
// can be represented as a list of integers
// (representing indices). For instance, given
// xs = "Hello", (0, 2, 4) refers to the subeqence
// "Hlo" (since xs[0] = 'H', xs[2] = 'l', and
// xs[4] = 'o'); (0, 3, 4) also refers to "Hlo".
// The subsequece (0, 2, 4) is to the left of
// the subsequece (0, 3, 4) as (0, 2, 4) is less
// than (0, 3, 4) according to the lexicographic
// ordering.
//
// Here you are asked to implement a function that
// finds the longest leftmost ascending subsequence
// of a given sequence.
// For instance, suppose xs = [1,2,1,2,3,1,2,3,4],
// the longest leftmost ascending subsequence of xs
// is represented by (0, 1, 3, 4, 7, 8) (which refers
// to [1,2,2,3,3,4] in xs).
//
// In order to receive 50 points, your implementation
// should be quadratic time, that is, O(n^2) time and
// you MUST give a brief explanation as to why it is so.
// Otherwise, a working solution receives at most 60%, that
// is, 30 points out of 50 points.
//
import Library.FnList.*;
// Please see Library/FnList for FnList.java
import Library.FnA1sz.*;
// Please see Library/FnA1sz for FnA1sz.java
public class Quiz02_01 {
    public static
	<T extends Comparable<T>>
	FnList<Integer> FnA1szLongestMonoSubsequence(FnA1sz<T> xs) {
	// HX-2025-11-19:
	// This method finds the leftmost longest ascending subsequence
	// of xs. Note that the returned list consists of the indices of
	// the elements of the subsequence.

	/*This impplementation runs in O(n^2) time as n = xs.length() as we 
	have loops iterating through the full length and a loop within that
	looping through the length for each iteration of the previous loop. */

	int n = xs.length();
	if (n == 0) {
	    return new FnList<Integer>();
	}

	int[] dp = new int[n];
	int[] prev = new int[n];

	for (int i = 0; i < n; i++) {
	    dp[i] = 1;
	    prev[i] = -1;
	}

	for (int i = 1; i < n; i++) {
	    for (int j = 0; j < i; j++) {
		if (xs.getAt(j).compareTo(xs.getAt(i)) < 0) {
		    if (dp[j] + 1 > dp[i]) {
			dp[i] = dp[j] + 1;
			prev[i] = j;
		    }
		}
	    }
	}

	int maxLen = 0;
	int maxIdx = 0;
	for (int i = 0; i < n; i++) {
	    if (dp[i] > maxLen) {
		maxLen = dp[i];
		maxIdx = i;
	    }
	}

	FnList<Integer> result = new FnList<Integer>();
	int idx = maxIdx;
	while (idx != -1) {
	    result = new FnList<Integer>(idx, result);
	    idx = prev[idx];
	}

	return result;
    }
    public static void main (String[] args) {
	// HX-2025-11-19:
	// Please write minimal testing code for FnA1szLongestMonoSubsequence
		Integer[] arr = {1, 2, 1, 2, 3, 1, 2, 3, 4};
		FnA1sz<Integer> xs = new FnA1sz<Integer>(arr);
		FnList<Integer> result = FnA1szLongestMonoSubsequence(xs);
		result.System$out$print();
		
		return /*void*/;
    }
}
