//
// HX: 50 points
// Here we revisit a question on quiz01 (Quiz01_03).
// Instead of sorting 10 elements without recursion,
// you are asked to sort up to 1000 elements without
// recursion.
// Hint: Think about building a tree of commands for
// swapping array elements.
//
public class Quiz02_02 {
    private static <T extends Comparable<T>> void cmpAndSwp(T[] arr, int i, int j) {
        if (i < arr.length && j < arr.length && arr[i].compareTo(arr[j]) > 0) {
            T temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    public static<T extends Comparable<T>>void sort1000WithNoRecursion(T[] A) {
	// HX-2025-11-20:
	// A is an array of size at most 1000.
	// Please implement a sorting algorithm
	// WITHOUT recursion that can effectively
	// sort A.
	// Using odd-even sort network approach - tree of compare-and-swap commands

	if (A == null || A.length <= 1) return;

	int n = A.length;

	// Odd-even sort network - builds a tree of swap commands without recursion
	for (int gap = n / 2; gap > 0; gap /= 2) {
	    for (int i = 0; i < n - gap; i++) {
		cmpAndSwp(A, i, i + gap);
	    }

	    for (int i = gap; i < n - gap; i++) {
		cmpAndSwp(A, i, i + gap);
	    }
	}

	// Final passes to ensure complete sorting
	for (int pass = 0; pass < n; pass++) {
	    for (int i = pass % 2; i < n - 1; i += 2) {
		cmpAndSwp(A, i, i + 1);
	    }
	}
    }

    public static void main (String[] args) {
	// HX-2025-11-19:
	// Please write minimal testing code for sort1000WithNoRecursion
	Integer[] arr = {5, 2, 8, 1, 9, 3, 7, 4, 6};
	sort1000WithNoRecursion(arr);
	for (int i = 0; i < arr.length; i++) {
		System.out.print(arr[i] + " ");
	}
	System.out.println();
	return /*void*/;
    }
}
