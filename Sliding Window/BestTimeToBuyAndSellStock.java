class Solution {
    // BRUTE FORCE
    // T: O(n^2), S: O(1)
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
    // T: O(n), S: O(1)
    public int maxProfit(int[] prices) {
        int left = 0, right = 1, n = prices.length;
        int max = 0;

        // left = buying price, right = selling price
        while (right < n) {
            if (prices[left] < prices[right]) {
                int profit = prices[right] - prices[left];
                max = Math.max(max, profit);
            } else {
                left = right;
            }
            right++;
        }
        return max;
    }
}
