## Assign03_01 - Tail Recursion Fix
- **Issue**: Function was not tail-recursive
- **Fix**: Added a helper function `f91_helper(n, count)` that uses an accumulator parameter to track the count, making all recursive calls happen in tail position

## Assign03_02 - Balanced Parentheses Implementation
- **Issue**: No implementation provided
- **Fix**: Implemented the `balencedq` method using a stack-based algorithm to check for balanced parentheses, brackets, and braces

## Assign03_03 - Typo Correction
- **Issue**: Typo in `transferRearToFront` method - variable name `rea1r` (with number 1) instead of `rear`
- **Fix**: Corrected line 45 to use `rear.isEmpty()` instead of `rea1r.isEmpty()`

## Assign05_01 - Compilation Classpath Issue
- **Issue**: Code does not compile (instructor note: "Did you test?")
- **Fix**: Code compiles and runs successfully when using the correct classpath: `javac -cp ".;./../Code/Library.jar" Assign05_01.java`
- **Verification**: Tested with 1,000,000 random integers - successfully sorted and verified

## Assign06_01 - Added Testing Code
- **Issue**: No testing code provided
- **Fix**: Added main method with tests for mergeLnStrm function
- **Additional Fix**: Corrected all field access from `.head`/`.tail` to method calls `.hd()`/`.tl()` to match LnStcn API
- **Verification**: Tests merge 3 streams and 2 streams successfully

## Assign06_02 - Added Testing Code and Fixed Stream Consumption Bug
- **Issue**: No testing code provided
- **Fix**: Added main method to test ramanujanNumbers stream
- **Additional Fix**: Corrected all field access from `.head`/`.tail` to method calls `.hd()`/`.tl()`
- **Additional Fix**: Fixed recursive call bug - changed from `ramanujanNumbers()` to `new LnStrm<>(() -> findRamanujan(next.tl()))` to properly continue from current position instead of restarting
- **Verification**: Successfully generates first 10 Ramanujan numbers: 1729, 4104, 20683, 32832, 64232, 65728, 110656, 110808, 134379, 149389

## Assign06_03 - Added Testing Code
- **Issue**: No testing code provided
- **Fix**: Added comprehensive testing in main method
- **Verification**: Tests basic sorting, 1M zeros edge case (as requested in assignment comments), and 10K random integers
