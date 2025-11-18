import Library.FnList.*;
import static Library.FnList.FnListSUtil.*;
import Library.LnList.*;
import Library.LnStrm.*;
import Library.FnTuple.*;
import Library.MyMap00.*;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class Assign08_02<V>
    implements MyMap00<String, V> {
    // HX-2025-11-12:
    // Please give an implementation of hash table
    // based on open addressing. The probing strategy
    // chosen for handling collisions is quadratic probing.

    private FnTupl2<String, FnList<V>>[] table;
    private boolean[] deleted; // track deleted slots
    private int capacity;
    private int numKeys; // number of distinct keys
    private static final String DELETED = "__DELETED__"; // sentinel for deleted entries

    @SuppressWarnings("unchecked")
    public Assign08_02(int capacity) {
	this.capacity = capacity;
	this.numKeys = 0;
	this.table = (FnTupl2<String, FnList<V>>[]) new FnTupl2[capacity];
	this.deleted = new boolean[capacity];
    }

    public Assign08_02() {
	this(100); // default capacity
    }

    private int hash(String key) {
	return Math.abs(key.hashCode()) % capacity;
    }

    // Quadratic probing: h(k, i) = (h(k) + i^2) mod m
    private int probe(String key, int i) {
	return (hash(key) + i * i) % capacity;
    }

    @Override
    public int size() {
	return numKeys;
    }

    @Override
    public boolean isFull() {
	return numKeys >= capacity;
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
		// Find next non-null, non-deleted entry
		int idx = index;
		while (idx < capacity && (table[idx] == null || deleted[idx])) {
		    idx++;
		}

		if (idx >= capacity) {
		    return new LnStcn<FnTupl2<String, FnList<V>>>();
		}

		FnTupl2<String, FnList<V>> entry = table[idx];
		return new LnStcn<FnTupl2<String, FnList<V>>>(entry, strmize_helper(idx + 1));
	    }
	);
    }

    private int findSlot(String key) {
	// Returns the index where key is found, or -1 if not found
	int i = 0;
	while (i < capacity) {
	    int index = probe(key, i);
	    if (table[index] == null) {
		return -1; // key not found
	    }
	    if (!deleted[index] && table[index].sub0.equals(key)) {
		return index; // key found
	    }
	    i++;
	}
	return -1; // table full or key not found
    }

    private int findInsertSlot(String key) {
	// Returns the index where key should be inserted
	// Returns index of existing key if found, or first available slot
	int i = 0;
	int firstDeleted = -1;
	while (i < capacity) {
	    int index = probe(key, i);
	    if (table[index] == null) {
		// Found empty slot
		return (firstDeleted != -1) ? firstDeleted : index;
	    }
	    if (deleted[index] && firstDeleted == -1) {
		firstDeleted = index; // remember first deleted slot
	    }
	    if (!deleted[index] && table[index].sub0.equals(key)) {
		return index; // key already exists
	    }
	    i++;
	}
	return firstDeleted; // return first deleted slot if available
    }

    @Override
    public FnList<V> search$raw(String key) {
	int index = findSlot(key);
	if (index == -1) {
	    return null;
	}
	return table[index].sub1;
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
	int index = findInsertSlot(key);
	if (index == -1) {
	    throw new RuntimeException("Hash table is full");
	}

	if (table[index] != null && !deleted[index] && table[index].sub0.equals(key)) {
	    // Key already exists, prepend value to its list (LIFO)
	    table[index].sub1 = cons(val, table[index].sub1);
	} else {
	    // New key, create new entry
	    FnList<V> valueList = cons(val, nil());
	    table[index] = new FnTupl2<>(key, valueList);
	    deleted[index] = false;
	    numKeys++;
	}
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
	try {
	    insert$raw(key, val);
	    return true;
	} catch (RuntimeException e) {
	    return false;
	}
    }

    @Override
    public FnList<V> remove$raw(String key) {
	int index = findSlot(key);
	if (index == -1) {
	    return null;
	}

	FnList<V> values = table[index].sub1;
	deleted[index] = true; // mark as deleted
	numKeys--;
	return values;
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
	    if (table[i] != null && !deleted[i]) {
		String key = table[i].sub0;
		FnList<V> values = table[i].sub1;

		// Process each key-value pair
		values.foritm(val -> work.accept(key, val));
	    }
	}
    }

    // Test code
    public static void main(String[] args) {
	System.out.println("=== Testing Assign08_02 (Quadratic Probing) ===");

	Assign08_02<Integer> map = new Assign08_02<>(10);

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

	// Test insertion after removal
	map.insert$raw("date", 5);
	System.out.println("Size after inserting 'date': " + map.size());

	System.out.println("\nTest completed successfully!");
    }
}
