class Solution {
    // refer STRIVER
    // BRUTE FORCE
    // T: O(n^2);
    // S: O(n);
    // TEST CASE - ABCBABCDBA
    /*
     * 1. Make all substrings with no repeating characters and calculate their length.
     * 2. No repeating characters -> think HashSet to eliminate duplicates.
     * 3. Consider all substrings and update `maxLength` after every iteration of inner loop since all possible valid substrings are considered in inner loop.
     */
    public int lengthOfLongestSubstring(String s) {

        int maxLength = 0;

        for (int i = 0; i < s.length(); i++) {
            Set<Character> set = new HashSet<>();
            int length = 0;
            for (int j = i; j < s.length(); j++) {
                char ch = s.charAt(j);
                if (set.contains(ch)) {
                    break;
                } else {
                    set.add(ch);
                    length += 1;
                }
            }
            maxLength = Math.max(maxLength, length);
        }
        return maxLength;
    }
}

class Solution {
    // SLIDING WINDOW
    // BETTER
    // T: O(2n); 
    // S: O(n); In the worst case, the set would have 256 distinct elements
    /*
     * 1. Whenever question about substring or subarray, think about sliding window approach. 
     * 2. Concept - the window contains the valid scenario. Length of window = `right - left + 1`.
     * 3. Whenever, the window becomes invalid, we remove characters from `left` and make it valid again.
     * 4. We still need a Set to check whether the window is valid.
     * 5. When we encounter a repeating character, we start removing elements one-by-one from `left`.
     * 6. It has inner loop, yet the complexity is linear, because every element is accessed only twice. Once while adding to the set and second time while removing it from the set.
     */
    public int lengthOfLongestSubstring(String s) {
        int maxLength = 0;
        int left = 0, right = 0;
        int n = s.length();
        Set<Character> set = new HashSet<>();

        while (right < n) {
            char rightChar = s.charAt(right);

            if (!set.contains(rightChar)) {
                set.add(rightChar);
                maxLength = Math.max(maxLength, right - left + 1);
            } else {
                while (set.contains(rightChar)) {
                    set.remove(s.charAt(left));
                    left++;
                }
                set.add(rightChar);
                // we do not update maxLength here, as left has increased so, current window length cannot beat the `maxLength`
            }
            right++;
        }
        return maxLength;
    }
}

class Solution {
    // OPTIMAL
    // T: O(n), S: O(n) - one pass
    /*
     * Approach:
     * 1. Optimizing the sliding window approach by avoiding one-by-one character removal.
     * 2. Instead, we use a Map<Character, Integer> to store the lastSeenIndex of each character.
     * 3. When we encounter a repeating character, we jump the `left` pointer to `(lastSeenIndex + 1)`, skipping over unnecessary elements.
     * 4. However, before updating `left`, we must ensure it never moves backward.
     * 5. To prevent this, we use:
          `left = max(left, map.get(rightChar) + 1)`
       If the character was seen and is inside the current window,
       we move the left pointer to the 1 + (its last position).
     * 6. Key Idea: Instead of shrinking the window one character at a time when a duplicate is found, we jump the left pointer directly to the next position after the last occurrence of the duplicate character.
     */
    public int lengthOfLongestSubstring(String s) {
        int left = 0, right = 0;
        int n = s.length();
        int maxLength = 0;
        Map<Character, Integer> map = new HashMap<>();

        while (right < n) {
            char rightChar = s.charAt(right);
            // `left` is jumping instead of sliding to optimise
            // before setting it, check whether `left` is in the window under consideration
            if (map.containsKey(rightChar)) {
                left = Math.max(left, map.get(rightChar) + 1);
            }

            maxLength = Math.max(maxLength, right - left + 1);
            map.put(rightChar, right);
            right++;
        }
        return maxLength;
    }
}
