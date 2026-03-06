class Solution {
    // refer STRIVER - DP39.
    // similar to Stock II
    // RECURSION - TLE
    // T: O(2^n);
    // S: O(n);
    /**
     * 1. Approach same as Stock - II (LC. 122)
     * 2. Changes: After sell, we cannot buy on `i+1` because of cooldown
     * So, but we can buy from `i+2` . Therefore, change in the condition for Sell scenario.
     * 3. Base Case:
     * Now, `i` can increase to `prices.length + 1`, because of `i+2`
     * hence, change to `i >= n`.
     * 4. In base case, the profit is 0 only as cannot buy or sell.
     */
    public int maxProfit(int[] prices) {
        return dfs(0, 1, prices);
    }

    private int dfs(int i, int buy, int[] prices) {
        if (i >= prices.length) {
            return 0;
        }

        int profit = 0;
        if (buy == 1) {
            profit = Math.max(-prices[i] + dfs(i + 1, 0, prices),
                                0 + dfs(i + 1, 1, prices));
        } else {
            profit = Math.max(prices[i] + dfs(i + 2, 1, prices),
                                0 + dfs(i + 1, 0, prices));
        }
        return profit;
    }
}

class Solution {
    // refer STRIVER - DP39.
    // similar to Stock II
    // MEMOIZATION - TOP DOWN
    // T: O(n*2);
    // S: O(n*2) + O(n);
    /**
     * Standard Memoization - store the result before returning.
     */
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n+2][2];
        for(int[] ar: dp){
            Arrays.fill(ar, -1);
        }
        return dfs(0, 1, prices, dp);
    }

    private int dfs(int i, int buy, int[] prices, int[][] dp) {
        if (i >= prices.length) {
            return 0;
        }

        if(dp[i][buy] != -1){
            return dp[i][buy];
        }

        int profit = 0;
        if (buy == 1) {
            profit = Math.max(-prices[i] + dfs(i + 1, 0, prices, dp),
                                0 + dfs(i + 1, 1, prices, dp));
        } else {
            profit = Math.max(prices[i] + dfs(i + 2, 1, prices, dp),
                                0 + dfs(i + 1, 0, prices, dp));
        }
        return dp[i][buy] = profit;
    }
}

class Solution {
    // refer STRIVER - DP39.
    // similar to Stock II
    // TABULATION - BOTTOM UP
    // T: O(n*2);
    // S: O(n*2);
    /**
     * Convert the Recursion to tabulation: 
     * 1. Copy the base case.
     * 2. Number of states/parameters = number of nested loops
     * 3. Iterate in opposite direction of recursion.
     * 4. Copy the recurrence.
     * 5. Answer will be stored at the indices of `dp` array same as the recursion call parameters in recursion approach.
     * NOTE: Since JAVA has array values 0 by default, so we don't need to assign explicitly.
     */
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n + 2][2];

        for (int i = n - 1; i >= 0; i--) {
            for (int buy = 0; buy <= 1; buy++) {
                int profit = 0;
                if (buy == 1) {
                    profit = Math.max(-prices[i] + dp[i + 1][0],
                                        0 + dp[i + 1][1]);
                } else {
                    profit = Math.max(prices[i] + dp[i + 2][1],
                                        0 + dp[i + 1][0]);
                }
                dp[i][buy] = profit;
            }
        }

        return dp[0][1];
    }
}

class Solution {
    // refer STRIVER - DP39.
    // similar to Stock II
    // TABULATION - SPACE OPTIMIZED
    // T: O(n*2);
    // S: O(1); O(2*3) = O(1)
    /**
     * 1. Space can be optimized since we deoend on last 3 states here.
     * 2. curr - `i`, prev1 -> `i+1`, prev2 -> `i+2`
     * 3. Replace `dp[i] -> curr`, `dp[i+1] -> prev1`, `dp[i+2] -> prev2`.
     * 4. Swap the values after every iteration of inner loop as both buy and sell are explored at that time for the current index.
     */
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int[] prev2 = new int[2]; // i+2
        int[] prev1 = new int[2]; // i+1

        for (int i = n - 1; i >= 0; i--) {
            int[] curr = new int[2]; // i
            for (int buy = 0; buy <= 1; buy++) {
                int profit = 0;
                if (buy == 1) {
                    profit = Math.max(-prices[i] + prev1[0],
                            0 + prev1[1]);
                } else {
                    profit = Math.max(prices[i] + prev2[1],
                            0 + prev1[0]);
                }
                curr[buy] = profit;
            }
            // swap -> prev2 becomes prev1, prev1 becomes curr
            prev2 = prev1;
            prev1 = curr;
        }

        return prev1[1];
    }
}
