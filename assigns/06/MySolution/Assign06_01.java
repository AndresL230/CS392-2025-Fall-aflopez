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

	    LnStrm<T> firstStream = cell.head;
	    LnStcn<T> firstCell = firstStream.eval0();

	    if (firstCell.nilq()) {
		return mergeLnStrm(cell.tail, cmpr).eval0();
	    }

	    T head = firstCell.head;
	    LnStrm<LnStrm<T>> restStreams = insertStream(firstCell.tail, cell.tail, cmpr);

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

	    LnStrm<T> nextStream = streamsCell.head;
	    LnStcn<T> nextCell = nextStream.eval0();

	    if (nextCell.nilq()) {
		return new LnStcn<LnStrm<T>>(
		    new LnStrm<T>(() -> streamCell),
		    streamsCell.tail
		);
	    }

	    int comparison = cmpr.applyAsInt(streamCell.head, nextCell.head);

	    if (comparison <= 0) {
		return new LnStcn<LnStrm<T>>(
		    new LnStrm<T>(() -> streamCell),
		    new LnStrm<LnStrm<T>>(() -> new LnStcn<LnStrm<T>>(
			new LnStrm<T>(() -> nextCell),
			streamsCell.tail
		    ))
		);
	    } else {
		return new LnStcn<LnStrm<T>>(
		    new LnStrm<T>(() -> nextCell),
		    insertStream(new LnStrm<T>(() -> streamCell), streamsCell.tail, cmpr)
		);
	    }
	});
    }
//

} // end of [public class Assign06_01{...}]

