class Solution {
    // refer STRIVER DP 51. (v. good explanation)
    // BRUTE FORCE - RECURSION (TLE)
    // T: Exponential
    // S: O(n) - stack space
    /**
     * 1. The order in which we burst balloons will determine the maximum coins that can be earned.
     * If we change order, answer changes. So, same problem, different expressions give different answer.
     * PARTITION DP.
     * 2. If we partition the array at index, say `k`. The remaining subproblems (i,k-1) and (k+1,j) cannot be solved independently.
     * 3. This is because of the dependency in balloons.
     * E.g. [b1,b2,b3,b4,b5,b6]. Burst b4 first.
     * profit for b4 = b3*b4*b5. Remaining subproblem - 
     * [b1,b2,b3] and [b5,b6].
     * If we burst b5, then profit for b3 = b2*b3*b6
     * But, if we burst b6 instead, then profit for b3 = b2*b3*b5.
     * so, left and right partitions are dependent on each other. Hence, the subproblems cannot be sovled independently.
     * 4. So, in these cases we solve the problem in reverse order. E.g. we have nums=[3,1,5,8].
     * `8` is the last balloon to be burst, so we solve for it first.
     * profit for `8` = 1*8*1 (assume we added 1 on both left and right boundary to manage edge cases).
     * now, when we solve for the case before this [3,8].
     * we know that `8` was definitely there, hence, this works. And profit for 3 is not affected because it is calculated with 8 in mind.
     * e.g. [b1,b2,b3,b4,b5,b6]
     * b4 is the last balloon to be burst.
     * So, the last state would contain `b4` -> 1*b4*1
     * And the second last state would also contain `b4`.
     * Possible second last state:[b1,b4], [b2,b4], [b3,b4], [b4,b5], [b4,b6].
     * both left and right whoever had b4 has it now as well. So there answer will remain same. 
     * 5. Recurrence - 
     * profit = coins[i-1]*coins[k]*coins[j+1] -> 
     * this means, if `k` is burst last, then would be the profit, so that is modeled using indices.
     * dfs for left and right partitions.
     * 6. Base case: if i crosses j, return 0.
     * 7. Add `1` in both ends to avoid handling edge cases. Since, array cannot be modified, copy to arraylist and modify. (JAVA)
     */
    public int maxCoins(int[] nums) {
        int n = nums.length;
        List<Integer> coins = new ArrayList<>();
        coins.add(1);
        for (int num : nums) {
            coins.add(num);
        }
        coins.add(1);

        return dfs(1, n, coins);
    }

    private int dfs(int i, int j, List<Integer> coins) {
        if (i > j) {
            return 0;
        }

        int max = Integer.MIN_VALUE;
        for (int k = i; k <= j; k++) {
            int profit = coins.get(i - 1) * coins.get(k) * coins.get(j + 1) +
                    dfs(i, k - 1, coins) +
                    dfs(k + 1, j, coins);

            max = Math.max(max, profit);
        }
        return max;
    }
}

class Solution {
    // refer STRIVER DP 51. (v. good explanation)
    // BETTER - MEMOIZATION
    // T: O(n*n*n); n^2 dp states and inner loop of n
    // S: O(n*n) + O(n) + O(n); dp array, coins list, stack space
    /**
     * Standard memoization
     */
    public int maxCoins(int[] nums) {
        int n = nums.length;
        List<Integer> coins = new ArrayList<>();
        coins.add(1);
        for (int num : nums) {
            coins.add(num);
        }
        coins.add(1);

        int[][] dp = new int[n + 1][n + 1];
        for (int[] ar : dp) {
            Arrays.fill(ar, -1);
        }
        return dfs(1, n, coins, dp);
    }

    private int dfs(int i, int j, List<Integer> coins, int[][] dp) {
        if (i > j) {
            return 0;
        }

        if (dp[i][j] != -1) {
            return dp[i][j];
        }
        int max = Integer.MIN_VALUE;
        for (int k = i; k <= j; k++) {
            int profit = coins.get(i - 1) * coins.get(k) * coins.get(j + 1) +
                    dfs(i, k - 1, coins, dp) +
                    dfs(k + 1, j, coins, dp);

            max = Math.max(max, profit);
        }
        return dp[i][j] = max;
    }
}

class Solution {
    // refer STRIVER DP 51. (v. good explanation)
    // OPTIMAL - TABULATION
    // T: O(n*n*n); n^2 dp states and inner loop of n
    // S: O(n*n) + O(n); dp array, coins list
    /**
     * 1. Copy the base case.
     * 2. Number of nested loops = number of changing parameters.
     * 3. Iterate in opposite order of recursion.
     * 4. Copy the recurrence.
     * 5. Answer at same indices as invocation of recursion.
     * 6. Increase dp size to n+2 to support j+1 for j=n.
     */
    public int maxCoins(int[] nums) {
        int n = nums.length;
        List<Integer> coins = new ArrayList<>();
        coins.add(1);
        for (int num : nums) {
            coins.add(num);
        }
        coins.add(1);

        int[][] dp = new int[n + 2][n + 2];
        for (int i = n; i >= 1; i--) {
            for (int j = 1; j <= n; j++) {
                // base case
                if (i > j) {
                    continue;
                }
                // recurrence
                int max = Integer.MIN_VALUE;
                for (int k = i; k <= j; k++) {
                    int profit = coins.get(i - 1) * coins.get(k) * coins.get(j + 1) +
                            dp[i][k - 1] +
                            dp[k + 1][j];

                    max = Math.max(max, profit);
                }
                dp[i][j] = max;
            }
        }
        return dp[1][n];
    }

}
