class Solution {
    // refer NEETCODE
    // BRUTE FORCE
    // T: O(n^2)
    // S: O(1)
    /*
     * Approach:
     * 1. For every price, we will try to buy and sell on every other price after that.
     * 2. Keep track of maximum profit so far.
     * 3. Return maximum profit so far.
     */
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int maxSoFar = 0;

        for (int i = 0; i < n; i++) {
            int max = 0;
            // buy at prices[i]
            for (int j = i + 1; j < n; j++) {
                // sell at prices[j]
                max = Math.max(max, prices[j] - prices[i]);
            }
            maxSoFar = Math.max(maxSoFar, max);
        }
        return maxSoFar;
    }
}

class Solution {
    // OPTIMAL
    // Sliding window type solution
    // T: O(n)
    // S: O(1)
    /*
     * Approach:
     * 1. We can buy and sell only once.
     * 2. Use two pointers, left and right.
     * 3. left pointer is the buying price, right pointer is the selling price.
     * 4. We are always buying on the first day, so left=0 and right=1 initially.
     * 5. Calculate profit = prices[right] - prices[left].
     * 6. If profit > 0, we can make a profit, so update max profit.
     * 7. If profit <= 0, we cannot make a profit, so move the left pointer to right.
     * 8. Move the right pointer to the next position.
     * 9. The key here is jumping the left pointer to right when right is at a lower price than left. This is because we can only buy once, so we need to reset left to right.
     * 10. Return the maximum profit.
     */
    public int maxProfit(int[] prices) {
        int left = 0, right = 1, n = prices.length;
        int max = 0;

        // left = buying price, right = selling price
        while (right < n) {
            int profit = prices[right] - prices[left];
            if (profit > 0) {
                max = Math.max(max, profit);
            } else {
                left = right; // jump `left` to `right` because `right` is at a lower price
                // we can only buy once, so we need to reset `left` to `right`
            }
            right++;
        }
        return max;
    }
}

class Solution {
    // refer STRIVER DP 35. - PREFERRED SOLUTION
    // T: O(n)
    // S: O(1)
    /**
     * 1. We can buy and sell only once.
     * 2. If we buy on `ith` day, can sell after `ith` day, so from
     * `i+1` to `n` day.
     * 3. We have to mandatorily buy on the first day. So, we start from `i=1`.
     * 4. Keep track of profit, if profit is negative, means, don't sell on `ith` day.
     * 5. Keep track of minimum till now, because, to maximize profit, we have to buy on the day stock price is minimum and sell when the price is maximum.
     * 6. We buy when price = `min` and sell on `ith` day if profit > 0.
     * NOTE: It is a DP problem, because we are remembering the minimum as we are going forward.
     */
    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        int min = prices[0]; // we have to buy on the 0th day so `min=prices[0]` initially

        for (int i = 1; i < prices.length; i++) {
            int profit = prices[i] - min;
            if (profit >= 0) {
                maxProfit = Math.max(maxProfit, profit);
            } else {
                min = prices[i]; // update min if current price is less than min
            }
        }
        return maxProfit;
    }
}
