class Solution {
    // refer STRIVER - DP14 and DP15. lectures
    // BRUTE FORCE - Recursion
    // T: O(2^n); Two options at every stage -> pick, notPick and (nums.length = n) such stages.
    // S: O(n); - auxiliary space
    // THE MAIN PROBLEM IS - PARTITION ARRAY AND FIND IF ANY SUBSET GIVES SUM = `TARGET`
    public boolean canPartition(int[] nums) {
        // here target = totalSum / 2;
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        if (sum % 2 != 0) {
            return false; // odd sum, cannot partition to equal sum subset
        }

        return canPartitionTargetSumSubset(nums, sum / 2);
    }

    // THE MAIN PROBLEM ...
    /**
     * 1. Subsets, Subsequences and Subarrays expln- 
     * Subarray: contiguous sequence in an array. 
     * Subsequence: Need not to be contiguous, but maintains order.
     * Subset: Same as subsequence except it has empty set.
     * Subarray = n*(n+1)/2.
     * Subseqeunce = (2^n) -1 (non-empty subsequences).
     * Subset = 2^n.
     * 
     * 2. Subset Sum means we can generate all subsets recursively and then find if any of those satisfies has sum = `target`.
     * 3. But, if we only need sum, why generate subsets and store them, sum can be calculated on the fly when we are creating a subset. 
     * 4. To create a subset - 2 choices for every index, `pick` and `notPick`.
     * 5. `pick` -> `i-1`, and `target-nums[i]`
     * 6. `notPick` -> `i-1` and `target` (remains as it is). 
     * 7. We have a subset satisfiying the condition if either by picking or by not picking the current element we can make a subset with sum = `target`. 
     * So, OR condition in `pick` and `notPick`.
     * 8. Base Cases: 
     * a) `target = 0` -> achieved the target, TRUE
     * b) at `i=0`, can we achieve target = `nums[0]`.
     * 
     * NOTE: 
     * 1. In array questions, to write a recurrence, rule of thumb: identify the changing states. One is index always and the other is generally the condition we want to satisfy. 
     * Here, the changing states are the current index `i` being considered and the `target` sum remaining.
     * 2. I have done this from `i=n-1 to i=0`, but can be done reverse as well. 
     */
    private boolean canPartitionTargetSumSubset(int[] nums, int target) {
        int n = nums.length;
        return dfs(nums, n - 1, target);
    }

    private boolean dfs(int[] nums, int i, int target) {
        //base case
        if (target == 0) {
            return true;
        }

        if (i == 0) {
            return (nums[0] == target);
        }

        boolean pick = false;
        if (target >= nums[i]) {
            pick = dfs(nums, i - 1, target - nums[i]);
        }
        boolean notPick = dfs(nums, i - 1, target);
        return (pick | notPick);
    }
}

class Solution {
    // refer STRIVER - DP14 and DP15. lectures
    // BETTER - Memoization
    // T: O(n*target); Two options at every stage -> pick, notPick and (nums.length = n) such stages.
    // S: O(n*target) + O(n); dp array + auxiliary space
    // THE MAIN PROBLEM IS - PARTITION ARRAY AND FIND IF ANY SUBSET GIVES SUM = `TARGET`
    public boolean canPartition(int[] nums) {
        // here target = totalSum / 2;
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        if (sum % 2 != 0) {
            return false; // odd sum, cannot partition to equal sum subset
        }

        return canPartitionTargetSumSubset(nums, sum / 2);
    }

    // THE MAIN PROBLEM ...
    /**
     * 1. Memoize the recursion written before
     * 2. Cannot take an array of boolean because it will miss values - 
     * we need three states - -1(unvisited), 0(visited and false), 1(visited and true)
     * 3. Convert the return types to int.
     * 4. Save before returning
     */
    private boolean canPartitionTargetSumSubset(int[] nums, int target) {
        int n = nums.length;
        int[][] dp = new int[n][target+1];
        for (int[] ar : dp) {
            Arrays.fill(ar, -1);
        }
        return (dfs(nums, n - 1, target, dp) == 1);
    }

    private int dfs(int[] nums, int i, int target, int[][] dp) {
        //base case
        if (target == 0) {
            return 1;
        }

        if (i == 0) {
            return (nums[0] == target) ? 1 : 0;
        }

        if (dp[i][target] != -1) {
            return dp[i][target];
        }

        int pick = 0;
        if (target >= nums[i]) {
            pick = dfs(nums, i - 1, target - nums[i], dp);
        }
        int notPick = dfs(nums, i - 1, target, dp);
        return dp[i][target] = (pick | notPick);
    }
}

class Solution {
    // refer STRIVER - DP14 and DP15. lectures
    // BETTER - TABULATION
    // T: O(n*target); Two options at every stage -> pick, notPick and (nums.length = n) such stages.
    // S: O(n*target); dp array
    // THE MAIN PROBLEM IS - PARTITION ARRAY AND FIND IF ANY SUBSET GIVES SUM = `TARGET`
    public boolean canPartition(int[] nums) {
        // here target = totalSum / 2;
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        if (sum % 2 != 0) {
            return false; // odd sum, cannot partition to equal sum subset
        }

        return canPartitionTargetSumSubset(nums, sum / 2);
    }

    // THE MAIN PROBLEM ...
    /**
     * 1. Tabulation is Bottom-up approach.
     * 2. dp[i][j] = stores result of "can we we partition the array until `i` to achieve a target of `j`".
     * 3. Tabulation -> 
     * a) Bottom-up => build in the reverse order from recurion. Start from base case of recursion and go upto `n`.
     * b) number of states = number of nested loops, here, index and target => 2 nested loops.
     * c) recurrence relation as it is.
     * 4. Note: Base case changes a bit.
     * a) First - as it is, when the target is 0, it can be achieved by all.
     * b) Second - For `i=0`, in recursion, we had, `return (nums[0] == target)`
     * i.e, whether `nums[0]` can achieve the `target` remaining when index reaches `i=0`.
     * Here, it is different, but we carry the same sense as of recursion base case.
     * The target for `i=0`, we can achieve `target = nums[0]` if it within bounds of the array, i.e., if `nums[0] <= target`.
     */
    private boolean canPartitionTargetSumSubset(int[] nums, int target) {
        int n = nums.length;
        boolean[][] dp = new boolean[n][target + 1];

        // base case
        for (int i = 0; i < n; i++) {
            dp[i][0] = true;
        }

        if (nums[0] <= target) {
            dp[0][nums[0]] = true;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= target; j++) {
                boolean pick = false;
                if (j >= nums[i]) {
                    pick = dp[i - 1][j - nums[i]];
                }
                boolean notPick = dp[i - 1][j];
                dp[i][j] = (pick | notPick);
            }
        }
        return dp[n - 1][target];
    }
}

class Solution {
    // refer STRIVER - DP14 and DP15. lectures
    // OPTIMAL - TABULATION with SPACE OPTIMIZATION
    // T: O(n*target); Two options at every stage -> pick, notPick and (nums.length = n) such stages.
    // S: O(target); dp array
    // THE MAIN PROBLEM IS - PARTITION ARRAY AND FIND IF ANY SUBSET GIVES SUM = `TARGET`
    public boolean canPartition(int[] nums) {
        // here target = totalSum / 2;
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        if (sum % 2 != 0) {
            return false; // odd sum, cannot partition to equal sum subset
        }

        return canPartitionTargetSumSubset(nums, sum / 2);
    }

    // THE MAIN PROBLEM ...
    /**
     * 1. Improving the tabulation space.
     * 2. Space optimization is possible in cases when we are using only the previous state to calculate the answer.
     * 3. Here, we only need `i-1` to calculate for `i`.
     * 4. Hence, optimize space using `prev` -> `i-1` and `curr` -> `i`
     * 5. Base Case: 
     * a) First - The 0th column is false for both curr and prev by default in JAVA. So, set to true for both `prev` and `curr`. 
     * b) prev represents the 0th row
     * 6. Swap everytime because Java is pass by reference, it assignment will change reference only. Hence, we create a temp[] and swap.
     */
    private boolean canPartitionTargetSumSubset(int[] nums, int target) {
        int n = nums.length;
        boolean[] prev = new boolean[target+1];
        boolean[] curr = new boolean[target+1];

        // base case
        prev[0] = true;
        curr[0] = true;

        if (nums[0] <= target) {
            prev[nums[0]] = true;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= target; j++) {
                boolean pick = false;
                if (j >= nums[i]) {
                    pick = prev[j - nums[i]];
                }
                boolean notPick = prev[j];
                curr[j] = (pick | notPick);
            }
            // swap arrays
            boolean[] temp = prev;
            prev = curr;
            curr = temp;
        }
        return prev[target];
    }
}
