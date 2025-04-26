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
    // Brute Force (Naive)
    // T: O(height), S: O(n) - space of storing n elements in list
    /*
     * 1. Inorder of BST is sorted. 
     * 2. Hence, the kth element in sorted order is our answer.
     * 3. Store the inorder in list and return the (k-1)th element.
     */
    public int kthSmallest(TreeNode root, int k) {
        List<Integer> list = new ArrayList<>();
        helper(root, list);
        return list.get(k - 1);
    }

    private void helper(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }

        helper(root.left, list);
        list.add(root.val);
        helper(root.right, list);
    }
}

class Solution {
    // BETTER 
    // count and ans are taken as static variables
    // as Java only support pass by value
    // making static variables, to make the state available to the class.
    // T: O(height), S: O(height) - auxillary space
    int count = 0;
    int ans = 0;

    public int kthSmallest(TreeNode root, int k) {
        helper(root, k);
        return ans;
    }

    private void helper(TreeNode root, int k) {
        if (root == null) {
            return;
        }
        helper(root.left, k);
        count += 1;
        if (count == k) {
            ans = root.val;
            return;
        }
        helper(root.right, k);
    }
}

class Solution {
    /*
     * Using a small holder class to track state in recursion:
     *
     * private static class Counter {
     *     int count = 0;   // how many nodes we’ve visited so far
     *     int value;       // holds the kth smallest value once found
     * }
     *
     * Why `private static`?
     * 1. private: 
     *    - Encapsulates Counter inside Solution. No other class can see it.
     * 2. static:  
     *    - No implicit reference to the enclosing Solution instance.
     *    - Counter is just a simple data holder, so we avoid extra memory overhead.
     *
     * This gives a clean recursive in-order traversal without hidden static
     * fields or opaque 2-element arrays.
     */
    public int kthSmallest(TreeNode root, int k) {
        Counter ctr = new Counter();
        dfs(root, k, ctr);
        return ctr.value;
    }

    private void dfs(TreeNode node, int k, Counter ctr) {
        if (node == null) return;
        dfs(node.left, k, ctr);
        if (++ctr.count == k) {
            ctr.value = node.val;
            return;
        }
        dfs(node.right, k, ctr);
    }

    // static because it doesn't need a reference to Solution.this
    // private because it's an implementation detail
    private static class Counter {
        int count = 0;
        int value;
    }
}

class Solution {
    // OPTIMAL
    // refer STRIVER
    // Morris Traversal - inorder, only change, instead of adding to list
    // we increase count and store the value in ans
    // T: O(n) - Amortized, S: O(1)
    public int kthSmallest(TreeNode root, int k) {
        int count = 0, ans = 0;
        
        TreeNode curr = root;
        while(curr != null){
            if(count == k){
                break;
            }

            if(curr.left == null){
                ans = curr.val;
                count++;
                curr = curr.right;
            } else {
                TreeNode prev = curr.left;
                while(prev.right != null && prev.right != curr){
                    prev = prev.right;
                }
                if(prev.right == null){
                    prev.right = curr;
                    curr = curr.left;
                } else {
                    prev.right = null;
                    ans = curr.val;
                    count++;
                    curr = curr.right;
                }
            }
        }
        return ans;
    }
}

// FOLLOW-UP
