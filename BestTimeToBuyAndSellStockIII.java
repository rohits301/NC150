class Solution {
    // refer STRIVER - DP 37.
    // BRUTE FORCE - RECURSION
    // T: O(2^n)
    // S: O(1)
    /**
     * 1. Refer LC.122 Best Time to Buy and Sell II for explanation.
     * 2. This has a capacity factor, `cap = 2`. This makes it similar to the unbounded knapsack problem.
     * 3. So, we pass capacity as well because it is affected by a "buy and a sell".
     * 4. We pass `cap=2` as atmost 2 transactions allowed.
     * 5. Capacity only changes when we do a sell. Because as per the question, we can do 2 transactions, where a transaction = buy and sell.
     * 6. Additional Base case: 
     * If `cap` is 0, then, we cannot do any more transactions, so profit cannot be made when `cap=0`, so return 0.
     */
    public int maxProfit(int[] prices) {
        return dfs(0, 1, 2, prices);
    }

    private int dfs(int i, int buy, int cap, int[] prices){
        if(i == prices.length){
            return 0;
        }
        
        if(cap == 0){
            return 0;
        }

        int profit = 0;
        if(buy == 1){
            profit = Math.max(-prices[i] + dfs(i+1, 0, cap, prices),
                                0 + dfs(i+1, 1, cap, prices));
        } else {
            profit = Math.max(prices[i] + dfs(i+1, 1, cap - 1, prices),
                                0 + dfs(i+1, 0, cap, prices));
        }
        return profit;
    }
}

class Solution {
    // refer STRIVER - DP 37.
    // BETTER - MEMOIZATION
    // TOP-DOWN
    // T: O(n*2*3)
    // S: O(n*2*3) + o(n)
    /**
     * 1. Memoizing the recursion requires maintaing an array of 
     * size = (n+1)*2*3. Because, n states of `i`, 2 states of `buy` - 0,1, 3 states of `cap` - 0,1,2.
     * 2. Initialize the 3D array with -1. Basically the 3D array is `n+1` 2D arrays of (2*3) dimension.
     * 3. Return the result if already present.
     */
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int[][][] dp = new int[n+1][2][3];
        for(int[][] array2D: dp){
            for(int[] array1D: array2D){
                Arrays.fill(array1D, -1);
            }
        } 
        return dfs(0, 1, 2, prices, dp);
    }

    private int dfs(int i, int buy, int cap, int[] prices, int[][][] dp){
        if(i == prices.length){
            return 0;
        }
        
        if(cap == 0){
            return 0;
        }

        if(dp[i][buy][cap] != -1){
            return dp[i][buy][cap];
        }

        int profit = 0;
        if(buy == 1){
            profit = Math.max(-prices[i] + dfs(i+1, 0, cap, prices, dp),
                                0 + dfs(i+1, 1, cap, prices, dp));
        } else {
            profit = Math.max(prices[i] + dfs(i+1, 1, cap - 1, prices, dp),
                                0 + dfs(i+1, 0, cap, prices, dp));
        }
        return dp[i][buy][cap] = profit;
    }
}

class Solution {
    // refer STRIVER - DP 37.
    // BETTER - TABULATION
    // BOTTOM-UP
    // T: O(n*2*3)
    // S: O(n*2*3)
    /**
     * 1. Tabulation - Bottom-up, in opposite order of recursion.
     * Steps:
     * 2. Base case as it is.
     * 3. Number of states = number of nested loops. Write these parameters in reverse order. 
     * So, `i` goes from 0 to `n` in recursion, hence `n` to 0 in tabulation.
     * Similarly, buy from 0 to 1.
     * Similarly, cap from 0 to 2.
     * 4. Copy the recurrence.
     * NOTE: 
     * a) We can skip the base case loops in JAVA because all array values are initialized to 0 only by default.
     * b) The inner loop for `cap` will start from 1 because `cap = 0` is base case.
     */
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int capacity = 2;
        int[][][] dp = new int[n + 1][2][capacity + 1];

        // base case
        // for (int buy = 0; buy <= 1; buy++) {
        //     for (int cap = 0; cap <= capacity; cap++) {
        //         dp[n][buy][cap] = 0;
        //     }
        // }

        // for (int i = 0; i <= n; i++) {
        //     for (int buy = 0; buy <= 1; buy++) {
        //         dp[i][buy][0] = 0;
        //     }
        // }

        // recurrence
        for (int i = n - 1; i >= 0; i--) {
            for (int buy = 0; buy <= 1; buy++) {
                for (int cap = 1; cap <= 2; cap++) {
                    int profit = 0;
                    if (buy == 1) {
                        profit = Math.max(-prices[i] + dp[i + 1][0][cap],
                                0 + dp[i + 1][1][cap]);
                    } else {
                        profit = Math.max(prices[i] + dp[i + 1][1][cap - 1],
                                0 + dp[i + 1][0][cap]);
                    }
                    dp[i][buy][cap] = profit;
                }
            }
        }
        return dp[0][1][2];
    }
}

class Solution {
    // refer STRIVER - DP 37.
    // OPTIMAL - TABULATION (SPACE OPTIMIZED)
    // BOTTOM-UP
    // T: O(n*2*3)
    // S: O(1) = O(2*3)
    /**
     * 1. Building upon previous tabulation, space optimization is possible because we are using only `i+1`, that is the previous state when traverse from `i=n-1 to 0`.
     *  2. So, with 'prev' (representing dp[i+1]) and 'curr' (representing dp[i]), we can save space.
     * 3. `prev` -> dp[i+1][buy_state][capacity_remaining]
     * 4. `curr` -> dp[i][buy_state][capacity_remaining]
     * NOTE: The `curr` array should store the DP states for the current day `i` for both `buy=0` and `buy=1`. Therefore, it should be initialized once per day `i`, i.e., outside the `buy` loop but inside the `i` loop.
     * Hence, we swap `curr` and `prev` after `buy` loop ends
     */
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int capacity = 2;
        int[][] prev = new int[2][capacity+1];
        int[][] curr = new int[2][capacity+1];

        // base case
        // for (int buy = 0; buy <= 1; buy++) {
        //     for (int cap = 0; cap <= capacity; cap++) {
        //         dp[n][buy][cap] = 0;
        //     }
        // }

        // for (int i = 0; i <= n; i++) {
        //     for (int buy = 0; buy <= 1; buy++) {
        //         dp[i][buy][0] = 0;
        //     }
        // }

        // recurrence
        for (int i = n - 1; i >= 0; i--) {
            for (int buy = 0; buy <= 1; buy++) {
                for (int cap = 1; cap <= 2; cap++) {
                    int profit = 0;
                    if (buy == 1) {
                        profit = Math.max(-prices[i] + prev[0][cap],
                                0 + prev[1][cap]);
                    } else {
                        profit = Math.max(prices[i] + prev[1][cap - 1],
                                0 + prev[0][cap]);
                    }
                    curr[buy][cap] = profit;
                }
            }
            int[][] temp = prev;
            prev = curr;
            curr = temp;
        }
        return prev[1][2];
    }
}

class Solution {
    // refer STRIVER - DP 37.
    // ANOTHER APPROACH
    // TABULATION 
    // T: O(n * 4); 
    // S: O(n * 4); for the DP table 
    /** 
     * 1. Consider each buy and sell as 1 transaction, so capacity = 2
     * implies, 4 transactions - B1,S1,B2,S2.
     * 2. State: dp[i][t] = max profit from day 'i' onwards,
     * with 't' being the current transaction number.
     * 3. Each transaction - 
     * t = 0: Can perform 1st Buy (Buy/Don't buy)
     * t = 1: Can perform 1st Sell if already bought (Sell/Don't sell)
     * t = 2: Can perform 2nd Buy (1st transaction complete) (Buy/Don't buy)
     * t = 3: Can perform 2nd Sell if already bought 2nd time (Sell/Don't sell)
     * t = 4: All 2 transactions complete, profit = 0.
     * 4. Base case: when `i==n` or `transactions == 4`, no further transactions can happen, so profit = 0.
     */ 
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int transactions = 4; 
        int[][] dp = new int[n + 1][transactions + 1];
 
        // base case - not required, as all 0 by default in JAVA
        // for (int t = 0; t <= transactions; t++) {
        //     dp[n][t] = 0;
        // }
        // for (int i = 0; i <= n; i++) {
        //     dp[i][transactions] = 0;
        // }

        // recurrence
        for (int i = n - 1; i >= 0; i--) {
            for (int t = transactions - 1; t >= 0; t--) {
                int profit = 0;
                if (t % 2 == 0) {
                    // buy
                    profit = Math.max(-prices[i] + dp[i + 1][t + 1],
                                        0 + dp[i + 1][t]);
                } else {
                    // sell
                    profit = Math.max(prices[i] + dp[i + 1][t + 1],
                                        0 + dp[i + 1][t]);
                }
                dp[i][t] = profit;
            }
        }
        return dp[0][0];
    }
}
