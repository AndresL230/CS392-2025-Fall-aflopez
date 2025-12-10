# Assignment 7 - README

## Assignment 7-1: MyDequeList Integration

The implementation in `Assign07_01.java` uses the `MyDequeList` class from Assignment 04-02. No changes were required to the `MyDequeList` implementation itself. The deque is used in two different ways:

- **BFirstEnumerate**: Uses `MyDequeList` as a queue (FIFO) by enqueueing at rear (`renque$exn`) and dequeueing from front (`fdeque$raw`)
- **DFirstEnumerate**: Uses `MyDequeList` as a stack (LIFO) by enqueueing at front (`fenque$exn`) and dequeueing from front (`fdeque$raw`)

## Assignment 7-2: Game-of-24 Solver

### High-Level Algorithm Description

The Game-of-24 is solved by exploring a game tree where:

1. **Initial State**: Four numbers provided as input (e.g., 3, 3, 8, 8)

2. **State Representation**: Each state in the tree contains a list of terms (numbers or expressions)
   - Initial state: 4 TermInt nodes representing the 4 input numbers
   - Intermediate states: Fewer terms as numbers are combined
   - Goal state: Single term that evaluates to 24

3. **State Transitions (Children Generation)**:
   - For each state, generate children by:
     - Selecting any two terms from the current list
     - Applying each operator (+, -, *, /) to combine them
     - Creating a new state with the combined term plus remaining terms
   - For non-commutative operators (-, /), both orderings are tried (a-b and b-a)

4. **Search Strategy**:

   **DFS (Depth-First Search)**:
   - Uses `DFirstEnumerate` which explores deeply before backtracking
   - Implemented using `MyDequeList` as a stack (LIFO)
   - Processes children in reverse order to maintain proper traversal
   - Finds solutions by going deep into expression trees first

   **BFS (Breadth-First Search)**:
   - Uses `BFirstEnumerate` which explores level-by-level
   - Implemented using `MyDequeList` as a queue (FIFO)
   - Processes all states with N terms before moving to states with N-1 terms
   - Finds shortest expressions first

5. **Solution Detection**:
   - Filter states to find those with exactly one term
   - Check if that term evaluates to 24 (within 0.0001 tolerance for floating-point comparison)
   - Extract and return the term representing the solution

### Example Solution Path

For input (3, 3, 8, 8):
```
Initial: [3, 3, 8, 8]
    → Combine 8/3: [8/3, 3, 8]
        → Combine 3-(8/3): [3-(8/3), 8]
            → Combine 8/(3-(8/3)): [8/(3-8/3)]
                → Result: 24 ✓
```

### Key Design Decisions

- **FnGtree Interface**: GameState implements FnGtree to work with enumeration methods
- **Lazy Evaluation**: Uses LnStrm (lazy streams) to avoid computing all solutions upfront
- **Complete Search**: Both BFS and DFS find ALL valid solutions, not just the first one
- **Expression Tree**: Solutions are represented as Term objects that can be evaluated and printed