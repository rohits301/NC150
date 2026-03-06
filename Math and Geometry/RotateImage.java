class Solution {
    // refer STRIVER
    // BRUTE -> USE EXTRA MATRIX WITH FORMULA -> i,j -> (j, n-i-1)
    // OPTIMAL -> IN-PLACE, transpose the matrix and reverse rows
    // T: O(n^2)
    // S: O(1)
    /*
     * Approach - Transpose the matrix and then reverse each row.
     * 1. To transpose the matrix, we swap the elements at (i, j) with (j, i).
     * 2. Swapping is done for only the upper half of the matrix.
     * 3. After transposing, we reverse each row to get the final rotated matrix.
     * Note: Why swap only the upper half? Because the lower half is a mirror image of the upper half
     * in a square matrix, and swapping both would undo the transpose.
     */
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        // optimal app.
        // transpose
        // swap the a[i][j] with a[j][i] for the upper half matrix
        // i.e., everything after the main diagonal

        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                // swap
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        // row reverse
        for (int i = 0; i < n; i++) {
            int left = 0;
            int right = n - 1;
            while (left < right) {
                int temp = matrix[i][left];
                matrix[i][left] = matrix[i][right];
                matrix[i][right] = temp;

                left++;
                right--;
            }
        }
    }
}
