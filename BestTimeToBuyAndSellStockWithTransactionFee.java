class Solution {
    // refer STRIVER DP 40.
    // BRUTE FORCE - RECURSION - TLE
    // T:O(2^n)
    // S: O(n)
    /**
     * 1. Similar to Buy and Sell Stock - II. Unlimited transactions.
     * 2. Fee is charged when a transaction - 1 buy and 1 sell is completed.
     * 3. So, only change is in logic when we are calculating profit after sell.
     * 4. Fee will be subtracted when selling happens (or when buying happens, here we do it in selling),
     * so reduce it from the profit.
     * 5. Rest all same.
     * NOTE: we can reduce fee from "buy" stage as well. The gist is to pay the fee once during a transaction 
     * so, either at the time of buying or at the time of selling.
     */
    public int maxProfit(int[] prices, int fee) {
        return dfs(0, 1, fee, prices);
    }

    private int dfs(int i, int buy, int fee, int[] prices) {
        if (i == prices.length) {
            return 0;
        }

        int profit = 0;
        if (buy == 1) {
            profit = Math.max(-prices[i] + dfs(i + 1, 0, fee, prices), 
                                0 + dfs(i + 1, 1, fee, prices));
        } else {
            profit = Math.max(prices[i] - fee + dfs(i + 1, 1, fee, prices), 
                                0 + dfs(i + 1, 0, fee, prices));
        }
        return profit;
    }
}

class Solution {
    // refer STRIVER DP 40.
    // BETTER - MEMOIZATION
    // T:O(n*2);
    // S: O(n*2) + O(n);
    /**
     * 1. Standard memoization of the changing states/parameteres - `i, buy`
     * 2.`dp[n+1][2]`, n = prices.length
     */
    public int maxProfit(int[] prices, int fee) {
        int n = prices.length;
        int[][] dp = new int[n+1][2];
        for(int[] ar: dp){
            Arrays.fill(ar, -1);
        }
        return dfs(0, 1, fee, prices, dp);
    }

    private int dfs(int i, int buy, int fee, int[] prices, int[][] dp) {
        if (i == prices.length) {
            return 0;
        }

        if(dp[i][buy] != -1){
            return dp[i][buy];
        }

        int profit = 0;
        if (buy == 1) {
            profit = Math.max(-prices[i] + dfs(i + 1, 0, fee, prices, dp), 
                                0 + dfs(i + 1, 1, fee, prices, dp));
        } else {
            profit = Math.max(prices[i] - fee + dfs(i + 1, 1, fee, prices, dp), 
                                0 + dfs(i + 1, 0, fee, prices, dp));
        }
        return dp[i][buy] = profit;
    }
}

class Solution {
    // refer STRIVER DP 40.
    // BETTER - TABULATION
    // T:O(n*2);
    // S: O(n*2);
    /**
     * Convert to Tabulation:
     * 1. Copy the base case.
     * 2. Number of parameters in recurrence = Number of nested loops.
     * 3. Iterate in opposite order of recursion.
     * 4. Result is at the value of parameters from where the recursion was invoked. 
     * So, here `dp[0][1]` will have the result.
     */
    public int maxProfit(int[] prices, int fee) {
        int n = prices.length;
        int[][] dp = new int[n + 1][2];
        // skipping base case because array values are 0 by default in JAVA.
        
        for (int i = n - 1; i >= 0; i--) {
            for (int buy = 0; buy <= 1; buy++) {
                int profit = 0;
                if (buy == 1) {
                    profit = Math.max(-prices[i] + dp[i + 1][0],
                                        0 + dp[i + 1][1]);
                } else {
                    profit = Math.max(prices[i] - fee + dp[i + 1][1],
                                        0 + dp[i + 1][0]);
                }
                dp[i][buy] = profit;
            }
        }
        return dp[0][1];
    }
}

class Solution {
    // refer STRIVER DP 40.
    // OPTIMAL - TABULATION - SPACE OPTIMIZATION
    // T:O(n*2);
    // S: O(1) = O(2*2);
    /**
     * Space Optimization is applicable.
     * 1. We only depend on the results from the immediately next day (i+1).
     * 2. 'prev' array stores results for day i+1. 'curr' array is used to compute results for day i.
     * 3. After computing for day i into 'curr', 'curr' becomes 'prev' for the next iteration (day i-1),
     * and the old 'prev' array is reused as 'curr'. This is done via a reference swap.
     * 4. Transaction fee is applied upon selling or buying (here selling is chosen).
     * 5. After the loop, 'prev[1]' holds the max profit starting on day 0 in a 'can buy' state.
     * NOTE: we can remove the second loop as well because at a time, only buy = 0 or buy = 1 is executed.
     */
    public int maxProfit(int[] prices, int fee) {
        int n = prices.length;
        int[] prev = new int[2];
        int[] curr = new int[2];
        // skipping base case because array values are 0 by default in JAVA.

        for (int i = n - 1; i >= 0; i--) {
            // buy = 1
            curr[1] = Math.max(-prices[i] + prev[0],
                    0 + prev[1]);
            // buy = 0
            curr[0] = Math.max(prices[i] - fee + prev[1],
                    0 + prev[0]);
            int[] temp = prev;
            prev = curr;
            curr = temp;
        }
        return prev[1];
    }
}
