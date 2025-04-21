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
    // T: O(n1 * n2); O(n1) - for recursion of isSubtree, for O(n2) for every isSameTree() call
    // S: O(h1 + h2) - sum of heights is the stack size
    /*
     * 1. Let's consider, subroot as 't' and root as 's'.
     * 2. for `t` to be a subtree of `s`, it should have same structure and same values as a subtree in `s`.
     * 3. Hence, `t` and subtree of `s` should be same tree.
     * 4. So, we recursively check if any subtree of `s` either left or right is SameTree with `t`.
     * 5. Edge cases:
     * a) if both `s` and `t` are null, then null is a subtree of null -> return true.
     * b) if `t` is null but `s` is non-null. Then, `t` matches with the child of leaf nodes of `s` as they are also null -> return true.
     * c) if `s` is null but `t` is non-null, then there is no-way we can find `t` in `s` -> return false.
     * 6. Hence, in a nutshell, if `t` is null, return true, regardless of `s`. If `s` is null, and `t` is non-null, return false.
     */
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if(subRoot == null){
            return true;
        }

        if(root == null){
            return false;
        }

        if(isSameTree(root, subRoot)){
            return true;
        }

        return (isSubtree(root.left, subRoot) || 
                isSubtree(root.right, subRoot));
    }

    private boolean isSameTree(TreeNode p, TreeNode q){
        if(p == null || q == null){
            return (p == null && q == null);
        }

        return ((p.val == q.val) && 
                isSameTree(p.left, q.left) && 
                isSameTree(p.right, q.right));
    }
}
