class Solution {
    // refer NEETCODE
    // RECURSION - gives TLE
    // T: O(2^n*m*k); n = s.length(), m = wordDict.length, k = max(wordDict[i].length())
    // S: O(n); stack space 
    /**
     * 1. For every index in string `s`, we have `wordDict` options
     * 2. So, every time we are comparing our current string -> substring of `i + word.length()` with the `word`
     * where `word` represents a string from `wordDict`.
     * 3. If they match, we proceed, else we try other values in the `wordDict` list.
     * 4. When we get a match from the previous call (dfs), return early.
     * 5. Else, return false after iterating all word in wordDict since no match found.
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        return dfs(s, wordDict, 0);
    }

    private boolean dfs(String s, List<String> wordDict, int i) {
        if (i == s.length()) {
            return true;
        }

        for (String w : wordDict) {
            if (i + w.length() <= s.length() && 
                s.substring(i, i + w.length()).equals(w)) {
                
                if (dfs(s, wordDict, i + w.length())) {
                    return true;
                }
            }
        }
        return false;
    }
}

class Solution {
    // refer NEETCODE
    // TOP-DOWN (MEMOIZATION)
    // T: O(n*m*k); n = s.length(), m = wordDict.length, k = max(wordDict[i].length())
    // S: O(n) + O(n); stack space + dp space
    public boolean wordBreak(String s, List<String> wordDict) {
        int[] dp = new int[s.length() + 1];
        Arrays.fill(dp, -1);
        return dfs(s, wordDict, 0, dp);
    }

    private boolean dfs(String s, List<String> wordDict, int i, int[] dp) {

        if (i == s.length()) {
            return true;
        }

        if (dp[i] != -1) {
            return (dp[i] == 0 ? false : true);
        }

        for (String w : wordDict) {
            if (i + w.length() <= s.length() && 
                s.substring(i, i + w.length()).equals(w)) {
                
                if (dfs(s, wordDict, i + w.length(), dp)) {
                    dp[i] = 1;
                    return true;
                }
            }
        }
        dp[i] = 0;
        return false;
    }
}

class Solution {
    // Bottom Up (TABULATION)
    // T: O(n*m*k); n = s.length(), k = avg. word length, m = wordDict.length
    // S: O(n); dp space
    /**
     * Convert Recursion to Tabulation 
     * 1. Copy the base case.
     * 2. Iterate in opposite direction of recursion
     * 3. Copy the recurrence relation
     * 4. Early return will convert to either continue or break according to problem.
     * In current case, early return means string from index `i` can be successfully segmented, so, no more searching, hence, BREAK.
     * 5. Answer is at the index of invocation of recursion.
     *
     * SUMMARY FOR CONTINUE AND BREAK - 
     * break = "I found the answer for dp[i]. Stop searching for i." (Translates a final return true for a subproblem).
     * continue = "This specific option (w) won't work. Skip it and try the next option for i." (Translates a guard clause or a pruned path)
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[n] = true; // base case

        for (int i = n - 1; i >= 0; i--) {
            for (String w : wordDict) {
                if (i + w.length() <= s.length() &&
                    s.substring(i, i + w.length()).equals(w)) {

                    if (dp[i + w.length()] == true) {
                        dp[i] = true;
                        break; // We're done with this i, move to i-1
                    }
                }
            }
        }
        return dp[0];
    }
}
