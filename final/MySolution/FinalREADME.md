Implementation Notes and Changes - CS392 FINAL EXAM

Library Changes:
- Changed select package declarations from Library.* to MyFinalLib.* in MyFinalLib directory
- Added public getter methods to FnTupl2.java: get0() and get1() to access package-private fields sub0 and sub1

Final_01 (20 points):
- Implemented pg2701_word$strmize() using lazy stream evaluation
- Created helper functions: skip$nonword(), collect$word$acc(), to$lower(), is$word$char()

Final_02 (50 points):
- Implemented pg2701_word$count$listize2() using quicksort and mergesort
- Copied arrayQuickSort from Assign06_03 with three-way partitioning
- Copied mergeSort from Assign05_01 for FnList sorting
- Changed list$to$array() from recursive to iterative to prevent stack overflow on large word lists
- Uses get0() and get1() to access FnTupl2 fields
- Sorts by frequency descending, then alphabetically for ties

Final_03 (50 points):
- Implemented pg2701_word$count$listize3() using hash map approach
- Created custom WordHashMap class with quadratic probing (adapted from Assign08_02)
- Hash function operates on FnList<Character> instead of String
- Uses get0() and get1() to access FnTupl2 fields
- Produces identical output to Final_02

Final_04 (50 points):
- Implemented pg2701_word$count$listize4() using RBST approach
- Created custom WordRBST class as generic associative map (adapted from Quiz02_06)
- Stores FnList<Character> keys with Integer values
- Provides increment() method to update word counts
- Uses in-order traversal to convert tree to FnList
- Uses get0() and get1() to access FnTupl2 fields
- Produces identical output to Final_02 and Final_03

Final_05 (50 points):
- Implemented LnList_n$way$merge() using MyPQueueArray priority queue
- Created custom MyPQueueArray class adapted from Assignment#9 with min-heap implementation
- Created ListNode wrapper class to track list index and value for priority queue
- Reuses existing list nodes without creating new nodes by unlinking and relinking
- Ensures stable merge by preferring earlier list index when values are equal
- Implemented LnList_mergeSort$5way() with 5-way divide-and-conquer

Library Incompatible with Previous Assigns Note:
All sorting and data structure code from assignments had to be copied and adapted to work with MyFinalLib types so Assignment files (Assign05_01.java, Assign06_03.java, Assign08_02.java, Quiz02_06.java) use Library.* imports
Final exam files use MyFinalLib.* imports

Compile with: javac -cp ".;.." Final_XX.java
Run with: java -cp ".;.." Final_XX
