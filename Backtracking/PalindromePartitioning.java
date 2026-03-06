class Solution {
    // BRUTE/BETTER/OPTIMAL - refer STRIVER
    // T: O(2^n * n); At every partition, we have the pick/not-pick choice, so a binary choice. 
    // Hence, 2^n time to explore all decision. And O(n) time each to check palindrome and copy ds to ans.
    // S: O(2^n * n); 2^n answers which take O(n) space each in the worst case.
    /*
     * APPROACH - 
     * 1. This is not a subset problem.
     * Becuause - a) we are dealing with substrings, so contiguous
     * b) The partition is such that, both substrings are palindrome.
     * 2. At every level, we can partition at i = 0, 1, 2 or n-1 index.
     * 3. In every partition, we are making sure, susbtring `i to j` is a palindrome. We make a partition at `j` and 
     * then invoke the dfs() for the rest of the string.
     * 4. That is, if start with considering "a"(0, 0) and "abb"(1,n-1)
     * then from "abb", we take "a"(1, 1) and "bb"(2, n-1) and so on.
     * 5. Relation between i and j ->  i - the recursion index, j - the array index. In the loop, we need to consider the partitions from `j = i to n-1.`
     * 6. Base case: If `i == s.length()`, we add the generated palindrome partitions to our answer list.
     * 7. Eg. s = "aabb"
     */
    public List<List<String>> partition(String s) {
        List<List<String>> ans = new ArrayList<>();
        dfs(0, s, new ArrayList<>(), ans);
        return ans;
    }

    private void dfs(int i, String s, List<String> ds, List<List<String>> ans) {
        if (i == s.length()) {
            ans.add(new ArrayList<>(ds));
            return;
        }

        for (int j = i; j < s.length(); j++) {

            if (isPalindrome(i, j, s)) {

                ds.add(s.substring(i, j + 1));
                dfs(j + 1, s, ds, ans);
                ds.remove(ds.size() - 1);
            }
        }
    }

    private boolean isPalindrome(int l, int r, String s) {
        while (l < r) {
            if (s.charAt(l) != s.charAt(r)) {
                return false;
            }
            l++;
            r--;
        }
        return true;
    }
}
