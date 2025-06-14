class Solution {
    // refer NEETCODE
    // TOP-DOWN (MEMOIZATION)
    // T: O(n*m*t); n=s.length(), m=wordDict.length, t=max(wordDict[i].length())
    // S: O(n) + O(n); n = s.length(); stack space + dp space
    /**
     * 1. For every index in string `s`, we have `wordDict` options
     * 2. So, every time we are comparing our current string -> substrig of `i + word.length()` with the `word`
     * where `word` represents a string from `wordDict`.
     * 3. If they match, we proceed, else we try other values in the `wordDict` list.
     * 4. When we get a match from the previous call (dfs), return early.
     * 5. Else, return false since no match found.
     */
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
