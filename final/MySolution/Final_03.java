/*
// HX: 50 points for Final_03
// HX: This one tests your hash map implementation
// In Final_02, pg2701_word$count$listize2() is implemented
// to list words in pg2701.txt according their frequencies.
// In Final_03, you are asked to implement the same functionality
// with a different approach.
*/

import MyFinalLib.FnList.*;
import MyFinalLib.LnStrm.*;
import MyFinalLib.FnTuple.*;

import static MyFinalLib.FnList.FnListSUtil.*;

import java.util.function.ToIntBiFunction;

public class Final_03 {
    static FnList<FnTupl2<FnList<Character>, Integer>> pg2701_word$count$listize3() {
	// HX-2025-12-15:
	// Your implementation must contain the following steps:
	// 1. Call pg2701_word$strmize() to get a stream of words
	// 2. Then use the hash map implemented in Assign08_02 (open addressing)
	//    to count the number of occurrences of each word in the stream of words
	// 3. Then figure out a way to turn the hash map into a list WNS (FnList) of
	//    word-count pairs
	// 4. Use the mergesort (mergeSort) in Assign05_01 to sort WNS using
	//    the order (w1, n1) <= (w2, n2) if n1 > n2 or n1 = n2 and w1 <= w2
	// 5. The sorted WNS is the return value of pg2701_word$count$listize3()

	LnStrm<FnList<Character>> wordStream = Final_01.pg2701_word$strmize();

	WordHashMap map = new WordHashMap(50000);

	LnStcn<FnList<Character>> stcn = wordStream.eval0();
	while (stcn.consq()) {
	    FnList<Character> word = stcn.head;
	    map.increment(word);
	    stcn = stcn.tail.eval0();
	}

	FnList<FnTupl2<FnList<Character>, Integer>> WNS = map.toList();

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

    private static class WordHashMap {
	private FnTupl2<FnList<Character>, Integer>[] table;
	private boolean[] deleted;
	private int capacity;
	private int numKeys;

	@SuppressWarnings("unchecked")
	public WordHashMap(int capacity) {
	    this.capacity = capacity;
	    this.numKeys = 0;
	    this.table = (FnTupl2<FnList<Character>, Integer>[]) new FnTupl2[capacity];
	    this.deleted = new boolean[capacity];
	}

	private int hashWord(FnList<Character> word) {
	    int hash = 0;
	    FnList<Character> current = word;
	    while (!current.nilq()) {
		hash = 31 * hash + current.hd();
		current = current.tl();
	    }
	    return Math.abs(hash) % capacity;
	}

	private int probe(FnList<Character> word, int i) {
	    return (hashWord(word) + i * i) % capacity;
	}

	private boolean wordsEqual(FnList<Character> w1, FnList<Character> w2) {
	    return compareWords(w1, w2) == 0;
	}

	private int findSlot(FnList<Character> word) {
	    int i = 0;
	    while (i < capacity) {
		int index = probe(word, i);
		if (table[index] == null) {
		    return -1;
		}
		if (!deleted[index] && wordsEqual(table[index].get0(), word)) {
		    return index;
		}
		i++;
	    }
	    return -1;
	}

	private int findInsertSlot(FnList<Character> word) {
	    int i = 0;
	    int firstDeleted = -1;
	    while (i < capacity) {
		int index = probe(word, i);
		if (table[index] == null) {
		    return (firstDeleted != -1) ? firstDeleted : index;
		}
		if (deleted[index] && firstDeleted == -1) {
		    firstDeleted = index;
		}
		if (!deleted[index] && wordsEqual(table[index].get0(), word)) {
		    return index;
		}
		i++;
	    }
	    return firstDeleted;
	}

	public void increment(FnList<Character> word) {
	    int index = findInsertSlot(word);
	    if (index == -1) {
		throw new RuntimeException("Hash table is full");
	    }

	    if (table[index] != null && !deleted[index] && wordsEqual(table[index].get0(), word)) {
		table[index] = new FnTupl2<>(table[index].get0(), table[index].get1() + 1);
	    } else {
		table[index] = new FnTupl2<>(word, 1);
		deleted[index] = false;
		numKeys++;
	    }
	}

	public FnList<FnTupl2<FnList<Character>, Integer>> toList() {
	    FnList<FnTupl2<FnList<Character>, Integer>> result = nil();
	    for (int i = 0; i < capacity; i++) {
		if (table[i] != null && !deleted[i]) {
		    result = cons(table[i], result);
		}
	    }
	    return result;
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
	// Please write minimal testing code for pg2701_word$count$listize3()
	// In particular, please print out the first 100 word-count pairs, where
	// each line should contain only one word-count pair.
	FnList<FnTupl2<FnList<Character>, Integer>> result = pg2701_word$count$listize3();

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
