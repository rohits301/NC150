class Solution {
    // refer NEETCODE for code
    // BRUTE FORCE - RECURSION (TLE)
    // T: O(n*n); Exponentail, every index can have max. n jumps possible
    // S: O(n); stack space
    /**
     * 1. Building on jump game - I recursion.
     * 2. We need number of jumps. So count recursion.
     * 3. Every time we jump, we add 1 for the jump and invoke dfs(jump, n, nums) to solve the remaining problem.
     * 4. The dfs() gives us the minimum jumps required to reach `n-1` from `i`.
     * 4. Base case: when `i==n-1`, we don't need any more jumps. So, the minimum jumps to reach `n-1` from `n-1` is 0.
     * 5. Edge case: when nums[i] = 0, we cannot jump so, minimum jump = `maxValue`. So, it doesn't impact our minimum if we have a smaller value. 
     * So, we can either handle it directly also, but it is already handled in the code.
     */
    public static final int maxValue = 1000000; // can be anything >= nums.length as max jumps can be nums.length

    public int jump(int[] nums) {
        int n = nums.length;
        return dfs(0, n, nums);
    }

    private int dfs(int i, int n, int[] nums) {
        if (i == n - 1) {
            return 0;
        }

        // if(nums[i] == 0){
        //     return maxValue;
        // }

        int end = Math.min(n - 1, i + nums[i]);

        int min = maxValue;
        for (int jump = i + 1; jump <= end; jump++) {
            int count = 1 + dfs(jump, n, nums);
            min = Math.min(min, count);
        }
        return min;
    }
}

class Solution {
    // refer NEETCODE for code
    // BETTER - MEMOIZATION
    // T: O(n^2); every index can have n jumps available
    // S: O(n) + O(n); dp array + stack space
    /**
     * 1. Standard memoization
     */
    public static final int maxValue = 1000000;
    
    public int jump(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        return dfs(0, n, nums, dp);
    }

    private int dfs(int i, int n, int[] nums, int[] dp) {
        if (i == n - 1) {
            return 0;
        }

        // if(nums[i] == 0){
        //     return maxValue;
        // }

        if(dp[i] != -1){
            return dp[i];
        }
        int end = Math.min(n - 1, i + nums[i]);

        int min = maxValue;
        for (int jump = i + 1; jump <= end; jump++) {
            int count = 1 + dfs(jump, n, nums, dp);
            min = Math.min(min, count);
        }
        return dp[i] = min;
    }
}

class Solution {
    // refer NEETCODE for code
    // BETTER - TABULATION
    // T: O(n^2); every index can have n jumps available
    // S: O(n); dp array
    /**
     * 1. Copy the base case.
     * 2. Iterate in opposite order of recursion.
     * 3. Copy the recurrence.
     * 4. Answer at same indices as that of recursion invocation.
     */
    public static final int maxValue = 1000000;

    public int jump(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];

        dp[n - 1] = 0;
        for (int i = n - 2; i >= 0; i--) {
            int end = Math.min(n - 1, i + nums[i]);
            int min = maxValue;
            
            for (int jump = i + 1; jump <= end; jump++) {
                int count = 1 + dp[jump];
                min = Math.min(min, count);
            }
            dp[i] = min;
        }
        return dp[0];
    }
}

class Solution {
    // refer NEETCODE explanation
    // OPTIMAL - GREEDY/BFS APPROACH
    // T: O(n); 
    // each array index is visited once.
    // nested loops but every time we slide and there can be maximum `n` windows, each of length 1.
    // S: O(1); 
    /**
     * 1. Instead of recursively finding all paths and then getting the minimum jumps path, we have a greedy solution.
     * 2. What if, if we find the range that we can have from each index. 
     * E.g. nums[i] = 2 -> farthest I can go is i+2.
     * so the range or the window is `i+1 to i+2`.
     * 3. Similarly, we find the range for all indices.
     * Shifting the indices -> `left` = start of range, it is set as right + 1 for the next time.
     * `right` represents the farthest we can reach from the range.
     * 4. This algorithm is possible because we are guaranteed to reach `n-1`.
     * So, even if we encounter a nums[i] = 0, there will be nums[i-1] that will help cross that 0.
     */
    public int jump(int[] nums) {
        int n = nums.length;

        int left = 0, right = 0, res = 0;

        while (right < n - 1) {
            int farthest = 0;
            for (int i = left; i <= right; i++) {
                farthest = Math.max(farthest, i + nums[i]);
            }
            left = right + 1;
            right = farthest;
            res += 1;
        }
        return res;
    }
}
