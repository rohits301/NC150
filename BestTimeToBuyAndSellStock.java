class Solution {
    // refer STRIVER DP 35.
    // T: O(n)
    // S: O(1)
    /**
     * 1. We can buy and sell only once.
     * 2. If we buy on `ith` day, can sell after `ith` day, so from
     * `i+1` to `n` day.
     * 3. We have to mandatorily buy on the first day. So, we start from `i=1`.
     * 4. Keep track of profit, if profit is -ve, means, don't sell on `ith` day.
     * 5. Keep track of minimum till now, because, to maximize profit, we have to buy on the day stock price is minimum and sell when the price is maximum.
     * 6. We buy when price = `min` and sell on `ith` day if profit > 0.
     * NOTE: It is a DP problem, because we are remembering the minimum as we are going forward.
     */
    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        int min = prices[0];

        for (int i = 1; i < prices.length; i++) {
            int profit = prices[i] - min;
            if (profit >= 0) {
                maxProfit = Math.max(maxProfit, profit);
            }
            if (prices[i] < min) {
                min = prices[i];
            }
        }
        return maxProfit;
    }
}
