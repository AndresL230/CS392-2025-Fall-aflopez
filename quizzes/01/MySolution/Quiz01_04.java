//
// HX: 40 points
//
import Library.LnList.*;
import Library.FnA1sz.*;
// Please see Library/LnList for LnList.java
public class Quiz01_04 {
    public static<T extends Comparable<T>>LnList<T> LnListInsertSort(LnList<T> xs) {
	// HX-2025-10-12:
	// Please implement (stable) insert sort on a
	// linked list (LnList).
	// Note that you are not allowed to modify the definition
	// of the LnList class. You can only use the public methods
	// provided by the LnList class
	LnList<T> sorted = xs.tl1();
	sorted.free();

	while (xs.consq1()) {
	    LnList<T> current = xs;
	    xs = xs.unlink();

	    if (sorted.nilq1()) {
		sorted = current;
	    } else if (current.hd1().compareTo(sorted.hd1()) < 0) {
		current.link(sorted);
		sorted = current;
	    } else {
		LnList<T> prev = sorted;
		LnList<T> curr = prev.tl1();

		while (curr.consq1() && current.hd1().compareTo(curr.hd1()) >= 0) {
		    prev = curr;
		    curr = curr.tl1();
		}

		LnList<T> rest = prev.unlink();
		prev.link(current);
		current.link(rest);
	    }
	}

	return sorted;
    }
    public static void main (String[] args) {
	// HX-2025-10-12:
	// Please write minimal testing code for LnListInsertSort
		Integer[] testArr = {3, 1, 2};
		LnList<Integer> test = new LnList<Integer>(new FnA1sz<Integer>(testArr));
		LnList<Integer> sorted = LnListInsertSort(test);
		sorted.System$out$print1();
		System.out.println();
    }
}
