class Solution {
    // refer STRIVER DP38.
    // Recursion - TLE
    // T: O(2^n)
    // S: O(n)
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        int totalTransactions = 2 * k;
        return dfs(0, 0, totalTransactions, n, prices);
    }

    private int dfs(int i, int transaction, int totalTransactions, int n, int[] prices){
        if(i == n || transaction == totalTransactions){
            return 0;
        }

        int profit = 0;
        if(transaction % 2 == 0){
            // buy
            profit = Math.max(-prices[i] + dfs(i + 1, transaction + 1, totalTransactions, n, prices), 
                                  0 + dfs(i + 1, transaction, totalTransactions, n, prices));
        } else {
            profit = Math.max(prices[i] + dfs(i + 1, transaction + 1, totalTransactions, n, prices), 
                                  0 + dfs(i + 1, transaction, totalTransactions, n, prices));
        }
        return profit;
    }
}

class Solution {
    // refer STRIVER DP38.
    // Memoization 
    // T: O(n*2k);
    // S: O(n*2k) + O(n);
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        int totalTransactions = 2*k;
        int[][] dp = new int[n+1][totalTransactions + 1];
        for(int[] ar: dp){
            Arrays.fill(ar, -1);
        }
        return dfs(0, 0, totalTransactions, n, prices, dp);
    }

    private int dfs(int i, int transaction, int totalTransactions, int n, int[] prices, int[][] dp){
        if(i == n || transaction == totalTransactions){
            return 0;
        }

        if(dp[i][transaction] != -1){
            return dp[i][transaction];
        }

        int profit = 0;
        if(transaction % 2 == 0){
            // buy
            profit = Math.max(-prices[i] + dfs(i + 1, transaction + 1, totalTransactions, n, prices, dp), 0 + dfs(i+1, transaction, totalTransactions, n, prices, dp));
        } else {
            profit = Math.max(prices[i] + dfs(i+1, transaction + 1, totalTransactions, n, prices, dp), 0 + dfs(i + 1, transaction, totalTransactions, n, prices, dp));
        }

        return dp[i][transaction] = profit;
    }
}

class Solution {
    // refer STRIVER DP38.
    // Tabulation 
    // T: O(n*2k);
    // S: O(n*2k);
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        int totalTransactions = 2 * k;
        int[][] dp = new int[n + 1][totalTransactions + 1];

        for (int i = n - 1; i >= 0; i--) {
            for (int transaction = totalTransactions - 1; transaction >= 0; transaction--) {
                int profit = 0;
                if (transaction % 2 == 0) {
                    // buy
                    profit = Math.max(-prices[i] + dp[i + 1][transaction + 1],
                                        0 + dp[i + 1][transaction]);
                } else {
                    profit = Math.max(prices[i] + dp[i + 1][transaction + 1],
                                        0 + dp[i + 1][transaction]);
                }
                dp[i][transaction] = profit;
            }
        }
        return dp[0][0];
    }
}

class Solution {
    // refer STRIVER DP38.
    // Tabulation - Space Optimized
    // T: O(n*2k);
    // S: O(2k);
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        int totalTransactions = 2 * k;
        int[] prev = new int[totalTransactions + 1];
        int[] curr = new int[totalTransactions + 1];

        for (int i = n - 1; i >= 0; i--) {
            for (int transaction = totalTransactions - 1; transaction >= 0; transaction--) {
                int profit = 0;
                if (transaction % 2 == 0) {
                    // buy
                    profit = Math.max(-prices[i] + prev[transaction + 1],
                                        0 + prev[transaction]);
                } else {
                    profit = Math.max(prices[i] + prev[transaction + 1],
                                        0 + prev[transaction]);
                }
                curr[transaction] = profit;
            }
            int[] temp = curr;
            prev = curr;
            curr = temp;
        }
        return prev[0];
    }
}
