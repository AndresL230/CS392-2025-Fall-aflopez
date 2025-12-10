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
	if (xs == null || xs.length() == 0) {
	    return FnListSUtil.nil();
	}
	final int n = xs.length();
	final int[] tailLens = new int[n];
	for (int i = n - 1; i >= 0; i -= 1) {
	    tailLens[i] = 1;
	    final T cur = xs.getAt(i);
	    for (int j = i + 1; j < n; j += 1) {
		final T nxt = xs.getAt(j);
		if (cur.compareTo(nxt) <= 0) {
		    tailLens[i] = Math.max(tailLens[i], 1 + tailLens[j]);
		}
	    }
	}
	// HX-2025-11-19: Each ordered pair (i, j) with i < j is inspected once,
	// so the nested loop performs O(n^2) comparisons; the remaining passes are linear.
	int targetLen = 0;
	for (int len : tailLens) {
	    if (len > targetLen) {
		targetLen = len;
	    }
	}

	final int[] picks = new int[targetLen];
	int remaining = targetLen;
	int taken = 0;
	T lastValue = null;
	boolean hasLast = false;

	for (int i = 0; i < n && remaining > 0; i += 1) {
	    final T cur = xs.getAt(i);
	    if ((!hasLast || lastValue.compareTo(cur) <= 0) && tailLens[i] == remaining) {
		picks[taken] = i;
		taken += 1;
		lastValue = cur;
		hasLast = true;
		remaining -= 1;
	    }
	}

	FnList<Integer> result = FnListSUtil.nil();
	for (int k = taken - 1; k >= 0; k -= 1) {
	    result = FnListSUtil.cons(picks[k], result);
	}
	return result;
    }
    public static void main (String[] args) {
	// HX-2025-11-19:
	// Please write minimal testing code for FnA1szLongestMonoSubsequence
	Integer[] sample = {1,2,1,2,3,1,2,3,4};
	FnList<Integer> result = FnA1szLongestMonoSubsequence(new FnA1sz<Integer>(sample));
	result.System$out$print();
	System.out.println();
	return /*void*/;
    }
}
