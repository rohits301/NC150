class Solution {
    // refer STRIVER for explanation, NEETCODE for code
    // BRUTE FORCE - TLE
    // T: O(n)
    // S: O(1)
    /*
     * 1. Keep multiplying the number with itself until n (exponent) becomes 0.
     * 2. For -ve `n`, we divide the output by 1 in the end.
     * 3. if x == 0 -> res = 0;
     * 4. if n == 0 -> res = 1; 
     * NOTE: if n is negative, there is a chance, it's positive equivalent will overflow `int`.
     * eg. `n = -2147483648, nn = 2147483648` which is greater than `int` range, hence need `long` to store this.
     */
    public double myPow(double x, int n) {
        if (x == 0) {
            return 0;
        }

        if (n == 0) {
            return 1;
        }
        double res = 1.0;
        long nn = n; // first convert to long, then multiply with -1 if reqd.
        if (nn < 0) {
            nn *= -1; // convert to positive exponent
        }

        while (nn-- > 0) {
            res *= x;
        }

        return n < 0 ? (1.0 / res) : res;
    }
}

class Solution {
    // refer STRIVER
    // OPTIMAL - Binary Exponentiation
    // T: O(log n)
    // S: O(1)
    /*
     * 1. Instead of performing n multiplications (brute force), we reduce the exponent by half in each step and square the base accordingly. This reduces the number of multiplications from O(n) to O(log n), achieving the same result.
     * 2. x^n = (x*x)^(n/2) if n is even
     *    x^n = x * (x*x)^(n/2) if n is odd
     * 3. Convert n to long to safely handle Integer.MIN_VALUE (-2^31).
     * 4. For negative n, compute x^(-n) as 1 / x^n.
     * 5. Edge cases:
     *    a) x == 0 -> return 0
     *    b) n == 0 -> return 1
     * NOTE: if n is negative, there is a chance, it's positive equivalent will overflow `int`.
     * eg. `n = -2147483648, nn = 2147483648` which is greater than `int` range, hence need `long` to store this.
     */
    public double myPow(double x, int n) {
        if (x == 0) {
            return 0;
        }

        if (n == 0) {
            return 1;
        }

        double res = 1.0;
        long nn = n; // first convert to long, then multiply with -1 if reqd.
        if (nn < 0) {
            nn *= -1;
        }

        while (nn > 0) {
            if (nn % 2 == 1) {
                res *= x;
                nn -= 1;
            } else {
                // even
                x = x * x;
                nn /= 2;
            }
        }

        return n < 0 ? (1.0 / res) : res;
    }
}
