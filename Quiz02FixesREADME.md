## Quiz02_01 - Longest Ascending Subsequence Algorithm Fix
- **Issue**: Incorrect algorithm - built subsequence backwards from any position instead of leftmost from the start (instructor note: "However, the returned result seems incorrect!")
- **Fix**: Replaced forward DP approach with backward tailLens approach that computes longest subsequence from each position, then greedily selects leftmost valid indices from left to right, and added O(n²) time complexity explanation comment

## Quiz02_02 - Sorting Network Implementation Fix
- **Issue**: Used iterative quicksort with loops (instructor note: "Used loop (a special form of recursion)")
- **Fix**: Replaced stack-based quicksort with odd-even sorting network using compare-and-swap commands scaled for up to 1000 elements

## Quiz02_04 - Brief Explanation Format Fix
- **Issue**: Explanation was too verbose and not in proper format (instructor note: "I am looking for a brief argument")
- **Fix**: Condensed explanation to 3 lines matching HX comment style
