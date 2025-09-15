class Solution {
    // T: O(n^2) - where n is the number of rows or columns in the grid
    // S: O(n^2) - for the queue and visited array
    /*
     * Approach:
     * 1. We can use BFS to find the shortest path in an unweighted grid.
     * 2. We start from the top-left corner and explore all 8 possible directions (horizontally, vertically, and diagonally) to reach the bottom-right corner.
     * 3. We maintain a queue to explore the grid level by level and a visited array to keep track of the cells we have already visited.
     * 4. If we reach the bottom-right corner, we return the length of the path.
     * 5. If we exhaust all possibilities and do not reach the bottom-right corner, we return -1.
     * 6. Each element in queue is an array of three integers - row, column, and the length of the path till that cell.
     * Edge Cases:
     * a) If the starting cell (0,0) or the ending cell (n-1,n-1) is blocked (i.e., has a value of 1), we cannot find a path, so we return -1.
     * b) If the grid has only one cell and it is unblocked (i.e., has a value of 0), the shortest path length is 1.
     */
    public static final int[][] directions = {
            { -1, 0 }, { -1, 1 }, { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, -1 }, { -1, -1 }
    };

    public int shortestPathBinaryMatrix(int[][] grid) {
        // BFS
        int n = grid.length;
        // edge case - single cell grid
        if(n == 1){
            return grid[0][0] == 0 ? 1 : -1;
        }
        // edge case - start or end cell is blocked
        if (grid[0][0] != 0 || grid[n - 1][n - 1] != 0) {
            return -1;
        }
        
        // int[] -> i,j,lengthOfPathTillNow
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] vis = new boolean[n][n];

        vis[0][0] = true;
        queue.offer(new int[] { 0, 0, 1 });

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();
            int r = arr[0];
            int c = arr[1];
            int length = arr[2];

            if (r == n - 1 && c == n - 1) {
                return length;
            }

            for (int d = 0; d < 8; d++) {
                int x = directions[d][0] + r;
                int y = directions[d][1] + c;
                if (isValid(x, y, vis, grid)) {
                    vis[x][y] = true;
                    queue.offer(new int[] { x, y, length + 1 });
                }
            }
        }
        return -1;
    }

    private boolean isValid(int r, int c, boolean[][] vis, int[][] grid) {
        if (r >= 0 && c >= 0 && r < grid.length && c < grid[0].length && !vis[r][c] && grid[r][c] == 0) {
            return true;
        }
        return false;
    }
}
