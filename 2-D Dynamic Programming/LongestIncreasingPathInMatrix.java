class Solution {
    // refer NEETCODE
    // BRUTE FORCE - RECURSION (TLE)
    // T: O(4^(m+n)); 
    // S: O(m*n); visited array and the stack space
    /**
     * 1. To find the longest increasing path, we need to explore all paths, hence, recursion.
     * 2. Standard dfs in matrix.
     * 3. Extra constraint -> matrix[x][y] > matrix[i][j] - for increasing path.
     * 4. `res` is 1 by default, because path from every `i,j` is atleast of length 1.
     */
    public static final int[][] directions = { { 0, -1 }, { -1, 0 }, { 0, 1 }, { 1, 0 } };

    public int longestIncreasingPath(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        boolean[][] visited = new boolean[m][n];
        int max = -1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (visited[i][j] == false) {
                    max = Math.max(max, dfs(i, j, matrix, visited));
                }
            }
        }
        return max;
    }

    private int dfs(int i, int j, int[][] matrix, boolean[][] visited) {

        int res = 1; // longest path from every i,j is atleast of length = 1.

        for (int d = 0; d < directions.length; d++) {
            int x = i + directions[d][0];
            int y = j + directions[d][1];

            if (x >= 0 && x < matrix.length &&
                y >= 0 && y < matrix[0].length &&
                visited[x][y] == false &&
                matrix[x][y] > matrix[i][j]) {

                visited[x][y] = true;
                res = Math.max(res, 1 + dfs(x, y, matrix, visited));
                visited[x][y] = false;
            }
        }
        return res;
    }
}

class Solution {
    // refer NEETCODE
    // BETTER - MEMOIZATION
    // T: O(m*n);
    // S: O(m*n); visited array and the stack space
    /**
     * 1. Store answer before returning.
     */
    public static final int[][] directions = { { 0, -1 }, { -1, 0 }, { 0, 1 }, { 1, 0 } };

    public int longestIncreasingPath(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        boolean[][] visited = new boolean[m][n];
        int max = -1;

        int[][] dp = new int[m][n];
        for(int[] ar: dp){
            Arrays.fill(ar, -1);
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (visited[i][j] == false) {
                    max = Math.max(max, dfs(i, j, matrix, visited, dp));
                }
            }
        }
        return max;
    }

    private int dfs(int i, int j, int[][] matrix, boolean[][] visited, int[][] dp) {

        if(dp[i][j] != -1){
            return dp[i][j];
        }
        int res = 1; // longest path from every i,j is atleast of length = 1.

        for (int d = 0; d < directions.length; d++) {
            int x = i + directions[d][0];
            int y = j + directions[d][1];

            if (x >= 0 && x < matrix.length &&
                y >= 0 && y < matrix[0].length &&
                visited[x][y] == false &&
                matrix[x][y] > matrix[i][j]) {

                visited[x][y] = true;
                res = Math.max(res, 1 + dfs(x, y, matrix, visited, dp));
                visited[x][y] = false;
            }
        }
        return dp[i][j] = res;
    }
}
