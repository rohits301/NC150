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
    // refer NEETCODE for all 3 approaches and STRIVER for tc and sc
    // Recursive DFS
    // T: O(n), S: O(h) - auxilary space of recursion - O(height), worst case - skewed tree, h=n
    // Most preferred
    /*
     * 1. Maximum Depth = number of nodes on the longest path from the root node down to the farthest leaf node.
     * (An empty tree has a depth of 0).
     * 2. Depth is calculated recursively: standing at a node, its depth is
     * 1 + max(depth of left subtree, depth of right subtree).
     * 3. The base case is an empty subtree (null node), which has a depth of 0.
     * NOTE: for trees, number of nodes = number of edges + 1.
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);

        return Math.max(left, right) + 1;
    }
}

class Solution {
    // Iterative BFS
    // T: O(n), S: O(n) - queue, Worst case is complete binary tree, last level has maximum nodes
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        int level = 0;
        queue.add(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                TreeNode node = queue.poll();

                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            level += 1;
        }
        return level;
    }
}

class Solution {
    // Iterative DFS
    // T: O(n), S: O(n) - space of adding a Pair to stack
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Stack<Pair> st = new Stack<>();
        int res = 1;
        st.push(new Pair(root, 1));

        while (!st.isEmpty()) {
            Pair rp = st.pop();
            TreeNode node = rp.node;
            int depth = rp.depth;

            res = Math.max(res, depth);

            if (node.left != null) {
                st.push(new Pair(node.left, depth + 1));
            }
            if (node.right != null) {
                st.push(new Pair(node.right, depth + 1));
            }
        }
        return res;
    }

    public class Pair {
        TreeNode node;
        int depth;

        Pair() {
        }

        Pair(TreeNode node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }
}
