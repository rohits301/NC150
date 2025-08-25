class Solution {
    // refer NEETCODE
    // BRUTE/BETTER/OPTIMAL
    // T: O(m * n)
    // S: O(m + n)
    /*
     * 1. When multiplying two numbers, the maximum result can be the sum of length of both numbers.
     * 2. Eg. 999*999 = 998001 (3+3)
     * 3. In multiplying we start with units place, and then move to tens, hundreds.
     * 4. So, we will reverse the given strings to make our lives easier in managing indices. 
     * e.g. "123" * "456"
     * then, s1 = "321", s2 = "654"
     * 5. And instead of directly keeping a string, we calculate the result in a integer array
     * and then convert to string in the end.
     * 6. the index for the answer is `i+j`. - by observation
     * 7. In each multiplication, we get the remainder as product % 10 and carry = product / 10. 
     * 8. Before converting to string, we eliminate leading zeroes.
     * NOTE: Edge case is when any of the strings is "0", below code without the if check 
     * for this condition will return an empty string as number except zeros in the array, so handled separately.
     */
    public String multiply(String num1, String num2) {
        // handle separately as not handled in the rest of the code.
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }

        int m = num1.length();
        int n = num2.length();

        String s1 = new StringBuilder(num1).reverse().toString();
        String s2 = new StringBuilder(num2).reverse().toString();
        int[] res = new int[m + n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int product = (s1.charAt(i) - '0') * (s2.charAt(j) - '0');

                res[i + j] += product; // actual product
                res[i + j + 1] += res[i + j] / 10; // carry
                res[i + j] = res[i + j] % 10; // value to be stored at index
            }
        }

        int i = res.length - 1;
        StringBuilder sb = new StringBuilder();
        // skip leading zeroes in the result
        while (i >= 0 && res[i] == 0) {
            i--;
        }
        // append remaining digits
        while (i >= 0) {
            sb.append(res[i]);
            i--;
        }

        return sb.toString();
    }
}
