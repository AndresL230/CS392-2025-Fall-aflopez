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
    private static class Range {
		int left;
		int right;
		Range(int l, int r) {
			left = l;
			right = r;
		}
    }

    public static<T extends Comparable<T>>void sort1000WithNoRecursion(T[] A) {
	// HX-2025-11-20:
	// A is an array of size at most 1000.
	// Please implement a sorting algorithm
	// WITHOUT recursion that can effectively
	// sort A.

		if (A == null || A.length <= 1) return;

		Range[] stack = new Range[1000];
		int top = -1;

		stack[++top] = new Range(0, A.length - 1);

		while (top >= 0) {
			Range current = stack[top--];
			int left = current.left;
			int right = current.right;

			if (left >= right) continue;

			int pivotIndex = partition(A, left, right);

			if (pivotIndex + 1 < right) {
			stack[++top] = new Range(pivotIndex + 1, right);
			}

			if (left < pivotIndex - 1) {
			stack[++top] = new Range(left, pivotIndex - 1);
			}
		}
    }

    private static <T extends Comparable<T>>int partition(T[] A, int left, int right) {
		T pivot = A[right];
		int i = left - 1;

		for (int j = left; j < right; j++) {
			if (A[j].compareTo(pivot) <= 0) {
			i++;
			T temp = A[i];
			A[i] = A[j];
			A[j] = temp;
			}
		}

		T temp = A[i + 1];
		A[i + 1] = A[right];
		A[right] = temp;

		return i + 1;
    }
    public static void main (String[] args) {
	// HX-2025-11-19:
	// Please write minimal testing code for FnA1szLongestMonoSubsequence
		Integer[] arr = {5, 2, 8, 1, 9, 3, 7, 4, 6};
		sort1000WithNoRecursion(arr);
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
	return /*void*/;
    }
}
