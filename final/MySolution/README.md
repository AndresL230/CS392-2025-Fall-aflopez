# Final Exam of CS392X1, Fall, 2025

## Overview
This final exam consists of 6 problems (Final_00 through Final_05) worth a total of 220 points. All problems work with text processing of the file `Data/pg2701.txt` (Project Gutenberg's Moby Dick).

---

## Problem 0: Final_00 (0 points - Helper Code)

**File:** [Final_00.java](Final_00.java)

**Purpose:** Provides helper function `pg2701_char$strmize()` for use in other problems.

**What it does:**
- Reads `Data/pg2701.txt` and converts it into a lazy stream of characters (`LnStrm<Character>`)
- Already implemented - no work needed

**Constraints:**
- None (this is provided code)

**Solution Required:**
- No implementation needed - this is helper code for other problems

---

## Problem 1: Final_01 (20 points)

**File:** [Final_01.java](Final_01.java:13)

**Task:** Implement `pg2701_word$strmize()` to create a stream of words from pg2701.txt

**What needs to be done:**
1. Build a lazy stream (`LnStrm<FnList<Character>>`) of words from the text file
2. Each word is represented as `FnList<Character>` (a functional list of characters)
3. Convert all uppercase letters to lowercase
4. Write minimal testing code in `main()`

**Constraints:**
- Words consist of letters `[a-z]+[A-Z]` plus apostrophe `'`
- Words are separated by non-letters/non-apostrophes (blanks, punctuation, etc.)
- Must build on top of `Final_00.pg2701_char$strmize()` - cannot use Java file I/O directly
- Cannot use Java library functions for file processing
- All uppercase letters must be converted to lowercase

**Solution Required:**
- Implement `pg2701_word$strmize()` function
- Implement `main()` with minimal testing

---

## Problem 2: Final_02 (50 points)

**File:** [Final_02.java](Final_02.java:18)

**Task:** Implement `pg2701_word$count$listize2()` to count word frequencies using quicksort and mergesort

**What needs to be done:**
1. Generate a `FnList` of pairs: `(word, count)` where count is the number of occurrences
2. Words are case-insensitive (e.g., "Whale" and "whale" are the same)
3. Write testing code that prints the first 100 word-count pairs

**Required Implementation Steps:**
1. Call `pg2701_word$strmize()` to get a stream of words
2. Convert the stream into an array `A1` of words (`FnList<Character>[]`)
3. Call quicksort (`arrayQuickSort` from Assign06_03) to sort `A1`
4. Use sorted `A1` to generate list `L2` of word-count pairs
5. Use mergesort (`mergeSort` from Assign05_01) to sort `L2` with ordering:
   - `(w1, n1) <= (w2, n2)` if `n1 > n2` OR (`n1 = n2` AND `w1 <= w2`)
   - (Higher counts first, then alphabetical for ties)
6. Return the sorted `L2`

**Constraints:**
- Must use quicksort from Assign06_03
- Must use mergesort from Assign05_01
- Must follow the exact 6-step process outlined above
- Sorting order: descending by count, then ascending alphabetically for ties

**Solution Required:**
- Implement `pg2701_word$count$listize2()` following all 6 steps
- Implement `main()` to print first 100 word-count pairs (one per line)

---

## Problem 3: Final_03 (50 points)

**File:** [Final_03.java](Final_03.java:14)

**Task:** Implement `pg2701_word$count$listize3()` using hash map (alternative approach to Final_02)

**What needs to be done:**
1. Same output as Final_02 but using a hash map approach
2. Write testing code that prints the first 100 word-count pairs

**Required Implementation Steps:**
1. Call `pg2701_word$strmize()` to get a stream of words
2. Use the hash map from Assign08_02 (open addressing) to count occurrences of each word
3. Convert the hash map into a `FnList` (`WNS`) of word-count pairs
4. Use mergesort (`mergeSort` from Assign05_01) to sort `WNS` with ordering:
   - `(w1, n1) <= (w2, n2)` if `n1 > n2` OR (`n1 = n2` AND `w1 <= w2`)
5. Return the sorted `WNS`

**Constraints:**
- Must use hash map with open addressing from Assign08_02
- Must use mergesort from Assign05_01
- Same sorting order as Final_02 (descending by count, alphabetical for ties)

**Solution Required:**
- Implement `pg2701_word$count$listize3()` using hash map approach
- Implement `main()` to print first 100 word-count pairs (one per line)

---

## Problem 4: Final_04 (50 points)

**File:** [Final_04.java](Final_04.java:14)

**Task:** Implement `pg2701_word$count$listize4()` using RBST (Red-Black Search Tree)

**What needs to be done:**
1. Same output as Final_02/Final_03 but using RBST approach
2. Write testing code that prints the first 100 word-count pairs

**Required Implementation Steps:**
1. Call `pg2701_word$strmize()` to get a stream of words
2. Use RBST from Quiz02_06 to count occurrences of each word
   - **Note:** Must modify Quiz02_06 to make it a generic associative map
3. Convert the RBST-based map into a `FnList` (`WNS`) of word-count pairs
4. Use mergesort (`mergeSort` from Assign05_01) to sort `WNS` with ordering:
   - `(w1, n1) <= (w2, n2)` if `n1 > n2` OR (`n1 = n2` AND `w1 <= w2`)
5. Return the sorted `WNS`

**Constraints:**
- Must use RBST from Quiz02_06 (requires modification to generic map)
- Must use mergesort from Assign05_01
- Same sorting order as previous problems

**Solution Required:**
- Implement `pg2701_word$count$listize4()` using RBST approach
- Modify Quiz02_06 RBST to work as generic associative map
- Implement `main()` to print first 100 word-count pairs (one per line)

---

## Problem 5: Final_05 (50 points)

**File:** [Final_05.java](Final_05.java:11)

**Task:** Implement n-way merge and 5-way mergesort using priority queue

**What needs to be done:**
1. Implement `LnList_n$way$merge()` - merges n sorted linear lists into one sorted list
2. Implement `LnList_mergeSort$5way()` - performs 5-way mergesort on a linear list
3. Write testing code that parity-sorts a list of 1,000,000 elements [0, 1, 2, ..., 999999]

**Function 1: `LnList_n$way$merge()`**
- Input: Array of sorted `LnList` objects and a comparator
- Output: Single sorted `LnList` merging all input lists
- **Key constraint:** Cannot create new list nodes, only rearrange existing nodes
- Must use `MyPQueueArray` from Assignment#9 to find minimum nodes

**Function 2: `LnList_mergeSort$5way()`**
- Input: A `LnList` and a comparator
- Output: Sorted `LnList`
- Process:
  1. Split list evenly into 5 sublists
  2. Recursively sort each of the 5 sublists
  3. Use `LnList_n$way$merge()` to merge them
- **Key constraint:** Must perform stable sorting

**Constraints:**
- Cannot create new list nodes in n-way merge (reuse existing nodes)
- Must use priority queue (`MyPQueueArray`) from Assignment#9
- 5-way mergesort must be stable
- Must split into exactly 5 sublists

**Solution Required:**
- Implement `LnList_n$way$merge()` using priority queue
- Implement `LnList_mergeSort$5way()` with stable sorting
- Implement `main()` to parity-sort list [0, 1, ..., 999999] (1M elements)

---

## Key Dependencies

You will need implementations from previous assignments:
- **Assign05_01:** mergeSort function
- **Assign06_03:** arrayQuickSort function
- **Assign08_02:** Hash map with open addressing
- **Quiz02_06:** RBST (needs modification to generic map)
- **Assignment#9:** MyPQueueArray (priority queue)

## Testing Requirements

Each problem (except Final_00) requires:
- Working implementation of the specified function(s)
- Minimal testing code in `main()`
- For Final_02, Final_03, Final_04: Print first 100 word-count pairs (one per line)
- For Final_05: Parity-sort 1,000,000 elements

## Point Distribution

| Problem | Points | Focus Area |
|---------|--------|------------|
| Final_00 | 0 | Helper code (provided) |
| Final_01 | 20 | Stream processing, text parsing |
| Final_02 | 50 | Quicksort + Mergesort |
| Final_03 | 50 | Hash map implementation |
| Final_04 | 50 | RBST / Generic map |
| Final_05 | 50 | Priority queue + n-way merge |
| **Total** | **220** | |

