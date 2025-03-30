class Solution {
    // refer STRIVER for explanation, NEETCODE for code
    // BRUTE FORCE - TLE
    // T: O(n)
    // S: O(1)
    /*
     * 1. Keep multiplying the number with itself until n becomes 0.
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
            nn *= -1;
        }

        while (nn-- > 0) {
            res *= x;
        }

        return n < 0 ? (1.0 / res) : res;
    }
}

class Solution {
    // refer STRIVER
    // OPTIMAL - Binary exponentiation
    // T: O(log n)
    // S: O(1)
    /*
     * 1. Instead of brute force multiplication, we split n/2 and x*x.
     * 2. For n = Even, `n = n/2 and x = x*x`
     * 3. For n = Odd, we split like, `res *= x and n -= 1;` 
     * 4. For -ve `n`, we divide the output by 1 in the end.
     * 5. if x == 0 -> res = 0;
     * 6. if n == 0 -> res = 1; 
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
