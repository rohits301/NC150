class Solution {
    // refer NEETCODE
    // BRUTE/BETTER/OPTIMAL
    // T: O(n)
    // S: O(n)
    /*
     * 1. We can return the same array by just incrementing the unit's place by 1.
     * 2. If the unit's places has a nine, e.g. digits[i] = 9, then digits[i] = 0, and digits[i-1] = 1. 
     * 3. So, instead of creating a new array, we can return original array with updated values.
     * 4. Edge case - 
     * the array has an overflowing 1 - 
     * e.g digits = [9]
     * digits = [9,9]
     * 5. So, in this case, all the other digits are zero and there is a leading 1.
     * Hence, we create an array of size = n + 1 and set `res[0] = 1` for the leading 1.
     */
    public int[] plusOne(int[] digits) {
        int n = digits.length;
        for (int i = n - 1; i >= 0; i--) {
            if (digits[i] < 9) {
                digits[i] += 1;
                return digits;
            }
            digits[i] = 0;
        }

        // reaching here implies, there is an overflowing 1 as carry.
        int[] res = new int[n + 1];
        res[0] = 1;
        return res;
    }
}
