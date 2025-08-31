class Solution {
    // refer NEETCODE - all approaches
    // BRUTE FORCE - TLE
    // T: O(n^2), S: O(1)
    /*
     * Approach: Brute Force
     * For each element, calculate the product of all other elements.
     */
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];

        for (int i = 0; i < n; i++) {
            int product = 1;
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    continue;
                }
                product *= nums[j];
            }
            res[i] = product;
        }
        return res;
    }
}

class Solution {
    // BETTER
    // T: O(n), S: O(2n) = O(n)
    /*
     * Approach: Prefix and Suffix Products
     * Create two arrays to store the prefix and suffix products.
     * The final result for each element is the product of the corresponding
     * prefix and suffix products.
     */
    public int[] productExceptSelf(int[] nums) {

        int n = nums.length;
        int[] answer = new int[n];
        int[] lp = new int[n]; //left product = product of ele. before i
        int[] rp = new int[n]; //right product = product of ele. after i

        // handling corner cases
        lp[0] = 1;
        rp[n - 1] = 1;
        // creating left product array
        for (int i = 1; i < n; i++) {
            lp[i] = lp[i - 1] * nums[i - 1];
        }
        // creating right product array
        for (int i = n - 2; i >= 0; i--) {
            rp[i] = rp[i + 1] * nums[i + 1];
        }

        for (int i = 0; i < n; i++) {
            answer[i] = lp[i] * rp[i];
        }
        return answer;
    }
}

class Solution {
    // OPTIMAL
    // T: O(n), S: O(1) extra space
    // Since, only previous state is required, that is, lp[i-1] and rp[i+1]
    // we can track these with variables only. 
    public int[] productExceptSelf(int[] nums) {

        int n = nums.length;
        int[] answer = new int[n];
        int left = 1, right = 1;
        // initializing corner elements
        answer[0] = 1;
        answer[n - 1] = 1;

        // building answer with left product, that is answer[i] = product of num before i
        for (int i = 1; i < n; i++) {
            left = left * nums[i - 1];
            answer[i] = left;
        }
        // building answer with right product, that is, we have left product already in
        // answer[i], so multiply it with right product of i to get final answer
        for (int i = n - 2; i >= 0; i--) {
            right = right * nums[i + 1];
            answer[i] = answer[i] * right;
        }
        // this handles corner cases as answer[n-1] is updating in the first iteration
        // and ans[0] is updated in the second iteration
        return answer;
    }
}
