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
    // refer STRIVER video
    // T: O(n)
    // S: O(h)
    /*
     * 1. Two trees are same if they have same shape and same values
     * 2. Same shape implies, same `left` and `right` subtree. If we go `left` on `p`, then we go `left` on `q` as well.
     * 3. if any of the nodes is null, then to be valid both should be null, else it is invalid.
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p == null || q == null){
            return (p == null && q == null);
        }

        return p.val == q.val &&
               isSameTree(p.left, q.left) &&
               isSameTree(p.right, q.right);
    }
}
