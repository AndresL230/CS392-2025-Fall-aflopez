//
// HX: 50 points
//
import Library.FnList.*;
import java.util.function.ToIntBiFunction;

abstract public class Quiz01_06 {
    static class Indexed<T> {
		T value;
		int index;
		Indexed(T value, int index) {
			this.value = value;
			this.index = index;
		}
    }

    public static<T>FnList<T> someSort(FnList<T> xs, ToIntBiFunction<T,T> cmp) {
	// HX-2025-10-15:
	// This one is abstract, that is, not implemented
		return FnListSUtil.quickSort(xs, cmp);
    }
    
	public static<T extends Comparable<T>>FnList<T> someStableSort(FnList<T> xs, ToIntBiFunction<T,T> cmp) {
	// HX-2025-10-15:
	// Please implement a stable sorting method based on
	// someSort, which may not be stable
		FnList<Indexed<T>> indexed = FnListSUtil.imap_list(xs, (i, x) -> new Indexed<T>(x, i));
		
		ToIntBiFunction<Indexed<T>, Indexed<T>> stableCmp = (a, b) -> {
			int result = cmp.applyAsInt(a.value, b.value);
			if (result == 0) {
			return Integer.compare(a.index, b.index);
			}
			return result;
		};
		
		FnList<Indexed<T>> sorted = someSort(indexed, stableCmp);
		return FnListSUtil.map_list(sorted, item -> item.value);
		}
}

////////////////////////////////////////////////////////////////////////.
//
// HX-2025-10-15:
// Please find a way to test someStableSort by
// implementing someSort as quickSort on FnList
// and then use someStableSort to parity-sort the following
// list of 1M integers:
// 0, 1, 2, 3, 4, ..., 999999
// That is, the result of parity-sorting should be:
// 0, 2, ..., 999998, 1, 3, ..., 999999
//
// Note that you should be able to call the quickSort method
// in Library/FnList/FnListSUtil.java; should not do another
// implementation of quickSort in your testing code.
////////////////////////////////////////////////////////////////////////.

class Quiz01_06Test {
    public static void main(String[] args) {
		FnList<Integer> xs = FnListSUtil.int1$make(1000);
		FnList<Integer> sorted = Quiz01_06.someStableSort(xs, (a, b) -> Integer.compare(a % 2, b % 2));
		System.out.print("First 10: ");
		FnList<Integer> first10 = FnListSUtil.nil();
		
		for (int i = 0; i < 10; i++) {
			first10 = FnListSUtil.cons(sorted.hd(), first10);
			sorted = sorted.tl();
		}
		FnListSUtil.reverse(first10).System$out$print();
    }
}