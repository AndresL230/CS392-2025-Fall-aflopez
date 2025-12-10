import Library.FnList.*;
import static Library.FnList.FnListSUtil.*;
import Library.LnList.*;
import Library.LnStrm.*;
import Library.FnTuple.*;
import Library.MyMap00.*;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class Assign08_01<V>
    implements MyMap00<String, V> {
    // HX-2025-11-12:
    // Please give an implementation of hash table
    // that uses separate chaining for handling collisions.

    private LnList<FnTupl2<String, FnList<V>>>[] table;
    private int capacity;
    private int numKeys; // number of distinct keys

    @SuppressWarnings("unchecked")
    public Assign08_01(int capacity) {
	this.capacity = capacity;
	this.numKeys = 0;
	this.table = (LnList<FnTupl2<String, FnList<V>>>[]) new LnList[capacity];
	for (int i = 0; i < capacity; i++) {
	    table[i] = new LnList<FnTupl2<String, FnList<V>>>();
	}
    }

    public Assign08_01() {
	this(100); // default capacity
    }

    private int hash(String key) {
	return Math.abs(key.hashCode()) % capacity;
    }

    @Override
    public int size() {
	return numKeys;
    }

    @Override
    public boolean isFull() {
	return false; // separate chaining can grow indefinitely
    }

    @Override
    public boolean isEmpty() {
	return numKeys == 0;
    }

    @Override
    public LnStrm<FnTupl2<String, FnList<V>>> strmize() {
	return strmize_helper(0);
    }

    private LnStrm<FnTupl2<String, FnList<V>>> strmize_helper(int index) {
	return new LnStrm<FnTupl2<String, FnList<V>>>(
	    () -> {
		// Find next non-empty bucket
		int idx = index;
		while (idx < capacity && table[idx].nilq1()) {
		    idx++;
		}

		if (idx >= capacity) {
		    return new LnStcn<FnTupl2<String, FnList<V>>>();
		}

		// Get the chain at this bucket
		LnList<FnTupl2<String, FnList<V>>> chain = table[idx];
		if (chain.nilq1()) {
		    return strmize_helper(idx + 1).eval0();
		} else {
		    FnTupl2<String, FnList<V>> entry = chain.hd1();
		    LnList<FnTupl2<String, FnList<V>>> restChain = chain.tl1();

		    // Create stream that processes rest of chain, then moves to next bucket
		    LnStrm<FnTupl2<String, FnList<V>>> restStrm =
			restChain.nilq1() ? strmize_helper(idx + 1) :
			chainToStream(restChain, idx);

		    return new LnStcn<FnTupl2<String, FnList<V>>>(entry, restStrm);
		}
	    }
	);
    }

    private LnStrm<FnTupl2<String, FnList<V>>> chainToStream(
	LnList<FnTupl2<String, FnList<V>>> chain, int bucketIndex) {
	return new LnStrm<FnTupl2<String, FnList<V>>>(
	    () -> {
		if (chain.nilq1()) {
		    return strmize_helper(bucketIndex + 1).eval0();
		} else {
		    FnTupl2<String, FnList<V>> entry = chain.hd1();
		    LnList<FnTupl2<String, FnList<V>>> rest = chain.tl1();
		    LnStrm<FnTupl2<String, FnList<V>>> restStrm =
			rest.nilq1() ? strmize_helper(bucketIndex + 1) :
			chainToStream(rest, bucketIndex);
		    return new LnStcn<FnTupl2<String, FnList<V>>>(entry, restStrm);
		}
	    }
	);
    }

    @Override
    public FnList<V> search$raw(String key) {
	int index = hash(key);
	LnList<FnTupl2<String, FnList<V>>> chain = table[index];

	while (chain.consq1()) {
	    FnTupl2<String, FnList<V>> entry = chain.hd1();
	    if (entry.getSub0().equals(key)) {
		return entry.getSub1();
	    }
	    chain = chain.tl1();
	}
	return null; // not found
    }

    @Override
    public FnList<V> search$exn(String key) {
	FnList<V> result = search$raw(key);
	if (result == null) {
	    throw new MyMap00NoKeyExn();
	}
	return result;
    }

    @Override
    public FnList<V> search$opt(String key) {
	return search$raw(key);
    }

    @Override
    public void insert$raw(String key, V val) {
	int index = hash(key);
	LnList<FnTupl2<String, FnList<V>>> chain = table[index];

	// Search for existing key
	LnList<FnTupl2<String, FnList<V>>> current = chain;
	while (current.consq1()) {
	    FnTupl2<String, FnList<V>> entry = current.hd1();
	    if (entry.getSub0().equals(key)) {
		// Key exists, prepend value to its list (LIFO)
		entry.setSub1(cons(val, entry.getSub1()));
		return;
	    }
	    current = current.tl1();
	}

	// Key doesn't exist, add new entry
	FnList<V> valueList = cons(val, nil());
	FnTupl2<String, FnList<V>> newEntry = new FnTupl2<>(key, valueList);
	table[index] = new LnList<>(newEntry, table[index]);
	numKeys++;
    }

    @Override
    public void insert$exn(String key, V val) {
	if (isFull()) {
	    throw new MyMap00FullExn();
	}
	insert$raw(key, val);
    }

    @Override
    public boolean insert$opt(String key, V val) {
	if (isFull()) {
	    return false;
	}
	insert$raw(key, val);
	return true;
    }

    @Override
    public FnList<V> remove$raw(String key) {
	int index = hash(key);
	LnList<FnTupl2<String, FnList<V>>> chain = table[index];
	LnList<FnTupl2<String, FnList<V>>> prev = null;

	while (chain.consq1()) {
	    FnTupl2<String, FnList<V>> entry = chain.hd1();
	    if (entry.getSub0().equals(key)) {
		// Found the key, remove it
		FnList<V> values = entry.getSub1();

		if (prev == null) {
		    // Remove from head
		    table[index] = chain.tl1();
		} else {
		    // Remove from middle/end
		    prev.link1(chain.tl1());
		}

		numKeys--;
		return values;
	    }
	    prev = chain;
	    chain = chain.tl1();
	}

	return null; // not found
    }

    @Override
    public FnList<V> remove$exn(String key) {
	FnList<V> result = remove$raw(key);
	if (result == null) {
	    throw new MyMap00NoKeyExn();
	}
	return result;
    }

    @Override
    public FnList<V> remove$opt(String key) {
	return remove$raw(key);
    }

    @Override
    public void foritm(BiConsumer<? super String, ? super V> work) {
	for (int i = 0; i < capacity; i++) {
	    LnList<FnTupl2<String, FnList<V>>> chain = table[i];
	    while (chain.consq1()) {
		FnTupl2<String, FnList<V>> entry = chain.hd1();
		String key = entry.getSub0();
		FnList<V> values = entry.getSub1();

		// Process each key-value pair
		values.foritm(val -> work.accept(key, val));

		chain = chain.tl1();
	    }
	}
    }

    // Test code
    public static void main(String[] args) {
	System.out.println("=== Testing Assign08_01 (Separate Chaining) ===");

	Assign08_01<Integer> map = new Assign08_01<>(10);

	// Test insertions
	map.insert$raw("apple", 1);
	map.insert$raw("apple", 2);
	map.insert$raw("banana", 3);
	map.insert$raw("cherry", 4);

	System.out.println("Size after insertions: " + map.size());
	System.out.println("Is empty: " + map.isEmpty());

	// Test search
	FnList<Integer> appleValues = map.search$raw("apple");
	System.out.print("Values for 'apple': ");
	appleValues.foritm(v -> System.out.print(v + " "));
	System.out.println();

	// Test foritm
	System.out.println("All key-value pairs:");
	map.foritm((key, val) -> System.out.println("  " + key + " -> " + val));

	// Test remove
	FnList<Integer> removed = map.remove$raw("apple");
	System.out.print("Removed values for 'apple': ");
	removed.foritm(v -> System.out.print(v + " "));
	System.out.println();
	System.out.println("Size after removal: " + map.size());

	System.out.println("\nTest completed successfully!");
    }
}
