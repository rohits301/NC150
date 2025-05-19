class Solution {
    // refer NEETCODE - code and explanation
    // BRUTE FORCE - RECURSION - TLE
    // T: O(2^(n+m));
    // S: O(n+m);
    /**
     * 1. Problem - If we can form s3 by concatenating substrings of s1 and s2.
     * So, length of s3 should be = len(s1) + len(s2).
     * 2. Either current character, `s3[i+j]` matches with `s1[i]`or with `s2[j]`. So, we have two decisions, hence, explore all possibilities. Hence, RECURSION.
     * 3. If s1[i] == s3[i+j], make sure `i` is valid and explore this path. 
     * 4. If s2[j] == s3[i+j], make sure `j` is valid and explore this path.
     * 5. If either s1 or s2 matches, result is true.
     * 6. Base case: 
     * If both s1 and s2 are finished, then we have succesfully formed s3 from s1 and s2, so, return true.
     * NOTE: we check i < s1.length() every time before comparison because it is possible, i has reached end of length and that makes the s1[i] inaccessible. 
     * Similarly, for `j`. 
     */
    public boolean isInterleave(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }
        return dfs(0, 0, s1, s2, s3);
    }

    private boolean dfs(int i, int j, String s1, String s2, String s3) {
        if (i == s1.length() && j == s2.length()) {
            return true;
        }

        boolean matchS1 = false;
        if (i < s1.length() && s1.charAt(i) == s3.charAt(i + j)) {
            matchS1 = dfs(i + 1, j, s1, s2, s3);
        }

        boolean matchS2 = false;
        if (j < s2.length() && s2.charAt(j) == s3.charAt(i + j)) {
            matchS2 = dfs(i, j + 1, s1, s2, s3);
        }

        boolean res = (matchS1 || matchS2);
        return res;
    }
}

class Solution {
    // refer NEETCODE - code and explanation
    // BETTER - MEMOIZATION
    // T: O(n*m);
    // S: O(n*m) + O(n+m);
    /**
     * 1. Take int[][] dp instead of boolean as we need three states - true(1), false(0) and unvisited(-1).
     */
    public boolean isInterleave(String s1, String s2, String s3) {
        int n = s1.length();
        int m = s2.length();
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }

        int[][] dp = new int[n + 1][m + 1];
        for (int[] ar : dp) {
            Arrays.fill(ar, -1);
        }
        return dfs(0, 0, s1, s2, s3, dp);
    }

    private boolean dfs(int i, int j, String s1, String s2, String s3, int[][] dp) {
        if (i == s1.length() && j == s2.length()) {
            return true;
        }

        if (dp[i][j] != -1) {
            return (dp[i][j] == 1) ? true : false;
        }

        boolean matchS1 = false;
        if (i < s1.length() && s1.charAt(i) == s3.charAt(i + j)) {
            matchS1 = dfs(i + 1, j, s1, s2, s3, dp);
        }

        boolean matchS2 = false;
        if (j < s2.length() && s2.charAt(j) == s3.charAt(i + j)) {
            matchS2 = dfs(i, j + 1, s1, s2, s3, dp);
        }

        boolean res = (matchS1 || matchS2);
        dp[i][j] = (res == true) ? 1 : 0;
        return res;
    }
}

class Solution {
    // refer NEETCODE - code and explanation
    // BETTER - TABULATION
    // T: O(n*m);
    // S: O(n*m);
    /**
     * 1. Tabulation - same steps to convert
     * 2. Change - we copy the recurrence as it is.
     * 3. We cannot remove the shorten the loop to `i=n-1 and j=m-1` because we have to compute dp[i][m] and dp[n][j] - the edge cases.
     * 4. These are missed if we shorten the loop to i=n-1 and j=m-1.
     * 5. Also, when we are starting i from n, then for i=n, s1 is not valid, so we need the `if` check to validate s1 before comparing `s1` and `s3`. Similarly, for `j=m`.
     */
    public boolean isInterleave(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }
        int n = s1.length();
        int m = s2.length();

        boolean[][] dp = new boolean[n + 1][m + 1];

        for (int i = n; i >= 0; i--) {
            for (int j = m; j >= 0; j--) {
                if (i == n && j == m) {
                    dp[i][j] = true; // base case
                } else {
                    boolean matchS1 = false, matchS2 = false;
                    if (i < n && s1.charAt(i) == s3.charAt(i + j)) {
                        matchS1 = dp[i + 1][j];
                    }
                    if (j < m && s2.charAt(j) == s3.charAt(i + j)) {
                        matchS2 = dp[i][j + 1];
                    }
                    dp[i][j] = (matchS1 || matchS2);
                }
            }
        }
        return dp[0][0];
    }
}

class Solution {
    // refer NEETCODE - code and explanation
    // OPTIMAL - TABULATION (SPACE OPTIMIZATION)
    // T: O(n*m);
    // S: O(m);
    /**
     * 1. prev -> dp[i+1], curr -> dp[i]
     */
    public boolean isInterleave(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }
        int n = s1.length();
        int m = s2.length();

        boolean[] prev = new boolean[m+1];
        boolean[] curr = new boolean[m+1];

        for (int i = n; i >= 0; i--) {
            for (int j = m; j >= 0; j--) {
                if (i == n && j == m) {
                    curr[j] = true; // base case
                } else {
                    boolean matchS1 = false, matchS2 = false;
                    if (i < n && s1.charAt(i) == s3.charAt(i + j)) {
                        matchS1 = prev[j];
                    }
                    if (j < m && s2.charAt(j) == s3.charAt(i + j)) {
                        matchS2 = curr[j + 1];
                    }
                    curr[j] = (matchS1 || matchS2);
                }
            }
            boolean[] temp = prev;
            prev = curr;
            curr = temp;
        }
        return prev[0];
    }
}
