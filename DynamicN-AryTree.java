import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Approach:
 * 
 * This implementation provides a dynamic N-ary tree supporting efficient addition and removal
 * of nodes, as well as O(1) queries for the number of nodes at any level.
 * 
 * - Each node maintains its value, parent, children, and its level in the tree.
 * - The tree tracks the count of nodes at each level via an ArrayList<Integer> (levelCounts).
 *   This allows O(1) retrieval of node counts at any level.
 * - When nodes are added, their level is set to parent's level + 1, and levelCounts is updated.
 * - When nodes (and their subtrees) are removed, a BFS is used to determine how many nodes
 *   exist at each level in the subtree, and levelCounts is decremented accordingly.
 * - Root node cannot be removed. Attempting to remove it throws an exception.
 * 
 * Example usage demonstrates adding and removing nodes while querying node counts at given levels.
 */

public class Main {

    // ------------------- Node Class -------------------
    public static class Node<T> {
        T value;
        Node<T> parent;
        List<Node<T>> children;
        int level;

        public Node(T value) {
            this.value = value;
            this.parent = null;
            this.children = new ArrayList<>();
            this.level = 0;
        }
    }

    // ------------------- DynamicNaryTree Class -------------------
    public static class DynamicNaryTree<T> {
        private Node<T> root;
        private List<Integer> levelCounts;

        public DynamicNaryTree(T rootValue) {
            this.root = new Node<>(rootValue);
            this.root.level = 0;
            this.levelCounts = new ArrayList<>();
            this.levelCounts.add(1); // Count for the root at level 0
        }

        /**
         * Returns the number of nodes at a given level. Time: O(1)
         */
        public int getNodeCountAtLevel(int level) {
            if (level < 0 || level >= this.levelCounts.size()) {
                return 0;
            }
            return this.levelCounts.get(level);
        }

        /**
         * Adds a new node as a child. Time: O(1) (amortized)
         */
        public Node<T> addNode(Node<T> parentNode, T value) {
            if (parentNode == null) {
                throw new IllegalArgumentException("Parent node cannot be null.");
            }

            Node<T> newNode = new Node<>(value);
            newNode.parent = parentNode;
            newNode.level = parentNode.level + 1;
            parentNode.children.add(newNode);

            while (this.levelCounts.size() <= newNode.level) {
                this.levelCounts.add(0);
            }

            int currentCount = this.levelCounts.get(newNode.level);
            this.levelCounts.set(newNode.level, currentCount + 1);

            return newNode;
        }

        /**
         * Removes a node and its entire subtree. Time: O(K)
         */
        public void removeNode(Node<T> nodeToRemove) {
            if (nodeToRemove == this.root) {
                throw new IllegalArgumentException("Cannot remove the root node.");
            }
            if (nodeToRemove == null || nodeToRemove.parent == null) {
                throw new IllegalArgumentException("Node is invalid or does not have a parent.");
            }

            Map<Integer, Integer> subtreeLevelCounts = new HashMap<>();
            Queue<Node<T>> queue = new LinkedList<>();
            queue.add(nodeToRemove);

            while (!queue.isEmpty()) {
                Node<T> current = queue.poll();
                subtreeLevelCounts.put(current.level, subtreeLevelCounts.getOrDefault(current.level, 0) + 1);
                queue.addAll(current.children);
            }

            for (Map.Entry<Integer, Integer> entry : subtreeLevelCounts.entrySet()) {
                int level = entry.getKey();
                int countToRemove = entry.getValue();
                int currentCount = this.levelCounts.get(level);
                this.levelCounts.set(level, currentCount - countToRemove);
            }
            
            nodeToRemove.parent.children.remove(nodeToRemove);
        }
        
        public Node<T> getRoot() {
            return this.root;
        }
    }

    // ------------------- Main Method for Demonstration -------------------
    public static void main(String[] args) {
        // Create a tree with a String root
        DynamicNaryTree<String> tree = new DynamicNaryTree<>("CEO");
        Node<String> root = tree.getRoot();

        System.out.println("Initial state:");
        System.out.println("Nodes at level 0: " + tree.getNodeCountAtLevel(0)); // Expected: 1
        System.out.println("Nodes at level 1: " + tree.getNodeCountAtLevel(1)); // Expected: 0
        System.out.println("---");

        // Add nodes at level 1
        Node<String> vp1 = tree.addNode(root, "VP of Engineering");
        Node<String> vp2 = tree.addNode(root, "VP of Marketing");

        System.out.println("After adding 2 VPs:");
        System.out.println("Nodes at level 1: " + tree.getNodeCountAtLevel(1)); // Expected: 2
        System.out.println("---");

        // Add nodes at level 2
        Node<String> manager1 = tree.addNode(vp1, "Engineering Manager 1");
        tree.addNode(vp1, "Engineering Manager 2");
        tree.addNode(vp2, "Marketing Manager");
        
        System.out.println("After adding 3 managers:");
        System.out.println("Nodes at level 2: " + tree.getNodeCountAtLevel(2)); // Expected: 3
        System.out.println("---");

        // Remove the entire "VP of Engineering" subtree
        tree.removeNode(vp1);

        System.out.println("After removing VP of Engineering and their reports:");
        System.out.println("Nodes at level 1: " + tree.getNodeCountAtLevel(1)); // Expected: 1 
        System.out.println("Nodes at level 2: " + tree.getNodeCountAtLevel(2)); // Expected: 1
    }
}
