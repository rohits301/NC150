class Solution {
    // refer STRIVER DP.36
    // BRUTE FORCE - RECURSION
    // T: O(2^n); exponential, two decision at every index
    // S: O(n); auxiliary stack space
    /**
     * 1. Variation from I: Can buy and sell unlimited number of times. But, can only sell after buying. And only buy after selling.
     * 2. Hence, if I have stock, I can only sell it. If don't have stock, I can buy it.
     * 3. To find maximum profit by buying and selling. I have to explore all possibilities. So, recursion.
     * 4. At every index, 
     * a) I can either buy or not buy if I am eligible to buy. 
     * b) I can sell or not sell if I am eligible to sell.
     * 5. The eligibility is tracked with `buy` flag.
     * Recurrence - 
     * 6. If `buy` is `1`, I can buy on `ith` day. 2 choices - 
     * If I buy stock, then profit made is `-prices[i]` because profit comes from selling, not buying. Invoke dfs for the next day with `buy` set to `0` as I have bought stock.
     * If I choose to not buy at `i`, then I can buy later `i+1`, so `buy` is set to `1` only. Invoke dfs for the next day with `buy` set to `1` to enable future purchase.
     * 7. If `buy` is `0`, I can sell on `ith day`. 2 choices - 
     * If I sell on `ith` day, I make profit = `prices[i]`. Invoke dfs for the next day with `buy` as `1`, as I have sold the stock and can buy more.
     * If I don't sell on `ith` day, I don't make any profit, and I can sell in future. Invoke dfs with `buy` as `0`.
     * 8. Base case - 
     * When I have reached the end of the array (`prices.length`), doesn't matter if I have the stock or not, I cannot sell it to make profit, so my profit is 0. 
     * NOTE: The choice `buy` and not buy are mutually exclusive, so only one happens at a time. Hence, we don't add profit of `if` and `else` cases.
     */
    public int maxProfit(int[] prices) {
        return dfs(0, 1, prices);
    }

    private int dfs(int i, int buy, int[] prices){
        // base case
        if(i == prices.length){
            return 0;
        }

        int profit = 0;
        if(buy == 1){
            // buy on ith day
            // calls similar to take and notTake subsets problems
            profit = Math.max(-prices[i] + dfs(i+1, 0, prices),
                                0 + dfs(i+1, 1, prices));
        } else {
            profit = Math.max(prices[i] + dfs(i+1, 1, prices),
                                0 + dfs(i+1, 0, prices));
        }
        return profit;
    }
}

class Solution {
    // refer STRIVER DP.36
    // BETTER - MEMOIZATION
    // TOP-DOWN
    // T: O(n*2); two decision at every index
    // S: O(n*2) + O(n); dp array + auxiliary stack space
    /**
     * 1. Cache the recursion result in a 2D array.
     * 2. 2D array because - we have two states, index and buy flag.
     */
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n+1][2];
        for(int[] ar: dp){
            Arrays.fill(ar, -1);
        }

        return dfs(0, 1, prices, dp);
    }

    private int dfs(int i, int buy, int[] prices, int[][] dp){
        // base case
        if(i == prices.length){
            return 0;
        }

        if(dp[i][buy] != -1){
            return dp[i][buy];
        }

        int profit = 0;
        if(buy == 1){
            // buy on ith day
            // calls similar to take and notTake subsets problems
            profit = Math.max(-prices[i] + dfs(i+1, 0, prices, dp),
                                0 + dfs(i+1, 1, prices, dp));
        } else {
            profit = Math.max(prices[i] + dfs(i+1, 1, prices, dp),
                                0 + dfs(i+1, 0, prices, dp));
        }
        return dp[i][buy] = profit;
    }
}

class Solution {
    // refer STRIVER DP.36
    // BETTER - TABULATION
    // BOTTOM-UP
    // T: O(n*2); two decision at every index
    // S: O(n*2); dp array 
    /**
     * 1. Convert to tabulation - 
     * a) Bottom - up -> In opposite direction of recursion
     * b) Base case - same.
     * c) The number of states = number of nested loops.
     * d) Copy the recurrence
     * 2. Applying these - 
     * a) n to 0.
     * b) Base case - when `i==n`, we don't have any stock, so `profit = 0`. Both `buy - dp[n][1]` and `sell - dp[n][0]` will be set to 0.
     * c) dp[n+1][2] - outer loop on `i` and inner loop on `buy`. Can exchange the outer and inner as well. (Both works)
     * d) Same recurence, replace `dfs` with `dp[][]`.
     * 3. Each cell represents - 
     * dp[i][0] = max profit if I sell or not sell on ith day
     * dp[i][1] = max profit if I buy or not buy on ith day
     * 4. IMPORTANT - Result at the end is whatever profit I made by selling the last of stocks and now I'm in eligible to buy state. So, result = `dp[0][1]`.
     */
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n+1][2];
        dp[n][0] = 0;
        dp[n][1] = 0;

        for(int i = n - 1; i >= 0; i--){
            for(int buy = 0; buy < 2; buy++){
                int profit = 0;
                if(buy == 1){
                    profit = Math.max(-prices[i] + dp[i+1][0],
                                        0 + dp[i+1][1]);
                } else {
                    profit = Math.max(prices[i] + dp[i+1][1],
                                        0 + dp[i+1][0]);
                }
                dp[i][buy] = profit;
            }
        }
        return dp[0][1];
    }
}

class Solution {
    // refer STRIVER DP.36
    // OPTIMAL - TABULATION (SPACE OPTIMIZED)
    // T: O(n*2); two decision at every index
    // S: O(1); dp array of size 2, total 4 units of space.
    /**
     * 1. Space optimization because - 
     * the result is dependent only on the previous row, which is,
     * `i+1`, because iterate from end of the array.
     * 2. Current state = `i` and previous state = `i+1`.
     * 3. Both previous and curr have total 4 states ->
     * prev[0], prev[1] -> prev sell, prev buy
     * curr[0], curr[1] -> curr sell, curr buy
     * 4. `dp[i][0]` -> curr[0] and `dp[i+1][0]` -> `prev[0]`.
     * 5. Result = `prev[1]` because `dp[0] -> prev`
     */
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int[] prev = new int[2];
        int[] curr = new int[2];
        prev[0] = 0;
        prev[1] = 0;
        curr[0] = 0;
        curr[1] = 0;

        for(int i = n - 1; i >= 0; i--){
            for(int buy = 0; buy < 2; buy++){
                int profit = 0;
                if(buy == 1){
                    profit = Math.max(-prices[i] + prev[0],
                                        0 + prev[1]);
                } else {
                    profit = Math.max(prices[i] + prev[1],
                                        0 + prev[0]);
                }
                curr[buy] = profit;
            }
            int[] temp = prev;
            prev = curr;
            curr = temp;
        }
        return prev[1];
    }
}
