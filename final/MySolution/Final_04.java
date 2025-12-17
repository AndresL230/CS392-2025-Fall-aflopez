/*
// HX: 50 points for Final_04
// HX: This one tests your RBST implementation done in Quiz02_06.
// In Final_02, pg2701_word$count$listize1() is implemented
// to list words in pg2701.txt according their frequencies.
// In Final_04, you are asked to implement the same functionality
// with a different approach.
*/

import MyFinalLib.FnList.*;
import MyFinalLib.LnStrm.*;
import MyFinalLib.FnTuple.*;

import static MyFinalLib.FnList.FnListSUtil.*;

import java.util.function.ToIntBiFunction;

public class Final_04 {
    static FnList<FnTupl2<FnList<Character>, Integer>> pg2701_word$count$listize4() {
	// HX-2025-12-15:
	// Your implementation must contain the following steps:
	// 1. Call pg2701_word$strmize() to get a stream of words
	// 2. Then use the RBST implemented in Quiz02_06 to count the number of
	//    occurrences of each word in the stream of words.
	//    Note that you need to modify your Quiz02_06 implementation to turn
	//    it into an generic associative map for this part.
	// 3. Then figure out a way to turn the RBST-based map into a list WNS
	//    (FnList) of word-count pairs
	// 4. Use the mergesort (mergeSort) in Assign05_01 to sort WNS using
	//    the order (w1, n1) <= (w2, n2) if n1 > n2 or n1 = n2 and w1 <= w2
	// 5. The sorted WNS is the return value of pg2701_word$count$listize4()

	LnStrm<FnList<Character>> wordStream = Final_01.pg2701_word$strmize();

	WordRBST rbst = new WordRBST();

	LnStcn<FnList<Character>> stcn = wordStream.eval0();
	while (stcn.consq()) {
	    FnList<Character> word = stcn.head;
	    rbst.increment(word);
	    stcn = stcn.tail.eval0();
	}

	FnList<FnTupl2<FnList<Character>, Integer>> WNS = rbst.toList();

	FnList<FnTupl2<FnList<Character>, Integer>> sortedWNS = mergeSort(WNS, (p1, p2) -> {
	    if (p1.get1() > p2.get1()) return -1;
	    if (p1.get1() < p2.get1()) return 1;
	    return compareWords(p1.get0(), p2.get0());
	});

	return sortedWNS;
    }

    private static int compareWords(FnList<Character> w1, FnList<Character> w2) {
	if (w1.nilq() && w2.nilq()) return 0;
	if (w1.nilq()) return -1;
	if (w2.nilq()) return 1;

	char c1 = w1.hd();
	char c2 = w2.hd();

	if (c1 < c2) return -1;
	if (c1 > c2) return 1;

	return compareWords(w1.tl(), w2.tl());
    }

    private static class WordRBST {
	Node root = null;

	private class Node {
	    FnList<Character> key;
	    int value;
	    int size;
	    Node parent;
	    Node lchild;
	    Node rchild;
	}

	public void increment(FnList<Character> word) {
	    Node existing = search(word);
	    if (existing != null) {
		existing.value++;
	    } else {
		insert(word, 1);
	    }
	}

	private Node search(FnList<Character> word) {
	    Node current = root;
	    while (current != null) {
		int cmp = compareWords(word, current.key);
		if (cmp < 0) {
		    current = current.lchild;
		} else if (cmp > 0) {
		    current = current.rchild;
		} else {
		    return current;
		}
	    }
	    return null;
	}

	private void insert(FnList<Character> word, int count) {
	    if (root == null) {
		root = new Node();
		root.key = word;
		root.value = count;
		root.size = 1;
		root.parent = null;
		root.lchild = null;
		root.rchild = null;
		return;
	    }

	    Node current = root;
	    Node parent = null;

	    while (current != null) {
		parent = current;
		int cmp = compareWords(word, current.key);
		if (cmp < 0) {
		    current = current.lchild;
		} else if (cmp > 0) {
		    current = current.rchild;
		} else {
		    return;
		}
	    }

	    Node newNode = new Node();
	    newNode.key = word;
	    newNode.value = count;
	    newNode.size = 1;
	    newNode.parent = parent;
	    newNode.lchild = null;
	    newNode.rchild = null;

	    int cmp = compareWords(word, parent.key);
	    if (cmp < 0) {
		parent.lchild = newNode;
	    } else {
		parent.rchild = newNode;
	    }

	    Node ancestor = parent;
	    while (ancestor != null) {
		ancestor.size++;
		ancestor = ancestor.parent;
	    }
	}

	public FnList<FnTupl2<FnList<Character>, Integer>> toList() {
	    FnList<FnTupl2<FnList<Character>, Integer>> result = nil();
	    return inorderTraversal(root, result);
	}

	private FnList<FnTupl2<FnList<Character>, Integer>> inorderTraversal(
	    Node node, FnList<FnTupl2<FnList<Character>, Integer>> acc) {
	    if (node == null) {
		return acc;
	    }
	    acc = inorderTraversal(node.rchild, acc);
	    acc = cons(new FnTupl2<>(node.key, node.value), acc);
	    acc = inorderTraversal(node.lchild, acc);
	    return acc;
	}
    }

    public static<T>FnList<T>mergeSort(FnList<T> xs, ToIntBiFunction<T,T> cmp) {
	int n0 = xs.length();
	if (n0 <= 1)
	    return xs;
	else
	    return mergeSort_split(xs, nil(), n0, 0, cmp);
    }

    private static<T>FnList<T> mergeSort_split(FnList<T> xs, FnList<T> ys, int n0, int n1, ToIntBiFunction<T,T> cmp) {
	while (2*n1 < n0) {
	    ys = cons(xs.hd(), ys);
	    xs = xs.tl();
	    n1 += 1;
	}
	return mergeSort_merge
	    (mergeSort(reverse(ys), cmp), mergeSort(xs, cmp), cmp);
    }

    private static<T>FnList<T> mergeSort_merge(FnList<T> xs, FnList<T> ys, ToIntBiFunction<T,T> cmp) {
	FnList<T> zs = nil();
	while (!nilq(xs) && !nilq(ys)) {
	    if (cmp.applyAsInt(xs.hd(), ys.hd()) <= 0) {
		zs = cons(xs.hd(), zs);
		xs = xs.tl();
	    }
	    else {
		zs = cons(ys.hd(), zs);
		ys = ys.tl();
	    }
	}
	while (!nilq(xs)) {
	    zs = cons(xs.hd(), zs);
	    xs = xs.tl();
	}
	while (!nilq(ys)) {
	    zs = cons(ys.hd(), zs);
	    ys = ys.tl();
	}
	return reverse(zs);
    }

    public static void main (String[] args) {
	// HX-2025-12-16:
	// Please write minimal testing code for pg2701_word$count$listize4()
	// In particular, please print out the first 100 word-count pairs, where
	// each line should contain only one word-count pair.
	FnList<FnTupl2<FnList<Character>, Integer>> result = pg2701_word$count$listize4();

	int count = 0;
	FnList<FnTupl2<FnList<Character>, Integer>> current = result;

	while (count < 100 && current.consq()) {
	    FnTupl2<FnList<Character>, Integer> pair = current.hd();

	    pair.get0().foritm(ch -> System.out.print(ch));
	    System.out.println(": " + pair.get1());

	    current = current.tl();
	    count++;
	}

	return /*void*/;
    }
}
