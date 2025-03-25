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
    // refer STRIVER
    // OPTIMAL
    // T: O(n)
    // S: O(h)
    /*
     * The diameter of a binary tree is the longest path (number of edges) between any two nodes in the tree.
     * This path may or may not pass through the root.
     * The height of a tree is the number of edges in the longest path from the root to a leaf.
     * At any node, the diameter can be calculated as:
     * `diameter = left subtree height + right subtree height`
     * Why does this work?
     * The leftHeight (lh) gives the depth of the left subtree.
     * The rightHeight (rh) gives the depth of the right subtree.
     * The longest path through a node is given by `lh + rh`.
     * We track the maximum diameter across all nodes using a global variable.
     * Why is `1 + max(lh, rh)` returned in recursion?
     * When returning the height of a subtree, we count the number of edges, so we return `1 + max(lh, rh)` 
     * to include the current node in the depth.
     */
    public int diameterOfBinaryTree(TreeNode root) {
        int[] maxd = new int[1]; // max diameter
        dfs(root, maxd);
        return maxd[0];
    }

    private int dfs(TreeNode root, int[] maxd) {
        if (root == null) {
            return 0;
        }

        int lh = dfs(root.left, maxd);
        int rh = dfs(root.right, maxd);
        maxd[0] = Math.max(maxd[0], lh + rh);

        return 1 + Math.max(lh, rh);
    }
}
