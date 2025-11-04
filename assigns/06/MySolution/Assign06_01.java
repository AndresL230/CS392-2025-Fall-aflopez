import Library.LnStrm.*;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.BiFunction;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.ToIntBiFunction;

public class Assign06_01 {
//
    public static<T>LnStrm<T> mergeLnStrm(LnStrm<LnStrm<T>> fxss, ToIntBiFunction<T,T> cmpr) {
	return new LnStrm<T>(() -> mergeLnStrmHelper(fxss, cmpr));
    }

    private static<T>
	LnStcn<T> mergeLnStrmHelper(LnStrm<LnStrm<T>> fxss, ToIntBiFunction<T,T> cmpr) {
	LnStcn<LnStrm<T>> stcn = fxss.eval0();

	if (stcn.nilq()) {
	    return new LnStcn<T>();
	}

	java.util.PriorityQueue<StreamHead<T>> minHeap =
	    new java.util.PriorityQueue<>((a, b) -> cmpr.applyAsInt(a.value, b.value));

	LnStcn<LnStrm<T>> current = stcn;
	while (current.consq()) {
	    LnStrm<T> stream = current.head;
	    LnStcn<T> streamCon = stream.eval0();

	    if (streamCon.consq()) {
		minHeap.offer(new StreamHead<T>(streamCon.head, streamCon.tail));
	    }

	    if (current.tail != null) {
		current = current.tail.eval0();
	    } else {
		break;
	    }
	}

	if (minHeap.isEmpty()) {
	    return new LnStcn<T>();
	}

	StreamHead<T> min = minHeap.poll();
	T minValue = min.value;

	if (min.tail != null) {
	    LnStcn<T> nextInStream = min.tail.eval0();
	    if (nextInStream.consq()) {
		minHeap.offer(new StreamHead<T>(nextInStream.head, nextInStream.tail));
	    }
	}

	LnStrm<LnStrm<T>> remainingStreams = heapToStreamOfStreams(minHeap);

	return new LnStcn<T>(minValue, mergeLnStrm(remainingStreams, cmpr));
    }

    private static class StreamHead<T> {
	T value;
	LnStrm<T> tail;

	StreamHead(T value, LnStrm<T> tail) {
	    this.value = value;
	    this.tail = tail;
	}
    }

    private static<T>
	LnStrm<LnStrm<T>> heapToStreamOfStreams(java.util.PriorityQueue<StreamHead<T>> heap) {
	if (heap.isEmpty()) {
	    return new LnStrm<LnStrm<T>>(() -> new LnStcn<LnStrm<T>>());
	}

	return new LnStrm<LnStrm<T>>(() -> {
	    if (heap.isEmpty()) {
		return new LnStcn<LnStrm<T>>();
	    }

	    StreamHead<T> head = heap.poll();
	    LnStrm<T> stream = new LnStrm<T>(() -> new LnStcn<T>(head.value, head.tail));

	    return new LnStcn<LnStrm<T>>(stream, heapToStreamOfStreams(heap));
	});
    }
//

} // end of [public class Assign06_01{...}]

