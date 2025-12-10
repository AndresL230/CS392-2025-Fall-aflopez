package Library.FnGtree;

import Library.FnList.*;
import Library.LnStrm.*;
import Library.MyPQueue.*;

import java.util.function.Consumer;

public class FnGtreeSUtil {
//
    public static<T> LnStrm<T>
	PFirstEnumerate(FnGtree<T> root) {
	// HX-2025-12-02:
	// This method enumerates nodes according
	// to their priority numbers (obtained by
	// calling priority()
	return new LnStrm<T>(
	  () -> {
	      MyPQueueArray<FnGtree<T>> pqueue =
		  new MyPQueueArray<FnGtree<T>>(10000);
	      pqueue.enque$raw(root);
	      return PFirstEnumerate$Helper(pqueue);
	  }
	);
    }

    private static<T> LnStcn<T>
	PFirstEnumerate$Helper(MyPQueueArray<FnGtree<T>> pqueue) {
	if (pqueue.isEmpty()) {
	    return new LnStcn<T>();
	}
	FnGtree<T> current = pqueue.deque$raw();
	FnList<FnGtree<T>> children = current.children();
	children.foritm((child) -> {
	    if (!pqueue.isFull()) {
		pqueue.enque$raw(child);
	    }
	});
	return new LnStcn<T>(
	  current.value(),
	  new LnStrm<T>(() -> PFirstEnumerate$Helper(pqueue))
	);
    }
//
} // end of [public class FnGtreeSUtil{...}]
