class Solution {
    // BRUTE FORCE
    // T: O(n^2)
    // S: O(n)
    /*
     * Approach:
     * 1. We remove all pairs of valid parentheses until no more can be removed.
     * 2. If the `lengthBefore` is equal to the `lengthAfter`, it means no more pairs can be removed.
     * 3. Else, we continue removing pairs.
     */
    public boolean isValid(String s) {
        // A string with an odd length can never be valid.
        if (s.length() % 2 != 0) {
            return false;
        }

        int lengthBefore;
        
        // Loop as long as we are successfully removing pairs.
        do {
            lengthBefore = s.length();
            s = s.replace("()", "");
            s = s.replace("[]", "");
            s = s.replace("{}", "");
        } while (lengthBefore != s.length());

        // If the string is empty, it means all pairs were matched and removed.
        return s.isEmpty();
    }
}

class Solution {
    // OPTIMAL
    // refer NeetCode video
    // T: O(n), S: O(n)
    /*
     * Approach:
     * 1. We use a stack to keep track of opening brackets.
     * 2. For each closing bracket, we check if it matches the top of the stack.
     *   If it does, we pop the stack. If it doesn't, we return false.
     * 3. If we reach the end of the string and the stack is empty, it means all brackets were matched.
     * Note: We check for stack is empty and if the current character is a closing bracket, because then it is unbalanced.
     */
    public boolean isValid(String s) {
        Stack<Character> st = new Stack<>();

        for (char ch : s.toCharArray()) {
            if (st.isEmpty() && (ch == ')' || ch == ']' || ch == '}')) {
                return false;
            } else {
                if (ch == ')' && st.peek() == '(' ||
                    ch == ']' && st.peek() == '[' ||
                    ch == '}' && st.peek() == '{') {
                    st.pop();
                } else {
                    st.push(ch);
                }
            }
        }
        return st.isEmpty();
    }
}