class Solution {
    // refer NEETCODE
    // BRUTE/BETTER
    // T: O(n^2) - O(81); each cell is visited thrice.
    // S: O(n) - O(9) for each set we have max 9 elements in it, at any given time only one set exists.
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
         * There are 9 (3x3) grids in the 9x9 grid
         * According to sudoku, all should be unique
         * so, we derive a formula such that
         * for every square, we can get the -
         * indices of the left-most corner, that is, the starting corner of the square grid.
         * With the starting indices, we just have to loop through a 3x3 grid to check for duplicates.
         * observation -
         * 0 1 2
         * 3 4 5
         * 6 7 8
         * for first row squares - 0,1,2
         * starting row is 0, starting columns are 0,3,6
         * for second row squares - 3,4,5
         * starting row is 3, starting columns are 0,3,6
         * similarly, for third row squares - 6,7,8
         * starting row is 6, starting columns are 0,3,6
         * hence, the formula -
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
     * Previously, we were checking every row, col and square for duplicate.
     * The duplicate search can be done with bits because
     * `int` has 32 bits and we only need 9.
     * To represent numbers, we can create a mask that left shifts 1
     * in-order to turn on that particular bit.
     * For e.g. board[i][j] = '5', and i=1, j=5
     * val = '5'-'1' = 4
     * turn the 4th bit on => 00010000
     * mark the location in row -> 
     * rows[1] = 00010000 and cols[5] = 00010000
     * this means for 1st row, 5th column, we have 4th bit on.
     * since all loops run 9*9 times, so we run a single loop and do every marking there only.
     * To find square index - 
     * observe the pattern for indexes of row, col in 2D matrix
     * i/3 and j/3 both result in 0-2.
     * In the squares, to get the row index we need to multiply by 3. 
     * Column is the offset from that location.
     * hence, squareIdx = (i/3)*3 + j/3;
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
