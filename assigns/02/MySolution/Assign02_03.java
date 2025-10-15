public class Assign02_03 {
    	// Please give a soft qudratic time implementation
	// that solves the 3-sum problem. The function call
	// solve_3sum(A) returns true if and only if there exist
	// indices i, j, and k satisfying A[i]+A[j] = A[k].
	// Why is your implementation soft O(n^2)?
	
	public static boolean solve_3sum(Integer[] A) {
		for (int k = 0; k < A.length; k++) {
            int target = A[k];
            
            int left = 0;
            int right = k - 1;
            
            while (left < right) {
                int sum = A[left] + A[right];
                
                if (sum == target)
                    return true;
                else if (sum < target)
                    left+=1;
                else
                	right-=1;
            }
        }
        return false;
    }	
    
	//My solution is O(n^2) as in the outer loop it goes through the full array in the worst case and then once again in the inner while loop

    public static void main(String[] argv) {
		Integer[] test1 = {1, 2, 3, 4, 5};
        Integer[] test2 = {1, 3, 5, 7, 9};
        Integer[] test3 = {2, 4, 6, 8, 10};
        
        System.out.println("Test 1: " + solve_3sum(test1));
        System.out.println("Test 2: " + solve_3sum(test2));
        System.out.println("Test 3: " + solve_3sum(test3));
    }
}
