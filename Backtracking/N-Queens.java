class Solution {
    // refer STRIVER
    // BRUTE FORCE
    // T: O(4^n); each level has 4 options, and n such levels. Worst case - n^n if we consider no pruning. Pruning is there because of the three conditions to be met.
    // S: O(n*n);O(n) - recursion stack + O(n*n) - board space + O(n*n) - make copy of string to list
    /*
     * APRROACH - 
     * 1. There are 3 conditions to follow -
     * a) every row has 1 queen, b) every col has 1 queen, c) no two queens attack each other
     * 2. At every level in recursion, we try placing a queen in a column.
     * 3. For a `n*n` board, we have `n` positions in a column, so we try them all.
     * 4. After placing 1 queen, try placing the rest in other columns.
     * 5. `isSafe()` method for checking is it safe to place a queen.
     * 6. Since we start placing queens from `col=0`, so left side, hence, while checking the queen at `col=1`, we only to safe it from queens in previous columns, so `col=0` here.
     * 7. Hence, we check for leftRow, left major diagonal and left minor diagonal.
     * 8. Base case: reaching last column => all queens are safely placed.
     * 9. We loop over the rows and start placing queens in every column.
     * 10. Since, we need to retrace the path to explore further options, we have to restore the board to its previous state while backtracking. Hence, undo the processes in backtracking.
     */
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> ans = new ArrayList<>();
        char[][] board = new char[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '.';
            }
        }

        dfs(0, board, ans);
        return ans;
    }

    private void dfs(int col, char[][] board, List<List<String>> ans) {
        if (col == board.length) {
            // convert the board into List<String> and store in ans.
            List<String> list = new ArrayList<>();
            for (char[] row : board) {
                String str = new String(row);
                list.add(str);
            }
            ans.add(list);
        }

        for (int row = 0; row < board.length; row++) {
            if (isSafe(row, col, board)) {
                board[row][col] = 'Q';
                dfs(col + 1, board, ans);
                board[row][col] = '.';
            }
        }
    }

    private boolean isSafe(int row, int col, char[][] board) {
        //left row
        for (int j = col - 1; j >= 0; j--) {
            if (board[row][j] == 'Q') {
                return false;
            }
        }

        //left upper diagonal
        for (int i = row - 1, j = col - 1; j >= 0 && i >= 0; i--, j--) {
            if (board[i][j] == 'Q') {
                return false;
            }
        }

        //left lower diagonal
        for (int i = row + 1, j = col - 1; j >= 0 && i < board.length; i++, j--) {
            if (board[i][j] == 'Q') {
                return false;
            }
        }

        return true;
    }
}

class Solution {
    // refer STRIVER
    // OPTIMAL - REDUCED THE CHECKS FOR left row, upper and lower diagonal from loops to simple array check by hashing.
    // T: O(4^n); each level has 4 options, and n such levels. Worst case - n^n if we consider no pruning. Pruning is there because of the three conditions to be met.
    // S: O(n*n); O(n) - recursion stack + O(n*n) - board space + O(n*n) - make copy of string to list

    /*
     * APRROACH (same as brute force, only the safe checks change) - 
     * 
     * 1. Instead of having three loops for traversing left row, upper and lower diagonal.
     * 2. We maintain an array - leftRow[n], leftUpperDiagonal[2*n-1] and leftLowerDiagonal[2*n-1].
     * 3. This is by observation. They can be used for hashing.
     * 4. For left row, if a queen is in the same row, then it is not safe. leftRow[row] = true
     * 5. Left upper diagonal, `(n-1)+ col - row` has same value for a the entire diagonal, hence, if any index [n-1 + col-row] is true, then the queen is unsafe there.
     * 6. Left lower diagonal, `row + col` has same value for the entire diagonal, hence, if any index [row + col] is true, then the queen is not safe there. 
     * 7. This reduces the 3*O(n) checks to O(1).
     */
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> ans = new ArrayList<>();
        char[][] board = new char[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '.';
            }
        }

        boolean[] leftRow = new boolean[n];
        boolean[] leftUpperDiagonal = new boolean[2*n-1];
        boolean[] leftLowerDiagonal = new boolean[2*n-1];
        dfs(0, n, board, leftRow, leftUpperDiagonal, leftLowerDiagonal, ans);
        return ans;
    }

    private void dfs(int col, int n, char[][] board, boolean[] leftRow, boolean[] leftUpperDiagonal, boolean[] leftLowerDiagonal, List<List<String>> ans) {

        if (col == n) {
            // convert the board into List<String> and store in ans.
            List<String> list = new ArrayList<>();
            for (char[] row : board) {
                String str = new String(row);
                list.add(str);
            }
            ans.add(list);
        }

        for (int row = 0; row < n; row++) {
            if (leftRow[row] == false && 
                leftUpperDiagonal[(n - 1) + col - row] == false && 
                leftLowerDiagonal[row + col] == false) {

                board[row][col] = 'Q';
                leftRow[row] = true;
                leftUpperDiagonal[(n - 1) + col - row] = true;
                leftLowerDiagonal[row + col] = true;
                
                dfs(col + 1, n, board, leftRow, leftUpperDiagonal, leftLowerDiagonal, ans);
                
                board[row][col] = '.';
                leftRow[row] = false;
                leftUpperDiagonal[(n - 1) + col - row] = false;
                leftLowerDiagonal[row + col] = false;
            }
        }
    }
}
