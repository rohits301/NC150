class Solution {
    // refer NEETCODE
    // BRUTE FORCE - TLE
    // T: O(2^(m+n))
    // S: O(m+n); stack space
    // backtracking not required, we are always moving forward
    // no backtracking in recursion tree as well.
    // we don't need visited[][] here, because no same block will be visited twice
    // since we are moving only down and right.
    public int uniquePaths(int m, int n) {
        return dfs(0, 0, m, n);
    }

    private int dfs(int i, int j, int m, int n) {
        // base case, we've reached the destination
        if (i == (m - 1) && j == (n - 1)) {
            return 1;
        }

        // invalid position check
        if (i >= m || j >= n) {
            return 0;
        }

        int res = 0;
        res += dfs(i + 1, j, m, n); // down
        res += dfs(i, j + 1, m, n); // right

        return res; // total ways
    }

}

class Solution {
    // refer NEETCODE
    // BETTER
    // TOP-DOWN (MEMOIZATION)
    // T: O(m*n)
    // S: O(m*n) + O(m+n); dp size + stack space
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        for (int[] ar : dp) {
            Arrays.fill(ar, -1);
        }
        return dfs(0, 0, m, n, dp);
    }

    private int dfs(int i, int j, int m, int n, int[][] dp) {
        // base case, we've reached the destination
        if (i == (m - 1) && j == (n - 1)) {
            return 1;
        }

        // invalid position check
        if (i >= m || j >= n) {
            return 0;
        }
        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        int res = 0;
        res += dfs(i + 1, j, m, n, dp); // down
        res += dfs(i, j + 1, m, n, dp); // right

        return dp[i][j] = res;
    }

}

class Solution {
    // refer NEETCODE
    // BETTER
    // BOTTOM-UP (TABULATION)
    // T: O(m*n)
    // S: O(m*n); dp size
    public int uniquePaths(int m, int n) {
        // in order to avoid managing the out-of-bounds
        // for last row and last column separately
        // we make dp[][] of row+1, col+1 size
        int[][] dp = new int[m + 1][n + 1];
        for (int i = m - 1; i >= 0; i--) {
            // Iterate j from n-1 (last col of actual grid) down to 0 (first col)
            for (int j = n - 1; j >= 0; j--) {
                if (i == m - 1 && j == n - 1) {
                    // Base Case: We are at the destination cell. There is 1 path.
                    dp[i][j] = 1;
                } else {
                    // Number of paths from (i,j) is sum of:
                    // paths from cell below (i+1, j) -> dp[i+1][j]
                    // paths from cell to the right (i, j+1) -> dp[i][j+1]
                    // If i+1 = m, dp[i+1][j] is dp[m][j] which is 0 (correct).
                    // If j+1 = n, dp[i][j+1] is dp[i][n] which is 0 (correct).
                    dp[i][j] = dp[i + 1][j] + dp[i][j + 1];
                }
            }
        }
        return dp[0][0];
    }

}

class Solution {
    // refer NEETCODE
    // BETTER - SPACE OPTIMIZED TABULATION
    // T: O(m*n) - Each cell's logic is computed once.
    // S: O(n)   - Using two 1D arrays of size n (or n+1).
    /**
     * 1. We only need the previous row state (dp[i+1][j]) to calculate dp[i][j].
     * - `prev`: previously computed row (`dp[i+1]`).
     * - `curr`: current row `i` (`dp[i]`).
     * 2. Base Case Handling:
     * The base case `dp[m-1][n-1] = 1` is handled when `i = m-1` and `j = n-1`
     * by setting `curr[j] = 1`.
     * 3. Row Update:
     * After the inner loop (for `j`) completes and the entire `curr` row (for `dp[i]`)
     * is computed, `curr` becomes the `prev` row for the next iteration. 
     * The array previously holding `prev` values can be reused for the new `curr` values. 
     * This is achieved by swapping the `prev` and `curr` array references.
     * 4. Final Result:
     * After all rows are processed, the loop for i=0 computes dp[0][...] into 'curr',
     * then 'prev' is updated to point to this 'curr'.
     * So, prev[0] holds dp[0][0].
     */
    public int uniquePaths(int m, int n) {
        // Size n+1 allows curr[n] to act as a 0-boundary for curr[j+1] when j=n-1
        int[] prev = new int[n + 1];
        int[] curr = new int[n + 1];
        
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (i == m - 1 && j == n - 1) {
                    // Base Case: At the destination cell, there is 1 path.
                    curr[j] = 1;
                } else {
                    curr[j] = prev[j] + curr[j + 1];
                }
            }
            // After the current row 'i' is computed in 'curr',
            // 'curr' becomes 'prev' for the next iteration (row 'i-1').
            // The array that 'prev' was pointing to is now used for 'curr'.
            int[] temp = prev;
            prev = curr;
            curr = temp;
            // For the next iteration, 'curr' (which now points to the old 'prev' array)
            // will be entirely overwritten, so its old values don't interfere.
        }
        return prev[0];
    }
}
