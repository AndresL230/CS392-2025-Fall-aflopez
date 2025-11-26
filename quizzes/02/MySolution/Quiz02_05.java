//
// HX-2025-11-20: 30 points
// (plus up to 20 bonus points)
// This is more of a theory problem
// than a programming one.
//
public class Quiz02_05 {
    public class RBTnode {
	int key;
	int color; // Red = 0; Black = 1
	RBTnode lchild;
	RBTnode rchild;
    }
    //
    // HX: 10 points for this one
    // HX: If your implementation only
    // visit each node in [rbt] at most once,
    // then it will be rewarded with some bonus
    // points (up to 20 bonus points).
    // For instance, if you compute the size of
    // height of a tree, then you already visit
    // each node once.
    //
    public static boolean isRBT (RBTnode rbt) {
	// HX: Please implement a function that
	// tests whether a given RBTnode is a valid
	// red-black tree. If it is unclear what a
	// red-black tree, you can readily find it on-line
	// Note that you are not asked to check if rbt is
	// a binary search tree in this case.
	if (rbt == null) return true;
	if (rbt.color != 1) return false;
	return checkRBT(rbt) != -1;
    }

    private static int checkRBT(RBTnode node) {
	if (node == null) return 1;

	if (node.color == 0) {
	    if ((node.lchild != null && node.lchild.color == 0) ||
		(node.rchild != null && node.rchild.color == 0)) {
		return -1;
	    }
	}

	int leftBlackHeight = checkRBT(node.lchild);
	if (leftBlackHeight == -1) return -1;

	int rightBlackHeight = checkRBT(node.rchild);
	if (rightBlackHeight == -1) return -1;

	if (leftBlackHeight != rightBlackHeight) return -1;

	return leftBlackHeight + (node.color == 1 ? 1 : 0);
    }
    //
    // HX: 20 points
    // This is largely about understanding red-black trees.
    // Please explain BRIEFLY as to why the generated RBT is
    // of minimal black height (not height).
    //
    public static RBTnode genRedBLackBST() {
	// Please genenerate a binary search RBT that
	// contains exactly 1 million keys: 0, 1, 2, ..., 999999
	// such that the black height (not height) of this tree is
	// minimal (that is, as small as possible). What is this black
	// height? Please give a brief explanation on your implementation
	// strategy.

	int[] counter = {0};
	int blackHeight = (int) Math.ceil(Math.log(1000001) / Math.log(2));
	return buildMinBlackHeightRBT(blackHeight, counter, 1000000);
    }

    private static RBTnode buildMinBlackHeightRBT(int bh, int[] counter, int maxNodes) {
	if (bh == 0 || counter[0] >= maxNodes) return null;

	RBTnode node = new Quiz02_05().new RBTnode();
	node.color = 1;

	node.lchild = buildMinBlackHeightRBT(bh - 1, counter, maxNodes);
	if (counter[0] >= maxNodes) return node;
	node.key = counter[0]++;
	node.rchild = buildMinBlackHeightRBT(bh - 1, counter, maxNodes);

	return node;
    }
    public static void main (String[] args) {
	// Please add minimal testing code for isRBT()
	// Please add minimal testing code for genRedBlackBST()
	RBTnode tree = genRedBLackBST();
	System.out.println("Is RBT: " + isRBT(tree));
	System.out.println("Black Height: " + checkRBT(tree));
	return /*void*/;
    }
}
