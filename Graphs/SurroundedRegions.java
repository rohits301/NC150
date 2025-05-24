class Solution {
    // refer STRIVER
    // T: O(n*m)
    // S: O(n*m); stack space at max
    // both DFS and BFS work
    // below is DFS
    /**
     * 1. Replace the 'O' with 'X' only if it is not connected to boundary.
     * 2. The connection to boundary can be checked if we run dfs from the boundary 'O's and mark the component they have as visited.
     * 3. This way, we can then visit all the univisted Os in the matrix using nested loop and convert them into X.
     */
    public static final int[][] directions = { { -1, 0 }, { 0, -1 }, { 0, 1 }, { 1, 0 } };

    public void solve(char[][] board) {
        int m = board.length;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n];

        for (int j = 0; j < n; j++) {
            // first row
            if (!visited[0][j] && board[0][j] == 'O') {
                dfs(0, j, m, n, board, visited);
            }
            // last row
            if (!visited[m - 1][j] && board[m - 1][j] == 'O') {
                dfs(m - 1, j, m, n, board, visited);
            }
        }

        for (int i = 0; i < m; i++) {
            // first col
            if (!visited[i][0] && board[i][0] == 'O') {
                dfs(i, 0, m, n, board, visited);
            }
            // last col
            if (!visited[i][n - 1] && board[i][n - 1] == 'O') {
                dfs(i, n - 1, m, n, board, visited);
            }
        }

        // convert unvisited 0 to X
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (!visited[i][j] && board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
            }
        }

    }

    private void dfs(int i, int j, int m, int n, char[][] board, boolean[][] visited) {

        if (i < 0 || i >= m || j < 0 || j >= n ||
            board[i][j] == 'X' || visited[i][j] == true) {
            return;
        }

        visited[i][j] = true;

        for (int[] d : directions) {
            int x = i + d[0];
            int y = j + d[1];
            dfs(x, y, m, n, board, visited);
        }
    }
}
