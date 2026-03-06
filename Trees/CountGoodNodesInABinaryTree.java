/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    // refer NEETCODE
    // T: O(n)
    // S: O(h)
    /*
     * Root node is always a good node.
     * We need `maxSoFar` to keep track of largest value in the path.
     * If the current `node.val >= maxSoFar`
     * then, it is a good node.
     * `total good nodes = 1 (for root) + leftGoodNodes + rightGoodNodes`;
     * NOTE: 
     * res = 1 → Correctly sets res to 1 if the node is good.
     * res += 1 → Would incorrectly increment res each time the condition is met, 
     * which is not needed because res is initialized to 0.
     */
    public int goodNodes(TreeNode root) {
        return dfs(root, root.val);
    }

    private int dfs(TreeNode root, int maxSoFar){
        if(root == null){
            return 0;
        }

        int res = 0;
        if(root.val >= maxSoFar){
            res = 1;
        }
        maxSoFar = Math.max(maxSoFar, root.val);
        res += dfs(root.left, maxSoFar);
        res += dfs(root.right, maxSoFar);

        return res;
    }
}
