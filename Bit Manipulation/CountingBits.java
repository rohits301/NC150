
class Solution {

    // BRUTE FORCE
    // T: O(nlogn); n * logn
    // S: O(n); answer space
    /*
     * 1. for every integer upto n, loop and call a function findOnes(i)
     * 2. findOnes(i)
     * for every integer "i" until i>0
     * i & 1 -> gives counts of 1
     * i = i >> 1 (equivalent to i / 2) -> after every iteration
     * 3. this (log n) operation is performed n times
     * 4. hence, n*logn time.
     */
    public int[] countBits(int n) {
        if (n == 0) {
            return new int[]{0};
        }
        int[] dp = new int[n + 1]; // intially all are 0
        for (int i = 0; i <= n; i++) {
            dp[i] = findOnes(i);
        }
        return dp;
    }

    public int findOnes(int n) {
        int count = 0;
        while (n > 0) {
            count += (n & 1);
            n >>= 1;
        }
        return count;
    }
}

class Solution {
    // refer NEETCODE
    // T: O(n); iteration of dp array
    // S: O(n); ans space
    /**
     * Computes the number of 1's in the binary representation of
     * each integer from 0 up to n (inclusive), returning the results in an
     * array of size n + 1.
     *
     * 1. Use a dynamic programming approach with the recurrence: 
     * `dp[i] = 1 + dp[i - offset]`, where offset is the largest power of 2 ≤ i.
     *
     * 2. This works because any integer i can be expressed as:
     * `i = offset + remainder` where offset is a power of 2 (i.e., its highest bit), and
     * `remainder = i - offset` is the part without that highest bit.
     * Therefore, the bit count of i = 1 (for the highest bit) + bit count of remainder.
     *
     * 3. Example: For i = 5 (binary 101), offset = 4 (100), remainder = 1, so
     * dp[5] = 1 + dp[1] = 1 + 1 = 2.
     * 4. Example: For i = 8 (binary 1000), offset = 8 (1000), remainder = 0, so
     * dp[8] = 1 + dp[0] = 1 + 0 = 1.
     */
    public int[] countBits(int n) {
        // dp[i] will hold the number of 1's in the binary representation of i
        int[] dp = new int[n + 1];

        // 'offset' tracks the current highest power of 2 ≤ i
        int offset = 1;

        // We start from i = 1 because dp[0] is already 0
        for (int i = 1; i <= n; i++) {

            /*
             * If we've reached exactly twice the current offset, it means
             * we're now at the next power of two (e.g., from 1 to 2, from 2 to 4, 4 to 8, etc.).
             * So we update offset to reflect this.
             */
            if (offset * 2 == i) {
                offset = i;
            }

            /*
             * Number of set bits in i is:
             * 1 (for the leading bit contributed by offset) + dp[i - offset] (set bits in the remainder after removing that leading bit)
             */
            dp[i] = 1 + dp[i - offset];
        }

        // Return the array populated with bit counts for 0..n
        return dp;
    }
}
