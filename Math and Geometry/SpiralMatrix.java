
class Solution {
    // refer STRIVER
    // OPTIMAL
    // T: O(n*m)
    // S: O(1)
    /*
     * Intuition:
     * 1. We need to traverse the matrix in a spiral order.
     * 2. We can achieve this by maintaining four pointers: top, bottom, left, and right.
     * 3. We will keep moving in the right, down, left, and up directions while updating the pointers.
     *
     * Approach:
     * 1. Initialize the four pointers.
     * 2. Use a while loop to traverse the matrix in a spiral order.
     * 3. Update the pointers after each direction is completed.
     * 4. Continue the process until all elements are traversed.
     * Why the extra if-checks?
     * To avoid adding duplicate elements in case of single row or single column matrices.
     * For single row, `matrix[top][i]` is repetition
     * For single column, `matrix[i][left]` is repetition
     * E.g. matrix[][] = {{1, 2, 3}}, single row case
     *      matrix[][] = {{1}, {2}, {3}}, single column case
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        // directions -> right, bottom, left, top
        int m = matrix.length;
        int n = matrix[0].length;
        int top = 0, bottom = m - 1;
        int left = 0, right = n - 1;
        List<Integer> ans = new ArrayList<>();

        while (top <= bottom && left <= right) {
            for (int i = left; i <= right; i++) {
                ans.add(matrix[top][i]);
            }
            top++;

            for (int i = top; i <= bottom; i++) {
                ans.add(matrix[i][right]);
            }
            right--;

            if (top <= bottom) {
                for (int i = right; i >= left; i--) {
                    ans.add(matrix[bottom][i]);
                }
                bottom--;
            }

            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    ans.add(matrix[i][left]);
                }
                left++;
            }
        }
        return ans;
    }
}
