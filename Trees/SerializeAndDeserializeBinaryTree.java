/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Codec {
    // refer NEETCODE
    // using Preorder traversal (intuitive)
    // T: O(n);
    // S: O(n); O(n) for values[] in deserialize and StringBuilder in serialize + O(h) for recursion stack space
    /*
     * 1. Serialization and Deserialization can be done using any tree traversal, basically, it should be the same traversal.
     * 2. Similar to Encode and Decode strings.
     * 3. Serialization - we traverse in preorder, and append "N#" in the StringBuilder for null nodes and "<root.val>#" for other nodes.
     * 4. Eg. [1#2#N#N#3#4#N#N#5#N#N#]
     * 5. Deserialization - we split the serialized string on "#" as it is our delimiter.
     * 6. after this we get String[] containing values and "N" denoting null nodes.
     * 7. In JAVA, values are not passed by reference, so index will change state and i+1 in the parameter will not work.
     * 8. Hence, we pass the index in an array to preserve its state.
     * 9. Traverse the array and construct tree. For every node, create a new node with value from values[].
     * 10. Return the root in the end.
     * 11. Edge cases- the root in serialize is null, hence no serialization required.
     * The string data in deserialization is empty, hence, the root is null.
     */
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null) {
            return "";
        }
        StringBuilder res = new StringBuilder();
        buildString(root, res);
        return res.toString();
    }

    private void buildString(TreeNode root, StringBuilder res) {
        if (root == null) {
            res.append("N").append("#");
            return;
        }
        res.append(root.val).append("#");
        buildString(root.left, res);
        buildString(root.right, res);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data.equals("")) {
            return null;
        }
        String[] values = data.split("#");
        TreeNode root = buildTree(values, new int[1]);
        return root;
    }

    private TreeNode buildTree(String[] values, int[] idx) {
        if (values[idx[0]].equals("N")) {
            idx[0]++;
            return null;
        }
        TreeNode node = new TreeNode(Integer.parseInt(values[idx[0]]));
        idx[0]++;
        node.left = buildTree(values, idx);
        node.right = buildTree(values, idx);
        return node;
    }
}


// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));

public class Codec {
    // refer STRIVER
    // Using Level Order Traversal
    // T: O(n), S: O(n)
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null) {
            return "";
        }
        StringBuilder res = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                TreeNode removeNode = queue.poll();
                if (removeNode == null) {
                    res.append("N#");
                    continue;
                }
                res.append(removeNode.val).append("#");
                queue.offer(removeNode.left);
                queue.offer(removeNode.right);
            }
        }
        // delete extra # at the end
        res.deleteCharAt(res.length() - 1);
        return res.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data.equals("")) {
            return null;
        }
        String[] values = data.split("#");

        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.parseInt(values[0]));
        queue.offer(root);

        for (int i = 1; i < values.length; i++) {
            TreeNode removeNode = queue.poll();
            if (!values[i].equals("N")) {
                TreeNode left = new TreeNode(Integer.parseInt(values[i]));
                removeNode.left = left;
                queue.offer(left);
            }
            if (!values[++i].equals("N")) {
                TreeNode right = new TreeNode(Integer.parseInt(values[i]));
                removeNode.right = right;
                queue.offer(right);
            }
        }
        return root;
    }
}
