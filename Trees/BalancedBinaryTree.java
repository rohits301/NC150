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
    // refer STRIVER, ChatGPT
    // BRUTE FORCE
    // T: O(n^2)
    // S: O(n)
    /*
     * For each node, we calculate it's left and right height. 
     * If the difference of left and right height is <= 1.
     * Then, the current node is balanced, now recursively check for all nodes in left and right subtree.
     */
    public boolean isBalanced(TreeNode root) {
        if(root == null){
            return true;
        }

        int lh = height(root.left);
        int rh = height(root.right);

        return ((Math.abs(lh - rh) <= 1) &&
                (isBalanced(root.left)) &&
                (isBalanced(root.right)));
    }

    private int height(TreeNode root){
        if(root == null){
            return 0;
        }

        int lh = height(root.left); 
        int rh = height(root.right); 

        return 1 + Math.max(lh, rh);
    }
}

class Solution {
    // refer STRIVER
    // OPTIMAL
    // T: O(n)
    // S: O(h)
    /*
     * Instead of calculating height repeatedly for all nodes,
     * we calculate it only once. 
     * this can be achieved if we modify the dfs for calculating height.
     * We return -1 if the current node is unbalanced.
     * If any subtree is unbalanced, return -1 immediately (early termination).
     * If `dfsHeight(root) == -1`, the tree is unbalanced.
     */
    public boolean isBalanced(TreeNode root) {
        return (dfsHeight(root) != -1);
    }

    private int dfsHeight(TreeNode root){
        if(root == null){
            return 0;
        }

        int leftHeight = dfsHeight(root.left); 
        if(leftHeight == -1){
            return -1;
        }

        int rightHeight = dfsHeight(root.right); 
        if(rightHeight == -1){
            return -1;
        }

        // unbalanced
        if(Math.abs(leftHeight - rightHeight) > 1){
            return -1;
        }

        return 1 + Math.max(leftHeight, rightHeight);
    }
}
