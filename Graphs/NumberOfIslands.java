class Solution {
    // refer NEETCODE
    // can use both DFS, BFS
    // DFS
    // T: O(m*n)
    // S: O(m*n)
    /**
     * 1. We invoke DFS from every '1' and check how far it goes by traversing to the cells
     * which have value '1'. 
     * 2. Since, we can visit the same cell twice, to avoid that, we maintain a `visited[][]` boolean array.
     * 3. The number of times we invoke DFS is the number of islands.
     * 4. Each island is identified when we invoke a DFS.
     * 5. More saving is achieved by invoking the DFS only through cells which are not visited yet.
     */
    public static final int[][] directions = { { -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 } };

    public int numIslands(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int count = 0;
        boolean[][] visited = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1' && !visited[i][j]) {
                    dfs(i, j, grid, visited);
                    count++;
                }
            }
        }
        return count;
    }

    private void dfs(int i, int j, char[][] grid, boolean[][] visited) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] == '0' || visited[i][j] == true) {
            return;
        }

        visited[i][j] = true;
        for (int[] dir : directions) {
            int x = i + dir[0];
            int y = j + dir[1];
            dfs(x, y, grid, visited);
        }
    }
}

class Solution {
    // refer NEETCODE
    // BFS
    // T: O(m*n)
    // S: O(m*n)
    /**
     * 1. Similar can be achieved using BFS.
     * 2. Here, we use queue.
     * 3. The marking of visited is done when we add the `i,j` in the queue.
     */
    private static final int[][] directions = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

    public int numIslands(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        boolean[][] vis = new boolean[m][n];
        int islands = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1' && vis[i][j] == false) {
                    bfs(i, j, vis, grid);
                    islands += 1;
                }
            }
        }
        return islands;
    }

    private void bfs(int i, int j, boolean[][] vis, char[][] grid) {
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[] { i, j });
        vis[i][j] = true;

        while (!q.isEmpty()) {
            int[] node = q.poll();
            int r = node[0];
            int c = node[1];

            for (int[] direction : directions) {
                int nr = r + direction[0];
                int nc = c + direction[1];
                if (nr >= 0 && nr < grid.length && 
                    nc >= 0 && nc < grid[0].length && 
                    grid[nr][nc] == '1' && 
                    vis[nr][nc] == false) {

                    q.add(new int[] { nr, nc });
                    vis[nr][nc] = true;
                }
            }
        }
    }
}
