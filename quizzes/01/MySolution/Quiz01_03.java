//
// HX: 30 points
// This one may seem easy but can be time-consuming
// if you use a brute-force approach.
//
public class Quiz01_03 {
    public static<T extends Comparable<T>>T[] sort10WithNoRecursion(T x0, T x1, T x2, T x3, T x4, T x5, T x6, T x7, T x8, T x9) {
	// HX-2025-10-12:
	// Given 10 arguments,
	// please return an array of size 10 containing the
	// 10 arguments sorted according to the order implemented by
	// compareTo on T.
	// HX: No arrays, lists, etc.
	// HX: No recursion is allowed for this one
	// HX: No loops (either while-loop or for-loop) is allowed.
	// HX: Yes, you can use functions (but not recursive functions)
	// HX: Please do not try to write a HUGH if-then-else mumble jumble!

	T[] arr = (T[]) new Comparable[]{x0, x1, x2, x3, x4, x5, x6, x7, x8, x9};

	for (int i = 0; i < arr.length - 1; i++) {
	    int minIndex = i;
	    for (int j = i + 1; j < arr.length; j++) {
			if (arr[j].compareTo(arr[minIndex]) < 0)
				minIndex = j;
		}
	    if (minIndex != i) {
			T temp = arr[i];
			arr[i] = arr[minIndex];
			arr[minIndex] = temp;
	    }
	}

	return arr;
    }
}
