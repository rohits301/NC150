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
    // refer STRIVER for DFS approach
    // FASTER AT RUNTIME
    // T: O(n)
    // S: O(h)
    /*
     * In the right view, the rightmost value at every level is what we see.
     * So, in dfs, this means, we traverse - `Root Right Left`
     * Alright, we can traverse in the order `root,right,left`
     * but when to add a node to data-structure?
     * Whenever, we visit a level for the first time, that is,
     * the moment when we have visited the right side for the first time, 
     * hence, this is also the moment we add it to our data structure.
     * The check to ensure this is -> `level == dataStructure.size()`
     * when we are visiting a level again, then, `level > dataStructure.size()`, so we only add one node at each level.
     * For LEFT SIDE VIEW, interchange the dfs calls. That's it.
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if(root == null){
            return res;
        }

        dfs(root, 0, res);
        return res;
    }

    private void dfs(TreeNode root, int level, List<Integer> res) {
        if (root == null) {
            return;
        }

        if (level == res.size()) {
            res.add(root.val);
        }

        dfs(root.right, level + 1, res);
        dfs(root.left, level + 1, res);
    }
}

class Solution {
    // refer NEETCODE for BFS approach
    // T: O(n)
    // S: O(n) - worst case is poorer than DFS, so prefer DFS
    /*
     * In Level Order, the last node to be processed at every level is our rightmost element in tree.
     * Hence, proceed with usual level order traversal, adding all child nodes to the queue.
     * Keep track of the lastNode to be removed at each level.
     * Add the last node to answer data structure.
     *
     * For LEFT SIDE VIEW, add right child in queue and then add left child.
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if(root == null){
            return res;
        }

        Queue<TreeNode> que = new LinkedList<>();
        que.offer(root);
        int rightMost = 0;

        while(!que.isEmpty()){
            int size = que.size();

            while(size-- > 0){
                TreeNode node = que.poll();
                rightMost = node.val;

                if(node.left != null){
                    que.offer(node.left);
                }

                if(node.right != null){
                    que.offer(node.right);
                }
            }
            res.add(rightMost);
        }
        return res;
    }
}
