class Solution {
    // refer NEETCODE
    // BRUTE FORCE - TLE
    // T: O(2^n)
    // S: O(n) - stack space
    /*
     * At every index, we have two choices - either take 1 step or 2 step
     * So, we try out all possibilities.
     * And, we proceed with whichever is minimum of the two.
     * Base case: `i >= cost.length` as we stop at `cost.length`, but
     * since, two steps so it can reach more than `cost.length` also.
     * Ans = min(dfs from 0, dfs from 1), since both 0 and 1 can be starting points.
     */
    public int minCostClimbingStairs(int[] cost) {
        return Math.min(dfs(cost, 0), dfs(cost, 1));
    }

    private int dfs(int[] cost, int i){
        if(i >= cost.length){
            return 0;
        }

        int minCost = 0;
        minCost += cost[i] + Math.min(dfs(cost, i + 1), dfs(cost, i + 2));
        return minCost; 
    }
}

class Solution {
    // refer NEETCODE
    // BETTER - MEMOIZATION
    // T: O(n)
    // S: O(n) - dp array
    /*
     * APPROACH - same, just use array to store result
     * At every index, we have two choices - either take 1 step or 2 step
     * So, we try out all possibilities.
     * And, we proceed with whichever is minimum of the two.
     * Base case: `i >= cost.length` as we stop at `cost.length`, but
     * since, two steps so it can reach more than `cost.length` also.
     * Ans = min(dfs from 0, dfs from 1), since both 0 and 1 can be starting points.
     */
    public int minCostClimbingStairs(int[] cost) {
        int[] dp1 = new int[cost.length + 1];
        int[] dp2 = new int[cost.length + 1];
        Arrays.fill(dp1, -1);
        Arrays.fill(dp2, -1);

        return Math.min(dfs(cost, 0, dp1), dfs(cost, 1, dp2));
    }

    private int dfs(int[] cost, int i, int[] dp){
        if(i >= cost.length){
            return 0;
        }

        if(dp[i] != -1){
            return dp[i];
        }

        int minCost = 0;
        minCost += cost[i] + Math.min(dfs(cost, i + 1, dp), dfs(cost, i + 2, dp));
        return dp[i] = minCost;
    }
}

class Solution {
    // refer NEETCODE
    // BETTER - TABULATION
    // T: O(n)
    // S: O(n) - dp array
    /*
     * APPROACH - 
     * Building upon the recursion
     * The base case is when we reach either `n` or `n+1` in case of two steps. 
     * `dp[n] = 0, dp[n+1] = 0;` (don't move, no cost for standing on the top)
     * Build backwards.
     * `dp[i]` = cost of reaching ith index
     * hence, `dp[i] = cost[i] + min(dp[i+1], dp[i+2])`
     * i.e., the cost of reaching the ith step + minimum cost considering 1 step and 2 step
     * Ans = `Min(dp[0], dp[1])` because we can start from 0 or 1.
     *
     * eg. cost = [10,15,30,5,40]
     */
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        int[] dp = new int[n + 2];
        dp[n] = 0;
        dp[n + 1] = 0;
        
        for(int i=n-1; i >= 0; i--){
            dp[i] = cost[i] + Math.min(dp[i + 1], dp[i + 2]);
        }

        return Math.min(dp[0], dp[1]);
    }
}

class Solution {
    // refer NEETCODE
    // OPTIMAL - SPACE OPTIMIZED
    // T: O(n)
    // S: O(1)
    /*
     * APPROACH - 
     * Building upon the tabulation
     * we only need to track `prev` and `prev2` like fibonacci
     * hence, array is not required.
     * Also, since answer is formed using last two values.
     * Here, we are iterating from back always because the answer for later part is calculated first.
     * Ans = Math.min(prev, prev2); // the eqv. of dp[0], dp[1]
     * 
     * eg. cost = [10,15,30,5,40]
     */
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        int prev = 0; // dp[i+1]
        int prev2 = 0; // dp[i+2]
        
        for(int i = n - 1; i >= 0; i--){
            int temp = cost[i] + Math.min(prev, prev2);
            prev2 = prev;
            prev = temp;
        }

        return Math.min(prev, prev2);
    }
}
