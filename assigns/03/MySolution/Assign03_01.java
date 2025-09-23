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
    
    static int f91(int n) {
	if (n > 100)
	    return n-10;
	else
	    return f91(f91(n+11));
    }

    public static void main(String[] args) {
	// Please write some testing code here
        System.out.println(f91(1100));
    }
}
