class Solution {
    // BRUTE - O(m*n) - linear search in entire matrix
    // BETTER 
    // T: O(m * logn)
    // S: O(1)
    /**
     * Binary search in every 1D row of the matrix
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;

        for (int i = 0; i < m; i++) {
            int lo = 0, hi = n - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                if (matrix[i][mid] == target) {
                    return true;
                }

                if (matrix[i][mid] < target) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
        }
        return false;
    }
}

class Solution {

    // refer STRIVER
    // OPTIMAL - O(log (m*n))
    /*
     * The matrix has special properties where each row is sorted and the first element of each row is greater than the last element of the previous row.
     * This means that matrix is sorted if you go from top left to bottom right, row by row. 
     * So we can treat this 2D matrix as a sorted 1D array and use Binary Search to find the target.
     *
     * Formula explanation (INTUITION):
     * n = number of columns 
     * In every row we have `n` elements, so the first element index will always be a multiple of `n`. 
     * how many complete rows fit before index is my row number.
     * `row number = index / n`.
     * Column number is how many elements have gone before me.
     * It is the number of elements remaining after filling rows, so remainder with `n`.
     * `col number = index % n`.
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        // BS considering a 1D matrix of m*n
        // convert indices using formula

        int m = matrix.length;
        int n = matrix[0].length;

        int lo = 0, hi = (m * n) - 1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;

            int row = mid / n;
            int col = mid % n;

            if (matrix[row][col] == target) {
                return true; // mid
            }

            if (matrix[row][col] < target) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return false;
    }

}
