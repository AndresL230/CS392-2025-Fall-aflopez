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
		
		T pivot = xs.hd1();
		LnList<T> rest = xs.tl1();
		LnList<T> smaller = new LnList<T>();
		LnList<T> greaterOrEqual = new LnList<T>();
		
		rest.foritm1((item) -> {
			if (item.compareTo(pivot) < 0)
				smaller.append1(new LnList<T>(item, new LnList<T>()));
			else
				greaterOrEqual.append1(new LnList<T>(item, new LnList<T>()));	
		});

		LnList<T> sortedSmaller = LnListQuickSort(smaller);
		LnList<T> sortedGreater = LnListQuickSort(greaterOrEqual);
		LnList<T> result = sortedSmaller;
		result.append1(new LnList<T>(pivot, new LnList<T>()));
		result.append1(sortedGreater);
		
		return result;
    }
    public static int main (String[] args) {
	// HX-2025-10-12:
	// Please write minimal testing code for LnListQuickSort
		LnList<Integer> test = new LnList<Integer>(new FnA1sz<Integer>(new Integer[]{5, 2, 8, 1, 9}));
		LnListQuickSort(test).System$out$print1();
		return 0;
    }
}
