/*
// HX: 50 points for Final_05
// HX: This one tests your priority queue implementation
*/

import MyFinalLib.LnList.*;

import java.util.function.ToIntBiFunction;

public class Final_05 {

    private static class ListNode<T> {
	int listIndex;
	T value;

	ListNode(int idx, T val) {
	    listIndex = idx;
	    value = val;
	}
    }

    private static class MyPQueueArray<T> {
	int nitm = -1;
	Object[] itms = null;
	int[] prio = null;
	ToIntBiFunction<T,T> cmp;

	public MyPQueueArray(int cap, ToIntBiFunction<T,T> cmp) {
	    assert (cap >= 1);
	    nitm = 0;
	    itms = new Object[cap];
	    prio = new int[cap];
	    this.cmp = cmp;
	}

	public int size() {
	    return nitm;
	}

	public boolean isEmpty() {
	    return (nitm <= 0);
	}

	public boolean isFull() {
	    return (nitm >= itms.length);
	}

	@SuppressWarnings("unchecked")
	public T top() {
	    return (T)itms[0];
	}

	@SuppressWarnings("unchecked")
	public T deque() {
	    T result = (T)itms[0];
	    nitm -= 1;
	    if (nitm > 0) {
		itms[0] = itms[nitm];
		prio[0] = prio[nitm];
		heapifyDown(0);
	    }
	    return result;
	}

	public void enque(T itm, int priority) {
	    itms[nitm] = itm;
	    prio[nitm] = priority;
	    heapifyUp(nitm);
	    nitm += 1;
	}

	@SuppressWarnings("unchecked")
	private void heapifyUp(int i) {
	    while (i > 0) {
		int parent = (i - 1) / 2;
		if (prio[i] < prio[parent] ||
		    (prio[i] == prio[parent] &&
		     cmp.applyAsInt((T)itms[i], (T)itms[parent]) < 0)) {
		    swap(i, parent);
		    i = parent;
		} else {
		    break;
		}
	    }
	}

	@SuppressWarnings("unchecked")
	private void heapifyDown(int i) {
	    while (true) {
		int leftChild = 2 * i + 1;
		int rightChild = 2 * i + 2;
		int smallest = i;
		if (leftChild < nitm &&
		    (prio[leftChild] < prio[smallest] ||
		     (prio[leftChild] == prio[smallest] &&
		      cmp.applyAsInt((T)itms[leftChild], (T)itms[smallest]) < 0))) {
		    smallest = leftChild;
		}
		if (rightChild < nitm &&
		    (prio[rightChild] < prio[smallest] ||
		     (prio[rightChild] == prio[smallest] &&
		      cmp.applyAsInt((T)itms[rightChild], (T)itms[smallest]) < 0))) {
		    smallest = rightChild;
		}
		if (smallest != i) {
		    swap(i, smallest);
		    i = smallest;
		} else {
		    break;
		}
	    }
	}

	private void swap(int i, int j) {
	    Object tempItem = itms[i];
	    int tempPrio = prio[i];
	    itms[i] = itms[j];
	    prio[i] = prio[j];
	    itms[j] = tempItem;
	    prio[j] = tempPrio;
	}
    }

    public static<T> LnList<T>
	LnList_n$way$merge(LnList<T> xss[], ToIntBiFunction<T,T> cmp) {
	// HX: Given an array of (linear) lists (LnList), each of which is
	// ordered according to cmp, please implement a function to merge them
	// into one ordered (linear) list. Please note that you cannot create
	// new list nodes; you can only use existing nodes to form the returned
	// linear list. You are asked to use MyPQueueArray.java implemented in
	// Assigment#9 for finding the minimum of a collection of nodes.

	if (xss == null || xss.length == 0) {
	    return new LnList<T>();
	}

	int nonEmpty = 0;
	for (int i = 0; i < xss.length; i++) {
	    if (xss[i] != null && xss[i].consq1()) {
		nonEmpty++;
	    }
	}

	if (nonEmpty == 0) {
	    return new LnList<T>();
	}

	MyPQueueArray<ListNode<T>> pq = new MyPQueueArray<>(nonEmpty,
	    (n1, n2) -> {
		int cmpResult = cmp.applyAsInt(n1.value, n2.value);
		if (cmpResult == 0) {
		    return Integer.compare(n1.listIndex, n2.listIndex);
		}
		return cmpResult;
	    });

	for (int i = 0; i < xss.length; i++) {
	    if (xss[i] != null && xss[i].consq1()) {
		pq.enque(new ListNode<T>(i, xss[i].hd1()), 0);
	    }
	}

	LnList<T> result = new LnList<T>();
	LnList<T> resultTail = null;

	while (!pq.isEmpty()) {
	    ListNode<T> minNode = pq.deque();
	    int minIndex = minNode.listIndex;

	    LnList<T> extractedNode = xss[minIndex];
	    xss[minIndex] = extractedNode.tl1();

	    extractedNode.unlink1();

	    if (result.nilq1()) {
		result = extractedNode;
		resultTail = extractedNode;
	    } else {
		resultTail.link1(extractedNode);
		resultTail = extractedNode;
	    }

	    if (xss[minIndex] != null && xss[minIndex].consq1()) {
		pq.enque(new ListNode<T>(minIndex, xss[minIndex].hd1()), 0);
	    }
	}

	return result;
    }

    public static<T>
	LnList<T>
	LnList_mergeSort$5way(LnList<T> xs, ToIntBiFunction<T,T> cmp) {
	// HX: Please use LnList_n$way$merge to implement 5-way mergesort
	// on a linear list. That is, split each list evenly into 5 sublists;
	// recursely sort the 5 sublist and then use LnList_n$way$merge to merge
	// them into one sorted list.
	// Please make sure that your implementation of LnList_mergeSort$5way
	// does stable sorting!

	if (xs == null || xs.nilq1()) {
	    return xs;
	}

	int length = xs.length1();
	if (length <= 1) {
	    return xs;
	}

	int subLength = length / 5;
	int remainder = length % 5;

	@SuppressWarnings("unchecked")
	LnList<T>[] sublists = (LnList<T>[]) new LnList[5];

	LnList<T> current = xs;

	for (int i = 0; i < 5; i++) {
	    int currentSubLength = subLength + (i < remainder ? 1 : 0);

	    if (currentSubLength == 0) {
		sublists[i] = new LnList<T>();
		continue;
	    }

	    sublists[i] = current;

	    LnList<T> lastNode = current;
	    for (int j = 1; j < currentSubLength && lastNode.consq1(); j++) {
		lastNode = lastNode.tl1();
	    }

	    if (lastNode.consq1()) {
		current = lastNode.tl1();
		lastNode.unlink1();
	    } else {
		current = new LnList<T>();
	    }
	}

	for (int i = 0; i < 5; i++) {
	    if (sublists[i] != null && sublists[i].consq1()) {
		sublists[i] = LnList_mergeSort$5way(sublists[i], cmp);
	    }
	}

	return LnList_n$way$merge(sublists, cmp);
    }

    public static void main(String[] args) {
	// Please write some testing code that applies
	// mergeSort to parity-sort the list [0,1,2,...,999998,999999]
	// of 1000000 elements.

	LnList<Integer> list = new LnList<Integer>();
	LnList<Integer> tail = null;

	for (int i = 0; i < 1000000; i++) {
	    LnList<Integer> newNode = new LnList<Integer>(i, new LnList<Integer>());
	    if (list.nilq1()) {
		list = newNode;
		tail = newNode;
	    } else {
		tail.link1(newNode);
		tail = newNode;
	    }
	}

	System.out.println("Starting parity sort of 1,000,000 elements...");

	ToIntBiFunction<Integer, Integer> parityCmp = (a, b) -> {
	    int parityA = a % 2;
	    int parityB = b % 2;
	    if (parityA < parityB) return -1;
	    if (parityA > parityB) return 1;
	    if (a < b) return -1;
	    if (a > b) return 1;
	    return 0;
	};

	LnList<Integer> sorted = LnList_mergeSort$5way(list, parityCmp);

	System.out.println("First 20 elements after parity sort:");
	int count = 0;
	LnList<Integer> current = sorted;
	while (count < 20 && current.consq1()) {
	    System.out.print(current.hd1() + " ");
	    current = current.tl1();
	    count++;
	}
	System.out.println();

	System.out.println("Parity sort completed successfully!");

	return /*void*/;
    }


}


