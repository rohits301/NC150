class Solution {
    // 1st Solution, using String methods of alphanumeric and lowercase
    // T: O(n)
    // S: O(n)
    /*
     * Approach:
     * 1. Two pointers `i` and `j` to check characters from both ends.
     * 2. Move the pointers towards each other, skipping non-alphanumeric characters.
     * 3. Compare the characters at the two pointers, ignoring case.
     * 4. If they are not equal, return false. If they are equal, continue.
     * 5. If the pointers cross each other, return true.
     * Note: Here we modify the original string by replacing non-alphanumeric characters with empty strings using regex.
     */
    public boolean isPalindrome(String s) {
        if (s.length() == 1) {
            return true;
        }

        // to remove non-alphanumeric characters and then convert to lowercase
        s = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

        int i = 0, j = s.length() - 1;
        while (i <= j) {
            char chi = s.charAt(i);
            char chj = s.charAt(j);
            if (chi != chj) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
}

class Solution {
    // 2nd Solution, better, without using any external library
    // T: O(n)
    // S: O(1) as no extra space because we do not use lower case method
    /*
     * Approach: Two pointers but without any external library
     * 1. Create an `isAlphanumeric()` to check if a character is alphanumeric (a-z, A-Z, 0-9).
     * 2. Convert both characters to lowercase if they are uppercase.
     * 3. This is done by adding `('a' - 'A')`. That is, subtract the uppercase 'A' and add the difference from 'a' to the character.
     */
    public boolean isPalindrome(String s) {
        int i = 0, j = s.length() - 1;
        while (i <= j) {
            char ch1 = s.charAt(i);
            char ch2 = s.charAt(j);

            if (!isAlphanumeric(ch1)) {
                i++;
            } else if (!isAlphanumeric(ch2)) {
                j--;
            } else {
                // both i and j are alphanumeric
                // convert ch1 and ch2 to lowercase if they are uppercase
                // ONLY WORKS FOR ASCII CHARACTERS
                if (ch1 >= 'A' && ch1 <= 'Z') {
                    ch1 += 'a' - 'A';
                }
                if (ch2 >= 'A' && ch2 <= 'Z') {
                    ch2 += 'a' - 'A';
                }

                if (ch1 != ch2) {
                    return false;
                }
                i++;
                j--;
            }
        }
        return true;
    }

    private boolean isAlphanumeric(char ch) {
        if (ch >= 'A' && ch <= 'Z') {
            return true;
        } else if (ch >= 'a' && ch <= 'z') {
            return true;
        } else if (ch >= '0' && ch <= '9') {
            return true;
        }
        return false;
    }
}

class Solution {
    // 3rd Solution, suggested improvements by ChatGPT that maintain the time and space complexity
    // T: O(n)
    // S: O(1) (no extra space for lowercase conversion)
    /*
     * Approach:
     * 1. Two pointers but even simpler.
     * 2. Use built-in function `Character.isLetterOrDigit()` to check for alphanumeric characters and `Character.toLowerCase()` to convert to lowercase.
     */
    public boolean isPalindrome(String s) {
        int i = 0, j = s.length() - 1;

        while (i <= j) {
            char ch1 = s.charAt(i);
            char ch2 = s.charAt(j);

            if (!Character.isLetterOrDigit(ch1)) {
                i++;
            } else if (!Character.isLetterOrDigit(ch2)) {
                j--;
            } else {
                // Both i and j are alphanumeric
                // Convert to lowercase for case-insensitive comparison
                if (Character.toLowerCase(ch1) != Character.toLowerCase(ch2)) {
                    return false;
                }
                i++;
                j--;
            }
        }

        return true;
    }
}
