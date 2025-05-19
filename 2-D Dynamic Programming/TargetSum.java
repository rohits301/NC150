class Solution {
    // refer NEETCODE - code
    // refer STRIVER - DP 17,18,21
    // T: O(2^n);
    // S: O(n);
    /**
     * 1. Either adding a plus sign can make sum = target or adding a negative sign make sum = target or both can make sum = target.
     * So, explore all possibilites. Hence, RECURSION.
     * 2. For every `i`, explore adding a `+` sign and `-` sign.
     * 3. Starting from back, we want to achieve target with n-1 elements.
     * 4. Plus -> reduce target by `nums[i]`.
     * 5. Negative -> reduce target by `-nums[i]`.
     * 6. Result is the sum of both these ways.
     * 7. **Base case**: 
     * When `i == 0`, the number of ways = sum of ways of adding a plus sign and ways of adding a negative sign.
     * At i=0, only nums[0] is the element, so take both cases for it.
     * This handles case when nums[i] = 0.
     * e.g. {0, 0, 2}, target = 2
     * No. of ways -> 4
     * 0 + 0 + 2
     * 0 - 0 + 2
     * - 0 + 0 + 2
     * - 0 - 0 + 2
     * Hence, for zero as well we are counting twice and both are treated as different expressions.
     */
    public int findTargetSumWays(int[] nums, int target) {
        int n = nums.length;
        return dfs(n - 1, target, nums);
    }

    private int dfs(int i, int target, int[] nums) {
        // base case
        if (i == 0) {
            int ways = 0;
            if (nums[0] == target) {
                ways += 1;
            }
            if (-nums[0] == target) {
                ways += 1;
            }
            return ways;
        }

        int plus = dfs(i - 1, target - nums[i], nums);
        int negative = dfs(i - 1, target - (-nums[i]), nums);

        return (plus + negative);
    }
}

