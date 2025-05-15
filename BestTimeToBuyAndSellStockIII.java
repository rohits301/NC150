class Solution {
    // refer STRIVER - DP 38.
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
    // refer STRIVER - DP 38.
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

