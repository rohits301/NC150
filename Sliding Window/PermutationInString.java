class Solution {
    // BRUTE FORCE
    // refer LEETCODE Editorial and NEETCODE for code and expln.
    // T: O((m-n)*(nlogn); n*logn + (m-n+1)*(nlogn) = nlogn + (m-n)*(nlogn) = (m-n)*(nlogn)
    // sorting s1 and sorting (m-n+1) substrings of size = n
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

class Solution {
    // BETTER
    // refer LEETCODE Editorial - Approach 4
    // T: O(n*m); n + (m-n+1)*(n+26) = n*(m-n) = m*n
    // S: O(1); 2 arrays of size 26
    /*
     * 1. Choti string - `s1`, Badi string - `s2`
     * 2. Building upon prev. approach, anagram check can be done using array as well instead of a sorting.
     * 3. Check - If both strings have the same frequency array, then they are anagrams. That is, same characters appear in both strings with the same frequency.
     * 4. Code -
     * a) Make a frequency array for `s1`.
     * b) Generating `n` length strings is not required, we just need the frequency array for the substrings. Hence, for `i = 0 to m-n`
     * prepare the frequency array for substring - `i to i + n` in `s2`
     * c) Compare both arrays if they are equal
     * d) equal - true, else check other substrings
     * e) In the end, return false
     */
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }

        int n = s1.length();
        int m = s2.length();

        int[] s1Arr = new int[26];
        for (int i = 0; i < n; i++) {
            s1Arr[s1.charAt(i) - 'a']++;
        }

        for (int i = 0; i <= m - n; i++) {
            int[] s2Arr = new int[26];

            for (int j = 0; j < n; j++) {
                char ch = s2.charAt(i + j);
                s2Arr[ch - 'a']++;

            }
            if (matches(s1Arr, s2Arr)) {
                return true;
            }
        }
        return false;
    }

    private boolean matches(int[] a, int[] b) {
        return Arrays.equals(a, b);
    }
}
