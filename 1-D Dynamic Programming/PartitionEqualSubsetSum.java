class Solution {
    // refer STRIVER - DP14 and DP15. lectures
    // BRUTE FORCE
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
     * 7. We have a subset satisfiying the condition if either by picking or by not picking the current element we can make a subset with sum = `target`. So, OR condition in `pick` and `notPick`.
     * 8. Base Cases: 
     * a) `target = 0` -> achieved the target, TRUE
     * b) at `i=0` -> target can be achieved only if `nums[0] = target`.

     * NOTE: 
     * 1. In array questions, to write a recurrence, rule of thumb: identify the changing states. One is index always and the other is generally the condition we want to satisfy. Here, the changing states are the current index `i` being considered and the `target` sum remaining.
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
