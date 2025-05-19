class Solution {
    // refer STRIVER DP 32.
    // BRUTE FORCE - RECURSION (TLE)
    // T: O(2^n); exponential, 2^(min(n,m));
    // S: O(n+m); auxiliary stack space
    /**
     * 1. The brute force approach to achieve is we can compare characters of `t` with that of `s` but we have to do it in different ways. E.g. s = "babgbag", t = "bag".
     * First - compare ending "g" of `s` with "g" in `t`
     * Second - compare middle "g" of `s` with "g" in `t`.
     * Hence, we are exploring all possibilities, so use RECURSION.
     * 2. DP on strings ->
     * a) Express everything in terms of index - `i` and `j`.
     * b) Explore all possibilities - match and notMatch
     * c) Write the recurrence formula
     * d) Base case - when does the process stop
     * 3. We start from i = `n-1` and j = `m-1`.
     * 4. Two cases - 
     * a) `s[i]` matches with `t[j]`, either we pick this subsequence or we don't. 
     * If we pick we shift and `i` and `j`
     * If we don't, we shift `i` only and will match `j` with some other `i` in future.
     * b) `s[i]` doesn't match with `t[j]`. So, shift `i`. Maybe some future `i` could match.
     * 5. Base case:
     * a) If there are no more characters left in `i`. Stop as cannot match now. Return `0`, because we didn't find match because `i` is finished but `j` is remaining.
     * b) If `j` is finished (-1), then, we return 1 because all characters have matched.
     * 6. Result is the total number of distinct subsequences when `s[i]` and `t[j]` match and not match.
     * NOTE: dfs(i,j) -> number of distinct subsequences of string s[0...i] that are equal to string t[0...j].
     */
    public int numDistinct(String s, String t) {
        int n = s.length();
        int m = t.length();
        return dfs(n - 1, m - 1, s, t);
    }

    private int dfs(int i, int j, String s, String t) {
        // base case
        if (j < 0) {
            return 1;
        }
        if (i < 0) {
            return 0;
        }

        int match = 0, notMatch = 0;
        if (s.charAt(i) == t.charAt(j)) {
            match = dfs(i - 1, j - 1, s, t) + dfs(i - 1, j, s, t);
        } else {
            notMatch = dfs(i - 1, j, s, t);
        }
        return (match + notMatch);
    }
}

class Solution {
    // refer STRIVER DP 32.
    // BETTER - MEMOIZATION
    // T: O(n*m); 
    // S: O(n*m) + O(n+m); dp array + auxiliary stack space
    /**
     * 1. Two parameters - i and j, so 2D array.
     * 2. Slight change - 
     * In recursion, we follow 0-based indexing and the base case is when the indices become -ve. But, in dp[][] we are not storing -ve indices. 
     * Hence, we shift to simulate this.
     * Shift each index by +1.
     * 3. Size becomes (n+1)*(m+1).
     * 4. invocation for (n,m).
     * 5. Base case changes -> `i==0` and `j==0`
     * 6. Recurrence -> formula remains same
     * only string comparison indices change.
     */
    public int numDistinct(String s, String t) {
        int n = s.length();
        int m = t.length();
        int[][] dp = new int[n+1][m+1];
        for(int[] ar: dp){
            Arrays.fill(ar, -1);
        }
        return dfs(n, m, s, t, dp);
    }

    private int dfs(int i, int j, String s, String t, int[][] dp) {
        // base case
        if (j == 0) {
            return 1;
        }
        if (i == 0) {
            return 0;
        }

        if(dp[i][j] != -1){
            return dp[i][j];
        }

        int match = 0, notMatch = 0;
        if (s.charAt(i - 1) == t.charAt(j - 1)) {
            match = dfs(i - 1, j - 1, s, t, dp) + dfs(i - 1, j, s, t, dp);
        } else {
            notMatch = dfs(i - 1, j, s, t, dp);
        }
        return dp[i][j] = (match + notMatch);
    }
}

class Solution {
    // refer STRIVER DP 32.
    // BETTER - TABULATION
    // T: O(n*m); 
    // S: O(n*m); dp array
    /**
     * Convert to Tabulation - 
     * 1. Copy the base case. 
     * Ordering of changes is important. E.g. here, we first set the base case for `i==0` and then for `j==0` to avoid `dp[i][0]` being overwritten by the result of `i==0`.
     * 2. Number of changing parameters = number of nested loops.
     * 3. Iterate in opposite order of recursion.
     * 4. Copy the recurrence.
     * 5. Result at the same indices as that of recursion invocation.
     */
    public int numDistinct(String s, String t) {
        int n = s.length();
        int m = t.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int j = 0; j <= m; j++) {
            dp[0][j] = 0; // first row
        }
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 1; // first column
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int match = 0, notMatch = 0;
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    match = dp[i - 1][j - 1] + dp[i - 1][j];
                } else {
                    notMatch = dp[i - 1][j];
                }
                dp[i][j] = (match + notMatch);
            }
        }
        return dp[n][m];
    }
}

class Solution {
    // refer STRIVER DP 32.
    // OPTIMAL - SPACE OPTIMIZATION (1)
    // T: O(n*m); 
    // S: O(2*m); two arrays
    /**
     * 1. possible because we are using `i` and `i-1`.
     * 2. curr -> dp[i], prev -> dp[i-1]
     * 3. First base case is redundant, so remove, as 0 by default in java.
     * 4. match and notMatch can be replaced by simple variables, we can assign the result to `curr[j]` directly as only one out of `if` and `else` will be executed at a time.
     * 5. Swap prev and curr after inner loop.
     */
    public int numDistinct(String s, String t) {
        int n = s.length();
        int m = t.length();
        int[] prev = new int[m + 1];
        int[] curr = new int[m + 1];
        prev[0] = 1; // base case
        curr[0] = 1; // base case

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    curr[j] = prev[j - 1] + prev[j];
                } else {
                    curr[j] = prev[j];
                }
            }
            int[] temp = prev;
            prev = curr;
            curr = temp;
        }
        return prev[m];
    }
}

class Solution {
    // ???
    // refer STRIVER DP 32.
    // OPTIMAL - SPACE OPTIMIZATION (2)
    // T: O(n*m); 
    // S: O(m); one array
    /**
     * 1. can trim this down further to only one array.
     * 2. Because, we are only reliant on the previous state for the result.
     * BUT, IMP. NOTE: avoid blindly remove conditions.
     * here, if we go with forward loop of `j`, we get WRONG ANSWER.
     * This is because, prev[j] = prev[j-1]+ prev[j] translates to ->
     dp[i][j] = dp[i][j-1] + dp[i-1][j]; because of update in ith iteration.
     * 3. Solution: Use a backward loop
     * 4. Similar to 01 knapsack 1D array optimization.
     */
    public int numDistinct(String s, String t) {
        int n = s.length();
        int m = t.length();
        int[] prev = new int[m + 1];
        prev[0] = 1; // base case

        for (int i = 1; i <= n; i++) {
            for (int j = m; j >= 1; j--) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    prev[j] = prev[j - 1] + prev[j];
                }
            }
        }
        return prev[m];
    }
}
