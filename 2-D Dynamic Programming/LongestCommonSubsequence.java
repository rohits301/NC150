class Solution {
    // refer STRIVER for all solutions
    // BRUTE FORCE
    // T: O(2^n * 2^m) - exponential; comparing all subsequences of both strings
    // n = text1.length(), m = text2.length()
    // S: O(n + m); max. depth of recursion tree
    /*
     * 1. Generating all subsequences and comparing them via a linear comparison is not feasible.
     * 2. So, we try to generate the subsequences and compare them along the way.
     * 3. Instead of generating different strings, we will express them in terms of indices and consider the strings via indices.
     * 4. We need two indices, one for each string.
     * 5. We start from back, can start from starting as well, no particular reason to start from back.
     * The string under consideration `dfs(text1, i, text2, j)` -> `text1 (0 to i), text2 (0 to j)`
     * 6. Match -> increase length as matching character and consider the rest of the strings
     * 7. Not match -> maximum of `(i-1, j) and (i, j-1)` - to ensure both possibilities are considered
     * 8. Base case: when no string left to compare, i.e., `i<0 || j<0`
     */
    public int longestCommonSubsequence(String text1, String text2) {
        int n = text1.length();
        int m = text2.length();
        return dfs(n-1, m-1, text1, text2);
    }

    private int dfs(int i, int j, String text1, String text2){
        // base case
        // for -ve indices,cannot compare strings, hence, lcs = 0
        if(i < 0 || j < 0){
            return 0;
        }

        // every call returns the lcs of text1[0..i] & text2[0..j]
        if(text1.charAt(i) == text2.charAt(j)){
            return 1 + dfs(i-1, j-1, text1, text2);
        }

        // the split call
        return Math.max(dfs(i-1, j, text1, text2), dfs(i, j-1, text1, text2));
    }
}

class Solution {
    // refer STRIVER for all solutions
    // BETTER
    // TOP-DOWN (MEMOIZATION)
    // T: O(n*m); comparing all subsequences of both strings
    // n = text1.length(), m = text2.length()
    // S: O(n*m) + O(n + m); max. depth of recursion tree is n+m (Aux. stack space), n*m = space of dp array
    public int longestCommonSubsequence(String text1, String text2) {
        int n = text1.length();
        int m = text2.length();
        int[][] dp = new int[n][m];
        for(int[] ar: dp){
            Arrays.fill(ar, -1);
        }
        return dfs(n-1, m-1, text1, text2, dp);
    }

    private int dfs(int i, int j, String text1, String text2, int[][]dp){
        // base case
        // for -ve indices,cannot compare strings, hence, lcs = 0
        if(i < 0 || j < 0){
            return 0;
        }

        if(dp[i][j] != -1){
            return dp[i][j];
        }
        // every call returns the lcs of text1[0..i] & text2[0..j]
        if(text1.charAt(i) == text2.charAt(j)){
            return dp[i][j] = 1 + dfs(i-1, j-1, text1, text2, dp);
        }

        // the split call
        return dp[i][j] = Math.max(dfs(i-1, j, text1, text2, dp), dfs(i, j-1, text1, text2, dp));
    }
}

class Solution {
    // refer STRIVER for all solutions
    // BETTER dp
    // BOTTOM-UP (TABULATION)
    // T: O(n*m); comparing all subsequences of both strings
    // n = text1.length(), m = text2.length()
    // S: O(n*m); n*m = size of dp array
    /*
     * 1. dp[i][j] represents LCS of text1[0...i], text2[0...j]
     * 2. the base case of recursion was for -ve indices
     * 3. in the array, since, we don't have -ve indices
     * 4. it can be simulated by shifting the indices to the right
     * 5. => we consider string from 1 to n, 1 to m (instead of 0)
     * 6. => the base case translates to the zero indices of the dp[][]
     */
    public int longestCommonSubsequence(String text1, String text2) {
        int n = text1.length();
        int m = text2.length();
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 0; i <= n; i++) {
            dp[i][0] = 0;
        }
        for (int j = 0; j <= m; j++) {
            dp[0][j] = 0;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        // the answer will be on dp[n][m] where we consider the entire string
        return dp[n][m];
    }

}

class Solution {
    // refer STRIVER for all solutions, neetcode has proper code
    // OPTIMAL dp
    // SPACE-OPTIMIZED (TABULATION)
    // T: O(n*m); comparing length of all subsequences of both strings
    // n = text1.length(), m = text2.length()
    // S: O(m); 
    public int longestCommonSubsequence(String text1, String text2) {
        int n = text1.length();
        int m = text2.length();
        int[] prev = new int[m+1];
        int[] curr = new int[m+1];
        // building upon the tabulation solution
        // we need only previous row for the answer
        // when text1[i] == text2[j] -> dp[i-1][j-1] has the ans.
        // when text1[i] != text2[j] -> max(dp[i-1][j], dp[i][j-1]) has the ans.
        // so we need track of only dp[i-1] i.e. previous row
        // number of columns = m in our case
        // current row -> curr[], previous row -> prev[]
        
        // base case, the previous row is 0 (similar to -ve indices)
        // below code not requires as JAVA has 0 as default value in arrays
        // for (int j = 0; j <= m; j++) {
        //     prev[j] = 0;
        //     curr[j] = 0;
        // }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    curr[j] = 1 + prev[j - 1]; // prev is already i-1
                } else {
                    curr[j] = Math.max(prev[j], curr[j - 1]);
                }
            }
            // VERY IMP. STEP
            // MISSED BY STRIVER AS CODED IN C++
            // IF WE DON'T SWAP THE ARRAYS & SIMPLY ASSIGN
            // THEY POINT TO SAME MEMORY LOCATION 
            int[] temp = prev;
            prev = curr;
            curr = temp;
        }
        // the answer will in prev[m] as after last iteration
        // prev holds the most recent ans.
        return prev[m];
    }

}
