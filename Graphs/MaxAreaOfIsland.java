class Solution {
    // refer NEETCODE
    // DFS - FASTER
    // T: O(M*N)
    // S: O(M*N)
    /** 
     * we want the dfs to return us the maximum area of island
     * answer is max of all areas.
     */
  public static final int[][] directions = { { -1, 0 }, { 0, -1 }, { 0, 1 }, { 1, 0 } };

    public int maxAreaOfIsland(int[][] grid) {
        int area = 0;
        int m = grid.length;
        int n = grid[0].length;
        boolean[][] visited = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1 && visited[i][j] == false) {
                    area = Math.max(area, dfs(i, j, m, n, grid, visited));
                }
            }
        }
        return area;
    }

    private int dfs(int i, int j, int m, int n, int[][] grid, boolean[][] visited) {
        if (i < 0 || j < 0 || i >= m || j >= n || grid[i][j] == 0 || visited[i][j] == true) {
            return 0;
        }

        visited[i][j] = true; // Mark as visited PERMANENTLY for this overall island search

        int res = 1; // count of 1's in the island
        for (int[] d : directions) {
            int x = i + d[0];
            int y = j + d[1];
            res += dfs(x, y, m, n, grid, visited);
        }
        return res;
    }
}

class Solution {
    // refer NEETCODE
    // BFS APPROACH
    // T: O(m*n)
    // S: O(m*n)
    // Similar to DFS, for each island, we invoke a bfs
    /**
     * Cannot add all 1's together in the grid because each 1 might be a different island. We have to process each island separately in order to calculate its area.
     */
    public static final int[][] directions = { { -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 } };

    public int maxAreaOfIsland(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        int area = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1 && visited[i][j] == false) {
                    area = Math.max(area, bfs(i, j, m, n, grid, visited));
                }
            }
        }
        return area;
    }

    private int bfs(int i, int j, int m, int n, int[][] grid, boolean[][] visited) {
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[] { i, j });

        visited[i][j] = true;
        int res = 1;

        while (!q.isEmpty()) {
            int[] pair = q.poll();
            int r = pair[0];
            int c = pair[1];

            for (int[] d : directions) {
                int x = r + d[0];
                int y = c + d[1];
                if (x >= 0 && x < m && y >= 0 && y < n &&
                    grid[x][y] == 1 && visited[x][y] == false) {
                    q.offer(new int[] { x, y });
                    visited[x][y] = true;
                    res += 1;
                }
            }
        }
        return res;
    }
}
