//
// HX: 40 points
//
import Library.LnList.*;
// Please see Library/LnList for LnList.java
public class Quiz01_04 {
    public static<T extends Comparable<T>>LnList<T> LnListInsertSort(LnList<T> xs) {
	// HX-2025-10-12:
	// Please implement (stable) insert sort on a
	// linked list (LnList).
	// Note that you are not allowed to modify the definition
	// of the LnList class. You can only use the public methods
	// provided by the LnList class
	LnList<T> sorted = new LnList<T>();

	while (xs.consq1()) {
	    T current = xs.hd1();
	    xs = xs.tl1();

	    if (sorted.nilq1())
			sorted = new LnList<T>(current, new LnList<T>());
	    else if (current.compareTo(sorted.hd1()) < 0) 
			sorted = new LnList<T>(current, sorted);
	    else {
			LnList<T> temp = new LnList<T>();
			boolean inserted = false;

			while (sorted.consq1()) {
				T sortedItem = sorted.hd1();
				sorted = sorted.tl1();

				if (!inserted && (sorted.nilq1() || current.compareTo(sorted.hd1()) < 0)) {
					temp.append1(new LnList<T>(sortedItem, new LnList<T>()));
					temp.append1(new LnList<T>(current, new LnList<T>()));
					inserted = true;
				} else
					temp.append1(new LnList<T>(sortedItem, new LnList<T>()));
			}

			sorted = temp;
	    }
	}

	return sorted;
    }
    public static int main (String[] args) {
	// HX-2025-10-12:
	// Please write minimal testing code for LnListInsertSort
		LnList<Integer> test = new LnList<Integer>(3, new LnList<Integer>(1, new LnList<Integer>(2, new LnList<Integer>())));
		LnList<Integer> sorted = LnListInsertSort(test);
		sorted.System$out$print1();
		System.out.println();

		return 0;
    }
}
