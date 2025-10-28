//
// HX: 30 points
// This one may seem easy but can be time-consuming
// if you use a brute-force approach.
//
public class Quiz01_03 {

    private static <T extends Comparable<T>> void cmpAndSwp(T[] arr, int i, int j) {
        if (arr[i].compareTo(arr[j]) > 0) {
            T temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

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

	@SuppressWarnings("unchecked")

	T[] arr = (T[]) new Comparable[]{x0, x1, x2, x3, x4, x5, x6, x7, x8, x9};

        cmpAndSwp(arr, 0, 1);
        cmpAndSwp(arr, 2, 3);
        cmpAndSwp(arr, 4, 5);
        cmpAndSwp(arr, 6, 7);
        cmpAndSwp(arr, 8, 9);

        cmpAndSwp(arr, 0, 2);
        cmpAndSwp(arr, 1, 3);
        cmpAndSwp(arr, 4, 6);
        cmpAndSwp(arr, 5, 7);

        cmpAndSwp(arr, 1, 2);
        cmpAndSwp(arr, 5, 6);
        cmpAndSwp(arr, 0, 4);
        cmpAndSwp(arr, 3, 7);

        cmpAndSwp(arr, 1, 5);
        cmpAndSwp(arr, 2, 6);
        cmpAndSwp(arr, 1, 4);
        cmpAndSwp(arr, 3, 6);

        cmpAndSwp(arr, 2, 4);
        cmpAndSwp(arr, 3, 5);
        cmpAndSwp(arr, 8, 9);
        cmpAndSwp(arr, 0, 8);
        cmpAndSwp(arr, 4, 8);
        cmpAndSwp(arr, 1, 9);
        cmpAndSwp(arr, 5, 9);
        cmpAndSwp(arr, 2, 8);
        cmpAndSwp(arr, 3, 9);
        cmpAndSwp(arr, 6, 8);
        cmpAndSwp(arr, 7, 9);
        cmpAndSwp(arr, 3, 8);
        cmpAndSwp(arr, 5, 8);

	return arr;
    }

	public static void main(String[] args) {
        Integer[] result1 = Quiz01_03.sort10WithNoRecursion(5, 2, 9, 1, 7, 3, 8, 4, 6, 0);
        System.out.println("Test 1: " + java.util.Arrays.toString(result1));
        
        Integer[] result2 = Quiz01_03.sort10WithNoRecursion(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        System.out.println("Test 2: " + java.util.Arrays.toString(result2));
        
        Integer[] result3 = Quiz01_03.sort10WithNoRecursion(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        System.out.println("Test 3: " + java.util.Arrays.toString(result3));
        
        String[] result4 = Quiz01_03.sort10WithNoRecursion("j", "i", "h", "g", "f", "e", "d", "c", "b", "a");
        System.out.println("Test 4: " + java.util.Arrays.toString(result4));
    }


}