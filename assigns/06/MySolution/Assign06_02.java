import Library.LnStrm.*;
import Library.FnTuple.*;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.BiFunction;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.ToIntBiFunction;

public class Assign06_02 {
    public static
	LnStrm<Integer>
	ramanujanNumbers() {
	// Return a stream of all the ramanujanNumbers
	LnStrm<FnTupl2<Integer,Integer>> pairs = cubeSumOrderedIntegerPairs();
	return new LnStrm<Integer>(() -> findRamanujan(pairs));
    }

    private static
	LnStcn<Integer> findRamanujan(LnStrm<FnTupl2<Integer,Integer>> pairs) {
	LnStcn<FnTupl2<Integer,Integer>> stcn = pairs.eval0();

	if (stcn.nilq()) {
	    return new LnStcn<Integer>();
	}

	PairWithSum first = extractPair(stcn.head);
	LnStcn<FnTupl2<Integer,Integer>> next = stcn.tail.eval0();

	if (next.nilq()) {
	    return new LnStcn<Integer>();
	}

	PairWithSum second = extractPair(next.head);

	if (first.sum == second.sum) {
	    return new LnStcn<Integer>(first.sum, ramanujanNumbers());
	} else {
	    return findRamanujan(stcn.tail);
	}
    }

    private static class PairWithSum {
	int a;
	int b;
	int sum;
	PairWithSum(int a, int b, int sum) {
	    this.a = a;
	    this.b = b;
	    this.sum = sum;
	}
    }

    private static PairWithSum extractPair(FnTupl2<Integer,Integer> pair) {
	String str = pair.toString();
	str = str.substring(8, str.length()-1);
	String[] parts = str.split(",");
	int a = Integer.parseInt(parts[0]);
	int b = Integer.parseInt(parts[1]);
	return new PairWithSum(a, b, cube(a) + cube(b));
    }

    public static
	LnStrm<
	  FnTupl2<Integer,Integer>>
	cubeSumOrderedIntegerPairs() {
	// Return a stream of all the positive integer pairs
	// that are ordered according to the sum of the cubes
	// of the two integer components
	return new LnStrm<FnTupl2<Integer,Integer>>(() -> {
	    java.util.PriorityQueue<PairState> heap =
		new java.util.PriorityQueue<>((a, b) -> Integer.compare(a.cubeSum, b.cubeSum));

	    for (int i = 1; i <= 100; i++) {
		heap.offer(new PairState(i, i));
	    }

	    return generatePairs(heap);
	});
    }

    private static class PairState {
	int a;
	int b;
	int cubeSum;

	PairState(int a, int b) {
	    this.a = a;
	    this.b = b;
	    this.cubeSum = cube(a) + cube(b);
	}
    }

    private static
	LnStcn<FnTupl2<Integer,Integer>> generatePairs(java.util.PriorityQueue<PairState> heap) {
	if (heap.isEmpty()) {
	    return new LnStcn<FnTupl2<Integer,Integer>>();
	}

	PairState min = heap.poll();

	if (min.b < min.a + 100) {
	    heap.offer(new PairState(min.a, min.b + 1));
	}

	FnTupl2<Integer,Integer> pair = new FnTupl2<>(min.a, min.b);

	return new LnStcn<FnTupl2<Integer,Integer>>(
	    pair,
	    new LnStrm<FnTupl2<Integer,Integer>>(() -> generatePairs(heap))
	);
    }

    private static int cube(int n) {
	return n * n * n;
    }

    public static void main(String[] args) {
	return; // Please provide some minimal testing code
    }

} // end of [public class Assign06_02{...}]
