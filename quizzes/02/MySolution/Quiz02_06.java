//
// HX-2025-11-20: 50 points
// A partial implementation of
// randomized doubly linked binary search tree
// 30 points for reroot and 20 points for insert
//
public class Quiz02_06 {
    Node root = null;
    public class Node {
	int key; // key stored in the node
	int size; // size of the tree rooted as the node
	Node parent; // parent of the node
	Node lchild; // left-child of the node
	Node rchild; // right-child of the node
    }
    public void reroot() {
	// HX-2025-11-20: 30 points
	// [reroot] picks a node RANDOMLY and
	// uses rotations to turn this picked node
	// into the root of a new binary search tree
	// (containing the same set of keys)
	if (root == null) return;

	int randomIndex = (int) (Math.random() * root.size);
	Node target = findNodeByIndex(root, randomIndex);

	while (target != root) {
	    if (target.parent == null) break;

	    if (target.parent.lchild == target) {
		rotateRight(target.parent);
	    } else {
		rotateLeft(target.parent);
	    }
	}
	root = target;
    }

    private Node findNodeByIndex(Node node, int index) {
	if (node == null) return null;

	int leftSize = (node.lchild == null) ? 0 : node.lchild.size;

	if (index < leftSize) {
	    return findNodeByIndex(node.lchild, index);
	} else if (index == leftSize) {
	    return node;
	} else {
	    return findNodeByIndex(node.rchild, index - leftSize - 1);
	}
    }

    private void rotateLeft(Node x) {
	Node y = x.rchild;
	if (y == null) return;

	x.rchild = y.lchild;
	if (y.lchild != null) {
	    y.lchild.parent = x;
	}

	y.parent = x.parent;
	if (x.parent == null) {
	    root = y;
	} else if (x.parent.lchild == x) {
	    x.parent.lchild = y;
	} else {
	    x.parent.rchild = y;
	}

	y.lchild = x;
	x.parent = y;

	updateSize(x);
	updateSize(y);
    }

    private void rotateRight(Node y) {
	Node x = y.lchild;
	if (x == null) return;

	y.lchild = x.rchild;
	if (x.rchild != null) {
	    x.rchild.parent = y;
	}

	x.parent = y.parent;
	if (y.parent == null) {
	    root = x;
	} else if (y.parent.lchild == y) {
	    y.parent.lchild = x;
	} else {
	    y.parent.rchild = x;
	}

	x.rchild = y;
	y.parent = x;

	updateSize(y);
	updateSize(x);
    }

    private void updateSize(Node node) {
	if (node == null) return;
	int leftSize = (node.lchild == null) ? 0 : node.lchild.size;
	int rightSize = (node.rchild == null) ? 0 : node.rchild.size;
	node.size = 1 + leftSize + rightSize;
    }
    public boolean insert(int key) {
	// HX-2025-11-20: 20 points
	// If key is in the tree stored at [root],
	// [insert] does no nothing and just returns false
	// If key is not in the tree stored at [root],
	// the key is inserted as a leaf node and the new
	// tree is still a binary search tree and [insert]
	// returns true (to indicate insertion is done).
	if (root == null) {
	    root = new Node();
	    root.key = key;
	    root.size = 1;
	    root.parent = null;
	    root.lchild = null;
	    root.rchild = null;
	    return true;
	}

	Node current = root;
	Node parent = null;

	while (current != null) {
	    parent = current;
	    if (key < current.key) {
		current = current.lchild;
	    } else if (key > current.key) {
		current = current.rchild;
	    } else {
		return false;
	    }
	}

	Node newNode = new Node();
	newNode.key = key;
	newNode.size = 1;
	newNode.parent = parent;
	newNode.lchild = null;
	newNode.rchild = null;

	if (key < parent.key) {
	    parent.lchild = newNode;
	} else {
	    parent.rchild = newNode;
	}

	Node ancestor = parent;
	while (ancestor != null) {
	    ancestor.size++;
	    ancestor = ancestor.parent;
	}

	return true;
    }
    public static void main (String[] args) {
	// Please add minimal testing code for reroot()
	// Please add minimal testing code for insert()
		Quiz02_06 tree = new Quiz02_06();
		tree.insert(5);
		tree.insert(3);
		tree.insert(7);
		tree.insert(1);
		tree.insert(9);
		System.out.println("Root key: " + tree.root.key);
		System.out.println("Tree size: " + tree.root.size);
		tree.reroot();
		System.out.println("New root key after reroot: " + tree.root.key);
		return /*void*/;
    }
}
