class Solution {
    // refer NEETCODE
    public static final int[][] directions = {
            { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 }
    };

    public int swimInWater(int[][] grid) {
        int n = grid.length;
        boolean[][] visited = new boolean[n][n];
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        pq.offer(new int[] { grid[0][0], 0, 0 }); // cost, i, j
        visited[0][0] = true;

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int t = current[0];
            int r = current[1];
            int c = current[2];

            if (r == n - 1 && c == n - 1) {
                return t;
            }

            for (int[] direction : directions) {
                int nr = r + direction[0];
                int nc = c + direction[1];

                if (nr >= 0 && nr < n &&
                        nc >= 0 && nc < n &&
                        !visited[nr][nc]) {
                    int maxTimeInPath = Math.max(t, grid[nr][nc]);
                    pq.offer(new int[] { maxTimeInPath, nr, nc });
                    visited[nr][nc] = true;
                }
            }
        }
        return n * n;
    }
}
