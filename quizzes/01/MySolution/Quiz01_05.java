//
// HX: 50 points
//
import Library.LnList.*;
import Library.FnA1sz.*;
// Please see Library/LnList for LnList.java
public class Quiz01_05 {
    public static<T extends Comparable<T>>LnList<T> LnListQuickSort(LnList<T> xs) {
	// HX-2025-10-12:
	// Please implement quicksort on a linked list (LnList).
	// Note that you are not allowed to modify the definition
	// of the LnList class. You can only use the public methods
	// provided by the LnList class
		if (xs.nilq1())
			return xs;

		LnList<T> pivotNode = xs;
		LnList<T> rest = xs.unlink();

		LnList<T> smallerHead = null;
		LnList<T> greaterHead = null;

		while (rest.consq1()) {
			LnList<T> current = rest;
			rest = rest.unlink();

			if (current.hd1().compareTo(pivotNode.hd1()) < 0) {
				if (smallerHead == null) {
					smallerHead = current;
				} else {
					current.link(smallerHead);
					smallerHead = current;
				}
			} else {
				if (greaterHead == null) {
					greaterHead = current;
				} else {
					current.link(greaterHead);
					greaterHead = current;
				}
			}
		}

		LnList<T> emptyList = rest;

		LnList<T> sortedSmaller = (smallerHead != null) ? LnListQuickSort(smallerHead) : emptyList;
		LnList<T> sortedGreater = (greaterHead != null) ? LnListQuickSort(greaterHead) : emptyList;

		pivotNode.link(sortedGreater);
		sortedSmaller.append1(pivotNode);

		return sortedSmaller;
    }
    public static void main (String[] args) {
	// HX-2025-10-12:
	// Please write minimal testing code for LnListQuickSort
		LnList<Integer> test = new LnList<Integer>(new FnA1sz<Integer>(new Integer[]{5, 2, 8, 1, 9}));
		LnListQuickSort(test).System$out$print1();
		System.out.println();
    }
}
