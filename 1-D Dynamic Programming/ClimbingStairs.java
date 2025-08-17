class Solution {
    // Parameterized recursion
    // own solution
    // TLE (BRUTE FORCE)
    // T: O(2^n)
    // S: O(n)
    public int climbStairs(int n) {
        return dfs(0, n);
    }
    private int dfs(int idx, int n){
        if(idx == n){
            return 1;
        }
        if(idx > n){
            return 0;
        }

        int count = 0;
        count += dfs(idx+1, n);
        count += dfs(idx+2, n);
        return count;
    }
}

class Solution {
    // refer STRIVER DP-1 video
    // BRUTE FORCE, TLE
    // similar to Fibonacci numbers sequence
    // why is n=0, return 1 because, 
    // for 0th stair, we have one way - don't move
    // T: O(2^n); 2 decisions at every step
    // S: O(n); stack space
    public int climbStairs(int n) {
        if (n <= 1) {
            return 1;
        }
        return climbStairs(n - 1) + climbStairs(n - 2);
    }
}

class Solution {
    // refer STRIVER, DP 1
    // MEMOIZATION (BETTER)
    // saves unncessary calls
    // T: O(n)
    // S: O(n)
    public int climbStairs(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, -1);
        return helper(n, dp);
    }

    private int helper(int n, int[] dp) {
        if (n <= 1) {
            return 1;
        }

        if (dp[n] != -1) {
            return dp[n];
        }
        return dp[n] = helper(n - 1, dp) + helper(n - 2, dp);
    }
}

class Solution {
    // refer STRIVER, DP 1
    // TABULATION (EVEN BETTER)
    // BOTTOM-UP APPROACH
    // T: O(n)
    // S: O(n)
    public int climbStairs(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1; // 1 way - don't move
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }
}

class Solution {
    // refer STRIVER, DP 1
    // TABULATION (OPTIMAL)
    // SPACE OPTIMIZED
    // we only need previous two values to create answer, hence, array not reqd.
    // T: O(n)
    // S: O(1)
    public int climbStairs(int n) {
        int prev2 = 1; // 0th stair(ground), 1 way - don't move
        int prev = 1;

        for (int i = 2; i <= n; i++) {
            int curr = prev + prev2;
            prev2 = prev;
            prev = curr;
        }
        return prev;
    }
}
