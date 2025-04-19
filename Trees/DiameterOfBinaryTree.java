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
     * 1. The diameter of a binary tree is the longest path (number of edges) between any two nodes in the tree.
     * 2. This path may or may not pass through the root.
     * 3. The height of a tree is the number of edges in the longest path from the root to a leaf.
     * 4. At any node, the diameter can be calculated as:
     * `diameter = left subtree height + right subtree height`
     * 5. Why does this work?
     * 6. The leftHeight (lh) gives the depth of the left subtree.
     * 7. The rightHeight (rh) gives the depth of the right subtree.
     * 8. The longest path through a node is given by `lh + rh`.
     * 9. We track the maximum diameter across all nodes using a global variable.
     * 10. Why is `1 + max(lh, rh)` returned in recursion?
     * 11. When returning the height of a subtree, we count the number of edges, so we return `1 + max(lh, rh)` 
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
