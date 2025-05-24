class Solution {
    // Walls And Gates - LC Premium
    // BELOW GIVES WRONG RESULT, EXPLAINED IN NOTE
    // submit on NeetCode
    /**
     * 1. Multi-source BFS - efficient
     * 2. Start BFS from all the gates at once
     * 3. In this way, we start the bfs with the same value cells
     * at once.
     * 4. Distance from Gates = level in the queue.
     * NOTE: In standard BFS - 
     * A cell should be marked as visited when it is added to the queue, not when it is polled
     * Not doing this leads to problem in below code of processing `0` again and 
     * this leads to distance of a gate from itself to be > 0 (updated due to level).
     */

    public static final int[][] directions = {{-1,0},{0,-1},{0,1},{1,0}};
    public void islandsAndTreasure(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        boolean[][] visited = new boolean[m][n];

        Queue<int[]> q = new LinkedList<>();

        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                if(grid[i][j] == 0){
                    q.offer(new int[]{i, j});
                }
            }
        }

        // BFS
        int level = 0; // level of BFS = distance
        while(!q.isEmpty()){
            int size = q.size();
            while(size-- > 0){
                int[] pair = q.poll();
                int r = pair[0];
                int c = pair[1];

                visited[r][c] = true;
                grid[r][c] = level;
                for(int[] d: directions){
                    int nr = r + d[0];
                    int nc = c + d[1];
                    if(nr >= 0 && nr < m &&
                       nc >= 0 && nc < n &&
                       grid[nr][nc] != -1 && 
                       !visited[nr][nc]){

                        q.offer(new int[]{nr, nc});
                    }
                }
            }
            level += 1;
        }
    }
}

class Solution {
    // Walls And Gates - LC Premium
    // submit on NeetCode
    /**
     * 1. Multi-source BFS - efficient
     * 2. Start BFS from all the gates at once
     * 3. In this way, we start the bfs with the same value cells
     * at once.
     * 4. Distance from Gates = level in the queue.
     * NOTE: In standard BFS - 
     * A cell should be marked as visited when it is added to the queue, not when it is polled
     * FIXED - Not doing this leads to problem in below code of processing `0` again and 
     * this leads to distance of a gate from itself to be > 0 (updated due to level).
     */

    public static final int[][] directions = {{-1,0},{0,-1},{0,1},{1,0}};
    public void islandsAndTreasure(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        boolean[][] visited = new boolean[m][n];

        Queue<int[]> q = new LinkedList<>();

        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                if(grid[i][j] == 0){
                    q.offer(new int[]{i, j});
                    visited[i][j] = true;
                }
            }
        }

        // BFS
        int level = 0; // level of BFS = distance of cell from gate
        while(!q.isEmpty()){
            int size = q.size();
            while(size-- > 0){
                int[] pair = q.poll();
                int r = pair[0];
                int c = pair[1];

                grid[r][c] = level;
                for(int[] d: directions){
                    int nr = r + d[0];
                    int nc = c + d[1];
                    if(nr >= 0 && nr < m &&
                       nc >= 0 && nc < n &&
                       grid[nr][nc] != -1 && 
                       !visited[nr][nc]){

                        visited[nr][nc] = true;
                        q.offer(new int[]{nr, nc});  
                    }
                }
            }
            level += 1;
        }
    }
}
