import java.util.Arrays;

public class Assign02_02 {
    /*
      HX-2025-02-13: 10 points
      Recursion is a fundamental concept in programming.
      However, the support for recursion in Java is very limited.
      Nontheless, we will be making extensive use of recursion in
      this class.
     */

    /*
    // This is a so-called iterative implementation:
    public static <T extends Comparable<T> > int indexOf(T[] a, T key) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            final int mid = lo + (hi - lo) / 2;
	    final int sign = key.compareTo(a[mid]);
            if (sign < 0)
                hi = mid - 1;
            else if (sign > 0)
                lo = mid + 1;
            else
                return mid;
        }
        return -1;
    }
    */
    public static <T extends Comparable<T> > int indexOf(T[] a, T key) {
        return indexOfHelper(a, key, 0, a.length - 1);
    }

    private static <T extends Comparable<T>> int indexOfHelper(T[] a, T key, int lo, int hi){
        if (lo > hi)
            return -1;

        int mid = lo + (hi - lo) / 2;
        int sign = key.compareTo(a[mid]);

        if (sign < 0)
            return indexOfHelper(a, key, lo, mid-1);
        else if (sign > 0)
            return indexOfHelper(a, key, mid+1, hi);
        else
            return mid;
    }


    public static void main(String[] argv) {
        Integer[] noList = {1,2,4,7,9,19,21};

        System.out.println(indexOf(noList, 19));
        System.out.println(indexOf(noList, 4));
        System.out.println(indexOf(noList, 1));
        System.out.println(indexOf(noList, 6));
    }
}
