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
    public static<T>
	LnStrm<T> mergeLnStrm(LnStrm<LnStrm<T>> fxss, ToIntBiFunction<T,T> cmpr) {
	return new LnStrm<T>(() -> {
	    LnStcn<LnStrm<T>> cell = fxss.eval0();

	    if (cell.nilq()) {
		return new LnStcn<T>();
	    }

	    LnStrm<T> firstStream = cell.hd();
	    LnStcn<T> firstCell = firstStream.eval0();

	    if (firstCell.nilq()) {
		return mergeLnStrm(cell.tl(), cmpr).eval0();
	    }

	    T head = firstCell.hd();
	    LnStrm<LnStrm<T>> restStreams = insertStream(firstCell.tl(), cell.tl(), cmpr);

	    return new LnStcn<T>(head, mergeLnStrm(restStreams, cmpr));
	});
    }

    private static<T>
	LnStrm<LnStrm<T>> insertStream(LnStrm<T> stream, LnStrm<LnStrm<T>> streams, ToIntBiFunction<T,T> cmpr) {
	return new LnStrm<LnStrm<T>>(() -> {
	    LnStcn<T> streamCell = stream.eval0();

	    if (streamCell.nilq()) {
		return streams.eval0();
	    }

	    LnStcn<LnStrm<T>> streamsCell = streams.eval0();

	    if (streamsCell.nilq()) {
		return new LnStcn<LnStrm<T>>(
		    new LnStrm<T>(() -> streamCell),
		    new LnStrm<LnStrm<T>>(() -> new LnStcn<LnStrm<T>>())
		);
	    }

	    LnStrm<T> nextStream = streamsCell.hd();
	    LnStcn<T> nextCell = nextStream.eval0();

	    if (nextCell.nilq()) {
		return new LnStcn<LnStrm<T>>(
		    new LnStrm<T>(() -> streamCell),
		    streamsCell.tl()
		);
	    }

	    int comparison = cmpr.applyAsInt(streamCell.hd(), nextCell.hd());

	    if (comparison <= 0) {
		return new LnStcn<LnStrm<T>>(
		    new LnStrm<T>(() -> streamCell),
		    new LnStrm<LnStrm<T>>(() -> new LnStcn<LnStrm<T>>(
			new LnStrm<T>(() -> nextCell),
			streamsCell.tl()
		    ))
		);
	    } else {
		return new LnStcn<LnStrm<T>>(
		    new LnStrm<T>(() -> nextCell),
		    insertStream(new LnStrm<T>(() -> streamCell), streamsCell.tl(), cmpr)
		);
	    }
	});
    }

    public static void main(String[] args) {
	System.out.println("Testing mergeLnStrm:");

	LnStrm<Integer> stream1 = new LnStrm<>(() -> new LnStcn<>(1, new LnStrm<>(() -> new LnStcn<>(4, new LnStrm<>(() -> new LnStcn<>(7, new LnStrm<>()))))));
	LnStrm<Integer> stream2 = new LnStrm<>(() -> new LnStcn<>(2, new LnStrm<>(() -> new LnStcn<>(5, new LnStrm<>(() -> new LnStcn<>(8, new LnStrm<>()))))));
	LnStrm<Integer> stream3 = new LnStrm<>(() -> new LnStcn<>(3, new LnStrm<>(() -> new LnStcn<>(6, new LnStrm<>(() -> new LnStcn<>(9, new LnStrm<>()))))));

	LnStrm<LnStrm<Integer>> streamOfStreams = new LnStrm<>(() -> new LnStcn<>(stream1, new LnStrm<>(() -> new LnStcn<>(stream2, new LnStrm<>(() -> new LnStcn<>(stream3, new LnStrm<>()))))));

	LnStrm<Integer> merged = mergeLnStrm(streamOfStreams, (a, b) -> a.compareTo(b));

	System.out.print("Merged stream: ");
	for (int i = 0; i < 9; i++) {
	    LnStcn<Integer> cell = merged.eval0();
	    if (!cell.nilq()) {
		System.out.print(cell.hd() + " ");
		merged = cell.tl();
	    }
	}
	System.out.println();

	LnStrm<Integer> stream4 = new LnStrm<>(() -> new LnStcn<>(10, new LnStrm<>(() -> new LnStcn<>(20, new LnStrm<>(() -> new LnStcn<>(30, new LnStrm<>()))))));
	LnStrm<Integer> stream5 = new LnStrm<>(() -> new LnStcn<>(15, new LnStrm<>(() -> new LnStcn<>(25, new LnStrm<>()))));
	LnStrm<LnStrm<Integer>> twoStreams = new LnStrm<>(() -> new LnStcn<>(stream4, new LnStrm<>(() -> new LnStcn<>(stream5, new LnStrm<>()))));
	LnStrm<Integer> mergedTwo = mergeLnStrm(twoStreams, (a, b) -> a.compareTo(b));

	System.out.print("Merged two streams: ");
	for (int i = 0; i < 5; i++) {
	    LnStcn<Integer> cell = mergedTwo.eval0();
	    if (!cell.nilq()) {
		System.out.print(cell.hd() + " ");
		mergedTwo = cell.tl();
	    }
	}
	System.out.println();
    }

} // end of [public class Assign06_01{...}]

