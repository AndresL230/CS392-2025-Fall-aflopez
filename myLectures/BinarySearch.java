import java.util.Arrays;
import edu.princeton.cs.algs4.*;

public class BinarySearch {
    public BinarySearch(){
    }

    public int indexOf(int[] a, int key){
        int lo = 0;
        int hi = a.length - 1;
        int mid;

        while(lo <= hi){
            mid = lo + (hi - lo)/2;

            if (key < a[mid])
                hi = mid-1;
            else if (key > a[mid])
                lo = mid + 1;
            else 
                return mid;
        }
    }
}