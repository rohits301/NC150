class Solution {
    // refer STRIVER
    // BRUTE FORCE
    // T: O(3^n); 3 choices whenever we encounter a "*"
    // S: O(n); stack space
    /**
     * The `dfs(i, count)` function recursively checks if the substring `s[i:]` can
     * form a valid parenthesis string, given a current open parenthesis `count`.
     *
     * How it works:
     * 1. State: `(i, count)` where `i` is the current index in `s`, and `count`
     * is the balance of open parentheses ('(' increments, ')' decrements).
     *
     * 2. Base Cases:
     * a. `if (count < 0)`: Invalid state (e.g., ')' before '('). Return `false`.
     * b. `if (i == s.length())`: End of string reached. Valid if `count == 0`
     * (all parentheses balanced). Return `(count == 0)`.
     *
     * 3. Recursive Steps for `s.charAt(i)`:
     * a. If `s.charAt(i) == '('`: Recurse with `dfs(i + 1, count + 1, ...)`.
     * b. If `s.charAt(i) == ')`: Recurse with `dfs(i + 1, count - 1, ...)`.
     * c. If `s.charAt(i) == '*'` (wildcard): This is the branching point.
     * The result (`res`) is true if any of these interpretations of '*' work:
     * - Treat '*' as '(': `dfs(i + 1, count + 1, ...)`
     * - Treat '*' as ')': `dfs(i + 1, count - 1, ...)`
     * - Treat '*' as empty: `dfs(i + 1, count, ...)`
     * The function returns `true` if any of these recursive calls return `true`.
     *
     * The initial call `dfs(0, 0, ...)` starts the process.
     */
    public boolean checkValidString(String s) {
        int n = s.length();
        return dfs(0, 0, s, n);
    }

    private boolean dfs(int i, int count, String s, int n) {
        if (count < 0) {
            // closing bracket came before opening, or '*' as ')' made count negative
            return false;
        }
        if (i == n) {
            // Reached end of string, valid if all open brackets are closed
            return (count == 0);
        }

        boolean res = false; // Stores the result for the current path

        if (s.charAt(i) == '(') {
            res = dfs(i + 1, count + 1, s, n);
        } else if (s.charAt(i) == ')') {
            res = dfs(i + 1, count - 1, s, n);
        } else { // s.charAt(i) == '*'
            // Explore three possibilities for '*'
            res = dfs(i + 1, count + 1, s, n) || // '*' as '('
                  dfs(i + 1, count - 1, s, n) || // '*' as ')'
                  dfs(i + 1, count, s, n);      // '*' as empty
        }

        return res;
    }
}

class Solution {
    // refer STRIVER
    // MEMOIZATION
    // T: O(n^2); dp array of size n*n
    // S: O(n^2) + O(n); dp array + stack space
    public boolean checkValidString(String s) {
        int n = s.length();
        int[][] dp = new int[n + 1][n + 1];
        for (int[] ar : dp) {
            Arrays.fill(ar, -1);
        }
        return dfs(0, 0, s, n, dp);
    }

    private boolean dfs(int i, int count, String s, int n, int[][] dp) {
        if (count < 0) {
            return false;
        }
        if (i == n) {
            return (count == 0);
        }

        if (dp[i][count] != -1) {
            return (dp[i][count] == 0) ? false : true;
        }
        boolean res = false; 

        if (s.charAt(i) == '(') {
            res = dfs(i + 1, count + 1, s, n, dp);
        } else if (s.charAt(i) == ')') {
            res = dfs(i + 1, count - 1, s, n, dp);
        } else { 
            res = dfs(i + 1, count + 1, s, n, dp) || 
                    dfs(i + 1, count - 1, s, n, dp) || 
                    dfs(i + 1, count, s, n, dp); 
        }

        dp[i][count] = (res == false) ? 0 : 1;
        return res;
    }
}

class Solution {
    // refer STRIVER
    // TABULATION
    // T: O(n^2); dp array of size n*n
    // S: O(n^2); dp array
    /**
     * 1. Copy the base case
     * 2. Number of nested loops = number of changing parameters.
     * 3. Iterate in opposite order of recursion
     * 4. Copy the recurrence
     * 5. Answer at same indices as of recursion invocation.
     * NOTE: Added boundary checks for count < 0 and count == n+1
     */
    public boolean checkValidString(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n + 1][n + 1];
        // base case
        dp[n][0] = true;

        for (int i = n - 1; i >= 0; i--) {
            for (int count = 0; count <= n; count++) {
                boolean res = false;

                if (s.charAt(i) == '(') {
                    if (count + 1 <= n) {
                        res = dp[i + 1][count + 1];
                    }
                } else if (s.charAt(i) == ')') {
                    if (count - 1 >= 0) {
                        res = dp[i + 1][count - 1];
                    }
                } else {
                    boolean open = false;
                    if (count + 1 <= n) {
                        open = dp[i + 1][count + 1];
                    }
                    boolean close = false;
                    if (count - 1 >= 0) {
                        close = dp[i + 1][count - 1];
                    }
                    boolean empty = dp[i + 1][count];

                    res = open || close || empty;
                }

                dp[i][count] = res;
            }
        }
        return dp[0][0];
    }
}

class Solution {
    // refer STRIVER
    // TABULATION - SPACE OPTIMIZATION
    // T: O(n^2); dp array of size n*n
    // S: O(n); 1D dp array
    /**
     * 1. SPACE OPTIMIZATION is possible since `i` depends on `i+1`.
     * 2. Since, we iterate from back, i+1 -> prev, i -> curr
     * 3. dp[i+1] -> prev, dp[i] -> curr.
     * 4. Loop starts from `i=n-1`, so `prev` is `i=n`
     * 5. Hence, base case will be on `prev`.
     */
    public boolean checkValidString(String s) {
        int n = s.length();
        boolean[] prev = new boolean[n + 1];
        boolean[] curr = new boolean[n + 1];
        // base case
        prev[0] = true;

        for (int i = n - 1; i >= 0; i--) {
            for (int count = 0; count <= n; count++) {
                boolean res = false;

                if (s.charAt(i) == '(') {
                    if (count + 1 <= n) {
                        res = prev[count + 1];
                    }
                } else if (s.charAt(i) == ')') {
                    if (count - 1 >= 0) {
                        res = prev[count - 1];
                    }
                } else {
                    boolean open = false;
                    if (count + 1 <= n) {
                        open = prev[count + 1];
                    }
                    boolean close = false;
                    if (count - 1 >= 0) {
                        close = prev[count - 1];
                    }
                    boolean empty = prev[count];

                    res = open || close || empty;
                }

                curr[count] = res;
            }
            boolean[] temp = prev;
            prev = curr;
            curr = temp;
        }
        return prev[0];
    }
}

class Solution {
    // refer STRIVER
    // OPTIMAL - GREEDY
    // T: O(n);
    // S: O(1);
    /**
     * 1. Keeping the approach similar to BRUTE FORCE, instead of maintaing count, we maintain a range min and max.
     * 2. In the count approach, if count became -1 (count<0), that indicated we had a closing bracket added to a balanced string, so the
     * answer was false.
     * Second observation - the values ranged in [-1,0,1].
     * -1 for a closing
     * 0 for empty
     * 1 for opening
     * 3. Hence, here our range min and max will vary accordingly.
     * 4. Algorithm Explanation:
     * This greedy approach iterates through the string, maintaining a possible range
     * [min_open, max_open] for the count of open parentheses.
     * - `min_open`: Minimum possible open parentheses if '*' are used optimally to close (as ')').
     * - `max_open`: Maximum possible open parentheses if '*' are used optimally to open (as '(').
     *
     * Character effects:
     * - '(': Both min_open and max_open increment.
     * - ')': Both min_open and max_open decrement.
     * - '*': min_open can decrement (treat '*' as ')'), max_open can increment (treat '*' as '(').
     * (Effectively, '*' broadens the possible range of open counts).
     *
     * Validation checks are performed after each character.
     * The string is valid if max_open never drops below 0, and min_open is 0 at the end.
     *
     * 5. Why `min_open` is reset to 0 if `min_open < 0`:
     * min cannot be less than 0. If it is, it means a '*' was used as ')'
     * unnecessarily, so we can treat that '*' as empty instead.
     * This ensures min tracks the "necessary" open brackets.
     *
     * 6. Why return `false` if `max_open < 0`:
     * If max becomes negative at any point, it means we have too many ')'
     * that cannot be balanced even if all '*' were '('.
     *
     * 7. For the string to be valid, all open brackets must be closable.
     * The minimum number of open brackets that must exist at the end should be 0.
     */
    public boolean checkValidString(String s) {
        int min = 0; // min_open: Minimum possible count of open parentheses needed
        int max = 0; // max_open: Maximum possible count of open parentheses present

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                min++;
                max++;
            } else if (c == ')') {
                min--;
                max--;
            } else { // c == '*'
                // If '*' is ')', min decreases. If '*' is '(', min could increase or stay (if it was already open due to real '(').
                // Effectively, '*' as ')' helps reduce 'min'.
                min--;
                // If '*' is '(', max increases.
                max++;
            }

            // If max becomes negative at any point, it means we have too many ')'
            // that cannot be balanced even if all '*' were '('.
            if (max < 0) {
                return false;
            }
            // min cannot be less than 0. If it is, it means a '*' was used as ')'
            // unnecessarily, so we can treat that '*' as empty instead.
            // This ensures min tracks the "necessary" open brackets.
            if (min < 0) {
                min = 0;
            }
        }
        // For the string to be valid, all open brackets must be closable.
        // The minimum number of open brackets that must exist at the end should be 0.
        return min == 0;
    }
}
