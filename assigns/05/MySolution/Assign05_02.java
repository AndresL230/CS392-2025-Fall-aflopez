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

public class Assign05_02 {

    public static
	<T extends Comparable<T>>
	FnList<T> insertSort(FnList<T> xs) {
	return insertSort(xs, (x1, x2) -> x1.compareTo(x2));
    }
//
    public static<T>
	FnList<T>
	insertSort(FnList<T> xs, ToIntBiFunction<T,T> cmp) {
	// HX-2025-10-08: Please implement this method
	// Loop-based implementation to avoid stack overflow
	// Using an array-based approach for efficiency with large lists

	int n = xs.length();
	if (n <= 1) return xs;

	// Convert list to array for efficient random access
	@SuppressWarnings("unchecked")
	T[] arr = (T[]) new Object[n];

	int idx = 0;
	while (!nilq(xs)) {
	    arr[idx++] = xs.hd();
	    xs = xs.tl();
	}

	// Perform insertion sort on the array
	for (int i = 1; i < n; i++) {
	    T key = arr[i];
	    int j = i - 1;

	    // Move elements greater than key one position ahead
	    // Use <= 0 for stability (equal elements maintain order)
	    while (j >= 0 && cmp.applyAsInt(arr[j], key) > 0) {
		arr[j + 1] = arr[j];
		j--;
	    }
	    arr[j + 1] = key;
	}

	// Convert array back to list
	FnList<T> result = nil();
	for (int i = n - 1; i >= 0; i--) {
	    result = cons(arr[i], result);
	}

	return result;
    }

    public static void main(String[] args) {
	// Please write some testing code that applies
	// insertSort to the following list of 1M numbers:
	// 1, 0, 3, 2, 5, 4, 7, 6, 9, 8, 11, 10, ..., 999999, 999998.

		System.out.println("Pattern: 1, 0, 3, 2, 5, 4, 7, 6, 9, 8, ...");

		FnList<Integer> patternList = nil();

		// Build the pattern: 1, 0, 3, 2, 5, 4, 7, 6, 9, 8, ...
		// This means pairs are swapped: (0,1), (2,3), (4,5), etc.
		for (int i = 999999; i >= 0; i -= 2) {
			patternList = cons(i - 1, patternList); // even number first
			patternList = cons(i, patternList);     // odd number second
		}

		System.out.println("List length: " + patternList.length());

		System.out.println("Sorting the list using insertSort...");

		FnList<Integer> sortedList = insertSort(patternList);


		boolean isSorted = orderedq(sortedList);
		System.out.println("Is sorted: " + isSorted);
		System.out.println("List length: " + sortedList.length());

		// The insertion sort should run in linear time for this nearly-sorted input
    }

} // end of [public class Assign05_02{...}]
