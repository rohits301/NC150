class Solution {
    // refer NEETCODE 
    // T: O(2^n)
    // S: O(n)
    /*
     * To generate all parantheses - think Recursion
     * particularly, backtracking as after going over one decision we come back and explore other options from the same node
     * since, it is Strings we are generating, we come back and remove the character we added, so deletion happens during backtracking.
     * open = count of open parantheses
     * closed = count of closed parantheses
     * 3 conditions -
     * 1) we always start with open parantheses, provided `open < n`.
     * 2) can only start with closed parantheses if `closed < open`. Because, if start with close('('), it will be an unbalanced pair.
     * 3) when open = close = n, we hit base case and stop.
     * 
     * Use a String Builder to manage append and delete in strings
     */
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        dfs(0, 0, n, new StringBuilder(), res);
        return res;
    }

    private void dfs(int open, int closed, int n, StringBuilder sb, List<String> res){
        if(open == closed && open == n){
            res.add(sb.toString());
            return;
        }

        if(open < n){
            sb.append("(");
            dfs(open + 1, closed, n, sb, res);
            sb.deleteCharAt(sb.length() - 1); // backtrack
        }

        if(closed < open){
            sb.append(")");
            dfs(open, closed + 1, n, sb, res);
            sb.deleteCharAt(sb.length() - 1); // backtrack
        }
    }
}
