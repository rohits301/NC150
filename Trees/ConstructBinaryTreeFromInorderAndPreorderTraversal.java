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
    // Brute/Better/Optimal
    // T: O(n), S: O(n) - visiting all elements of array so, tc: O(n)
    // space for map - O(n) + auxillary space - O(n), so sc: O(n)
    /*
     * Approach - 
     * 1. Observations - 
     * Preorder -> `Node Left Right`  Inorder -> `Left Node Right`
     * 2. The first element in preorder[] is always the root.
     * 3. Searching this root in inorder[] will tell the split for left and right subtree.
     * 4. In inorder[], all elements to the left of rootIndex are Left subtree and all to the right are right subtree.
     * 5. Calculating the elements in preorder[] can be done like -> count the number of elements to the left from inorder[] = numsLeft
     * then, the left subtree = `preStart + numsLeft`.
     * 6. Similarly, right subtree = `preStart + numsLeft + 1` to `preEnd`.
     * 7. For inorder[], left subtree = `inStart to inRoot - 1`, right subtree = `inRoot+1 to inEnd`.
     * 8. To search in O(1) in inorder[], we use a `map of k,v: inorder[i], i`.
     * 9. We split both arrays recursively (DFS) using 4 indices -> `preStart, preEnd, inStart, inEnd`. 
     * 10. Construct `root` and call for left and right subtrees.
     * 11. Base case: if start index crosses the end index, stop and return null -> this signifies the leaf node.
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }

        return buildTree(preorder, 0, preorder.length - 1,
                         inorder, 0, inorder.length - 1, map);
    }

    private TreeNode buildTree(int[] preorder, int preStart, int preEnd, 
                               int[] inorder, int inStart, int inEnd, Map<Integer, Integer> map) {

        if (preStart > preEnd || inStart > inEnd) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[preStart]);

        int inRoot = map.get(preorder[preStart]); // rootIndexInInorder
        int numsLeft = inRoot - inStart; // no. of elements in left subtree

        root.left = buildTree(preorder, preStart + 1, preStart + numsLeft,
                              inorder, inStart, inRoot - 1, map);
        root.right = buildTree(preorder, preStart + numsLeft + 1, preEnd,
                               inorder, inRoot + 1, inEnd, map);

        return root;
    }
}
