class Solution {
    // refer STRIVER
    // T: O(m*n); grid iteration: m*n + queue can have max m*n elements + m*n*4 (for all 4 directions) -> m*n + m*n*4
    // S: O(m*n); queue can have at max all m*n oranges for the case when all are rotten
    /*
     * 1. At every minute, oranges are rotten. Think, level order traversal in the graph.
     * 2. After processing all the oranges at a level, we increment the time.
     * 3. So, for BFS, we need a visited array, we can make a new arr[][] or use the grid itself 
     * and mark rotten oranges with the given marker, i.e., `grid[i][j] = 2`.
     * 4. Steps - 
     * a) maintain count of initial fresh oranges
     * b) add all the rotten oranges `(i,j)` to the queue.
     * c) initialize time = -1, to adjust for the initial state of queue where we have some rotten oranges but haven't processed them yet.
     * d) Traverse the queue level by level.
     * e) In every iteration, `poll()` from queue
     * f) iterate in the four directions to find neighbors and add them to the queue
     * g) reduce the fresh count and mark the neighbors as visited while adding them
     * h) At the end of each level, increment time
     * i) if no fresh oranges remain, return the time taken, else -1.
     * 5. Edge case: there is no fresh oranges initially, so return 0 as cannot rot.
     */
    // Directions for 4-way adjacent cells (up, left, right, down)
    private static final int[][] directions = { { -1, 0 }, { 0, -1 }, { 0, 1 }, { 1, 0 } };

    public int orangesRotting(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        int freshCount = 0; // Count of fresh oranges
        
        // Initialize queue with initially rotten oranges and count fresh ones
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 2) {
                    queue.offer(new int[] { i, j });
                } else if (grid[i][j] == 1) {
                    freshCount++;
                }
            }
        }

        // If there are no fresh oranges, no time is needed
        if (freshCount == 0) {
            return 0;
        }
        int time = -1; // Time counter, starts at -1 to adjust for the initial queue state

        // Perform BFS level-wise
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] pair = queue.poll();
                int r = pair[0];
                int c = pair[1];

                // Try all 4 adjacent directions
                for (int[] dir : directions) {
                    int nr = r + dir[0];
                    int nc = c + dir[1];

                    // If within bounds and fresh, make it rotten
                    if (nr >= 0 && nr < m && nc >= 0 && nc < n && grid[nr][nc] == 1) {
                        queue.offer(new int[] { nr, nc });
                        grid[nr][nc] = 2; // Mark as rotten
                        freshCount--;
                    }
                }
            }
            time++; // One level (minute) completed
        }

        // If there are still fresh oranges, return -1 (impossible to rot all)
        return (freshCount == 0) ? time : -1;
    }
}

class Solution {
    // refer STRIVER
    // WITHOUT MODIFYING INPUT GRID
    // T: O(m*n); grid iteration: m*n + queue can have max m*n elements + m*n*4 (for all 4 directions) -> m*n + m*n*4
    // S: O(2*m*n); visited array + queue can have at max all m*n oranges 
    // for the case when all are rotten

    // Directions for 4-way adjacent cells (up, left, right, down)
    private static final int[][] directions = { { -1, 0 }, { 0, -1 }, { 0, 1 }, { 1, 0 } };

    public int orangesRotting(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        int freshCount = 0; // Count of fresh oranges

        int[][] vis = new int[m][n];
        // Initialize queue with initially rotten oranges and count fresh ones
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 2) {
                    queue.offer(new int[] { i, j });
                    vis[i][j] = 2;
                } else if (grid[i][j] == 1) {
                    freshCount++;
                }
            }
        }

        // If there are no fresh oranges, no time is needed
        if (freshCount == 0) {
            return 0;
        }
        int time = -1; // Time counter, starts at -1 to adjust for the initial queue state

        // Perform BFS level-wise
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] pair = queue.poll();
                int r = pair[0];
                int c = pair[1];

                // Try all 4 adjacent directions
                for (int[] dir : directions) {
                    int nr = r + dir[0];
                    int nc = c + dir[1];

                    // If within bounds and fresh, make it rotten
                    if (nr >= 0 && nr < m && nc >= 0 && nc < n && grid[nr][nc] == 1 && vis[nr][nc] != 2) {
                        queue.offer(new int[] { nr, nc });
                        vis[nr][nc] = 2; // Mark as rotten
                        freshCount--;
                    }
                }
            }
            time++; // One level (minute) completed
        }

        // If there are still fresh oranges, return -1 (impossible to rot all)
        return (freshCount == 0) ? time : -1;
    }
}
