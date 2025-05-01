class Solution {
    // BRUTE FORCE
    // refer LEETCODE Editorial and NEETCODE for code and expln.
    // T: O((m-n)*(nlogn); n*logn + (m-n)*(nlogn) - sorting s1 and sorting (m-n) substrings of size = n
    // S: O(n + x); temp array of size=n, x = extra space used by internal sort algorithm (Quick Sort in java).

    /*
     * 1. Permutation of a string is formed by rearranging the characters. There are `n!` permutations for string of length = `n`.
     * 2. All permutations have same length and are anagrams of the string.
     * 3. Applying this, we can find if `s1` is an anagram with substrings of `s2`.
     * 4. Two strings are anagrams if there sorted strings are equal.
     * 5. Slight optimization: Instead of generating all substrings of `s2`, generate only `n` length substrings
     * This can be done by looping over the index from `0 to m-n` and picking `n` length substrings.
     */
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }

        int n = s1.length();
        int m = s2.length();

        String sortedS1 = sort(s1);

        for (int i = 0; i <= m - n; i++) {
            int j = i + n; // generate n-length substrings in s2
            String subStr = s2.substring(i, j);
            String sortedSubstr = sort(subStr);

            if (sortedS1.equals(sortedSubstr)) {
                return true;
            }
        }
        return false;
    }

    private String sort(String s) {
        char[] temp = s.toCharArray();
        Arrays.sort(temp);
        return new String(temp);
    }
}
