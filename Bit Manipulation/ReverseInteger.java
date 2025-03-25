class Solution {
    // refer NEETCODE
    // T: O(log x); (log base 10)
    // S: O(1);
    // shorter and smarter way to build the reversed value
    // `res = res * 10 + remainder (digit)`
    // this keeps multiplying the power without needing to calculate the number of digits beforehand
    /*
     * if x = 2147483647
     * reversed value = 7463847412 (exceeds the int limits)
     * to detect it, if remove the one's place 
     * we get reversed value = 746384741
     * hence if the last digit of x is > 7, then we know it will overflow
     * similarly, for x = -2147483648
     * reverse value = -8463847412 (exceeds the range)
     * we remove the last digit, one's place
     * we get reverse value = -846384741
     * hence, if the last digit of x is < -8, then it will overflow
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
