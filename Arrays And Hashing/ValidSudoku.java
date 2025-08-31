class Solution {
    // refer NEETCODE
    // BRUTE/BETTER
    // T: O(n^2) - O(81); each cell is visited thrice.
    // S: O(n) - O(9) for each set we have max 9 elements in it, at any given time only one set exists.
    /*
     * Approach: 
     * 1. Check each row, column, and 3x3 square for duplicates.
     * 2. Use a set to track seen numbers.
     * 3. If a duplicate is found, return false.
     * 4. If no duplicates are found, return true.
     */
    public boolean isValidSudoku(char[][] board) {
        int n = board.length; // n = 9
        Set<Character> set;

        // row-check
        for (int i = 0; i < n; i++) {
            set = new HashSet<>();
            for (int j = 0; j < n; j++) {
                if (board[i][j] == '.') {
                    continue;
                }
                // auto-boxing of char to Character
                if (set.contains(board[i][j])) {
                    return false;
                } else {
                    set.add(board[i][j]);
                }
            }
        }

        // col-check
        for (int j = 0; j < n; j++) {
            set = new HashSet<>();
            for (int i = 0; i < n; i++) {
                if (board[i][j] == '.') {
                    continue;
                }
                // auto-boxing of char to Character
                if (set.contains(board[i][j])) {
                    return false;
                } else {
                    set.add(board[i][j]);
                }
            }
        }

        // square check (3x3)
        /*
         * 1. There are 9 (3x3) grids in the board
         * 2. For a valid Sudoku, all must be unique.
         * 3. So, we derive a formula such that for every square,
         * we can get the indices of the top-left corner, that is, the starting corner of the square grid.
         * 4. With the starting indices, we just have to loop through a 3x3 grid to check for duplicates.
         * 5. Observation -
         * Below are the indices of squares in the 9*9 BOARD.
         * 0 1 2
         * 3 4 5
         * 6 7 8
         * 6. We will derive a formula to get the starting indices (top-left).
         * a) For first row squares - 0,1,2
         * starting row number is 0, starting column numbers are 0,3,6
         * b) For second row squares - 3,4,5
         * starting row number is 3, starting column numbers are 0,3,6
         * c) Similarly, for third row squares - 6,7,8
         * starting row number is 6, starting column numbers are 0,3,6
         * d) Hence, the formula -
         * row = (square / 3) * 3
         * col = (square % 3) * 3
         */

        for (int square = 0; square < n; square++) {
            int startRow = (square / 3) * 3;
            int startCol = (square % 3) * 3;

            // check in 3x3 grid
            set = new HashSet<>();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int r = startRow + i;
                    int c = startCol + j;

                    if (board[r][c] == '.') {
                        continue;
                    }
                    // auto-boxing of char to Character
                    if (set.contains(board[r][c])) {
                        return false;
                    } else {
                        set.add(board[r][c]);
                    }
                }
            }
        }
        return true;
    }
}

class Solution {
    // refer NEETCODE
    // OPTIMAL
    // using bit-masking
    // T: O(n^2) - O(81)
    // S: O(n) - O(9*3)
    /*
     * Approach - 
     * 1. Previously, we were checking every row, col and square for duplicate.
     * 2. The duplicate search can be done with bits because
     * `int` has 32 bits and we only need 9.
     * 3. To represent numbers, we can create a mask that left shifts 1
     * inorder to turn on that particular bit.
     * 4. For e.g. board[i][j] = '5', and i = 1, j = 5
     * val = '5'-'1' = 4
     * 5. Turn the 4th bit on of 0 => 00010000
     * 6. Mark the location in row and in column -> 
     * rows[1] = 00010000 and cols[5] = 00010000
     * 7. This means for 1st row and 5th column, we have 4th bit on.
     * 8. Since all loops run 9*9 times, so we run a single loop and do every marking there only.
     * 9. To find square index - 
     * a) Observe the pattern for indices of row, col in 2D matrix
     * b) i/3 and j/3 both result in [0,1,2].
     * c) In the squares, to get the row index we need to multiply by 3. 
     * d) Column is the offset from that location.
     * e) hence, `squareIdx = (i/3)*3 + j/3`
     * Question: How does this do the marking for the square?
     * Answer: It calculates the square index using the formula derived from the row and column indices. The square index is then used to mark the corresponding bit in the `squares` array. This ensures that we are keeping track of all numbers seen in each 3x3 sub-box.
     */
    public boolean isValidSudoku(char[][] board) {
        int[] rows = new int[9];
        int[] cols = new int[9];
        int[] squares = new int[9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    continue;
                }

                int val = board[i][j] - '1'; // '1'-'9' is mapped to 0-8
                int bit = (1 << val); // set the bit
                int squareIdx = (i / 3) * 3 + j / 3;

                // check whether it is already there (seen)
                if ((rows[i] & bit) > 0 ||
                    (cols[j] & bit) > 0 ||
                    (squares[squareIdx] & bit) > 0) {

                    return false;
                }

                // mark as seen
                // OR with bit at the particular location to mark
                rows[i] |= bit;
                cols[j] |= bit;
                squares[squareIdx] |= bit;
            }
        }
        return true;
    }
}
