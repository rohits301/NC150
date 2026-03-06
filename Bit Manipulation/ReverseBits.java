public class Solution {
    // refer NEETCODE
    // T: O(1)
    // S: O(1)
    public int reverseBits(int n) {
        /*
         * Approach:
         * 1. Right shift the number and use '&' with 1 to extract each bit.
         *    Example: n = 0010 (i = 0), 0010 & 1 => 0000
         *    Next iteration: i = 1, n = 001 (after right shift), 001 & 1 => 001 (bit is set)
         * 2. To reverse the bits, set the extracted bit at position (31 - i) in the result.
         *    Example: res = 0, res = 0000...0 (i = 0), res = 0100...0 (i = 30)
         */
        int res = 0;
        for (int i = 0; i < 32; i++) {
            int bit = ((n >> i) & 1);
            res |= (bit << (31 - i));
        }
        return res;
    }
}
