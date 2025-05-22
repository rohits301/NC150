class Solution {
    // refer STRIVER - DP 50.
    // BRUTE FORCE - RECURSION (TLE)
    // T: O(exponential);
    // S: O(m) + O(m); m (length of cuts list) + m stack space
    /**
     * 1. The way we make a cut, changes are answer. So, the order of operations can change the answer, hence, this is PARTITION DP.
     * 2. Cost of a cut = length on which the cut is made. E.g. if len=7, cut is made at 4, then cost = len = 7. Now, 2 sticks -> len=4 (0 to 4), len=3 (4 to 7). Hence, next cut will be on these lengths.
     * 3. Sorting the cuts[]. WHY?
     * a) If we do not sort the cuts[], then we cannot solve the two partitions that are generated from the cut independently. Because, it might happen, the cuts[i] has a value such that a cut in one partition is dependent on a cut in the other partition. E.g.[1,2,5,3,4]. If we cut at 5, then the cut = 3 is dependent on cut = 2. Because cutting at 2, will afect cut = 3 and vice-versa.
     * 4. Hence, to avoid this, we sort the cuts[].
     * 5. To handle edge cases, add `cut=0` and `cut=n` in the cuts[]. In JAVA, we can make an arraylist from array and insert these cuts.
     * 6. Partition for every (i,j). This is achieved by a loop. Loop from k=i to k=j. So that we try all possible cuts.
     * Initial i=1 (points to cuts[0]), and j=cuts.length. (points to cuts[cuts.length-1]).
     * 7. Recurrence -
     * cost = cost of the current cut (length on which cut is made) + cost of remaining cuts on left + cost of remaining cuts on right.
     * Cost of current cut = cuts[j+1] - cuts[i-1]. This represents the length of the cut. The initially added, 0 and `n`, handle the edge cases.
     * 8. Find minimum cost everytime. Return the minimum.
     * 9. Base case: if i crosses j, return 0.
     * 10. If (i == j), the cut can be calculated because we have `0` and `n` as boundaries.
     * 11. WHY IS MIN INITIALIZED EVERY TIME, don't we need a global min?
     * The min that is maintained is local. We propagate the min to further levels because the dfs call return the min. So min cost is returned at every level and used.
     * The minimums from subproblems are used by the parent call to determine its own minimum.
     */
    public int minCost(int n, int[] cuts) {
        int m = cuts.length;
        List<Integer> cutsList = new ArrayList<>();
        cutsList.add(0);
        for (int cut : cuts) {
            cutsList.add(cut);
        }
        cutsList.add(n);

        Collections.sort(cutsList);
        return dfs(1, m, cutsList);
    }

    private int dfs(int i, int j, List<Integer> cutsList) {
        if (i > j) {
            return 0;
        }

        int min = Integer.MAX_VALUE;
        for (int k = i; k <= j; k++) {
            int cost = cutsList.get(j + 1) - cutsList.get(i - 1) +
                    dfs(i, k - 1, cutsList) +
                    dfs(k + 1, j, cutsList);

            min = Math.min(min, cost);
        }
        return min;
    }
}

class Solution {
    // refer STRIVER - DP 50.
    // BETTER - MEMOIZATION
    // T: O(m*m*m); mlogm - sorting + m*m for states and `m` size loop
    // S: O(m*m) + O(m) + O(m); dp array + m (length of cuts list) + m stack space
    /**
     * 1. Changing states = i and j, the cut indices.
     * 2. 2D dp.
     */
    public int minCost(int n, int[] cuts) {
        int m = cuts.length;
        List<Integer> cutsList = new ArrayList<>();
        cutsList.add(0);
        for (int cut : cuts) {
            cutsList.add(cut);
        }
        cutsList.add(n);

        Collections.sort(cutsList);
        int[][] dp = new int[m+1][m+1];
        for(int[] ar: dp){
            Arrays.fill(ar, -1);
        }
        return dfs(1, m, cutsList, dp);
    }

    private int dfs(int i, int j, List<Integer> cutsList, int[][] dp) {
        if (i > j) {
            return 0;
        }

        if(dp[i][j] != -1){
            return dp[i][j];
        }

        int min = Integer.MAX_VALUE;
        for (int k = i; k <= j; k++) {
            int cost = cutsList.get(j + 1) - cutsList.get(i - 1) +
                    dfs(i, k - 1, cutsList, dp) +
                    dfs(k + 1, j, cutsList, dp);

            min = Math.min(min, cost);
        }
        return dp[i][j] = min;
    }
}

class Solution {
    // refer STRIVER - DP 50.
    // OPTIMAL - TABULATION
    // T: O(m*m*m); mlogm sorting + O(m*m) for loops in call
    // S: O(m*m) + O(m); dp array +  m (length of cuts list)
    /**
     * 1. Copy the base case.
     * 2. Iterate in opposite order of recursion.
     * 3. Increase the array size of dp to handle k-1 edge case. -> m+2.
     * 4. The answer is at same indices as invocation of recursion.
     */
    public int minCost(int n, int[] cuts) {
        int m = cuts.length;
        List<Integer> cutsList = new ArrayList<>();
        cutsList.add(0);
        for (int cut : cuts) {
            cutsList.add(cut);
        }
        cutsList.add(n);

        Collections.sort(cutsList);
        int[][] dp = new int[m + 2][m + 2];

        for (int i = m; i >= 1; i--) {
            for (int j = 1; j <= m; j++) {
                // base case
                if(i > j){
                    continue; // as already dp[i][j] = 0 by default.
                }
                // recurrence
                int min = Integer.MAX_VALUE;
                for (int k = i; k <= j; k++) {
                    int cost = cutsList.get(j + 1) - cutsList.get(i - 1) +
                                dp[i][k - 1] +
                                dp[k + 1][j];

                    min = Math.min(min, cost);
                }
                dp[i][j] = min;
            }
        }
        return dp[1][m];
    }
}
