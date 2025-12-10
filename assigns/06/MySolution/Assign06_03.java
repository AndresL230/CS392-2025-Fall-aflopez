import Library.LnStrm.*;
import Library.FnTuple.*;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.BiFunction;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.ToIntBiFunction;

public class Assign06_03 {
    public static<T> void arrayQuickSort(T[] A, ToIntBiFunction<T,T> cmp) {
	// Please implement standard array-based quickSort and make sure
	// that equal elements are properly handled. In particular, your
	// testing code should test your implementation on an array of 1M zeros!
	if (A == null || A.length <= 1) {
	    return;
	}
	quickSortHelper(A, 0, A.length - 1, cmp);
    }

    private static<T> void quickSortHelper(T[] A, int low, int high, ToIntBiFunction<T,T> cmp) {
	if (low >= high) {
	    return;
	}

	int[] pivots = threeWayPartition(A, low, high, cmp);

	quickSortHelper(A, low, pivots[0] - 1, cmp);
	quickSortHelper(A, pivots[1] + 1, high, cmp);
    }

    private static<T> int[] threeWayPartition(T[] A, int low, int high, ToIntBiFunction<T,T> cmp) {
	int mid = low + (high - low) / 2;
	T pivot = medianOfThree(A, low, mid, high, cmp);

	int lt = low;
	int gt = high;
	int i = low;

	while (i <= gt) {
	    int cmpResult = cmp.applyAsInt(A[i], pivot);

	    if (cmpResult < 0) {
		swap(A, lt, i);
		lt++;
		i++;
	    } else if (cmpResult > 0) {
		swap(A, i, gt);
		gt--;
	    } else {
		i++;
	    }
	}

	return new int[]{lt, gt};
    }

    private static<T> T medianOfThree(T[] A, int low, int mid, int high, ToIntBiFunction<T,T> cmp) {
	T a = A[low];
	T b = A[mid];
	T c = A[high];

	if (cmp.applyAsInt(a, b) < 0) {
	    if (cmp.applyAsInt(b, c) < 0) {
		return b;
	    } else if (cmp.applyAsInt(a, c) < 0) {
		return c;
	    } else {
		return a;
	    }
	} else {
	    if (cmp.applyAsInt(a, c) < 0) {
		return a;
	    } else if (cmp.applyAsInt(b, c) < 0) {
		return c;
	    } else {
		return b;
	    }
	}
    }

    private static<T> void swap(T[] A, int i, int j) {
	T temp = A[i];
	A[i] = A[j];
	A[j] = temp;
    }

    public static void main(String[] args) {
	System.out.println("Testing arrayQuickSort:");

	Integer[] test1 = {5, 2, 8, 1, 9, 3, 7, 4, 6};
	arrayQuickSort(test1, (a, b) -> a.compareTo(b));
	System.out.print("Sorted array: ");
	for (int i : test1) System.out.print(i + " ");
	System.out.println();

	Integer[] zeros = new Integer[1000000];
	for (int i = 0; i < zeros.length; i++) {
	    zeros[i] = 0;
	}
	System.out.println("Sorting 1M zeros...");
	arrayQuickSort(zeros, (a, b) -> a.compareTo(b));
	System.out.println("Successfully sorted 1M zeros");

	Random rand = new Random();
	Integer[] randomArray = new Integer[10000];
	for (int i = 0; i < randomArray.length; i++) {
	    randomArray[i] = rand.nextInt(1000);
	}
	arrayQuickSort(randomArray, (a, b) -> a.compareTo(b));
	boolean sorted = true;
	for (int i = 1; i < randomArray.length; i++) {
	    if (randomArray[i] < randomArray[i-1]) {
		sorted = false;
		break;
	    }
	}
	System.out.println("10K random integers sorted correctly: " + sorted);
    }

} // end of [public class Assign06_03{...}]
