class Solution {
    // refer STRIVER DP 22.
    // BRUTE FORCE - RECURSION
    // T: exponential; O(2^amount), worst case: the length of the tree is amount.
    // S: O(amount); the auxiliary stack space is upto amount.
    /**
     * 1. DP on subsequences as every stage, we have two choices - take and notTake.
     * 2. Given, the denominations of coins >= 1, and and we can take one coin unlimited times.
     * 3. Explore all possible ways to find number of ways.
     * a) Express everything in terms of index and amount(target).
     * b) Explore all possibilites -> take and notTake
     * c) Sum all possibilites and return.
     * 4. Starting from `i=n-1` and `target=amount`.
     * 5. take -> if the `target >= coins[i]`, then we take `coins[i]` and invoke the recursion with the same index and reduced target. 
     * 6. notTake -> invoke the recursion for next index and target remains as it is. The next index is `i-1`, since we start from back.
     * 7. Base case: 
     * When `i==0`, the target can be achieved only if it is a multiple of the `coins[0]`.
     */
    public int change(int amount, int[] coins) {
        int n = coins.length;
        return dfs(n-1, amount, n, coins);
    }

    private int dfs(int i, int target, int n, int[] coins){
        if(i == 0){
            return (target % coins[0] == 0)? 1 : 0;
        }
        
        int take = 0;
        if(target >= coins[i]){
            take = dfs(i, target - coins[i], n, coins);
        }
        int notTake = dfs(i-1, target, n, coins);

        return (take + notTake);
    }

}

class Solution {
    // refer STRIVER DP 22.
    // BETTER - MEMOIZATION
    // T:O(n*amount);
    // S: O(n*amount) + O(amount); the auxiliary stack space is upto amount.
    /**
     * 1. Standard memoization.
     * 2. Changing parameters - index, amount
     * 3. Size of dp -> n+1, amount+1
     */
    public int change(int amount, int[] coins) {
        int n = coins.length;
        int[][] dp = new int[n+1][amount+1];
        for(int[] ar: dp){
            Arrays.fill(ar, -1);
        }
        return dfs(n-1, amount, n, coins, dp);
    }

    private int dfs(int i, int target, int n, int[] coins, int[][] dp){
        // base case
        if(i == 0){
            return (target % coins[0] == 0)? 1 : 0;
        }
        
        if(dp[i][target] != -1){
            return dp[i][target];
        }
        int take = 0;
        if(target >= coins[i]){
            take = dfs(i, target - coins[i], n, coins, dp);
        }
        int notTake = dfs(i-1, target, n, coins, dp);

        return dp[i][target] = (take + notTake);
    }

}

class Solution {
    // refer STRIVER DP 22.
    // BETTER - TABULATION
    // T:O(n*amount);
    // S: O(n*amount);
    /**
     * Tabulation - Bottom Up.
     * 1. Copy the base case.
     * 2. The number of changing parameters = number of nested loops.
     * 3. Iterate in opposite direction of recursion.
     * 4. Copy the recurrence.
     * 5. Result obtained at same index as the main recursion invocation.
     */
    public int change(int amount, int[] coins) {
        int n = coins.length;
        int[][] dp = new int[n][amount + 1];

        // base case
        for (int target = 0; target <= amount; target++) {
            if (target % coins[0] == 0) {
                dp[0][target] = 1;
            }
        }

        // recurrence
        for (int i = 1; i < n; i++) {
            for (int target = 0; target <= amount; target++) {
                int take = 0;
                if (target >= coins[i]) {
                    take = dp[i][target - coins[i]];
                }
                int notTake = dp[i - 1][target];

                dp[i][target] = (take + notTake);
            }
        }
        return dp[n - 1][amount];
    }
}

class Solution {
    // refer STRIVER DP 22.
    // OPTIMAL - TABULATION - SPACE OPTIMIZATION
    // T:O(n*amount);
    // S: O(amount);
    /**
     * Space optimization is applicable
     * 1. We are using only `i` and `i-1` to build result.
     * 2. `dp[i] -> curr` and `dp[i-1] -> prev`.invocation.
     * 3. Swap `prev` and `curr`
     */
    public int change(int amount, int[] coins) {
        int n = coins.length;
        int[] prev = new int[amount + 1];
        int[] curr = new int[amount + 1];

        // base case
        for (int target = 0; target <= amount; target++) {
            if (target % coins[0] == 0) {
                prev[target] = 1;
            }
        }

        // recurrence
        for (int i = 1; i < n; i++) {
            for (int target = 0; target <= amount; target++) {
                int take = 0;
                if (target >= coins[i]) {
                    take = curr[target - coins[i]];
                }
                int notTake = prev[target];

                curr[target] = (take + notTake);
            }
            int[] temp = prev;
            prev = curr;
            curr = temp;
        }
        return prev[amount];
    }
}
