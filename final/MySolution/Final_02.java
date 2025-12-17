/*
// HX: 50 points for Final_02
// HX: This one tests your quicksort and mergesort
// In Final_01, pg2701_word$strmize() is implemented
// that lists all the words in pg2701.txt. Here, you
// are asked to generate FnList of pairs; each pair consists
// of a word (FnList<Character>) and a count (Integer) such that
// the count is the number of occurrences of the word in pg2701.txt.
// Note that a lower case letter is considered the same as its
// corresponding upper case. For instance, "Whale" and "whale"
// are considered the same word.
*/

import MyFinalLib.FnList.*;
import MyFinalLib.LnStrm.*;
import MyFinalLib.FnTuple.*;

import static MyFinalLib.FnList.FnListSUtil.*;

import java.util.function.ToIntBiFunction;

public class Final_02 {
    static FnList<FnTupl2<FnList<Character>, Integer>> pg2701_word$count$listize2() {
	// HX-2025-12-15:
	// Your implementation must contain the following steps:
	// 1. Call pg2701_word$strmize() to get a stream of words
	// 2. Turn this stream into an array A1 of words (FnList<Character>[])
	// 3. Call the quicksort (arrayQuickSort) done in Assign06_03 to sort A1
	// 4. Use sorted A1 to generate a list L2 of word-count pairs
	// 5. Use the mergesort (mergeSort) in Assign05_01 to sort L2 using
	//    the order (w1, n1) <= (w2, n2) if n1 > n2 or n1 = n2 and w1 <= w2
	// 6. The sorted L2 is the return value of pg2701_word$count$listize2()

	LnStrm<FnList<Character>> wordStream = Final_01.pg2701_word$strmize();

	FnList<FnList<Character>> wordList = stream$to$list(wordStream);

	@SuppressWarnings("unchecked")
	FnList<Character>[] A1 = (FnList<Character>[]) new FnList[wordList.length()];
	list$to$array(wordList, A1, 0);

	arrayQuickSort(A1, (w1, w2) -> compareWords(w1, w2));

	FnList<FnTupl2<FnList<Character>, Integer>> L2 = count$words(A1);

	FnList<FnTupl2<FnList<Character>, Integer>> sortedL2 = mergeSort(L2, (p1, p2) -> {
	    if (p1.get1() > p2.get1()) return -1;
	    if (p1.get1() < p2.get1()) return 1;
	    return compareWords(p1.get0(), p2.get0());
	});

	return sortedL2;
    }

    private static FnList<FnList<Character>> stream$to$list(LnStrm<FnList<Character>> stream) {
	FnList<FnList<Character>> result = nil();
	LnStcn<FnList<Character>> stcn = stream.eval0();

	while (stcn.consq()) {
	    result = cons(stcn.head, result);
	    stcn = stcn.tail.eval0();
	}

	return reverse(result);
    }

    private static void list$to$array(FnList<FnList<Character>> list, FnList<Character>[] array, int index) {
	FnList<FnList<Character>> current = list;
	int i = index;
	while (!current.nilq()) {
	    array[i] = current.hd();
	    current = current.tl();
	    i++;
	}
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

    private static FnList<FnTupl2<FnList<Character>, Integer>> count$words(FnList<Character>[] A1) {
	if (A1.length == 0) return nil();

	FnList<FnTupl2<FnList<Character>, Integer>> result = nil();
	int i = 0;

	while (i < A1.length) {
	    FnList<Character> currentWord = A1[i];
	    int count = 1;
	    i++;

	    while (i < A1.length && compareWords(currentWord, A1[i]) == 0) {
		count++;
		i++;
	    }

	    result = cons(new FnTupl2<>(currentWord, count), result);
	}

	return reverse(result);
    }

    public static<T> void arrayQuickSort(T[] A, ToIntBiFunction<T,T> cmp) {
	if (A == null || A.length <= 1) {
	    return;
	}
	quickSortHelper(A, 0, A.length - 1, cmp);
    }

    private static<T> void quickSortHelper(T[] A, int low, int high, ToIntBiFunction<T,T> cmp) {
	if (low >= high) {
	    return;
	}
	int[] pivots = threeWayPartition(A, low, high, cmp);
	quickSortHelper(A, low, pivots[0] - 1, cmp);
	quickSortHelper(A, pivots[1] + 1, high, cmp);
    }

    private static<T> int[] threeWayPartition(T[] A, int low, int high, ToIntBiFunction<T,T> cmp) {
	int mid = low + (high - low) / 2;
	T pivot = medianOfThree(A, low, mid, high, cmp);
	int lt = low;
	int gt = high;
	int i = low;
	while (i <= gt) {
	    int cmpResult = cmp.applyAsInt(A[i], pivot);
	    if (cmpResult < 0) {
		swap(A, lt, i);
		lt++;
		i++;
	    } else if (cmpResult > 0) {
		swap(A, i, gt);
		gt--;
	    } else {
		i++;
	    }
	}
	return new int[]{lt, gt};
    }

    private static<T> T medianOfThree(T[] A, int low, int mid, int high, ToIntBiFunction<T,T> cmp) {
	T a = A[low];
	T b = A[mid];
	T c = A[high];
	if (cmp.applyAsInt(a, b) < 0) {
	    if (cmp.applyAsInt(b, c) < 0) {
		return b;
	    } else if (cmp.applyAsInt(a, c) < 0) {
		return c;
	    } else {
		return a;
	    }
	} else {
	    if (cmp.applyAsInt(a, c) < 0) {
		return a;
	    } else if (cmp.applyAsInt(b, c) < 0) {
		return c;
	    } else {
		return b;
	    }
	}
    }

    private static<T> void swap(T[] A, int i, int j) {
	T temp = A[i];
	A[i] = A[j];
	A[j] = temp;
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
	// Please write minimal testing code for pg2701_word$count$listize2()
	// In particular, please print out the first 100 word-count pairs, where
	// each line should contain only one word-count pair.
	FnList<FnTupl2<FnList<Character>, Integer>> result = pg2701_word$count$listize2();

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
