package MyFinalLib.MyDeque;

import java.util.function.Consumer;
import java.util.function.BiConsumer;


class NotImplementedExn extends RuntimeException {}

public class MyDequeList<T> extends MyDequeBase<T> {

    int nitm = -1;
    Node frnt = null;
    Node rear = null;

    private class Node {
        private T item;
	private Node prev;
        private Node next;
        
        private Node(T itm, Node prv, Node nxt) {
            item = itm;
	    prev = prv;
            next = nxt;
        }
    }

    public MyDequeList() {
	nitm = 0;
	frnt = null; rear = null;
    }

    // @Override
    public int size() { return nitm; }
    // @Override
    public boolean isFull() { return false; }

    // @Override
    public T fpeek$raw() { return frnt.item; }
    // @Override
    public T rpeek$raw() { return rear.item; }
//
    public T fdeque$raw()
    {
	T result = frnt.item;
	frnt = frnt.next;
	if (frnt != null) {
	    frnt.prev = null;
	} else {
	    rear = null;
	}
	nitm--;
	return result;
    }
    // @Override
    public T rdeque$raw()
    {
	T result = rear.item;
	rear = rear.prev;
	if (rear != null) {
	    rear.next = null;
	} else {
	    frnt = null;
	}
	nitm--;
	return result;
    }
//
    // @Override
    public void fenque$raw(T itm)
    {
	Node newNode = new Node(itm, null, frnt);
	if (frnt != null) {
	    frnt.prev = newNode;
	} else {
	    rear = newNode;
	}
	frnt = newNode;
	nitm++;
    }
    // @Override
    public void renque$raw(T itm)
    {
	Node newNode = new Node(itm, rear, null);
	if (rear != null) {
	    rear.next = newNode;
	} else {
	    frnt = newNode;
	}
	rear = newNode;
	nitm++;
    }
    // @Override
//
    public void foritm(Consumer<? super T> work) {
	Node xs = frnt;
	while (xs != null) {
	    work.accept(xs.item); xs = xs.next;
	}
    }

    public void rforitm(Consumer<? super T> work) {
	Node xs = rear;
	while (xs != null) {
	    work.accept(xs.item); xs = xs.prev;
	}
    }

    public void
	iforitm(BiConsumer<Integer, ? super T> work) {
	int i = 0;
	Node xs = frnt;
	while (xs != null) {
	    work.accept(i, xs.item); i += 1; xs = xs.next;
	}
    }

    public void
	irforitm(BiConsumer<Integer, ? super T> work) {
	int i = 0;
	Node xs = rear;
	while (xs != null) {
	    work.accept(i, xs.item); i += 1; xs = xs.prev;
	}
    }
}
