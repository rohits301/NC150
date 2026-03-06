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
    // refer NEETCODE and STRIVER
    // T: O(n), S: O(n/2) = O(n)
    // 1
    // 2,3
    // 4,5,6,7
    // 3 levels, n=7, Logn + 1 = number of nodes at last level ???
    // how many children at last level
    public List<List<Integer>> levelOrder(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        List<List<Integer>> ans = new LinkedList<>();
        if (root == null) {
            return ans;
        }

        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> subList = new LinkedList<>();
            while (size-- > 0) {
                TreeNode removeNode = queue.poll();
                subList.add(removeNode.val);

                if (removeNode.left != null) {
                    queue.offer(removeNode.left);
                }
                if (removeNode.right != null) {
                    queue.offer(removeNode.right);
                }
            }
            ans.add(subList);
        }
        return ans;
    }
}
