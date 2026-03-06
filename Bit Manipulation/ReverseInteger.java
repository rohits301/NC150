class Solution {
    // refer NEETCODE
    // T: O(log x); (log base 10)
    // S: O(1);
    /*
     * Approach: Reverse the integer by extracting digits one by one and building the result using `res = res * 10 + digit`.
     * Intuition: Multiplying the result by 10 shifts its digits left, and adding the next digit appends it to the end and this process reverses the number.
     *
     * Edge Case Explanation:
     * 1. The reversed number may overflow the 32-bit signed integer range.
     * 2. For positive numbers (e.g., x = 2147483647), reversing gives 7463847412, which exceeds Integer.MAX_VALUE (2147483647).
     * 3. To detect overflow before it happens, check if `res > MAX / 10` or (`res == MAX / 10` and `digit > 7`).
     * 4. This works because if `res` is at the threshold (`214748364`), only digits 0-7 can be safely added.
     * 5. If `digit > 7`, the result will exceed the maximum allowed value.
     * 6. For negative numbers (e.g., x = -2147483648), reversing gives -8463847412, which is less than Integer.MIN_VALUE (-2147483648).
     * 7. To detect underflow, check if `res < MIN / 10` or (`res == MIN / 10` and `digit < -8`).
     * 8. This works because if `res` is at the threshold (`-214748364`), only digits -8 to 0 are safe.
     * 9. If `digit < -8`, the result will be less than the minimum allowed value.
     * 10. These checks ensure we never build a number larger than Integer.MAX_VALUE or smaller than Integer.MIN_VALUE during reversal.
     * 11. If overflow or underflow is detected, return 0.
     */
    public int reverse(int x) {
        int MAX = Integer.MAX_VALUE; // 2147483647
        int MIN = Integer.MIN_VALUE; // -2147483648

        int res = 0;
        while (x != 0) {
            int digit = x % 10;
            x = x / 10;

            if (res > MAX / 10 ||
                (res == MAX / 10 && digit > MAX % 10)) {
                // System.out.println(res);
                return 0;
            }

            if (res < MIN / 10 ||
                (res == MIN / 10 && digit < MIN % 10)) {
                // System.out.println(res);
                return 0;
            }
            res = res * 10 + digit;
        }
        return res;
    }
}
