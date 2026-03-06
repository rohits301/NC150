class Solution {
    // refer STRIVER DP 33.
    // BRUTE FORCE - RECURSION (TLE)
    // T: O(3^(n+m)); Exponential
    // S: O(n+m);
    /**
     * 1. With the given operations, it is always possible to convert word1 to word2.
     * 2. To find min. operations, we need to explore all operations. Hence, RECURSION.
     * 3. Take example and analyse all three operations - 
     * a) Insert -> `i` remains as it is, insertion happens at `i+1` (hypothetical) and this matches with `j`. So dfs(i,j-1), since `i+1` matches with `j`.
     * b) Delete -> we delete at i, we match the rest of the word1 with `j` -> dfs(i-1,j).
     * c) Replace -> we replace the character at i with the matching character, so now match the rest of the words -> dfs(i-1,j-1).
     * 4. If characters, match, no need for any operation, return directly.
     * 5. Result is the minimum of all operations.
     * 6. Base case: 
     * a) If word1 is exhausted, we have only one option, insert the `j` characters, that is, the remaining characters. So, `j` operations required.
     * b) If word2 is exhausted, we have only one option, delete the `i` characters and then the strings will match.
     * so, `i` operations required.
     */
    public int minDistance(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();
        return dfs(n - 1, m - 1, word1, word2);
    }

    private int dfs(int i, int j, String word1, String word2) {
        if (i < 0) {
            return j + 1;
        }
        if (j < 0) {
            return i + 1;
        }

        if (word1.charAt(i) == word2.charAt(j)) {
            return dfs(i - 1, j - 1, word1, word2);
        }

        int insert = 1 + dfs(i, j - 1, word1, word2);
        int delete = 1 + dfs(i - 1, j, word1, word2);
        int replace = 1 + dfs(i - 1, j - 1, word1, word2);

        return Math.min(insert, Math.min(delete, replace));
    }
}

class Solution {
    // refer STRIVER DP 33.
    // BETTER - MEMOIZATION
    // T: O(n*m); 
    // S: O(n*m) + O(n+m); dp array + auxiliary stack space
    /**
     * 1. Change to 1-index DP because we cannot map the i<0 in array.
     * 2. Invocation -> n to 0, m to 0
     * 3. String indices reduces by 1.
     * 4. Base case -> no need to add 1 in 1-based indexing.
     */
    public int minDistance(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int[] ar : dp) {
            Arrays.fill(ar, -1);
        }
        return dfs(n, m, word1, word2, dp);
    }

    private int dfs(int i, int j, String word1, String word2, int[][] dp) {
        if (i == 0) {
            return j;
        }
        if (j == 0) {
            return i;
        }

        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
            return dp[i][j] = dfs(i - 1, j - 1, word1, word2, dp);
        }

        int insert = 1 + dfs(i, j - 1, word1, word2, dp);
        int delete = 1 + dfs(i - 1, j, word1, word2, dp);
        int replace = 1 + dfs(i - 1, j - 1, word1, word2, dp);

        return dp[i][j] = Math.min(insert, Math.min(delete, replace));
    }
}

class Solution {
    // refer STRIVER DP 33.
    // BETTER - TABULATION
    // T: O(n*m); 
    // S: O(n*m);
    /**
     * 1. Base case: directly copy for this ques.
     * 2. Number of changing parameteres = Number of nested loops.
     * 3. Iterate in opposite order of recursion.
     * 4. Answer at same indices as invocation of recursion.
     */
    public int minDistance(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();
        int[][] dp = new int[n + 1][m + 1];

        // base case
        for (int j = 0; j <= m; j++) {
            dp[0][j] = j;
        }
        for (int i = 0; i <= n; i++) {
            dp[i][0] = i;
        }

        // recurrence
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    int insert = 1 + dp[i][j-1];
                    int delete = 1 + dp[i-1][j];
                    int replace = 1 + dp[i-1][j-1];

                    dp[i][j] = Math.min(insert, Math.min(delete, replace));
                }
            }
        }
        return dp[n][m];
    }
}

class Solution {
    // refer STRIVER DP 33.
    // OPTIMAL - TABULATION (SPACE OPTIMIZED)
    // T: O(n*m); 
    // S: O(m);
    /**
     * 1. Since, dp[i] is dependent on dp[i-1], we can space optimize.
     * dp[i] -> curr, dp[i-1] -> prev
     * 2. Base case -> 
     * a) for the first row, we want all values to be equal to `j`.
     * So, for i=1, i=0, should have all values as `j`. Hence,
     * `prev[j] = j`.
     * b) for the first column, we want all values to be equal to `i`.
     * So, for dp[i][0] = i, so every `curr` should be `i`.
     * Do this inside loop, because `curr` is updated everytime.
     */
    public int minDistance(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();
        int[] prev = new int[m + 1];
        int[] curr = new int[m + 1];

        // base case
        for (int j = 0; j <= m; j++) {
            prev[j] = j;
        }

        // recurrence
        for (int i = 1; i <= n; i++) {
            curr[0] = i;
            for (int j = 1; j <= m; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    curr[j] = prev[j - 1];
                } else {
                    int insert = 1 + curr[j - 1];
                    int delete = 1 + prev[j];
                    int replace = 1 + prev[j - 1];

                    curr[j] = Math.min(insert, Math.min(delete, replace));
                }
            }
            int[] temp = prev;
            prev = curr;
            curr = temp;
        }
        return prev[m];
    }
}
