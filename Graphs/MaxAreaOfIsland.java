class Solution {
    // refer NEETCODE
    // can do both DFS, BFS
    // DFS - FASTER
    // T: O(m*n)
    // S: O(m*n)
    /** 
     * we want the dfs to return us the maxArea of island
     * answer is max of all areas.
     */
  public static final int[][] directions = { { -1, 0 }, { 0, -1 }, { 0, 1 }, { 1, 0 } };

    public int maxAreaOfIsland(int[][] grid) {
        int maxArea = 0;
        int m = grid.length;
        int n = grid[0].length;
        boolean[][] visited = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1 && visited[i][j] == false) {
                    maxArea = Math.max(maxArea, dfs(i, j, m, n, grid, visited));
                }
            }
        }
        return maxArea;
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
    // 
    /**
     * Similar to DFS, for each island, we invoke a BFS
     * visited is marked right after we add in the queue
     * also, we increase `res` by 1 indicating we found a one.
     */
    public static final int[][] directions = { { -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 } };

    public int maxAreaOfIsland(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        int maxArea = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1 && visited[i][j] == false) {
                    maxArea = Math.max(maxArea, bfs(i, j, m, n, grid, visited));
                }
            }
        }
        return maxArea;
    }

    private int bfs(int i, int j, int m, int n, int[][] grid, boolean[][] visited) {
        Queue<int[]> q = new LinkedList<>();
        
        q.offer(new int[] { i, j });
        visited[i][j] = true;
        int res = 1;

        while(!q.isEmpty()){
            int[] rp = q.poll();
            int r = rp[0];
            int c = rp[1];
            for(int[] direction: directions){
                int nr = r + direction[0];
                int nc = c + direction[1];
                if(nr >= 0 && nr < grid.length && 
                   nc >= 0 && nc < grid[0].length && 
                   grid[nr][nc] == 1 && 
                   visited[nr][nc] == false){
                    
                    q.offer(new int[]{nr, nc});
                    visited[nr][nc] = true;
                    res += 1;
                }
            }
        }
        return res;
    }
}
