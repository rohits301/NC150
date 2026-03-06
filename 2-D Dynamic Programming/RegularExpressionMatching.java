class Solution {
    // refer NEETCODE - explanation and code
    // NEETCODE video has more readable code
    // BRUTE FORCE - RECURSION
    // T: O(2^(m+n)); exponential
    // S: O(m+n); Maximum depth is sum of both lengths
    // n = s.length(), m = p.length()
    /**
     * 1. If there is a "*", then it matches 0 or more characters. Hence, if we need to explore all possibilites of matching 0 or more characters. RECURSION.
     * 2. E.g. to understand the problem better - 
     * s="aa", p="a*"
     * possible values of p = ["","a","aa","aaa","aaa" ...]. 
     * s="aab", p=".*"
     * possible values of p = ["","a","b","ab","c","xyz" ....].
     * When there is a "*", it is pure regex matching.
     * We check the preceding character, here, "a" in "a*". And that can repeat 0 or more times. 
     * 0 times -> empty string
     * 1 or more -> preceding character repetition. e.g. a,aa,aaa ....
     * 3. Hence, when we encounter either the characters (i,j) are equal or `p` has a ".". Since "." can represent one character, there is a match.
     * 4. For "*" case -
     * We check if next character is "*", because it will always have a preceding character (given). 2 cases -  
     * We do not take it at all, so skip "*" and preceding character, so, `j+2`, but `i` remains same since we didn't find a match.
     * The preceding character `j` matches with `i`, proceed to match zero or more occurences.
     * `i` shifts to `i+1` because of matching but, `j` remains the same because we are using "*" so we are matching the characters and will only stop using it when we have found the match.
     * Since either case can match, return true if the OR of `zeroMatch` and `oneOrMoreMatch`.
     * 5. If the characters match, then we can directly move forward.
     * 6. If nothing matches, return false.
     * 7. Base case: 
     * a) if both `p` and `s` are exhausted -> true
     * b) if `p` is exhausted but `i` is remaining -> false because `s` is not matched completely. E.g. s="abc", p="b*"
     * c) If `s` is exhausted and `p` is remaining -> implicitly handled. 2 cases - 
     * Either s="", p="a".
     * OR s="b", p="b*c". 
     * Eventually, `i` reaches `m` and this where we get our answer.
     */
    public boolean isMatch(String s, String p) {
        int n = s.length();
        int m = p.length();
        return dfs(0, 0, n, m, s, p);
    }

    private boolean dfs(int i, int j, int n, int m, String s, String p) {
        // base case
        if (i == n && j == m) {
            return true;
        }
        if (j == m) {
            return false;
        }

        boolean match = (i < n) && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.');

        // if "*"
        if (j + 1 < m && p.charAt(j + 1) == '*') {
            // 0, don't use "*"
            boolean zeroMatch = dfs(i, j + 2, n, m, s, p);
            // 1 or more, use "*"
            boolean oneOrMoreMatch = match && dfs(i + 1, j, n, m, s, p);
            if (zeroMatch || oneOrMoreMatch) {
                return true;
            }
        }

        if (match) {
            return dfs(i + 1, j + 1, n, m, s, p);
        }
        return false;
    }
}

// while the above code works, it has a bug in handling of the `zeroMatch || oneOrMoreMatch`
// corrected below
class Solution {
    // refer NEETCODE - explanation and code
    // NEETCODE video has more readable code
    // BETTER - MEMOIZATION
    // T: O(n*m)); 
    // S: O(n*m) + O(m+n); dp array + stack space
    // n = s.length(), m = p.length()
    /**
     * **CORRECTION**
     * `if (zeroMatch || oneMoreMatch) return true` is INCORRECT.
     * because for "*", we have only these two cases -zeroMatch or oneOrMoreMatch, so we should return whatever the OR returns.
     */
    public boolean isMatch(String s, String p) {
        int n = s.length();
        int m = p.length();
        int[][] dp = new int[n+1][m+1];
        for(int[] ar: dp){
            Arrays.fill(ar, -1);
        }
        return dfs(0, 0, n, m, s, p, dp);
    }

    private boolean dfs(int i, int j, int n, int m, String s, String p, int[][] dp) {
        // base case
        if (i == n && j == m) {
            return true;
        }
        if (j == m) {
            return false;
        }

        if(dp[i][j] != -1){
            return (dp[i][j] == 1)? true : false;
        }
        boolean res = false;
        boolean match = (i < n) && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.');

        // if "*"
        if (j + 1 < m && p.charAt(j + 1) == '*') {
            // 0, don't use "*"
            boolean zeroMatch = dfs(i, j + 2, n, m, s, p, dp);
            // 1 or more, use "*"
            boolean oneOrMoreMatch = match && dfs(i + 1, j, n, m, s, p, dp);
            res = (zeroMatch || oneOrMoreMatch);
            dp[i][j] = (res == false)? 0: 1;
            return res;
        }

        if (match) {
            res = dfs(i + 1, j + 1, n, m, s, p, dp);
            dp[i][j] = (res == false)? 0: 1;
            return res;
        }
        res = false;
        dp[i][j] = (res == false)? 0: 1;
        return res;
    }
}

class Solution {
    // refer NEETCODE - explanation and code
    // NEETCODE video has more readable code
    // BETTER - TABULATION
    // T: O(n*m); 
    // S: O(n*m); dp array
    // n = s.length(), m = p.length()
    /**
     * 1. Copy the base case.
     * 2. Iterate in opposite order of recursion.
     * 3. Copy the recurrence
     * 4. Answer at same indices as invocation of recursion.
     * 5. Replace every return with continue.
     */
    public boolean isMatch(String s, String p) {
        int n = s.length();
        int m = p.length();
        boolean[][] dp = new boolean[n + 1][m + 1];

        // base case
        dp[n][m] = true;
        // for all cases except when `i=n`
        for (int i = 0; i < n; i++) {
            dp[i][m] = false;
        }

        for (int i = n; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                boolean match = (i < n) && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.');

                // if "*"
                if (j + 1 < m && p.charAt(j + 1) == '*') {
                    // 0, don't use "*"
                    boolean zeroMatch = dp[i][j + 2];
                    // 1 or more, use "*"
                    boolean oneOrMoreMatch = match && dp[i + 1][j];
                    dp[i][j] = (zeroMatch || oneOrMoreMatch);
                    continue;
                }

                if (match) {
                    dp[i][j] = dp[i + 1][j + 1];
                    continue;
                }
                dp[i][j] = false;
            }
        }
        return dp[0][0];
    }
}

class Solution {
    // refer NEETCODE - explanation and code
    // NEETCODE video has more readable code
    // BETTER - TABULATION 
    // T: O(n*m); 
    // S: O(n*m); dp array
    // n = s.length(), m = p.length()
    /**
     * More concise, replaced continue statements by splitting logic into  if-else blocks
     */
    public boolean isMatch(String s, String p) {
        int n = s.length();
        int m = p.length();
        boolean[][] dp = new boolean[n + 1][m + 1];

        // base case
        dp[n][m] = true;
        // for all cases except when `i=n`
        for (int i = 0; i < n; i++) {
            dp[i][m] = false;
        }

        for (int i = n; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                boolean match = (i < n) && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.');

                // if "*"
                if (j + 1 < m && p.charAt(j + 1) == '*') {
                    // 0, don't use "*"
                    boolean zeroMatch = dp[i][j + 2];
                    // 1 or more, use "*"
                    boolean oneOrMoreMatch = match && dp[i + 1][j];
                    dp[i][j] = (zeroMatch || oneOrMoreMatch);
                } else {
                    dp[i][j] = match && dp[i+1][j+1];
                }
            }
        }
        return dp[0][0];
    }
}

class Solution {
    // refer NEETCODE - explanation and code
    // NEETCODE video has more readable code
    // OPTIMAL - TABULATION (SPACE OPTIMIZATION)
    // T: O(n*m); 
    // S: O(m); 
    // n = s.length(), m = p.length()
    /**
     * Space optimization is possible since only `i` has dependency on only `i+1`
     * 1. dp[i+1] -> prev, since `i` is from `n-1` to 0.
     * 2. dp[i] -> curr
     * 3. Base Case:
     * a) Set the base case for the current row: curr[m] = dp[i][m]
     * If pattern is exhausted (j=m):
     * - String must also be exhausted (i=n) for a match.
     * - So, dp[i][m] is true if i == n, and false otherwise.
     *
     * b) Initialize 'prev' to conceptually represent dp[n+1][...].
     * For the first iteration when i = n, prev[j] will act as dp[n+1][j].
     * dp[n+1][m] can be seen as true (both string and pattern "over-exhausted" match).
     * dp[n+1][j] for j < m is false (string over-exhausted, pattern not empty).
     * So, prev[m] = true and other elements false is a correct starting point for prev.
     * prev[m] = true;
     */
    public boolean isMatch(String s, String p) {
        int n = s.length();
        int m = p.length();
        boolean[] prev = new boolean[m + 1];
        boolean[] curr = new boolean[m + 1];

        // base case
        prev[m] = true;

        for (int i = n; i >= 0; i--) {
            curr[m] = (i == n); // base case
            for (int j = m - 1; j >= 0; j--) {
                boolean match = (i < n) && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.');

                // if "*"
                if (j + 1 < m && p.charAt(j + 1) == '*') {
                    // 0, don't use "*"
                    boolean zeroMatch = curr[j + 2];
                    // 1 or more, use "*"
                    boolean oneOrMoreMatch = match && prev[j];
                    curr[j] = (zeroMatch || oneOrMoreMatch);
                } else {
                    curr[j] = match && prev[j + 1];
                }
            }
            boolean[] temp = prev;
            prev = curr;
            curr = temp;
        }
        return prev[0];
    }
}
