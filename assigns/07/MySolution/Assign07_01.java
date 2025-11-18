
import Library.FnList.*;
import Library.LnStrm.*;
import Library.FnGtree.*;
import Library.MyDeque.*;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Assign07_01 {
//
    public static<T> LnStrm<T>
	BFirstEnumerate(FnGtree<T> root) {
	// BFS implementation using MyDequeList as a queue
	// Uses the completed MyDequeList from Assign04_02
	MyDequeList<FnGtree<T>> queue = new MyDequeList<FnGtree<T>>();
	queue.renque$exn(root);

	return BFirstEnumerate$helper(queue);
    }

    private static<T> LnStrm<T>
	BFirstEnumerate$helper(MyDequeList<FnGtree<T>> queue) {
	return new LnStrm<T>(
	    () -> {
		if (queue.isEmpty()) {
		    return new LnStcn<T>();
		} else {
		    FnGtree<T> node = queue.fdeque$raw();
		    node.children().foritm((child) -> queue.renque$exn(child));
		    return new LnStcn<T>(node.value(), BFirstEnumerate$helper(queue));
		}
	    }
	);
    }
//
    public static<T> LnStrm<T>
	DFirstEnumerate(FnGtree<T> root) {
	// DFS implementation using MyDequeList as a stack
	// Uses the completed MyDequeList from Assign04_02
	MyDequeList<FnGtree<T>> stack = new MyDequeList<FnGtree<T>>();
	stack.fenque$exn(root);

	return DFirstEnumerate$helper(stack);
    }

    private static<T> LnStrm<T>
	DFirstEnumerate$helper(MyDequeList<FnGtree<T>> stack) {
	return new LnStrm<T>(
	    () -> {
		if (stack.isEmpty()) {
		    return new LnStcn<T>();
		} else {
		    FnGtree<T> node = stack.fdeque$raw();
		    node.children().rforitm((child) -> stack.fenque$exn(child));
		    return new LnStcn<T>(node.value(), DFirstEnumerate$helper(stack));
		}
	    }
	);
    }
//
} // end of [public class Assign07_01{...}]
