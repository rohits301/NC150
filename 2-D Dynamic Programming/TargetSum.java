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

class Solution {
    // refer NEETCODE - code
    // T: O(n*target); precise TC: O(n*(2*target+1))
    // S: O(n*target) + O(n); 
    /**
     * 1. Cannot map directly because target can be -ve as well.
     * 2. But, we have a range defined for target [-1000, 1000]. Hence, we can create an array to memoize.
     * 3. The max. target = totalSum, min. target = -totalSum.
     * 4. Range of target is 2*totalSum + 1.
     * 5. Hence, this our second dimension of the 2D array.
     * 6. `target` will be stored at column index-> `target + totalSum`.
     * 7. **NOTE**: In recursion tree, I found that the target can reach a value that is greater than totalSum because of the negative index call, so it is important to check whether the second dimension of array is valid before accessing it.
     * Thus, added check for index >= 0 && index < dp[0].length
     */
    public int findTargetSumWays(int[] nums, int target) {
        int n = nums.length;
        int totalSum = 0;
        for(int num: nums){
            totalSum += num;
        }
        int[][] dp = new int[n][2*totalSum + 1];
        for(int[] ar: dp){
            Arrays.fill(ar, Integer.MIN_VALUE);
        }
        return dfs(n-1, target, nums, totalSum, dp);
    }
    private int dfs(int i, int target, int [] nums, int totalSum, int[][] dp){
        // base case
        if(i == 0){
            int ways = 0;
            if(nums[0] == target){
                ways += 1;
            }
            if(-nums[0] == target){
                ways += 1;
            }
            return ways;
        }
        
        int index = totalSum + target;
        if(index >= 0 && index < dp[0].length && dp[i][index] != Integer.MIN_VALUE){
            return dp[i][index];
        }
        int plus = dfs(i-1, target - nums[i], nums, totalSum, dp);
        int negative = dfs(i-1, target - (-nums[i]), nums, totalSum, dp);

        if(index >= 0 && index < dp[0].length){
            dp[i][index] = (plus + negative);
        }
        return (plus + negative);
    }
}

class Solution {
    // refer STRIVER DP 17,18,21
    // BRUTE FORCE - RECURSION
    // T: O(2^n);
    // S: O(n);
    /**
     * 1. Think the problem like - we want to partition the array into two subsets such that the difference between the subsets = target.
     * 2. This is similar to Striver DP 18.
     * 3. So, two equations -> 
     * a) s1 + s2 = totalSum
     * b) s1 - s2 = target
     * Hence, s1 = (totalSum + target)/2;
     * s1 = subset sum of 1st partition, s2 = subset sum of 2nd partition.
     * 4. Problem reduces to find the number ways we can achieve subset sum = s1 for the given array.
     * 5. Like the previous method had an edge case if totalSum + target is out of bounds, similarly we have two edge cases here.
     * 6. Edge cases- 
     * a) totalSum + target > 2*totalSum, as the maximum possible sum is 2*totalSum. (REDUNDANT)
     * b) totalSum + target must be even. Otherwise partitions won't exist.
     * c) totalSum + target < 0. Cannot be achieved because this means the target is less than (-totalSum).
     * 7. Base case - 
     * i == 0 -> 
     * a) target is 0 and nums[0] = 0. Special case because when nums[0] = 0, we take into account both +0 and -0 cases. They form two subsets. E.g. nums = {0,0,2}, target = 2.
     * b) either the target has become 0 or nums[0] = target -> hence, target can be achieved, return 1.
     * c) else return 0.
     * 8. Recurrence- pick and notPick
     * pick -> move to next index and reduce target
     * not pick -> move to next with same target
     * NOTE: What if we have a `0` in nums but not at i=0.
     * E.g. nums = {1,0,2}, target = 1.
     * In this case, both pick and notPick calls at 0 are same so it is considered in both cases. Since we sum pick and notPick so we count the occurences of both +0 and -0.
     */
    public int findTargetSumWays(int[] nums, int target) {
        int n = nums.length;
        int totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }
        if ((totalSum + target) > 2 * totalSum ||
            (totalSum + target) % 2 != 0 || 
            (totalSum + target) < 0) {
            return 0;
        }

        int k = (totalSum + target) / 2;

        return dfs(n - 1, k, nums);
    }

    private int dfs(int i, int k, int[] nums) {
        // base case
        if (i == 0) {
            if (k == 0 && nums[0] == 0) {
                return 2;
            }
            if (k == 0 || nums[0] == k) {
                return 1;
            }
            return 0;
        }

        int pick = 0;
        if (k >= nums[i]) {
            pick = dfs(i - 1, k - nums[i], nums);
        }
        int notPick = dfs(i - 1, k, nums);

        return (pick + notPick);
    }
}

class Solution {
    // refer STRIVER DP 17,18,21
    // BETTER - MEMOIZATION
    // T: O(n*target);
    // S: O(n*target) + O(n);
    /**
     * 1. Standard memoization.
     * 2. Initialize with -1 as ways cannot be negative.
     */
    public int findTargetSumWays(int[] nums, int target) {
        int n = nums.length;
        int totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }
        if ((totalSum + target) > 2 * totalSum ||
            (totalSum + target) % 2 != 0 || 
            (totalSum + target) < 0) {
            return 0;
        }

        int k = (totalSum + target) / 2;
        int[][] dp = new int[n][k + 1];
        for(int[] ar: dp){
            Arrays.fill(ar, -1);
        }

        return dfs(n - 1, k, nums, dp);
    }

    private int dfs(int i, int k, int[] nums, int[][] dp) {
        // base case
        if (i == 0) {
            if (k == 0 && nums[0] == 0) {
                return 2;
            }
            if (k == 0 || nums[0] == k) {
                return 1;
            }
            return 0;
        }

        if(dp[i][k] != -1){
            return dp[i][k];
        }
        int pick = 0;
        if (k >= nums[i]) {
            pick = dfs(i - 1, k - nums[i], nums, dp);
        }
        int notPick = dfs(i - 1, k, nums, dp);

        return dp[i][k] = (pick + notPick);
    }
}

class Solution {
    // refer STRIVER DP 17,18,21
    // TABULATION
    // T: O(n*k);
    // S: O(n*k);
    /**
     * 1. Base case: 
     * Case 1: nums[0] is 0
     * We can get sum 0 in two ways: (+0) or (-0).
     * This means, for the 0th element, if it's a 0,
     * it contributes 2 ways to reach a sum of 0.
     * Case 2: nums[0] is not 0
     * To get sum 0 using only nums[0] (which is not 0), we must NOT pick it.
     * So, 1 way to get sum 0.
     * If nums[0] itself equals the target sum 'j' (only for j = nums[0]),
     * there's 1 way (by picking nums[0]).
     */
    public int findTargetSumWays(int[] nums, int target) {
        int n = nums.length;
        int totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }
        if ((totalSum + target) > 2 * totalSum ||
            (totalSum + target) % 2 != 0 ||
            (totalSum + target) < 0) {
            return 0;
        }

        int k = (totalSum + target) / 2;
        int[][] dp = new int[n][k + 1];
        // base case
        if (nums[0] == 0) {
            dp[0][0] = 2;
        } else {
            // nums[0] != 0
            dp[0][0] = 1;
            if (nums[0] <= k) {
                dp[0][nums[0]] = 1;
            }
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= k; j++) {
                int pick = 0;
                if (j >= nums[i]) {
                    pick = dp[i - 1][j - nums[i]];
                }
                int notPick = dp[i - 1][j];
                dp[i][j] = (pick + notPick);
            }
        }
        return dp[n - 1][k];
    }
}

class Solution {
    // refer STRIVER DP 17,18,21
    // TABULATION - SPACE OPTIMIZATION
    // T: O(n*target);
    // S: O(target);
    /**
     * 1. Standard conversion
     */
    public int findTargetSumWays(int[] nums, int target) {
        int n = nums.length;
        int totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }
        if ((totalSum + target) > 2 * totalSum ||
            (totalSum + target) % 2 != 0 ||
            (totalSum + target) < 0) {
            return 0;
        }

        int k = (totalSum + target) / 2;
        int[] prev = new int[k+1];
        int[] curr = new int[k+1];
        // base case
        if (nums[0] == 0) {
            prev[0] = 2;
        } else {
            // nums[0] != 0
            prev[0] = 1;
            if (nums[0] <= k) {
                prev[nums[0]] = 1;
            }
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= k; j++) {
                int pick = 0;
                if (j >= nums[i]) {
                    pick = prev[j - nums[i]];
                }
                int notPick = prev[j];
                curr[j] = (pick + notPick);
            }
            int[] temp = prev;
            prev = curr;
            curr = temp;
        }
        return prev[k];
    }
}
