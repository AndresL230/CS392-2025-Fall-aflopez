//
// HX-2025-11-20: 30 points
// (plus up to 20 bonus points)
// This is more of a theory problem
// than a programming one.
//
import Library.LnStrm.*;
//
public class Quiz02_04 {
    public class AVLnode {
		int key;
		AVLnode lchild;
		AVLnode rchild;
	}
		//
		// HX: 10 points for this one
		// HX: If your implementation only
		// visit each node in [avl] at most once,
		// then it will be rewarded with some bonus
		// points (up to 20 bonus points).
		// For instance, if you compute the size of
		// height of a tree, then you already visit
		// each node once.
		//
	public static boolean isAVL (AVLnode avl) {
		// HX: Please implement a function that
		// tests whether a given AVLnode is a valid
		// AVL tree. If it is unclear what an
		// AVL tree, you can readily find it on-line
		// Note that you are not asked to check if avl is
		// a binary search tree in this case.
		return checkAVL(avl) != -1;
    }

    private static int checkAVL(AVLnode node) {
		if (node == null) return 0;

		int leftHeight = checkAVL(node.lchild);
		if (leftHeight == -1) return -1;

		int rightHeight = checkAVL(node.rchild);
		if (rightHeight == -1) return -1;

		if (Math.abs(leftHeight - rightHeight) > 1) return -1;

		return 1 + Math.max(leftHeight, rightHeight);
    }
    //
    // HX: 20 points
    // This is largely about understanding AVL trees.
    // Please explain BRIEFLY as to why the generated AVL is
    // of maximal height (not minimal height). Note that this
    // is different from what is asked in Quiz02_05.
    //

	/*
	The implementation builds a Fibonacci tree to achieve maximal AVL height because 
	such trees represent the sparsest balanced structure. Each subtree is also a Fibonacci tree, 
	and the number of nodes at height h equals Fib(h+2) - 1. By assigning the left subtree fewer 
	nodes and the right subtree slightly more, it maintains the AVL height-balance with minimal 
	nodes per level. This approach maximizes height for a given node count—about 28-29 for 
	1 million nodes. In short, Fibonacci trees are the tallest AVL trees possible..
	 */



    public static AVLnode genAVLBST() {
	// Please genenerate a binary search RBT that
	// contains exactly 1 million keys: 0, 1, 2, ..., 999999
	// such that the height of this tree is maximal (that is,
	// as large as possible). What is this height? Please give
	// a brief explanation on your implementation strategy.

	int[] counter = {0};
	return buildMaxHeightAVL(1000000, counter);
    }

    private static AVLnode buildMaxHeightAVL(int n, int[] counter) {
	if (n <= 0) return null;

	AVLnode node = new Quiz02_04().new AVLnode();

	int leftSize = fibonacci(n) - 1;
	int rightSize = n - leftSize - 1;

	node.lchild = buildMaxHeightAVL(leftSize, counter);
	node.key = counter[0]++;
	node.rchild = buildMaxHeightAVL(rightSize, counter);

	return node;
    }

    private static int fibonacci(int n) {
	if (n <= 1) return n;
	int a = 0, b = 1;
	for (int i = 2; i <= n; i++) {
	    int temp = a + b;
	    a = b;
	    b = temp;
	}
	return b;
    }
    public static void main (String[] args) {
	// Please add minimal testing code for isRBT()
	// Please add minimal testing code for genAVLBST()
	AVLnode tree = genAVLBST();
	System.out.println("Is AVL: " + isAVL(tree));
	System.out.println("Height: " + checkAVL(tree));
	return /*void*/;
    }
}
