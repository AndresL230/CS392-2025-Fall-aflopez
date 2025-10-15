/*
  HX-2025-09-15: 10 points
*/
public class Assign03_01 {
    //
    // HX-2025-09-15:
    // This implementation of f91
    // is not tail-recursive. Please
    // translate it into a version that
    // is tail-recursive
    //
    /*
    static int f91(int n) {
	if (n > 100)
	    return n-10;
	else
	    return f91(f91(n+11));
    }
    */
    static int f91TailRecursive(int n, int count) {
        if (count == 0)
            return n;
        
        if (n > 100)
            return f91TailRecursive(n - 10, count - 1);
        else
            return f91TailRecursive(n + 11, count + 1);
    }

    public static void main(String[] args) {
        System.out.println(f91(1100));
        
        System.out.println("f91(95) = " + f91(95));
        System.out.println("f91(101) = " + f91(101));
        System.out.println("f91(102) = " + f91(102));
    }
}