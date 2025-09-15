class Solution {
    // T: O(m+n)
    // S: O(1)
    /*
     * Approach:
     * 1. Start from the top-right corner of the matrix. Can start from bottom-left too.
     * 2. If the current element is equal to the target, return true.
     * 3. If the current element is less than the target, move down to the
     * next row.
     * 4. If the current element is greater than the target, move left to the  
     * previous column.
     * 5. Repeat steps 2-4 until the target is found or the indices go out of bounds.
     * 6. If the target is not found, return false.
     * 
     * Note: This approach works because the matrix is sorted in ascending order both
     * row-wise and column-wise.
     * TC: In the worst case, we might have to traverse m rows and n columns. So, O(m+n) time.
     * 
     * Why this works?
     * It is staircase search. At each step, we eliminate either a row or a column.
     * If the current element is less than the target, all elements in that row to the left are also less than the target (because the row is sorted), 
     * so we move down to the next row.
     * If the current element is greater than the target, all elements in that column below are also greater than the target (because the column is sorted),
     * so we move left to the previous column.
     * This way, we systematically narrow down the search space.
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        // staircase search
        // start from top-right corner
        int m = matrix.length;
        int n = matrix[0].length;
        int row = 0, col = n - 1;

        while (row < m && col >= 0) {
            if (matrix[row][col] == target) {
                return true;
            }
            if (matrix[row][col] < target) {
                row++;
            } else {
                col--;
            }
        }
        return false;
    }
}
