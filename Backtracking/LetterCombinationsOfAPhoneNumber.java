class Solution {
    // refer NEETCODE for code & ChatGPT - o3-mini-high
    // T: O(n * 4^n); each digit maps to maximum 4 letters, and building each complete combination is O(n) as we convert StringBuilder to String.
    // S: O(n); O(n) - for mapping array, O(n) - depth of recursion
    // Space of Output list itself is also - O(n * 4^n)
    /* 
     * APPROACH - 
     * Use recursion to generate all possible combinations
     * We need a mapping such that 2 - "abc". Hence, use a String[]
     * Since, 0 and 1, do not have any mapping in the phone, so map them to "".
     * This dfs involves strings, so after exploring one path, when we go to second path, we need to restore the state.
     * Hence, we need backtracking.
     * We will use StringBuilder to avoid generating too many strings every time we explore an option.
     * So, numbers are at levels of recursion and the mapping is our options. 
     * e.g. 2 - level, "abc" - options
     */
    public List<String> letterCombinations(String digits) {
        List<String> ans = new ArrayList<>();
        if(digits == null || digits.isEmpty()){
            return ans; 
        }

        String[] mapping = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

        dfs(digits, 0, new StringBuilder(), mapping, ans);
        return ans;
    }

    private void dfs(String digits, int i, StringBuilder sb, String[] mapping, List<String> ans){

        if(i == digits.length()){
            ans.add(sb.toString());
            return;
        }

        String characters = mapping[digits.charAt(i) - '0'];
        for(char c : characters.toCharArray()){
            sb.append(c);
            dfs(digits, i + 1, sb, mapping, ans);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
