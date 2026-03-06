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
    // refer NEETCODE and STRIVER and Gemini
    // TC: O(n), SC: O(h)
    /*
     * Approach: 
     * Core Idea: At each node, we calculate two values based on its children:
     * 1. Max Path Sum THROUGH the current node:
     * - This path might "turn" at the current node, potentially using both left and right children.
     * - Calculated as: `node.val + max(0, left_child_sum) + max(0, right_child_sum)`.
     * - This value updates a global maximum (`res[0]`) because this path cannot be extended upwards.
     * 2. Max Path Sum EXTENDABLE upwards from the current node:
     * - This path includes the current node and the better single path extending downwards
     * into EITHER the left OR the right child.
     * - Calculated as: `node.val + max(0, max(left_child_sum, right_child_sum))`.
     * - This value is RETURNED by the `dfs` function to the node's parent, as it represents
     * a path the parent could potentially connect to and extend further. (Analogous to
     * returning height in the diameter of a binary tree problem).
     *
     * Handling Negative Path Sums (`max(0, child_sum)`):
     * - The `max(0, ...)` logic (implemented by resetting negative `leftSum`/`rightSum` to 0)
     * is crucial. It represents the choice to *not* include a path segment from a child
     * if that segment's sum is negative (as including it would decrease the overall path sum).
     * - Effectively, we are saying "if the best path down this branch is negative, don't take it;
     * the contribution from this branch is 0."
     *
     * `dfs` Function Role:
     * - Performs the post-order traversal.
     * - Takes `res` (a single-element array to simulate pass-by-reference for the global max) as input.
     * - Updates `res[0]` with the maximum path sum found *anywhere* so far.
     * - Returns the maximum *extendable* path sum starting downwards from the current `root`along only ONE path (left or right).
     * - Base Case: A null node contributes 0 to any path sum.
     */
    public int maxPathSum(TreeNode root) {
        // Use an array to pass the max value by reference through recursion
        int[] res = { Integer.MIN_VALUE };
        dfs(root, res);
        return res[0];
    }
    
    private int dfs(TreeNode root, int[] res) {
        if (root == null) {
            return 0;
        }

        int leftSum = dfs(root.left, res);
        int rightSum = dfs(root.right, res);

        if (leftSum < 0) {
            leftSum = 0;
        }
        if (rightSum < 0) {
            rightSum = 0;
        }

        res[0] = Math.max(res[0], root.val + leftSum + rightSum);
        
        return root.val + Math.max(leftSum, rightSum);
    }
}
