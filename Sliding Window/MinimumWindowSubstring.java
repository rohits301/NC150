class Solution {
    // BRUTE FORCE - TLE
    // T: O(m^2 * n) - where m is the length of string `s` and n is the length of string `t`
    // S: O(m + n) - as we are using two maps to store the frequency of characters in `t` and `s`
    /**
     * Approach:
     * 1. For every starting index `i` in string `s`, we will try to find the minimum substring that contains all characters of string `t`.
     * The frequency of the characters in `t` can be tracked with a map `need`.
     * 2. For every `i`, we will create a map `have` to keep track of characters and their frequencies in the substring starting from `i`.
     * 3. We will then iterate through the substring starting from `i` and keep adding characters to `have` until we have all characters of `t` in `have`.
     * 4. If we find a valid substring, we will check if its length is less than the current minimum length found and update accordingly.
     * 5. Finally, we will return the minimum substring found or an empty string if no such substring exists.
     */
    public String minWindow(String s, String t) {
        int minLength = Integer.MAX_VALUE;
        int m = s.length();
        int minStart = -1;
        Map<Character, Integer> need = new HashMap<>();

        for (char ch : t.toCharArray()) {
            need.put(ch, need.getOrDefault(ch, 0) + 1);
        }

        for (int i = 0; i < m; i++) {
            Map<Character, Integer> have = new HashMap<>();
            for (int j = i; j < m; j++) {
                char ch = s.charAt(j);
                have.put(ch, have.getOrDefault(ch, 0) + 1);

                boolean flag = true;
                for (char key : need.keySet()) {
                    // if we do not have the character or its frequency is less than required, we break
                    // this is the condition to check if we have all characters of `t` in the current substring
                    if (!have.containsKey(key) || have.get(key) < need.get(key)) {
                        flag = false;
                        break;
                    }
                }
                // first occurence where the if condition satisfies is the minimum we can get for a given i because j is increasing throughout, so we break.
                if (flag && minLength > (j - i + 1)) {
                    minLength = j - i + 1;
                    minStart = i;
                    break;
                }
            }
        }
        return (minLength != Integer.MAX_VALUE) ? (s.substring(minStart, minStart + minLength)) : "";
    }
}

class Solution {
    // OPTIMAL
    // T: O(m + n); One-pass solution
    // S: O(128) max as maximum unique characters is 128 
    // (upper and lowercase combined)
    /**
     * Approach:
     * 1. We use a frequency array to keep track of the characters in `t`.
     * 2. We then use a sliding window approach to find the minimum window in `s` that contains all characters of `t`.
     * 3. We maintain a count of how many characters from `t` are currently in the window.
     * 4. As we expand the right end of the window, we check if we have all characters from `t`.
     * 5. If we do, we try to contract the left end of the window to find the minimum length substring that still contains all characters from `t`.
     * `need == 0` indicates that we have all characters from `t` in the current window.
     * Essentially, the freq[] has positive values for characters in `t` and negative values for characters in `s` that are not in `t`. If it's zero for a character, it means we have enough of that character in the current window.
     * 6. We keep track of the minimum length found and return the corresponding substring.
     */
    public String minWindow(String s, String t) {
        int[] freq = new int[128];

        for (char ch : t.toCharArray()) {
            freq[ch]++;
        }

        int left = 0;
        int minLength = s.length() + 1; // alternative to Integer.MAX_VALUE
        int need = t.length();
        int minStart = 0;

        for (int right = 0; right < s.length(); right++) {
            char rightChar = s.charAt(right);
            if (freq[rightChar] > 0) {
                need--; // indicates the required character is found
            }
            freq[rightChar]--; // acquire

            while (need == 0) {
                if (minLength > (right - left + 1)) {
                    minLength = right - left + 1;
                    minStart = left;
                }

                char leftChar = s.charAt(left);
                freq[leftChar]++; // release
                if (freq[leftChar] > 0) {
                    need++; // released a character that must be included
                }
                left++;
            }
        }
        return (minLength > s.length()) ? "" : s.substring(minStart, minStart + minLength);
    }
}
