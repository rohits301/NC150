class Solution {
    // refer NEETCODE
    // BRUTE FORCE
    // T: O(n^2)
    // S: O(n) - will be O(26) as map would have max 26 unique keys
    /*
     * Approach:
     * 1. Use map to count frequency of the characters.
     * 2. For the current substring under consideration (`i to j`), if `windowLength - maxFrequency > k`
     * then, the window is invalid because we cannot replace more than `k` characters, hence break. We break because, further strings will have the above equation as true only for these `i` and `j`, hence not considering further substrings saves time.
     * 3. Keep updating the `maxLength`.
     * 
     * Intuition:
     * 1. To maximize the length of the substring, we need to minimize the number of replacements.
     * 2. The best strategy is to focus on the most frequent character in the current window.
     * 3. If we can make enough replacements (i.e., `windowLength - maxFrequency <= k`), we can extend the window.
     * 4. If not, we need to shrink the window from the left.
     */
    public int characterReplacement(String s, int k) {
        int maxLength = 0;
        int n = s.length();

        for (int i = 0; i < n; i++) {
            Map<Character, Integer> map = new HashMap<>();
            int maxFrequency = 0;
            for (int j = i; j < n; j++) {
                char ch = s.charAt(j);
                map.put(ch, map.getOrDefault(ch, 0) + 1);
                maxFrequency = Math.max(maxFrequency, map.get(ch));
                // windowSize = j-i+1;
                if ((j - i + 1) - maxFrequency > k) {
                    break;
                }
                maxLength = Math.max(maxLength, j - i + 1);
            }
        }
        return maxLength;
    }
}

class Solution {
    // BETTER Space Complexity
    // T: O(n^2)
    // S: O(26) = O(1)
    /*
     * Approach:
     * 1. Using frequency array[26] instead of map.
     * 2. Rest all same.
     */
    public int characterReplacement(String s, int k) {
        int maxLength = 0;
        int n = s.length();

        for (int i = 0; i < n; i++) {
            int[] freq = new int[26];
            int maxFrequency = 0;
            for (int j = i; j < n; j++) {
                char ch = s.charAt(j);
                freq[ch - 'A']++;
                maxFrequency = Math.max(maxFrequency, freq[ch - 'A']);
                // windowSize = j-i+1;
                if ((j - i + 1) - maxFrequency > k) {
                    break;
                }
                maxLength = Math.max(maxLength, j - i + 1);
            }
        }
        return maxLength;
    }
}

class Solution {
    // refer Neetcode's video
    // OPTIMAL
    // T: O(n)
    // S: O(26)
    /*
     * Approach: 
     * 1. Instead of changing the window itself like in previous approach where we break from loop in case of invalid window, we can instead slide the window.
     * 2. Slide -> release the `left` character until the window is valid again.
     * 3. Condition for valid window -> `windowSize - maxFrequency <= k`. Where `windowSize = right - left + 1`.
     * 4. Basically, count of non-repeating characters <= k for valid window, only then we can do `k` replacements.
     * 5. Acquire characters from `right` and check if window is valid.
     * 6. If it valid, we can update the `maxLength`.
     * 7. If it not valid, we need to slide the window from the left until it becomes valid.
     * 8. Then, update the `maxLength`.
     * NOTE: freq[26] only works when we have only English letters (A-Z) or (a-z).
     */
    public int characterReplacement(String s, int k) {
        int maxLength = 0;
        int n = s.length();
        int left = 0, right = 0;
        int[] freq = new int[26];
        int maxFrequency = 0;

        while (right < n) {
            char ch = s.charAt(right);
            freq[ch - 'A']++;
            maxFrequency = Math.max(maxFrequency, freq[ch - 'A']);

            // count of non-repeating characters should be <= k for a valid window
            // when window is invalid, we start sliding the window
            while ((right - left + 1) - maxFrequency > k) {
                freq[s.charAt(left) - 'A']--;
                left++;
            }
            maxLength = Math.max(maxLength, right - left + 1);
            right++;
        }
        return maxLength;
    }
}
