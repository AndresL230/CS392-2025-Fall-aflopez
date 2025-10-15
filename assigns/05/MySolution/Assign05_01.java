import Library.FnList.*;
import static Library.FnList.FnListSUtil.*;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.BiFunction;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.ToIntBiFunction;

public class Assign05_01 {

    public static<T extends Comparable<T>> FnList<T> mergeSort(FnList<T> xs) {
		return mergeSort(xs, (x1, x2) -> x1.compareTo(x2));
    }
//
    public static<T>FnList<T>mergeSort(FnList<T> xs, ToIntBiFunction<T,T> cmp) {
	// HX-2025-10-08: Please implement this method
	// Rewritten to be efficient for long lists (1M+ elements)
	// Using loop-based approach similar to quickSort
		int n0 = xs.length();
		if (n0 <= 1)
			return xs;
		else
			return mergeSort_split(xs, nil(), n0, 0, cmp);
    }

    private static<T>FnList<T> mergeSort_split(FnList<T> xs, FnList<T> ys, int n0, int n1, ToIntBiFunction<T,T> cmp) {
	// Loop-based split to avoid deep recursion during splitting
		while (2*n1 < n0) {
			ys = cons(xs.hd(), ys);
			xs = xs.tl();
			n1 += 1;
		}
		// Now recursively sort both halves
		return mergeSort_merge
			(mergeSort(reverse(ys), cmp), mergeSort(xs, cmp), cmp);
    }

    private static<T>FnList<T> mergeSort_merge(FnList<T> xs, FnList<T> ys, ToIntBiFunction<T,T> cmp) {
	// Loop-based merge to avoid stack overflow
		FnList<T> zs = nil();

		while (!nilq(xs) && !nilq(ys)) {
			if (cmp.applyAsInt(xs.hd(), ys.hd()) <= 0) {
			zs = cons(xs.hd(), zs);
			xs = xs.tl();
			}
			else {
			zs = cons(ys.hd(), zs);
			ys = ys.tl();
			}
		}

		// Append remaining elements
		while (!nilq(xs)) {
			zs = cons(xs.hd(), zs);
			xs = xs.tl();
		}

		while (!nilq(ys)) {
			zs = cons(ys.hd(), zs);
			ys = ys.tl();
		}

		return reverse(zs);
	}

    public static void main(String[] args) {
	// Please write some testing code that applies
	// mergeSort to a randomly generated list of 1000,000 integers.

		FnList<Integer> randomList = rand$int$make(1000000);

		FnList<Integer> sortedList = mergeSort(randomList);
		System.out.println("Sort time: " + (endSort - startSort) + " ms");

		System.out.println("Verifying the list is sorted...");
		boolean isSorted = orderedq(sortedList);
		System.out.println("Is sorted: " + isSorted);
		System.out.println("List length: " + sortedList.length());
    }

} // end of [public class Assign05_01{...}]

