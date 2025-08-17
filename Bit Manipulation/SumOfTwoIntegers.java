class Solution {
    // refer NEETCODE
    // T: O(1)
    // S: O(1)
    /*
     * Computes the sum of two integers without using '+' or '-' operators.
     * Approach:
     * 1. The sum without carry is calculated using XOR: a ^ b.
     * 2. The carry is calculated using AND and left shift: (a & b) << 1.
     * 3. The carry is left-shifted because it needs to be added to the next higher bit.
     * 4. Negative numbers are handled automatically due to Java's integer representation.
     * 5. Repeat until there is no carry left (b == 0).
     */
    public int getSum(int a, int b) {

        while(b != 0){
            int temp = ((a & b) << 1);
            a = (a ^ b);
            b = temp;
        }
        return a;
    }
}
