/*
// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
*/

class Solution {
    // refer NEETCODE
    // BRUTE force - recursive
    // BETTER - iterative (this)
    // T: O(2n) - 2 pass
    // S: O(n) - hashmap of old and new nodes
    /*
     * Two pass
     * first pass - make all new nodes
     * and create mapping hashmap: old -> new
     * second pass - link next and random pointers for new nodes
     * Head of new list = map.get(head);
     * we insert (null, null) to handle when either `next` or `random` pointers are null for a node.
     */
    public Node copyRandomList(Node head) {
        Map<Node, Node> oldToNew = new HashMap<>();
        oldToNew.put(null, null);

        Node temp = head;
        while(temp != null){
            Node copy = new Node(temp.val);
            oldToNew.put(temp, copy);
            temp = temp.next;
        }

        temp = head;
        while(temp != null){
            Node copy = oldToNew.get(temp);
            copy.next = oldToNew.get(temp.next);
            copy.random = oldToNew.get(temp.random);

            temp = temp.next;
        }
        return oldToNew.get(head);
    }
}

/*
// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
*/

class Solution {
    // Subhesh Bhai code
    // OPTIMAL
    // T: O(3n) - 3 pass
    // S: O(1) - no extra space
    /*
     * Three pass
     * first pass - make `copy` nodes
     * pattern: `old.next = copy` & `copy.next = (orignal) old.next`
     * second pass - link for random pointers
     * pattern: `copy.random = old.random`
     * and `copy = old.next` so,
     * `old.next.random = old.random.next` (linking)
     * third pass - extract old and copy lists
     * 
     */
    public Node copyRandomList(Node head) {
        if(head == null){
            return head;
        }

        // 1. make copy nodes and link in-between the original nodes
        Node temp = head;
        while(temp != null){
            Node forward = temp.next;

            Node copy = new Node(temp.val);
            temp.next = copy;
            copy.next = forward;

            temp = forward;
        }

        // 2. assigning the random ptrs to copy nodes
        temp = head;
        while(temp != null){
            // copy is temp.next
            temp.next.random = (temp.random != null)? temp.random.next : null;

            temp = temp.next.next;
        }

        // 3. restore original list and extract copy list
        temp = head;
        Node dummy = new Node(-1);
        Node copy = dummy;
        while(temp != null){
            Node forward = temp.next.next;

            // for copy list
            copy.next = temp.next;
            copy = copy.next;

            // for original list
            temp.next = forward;
            temp = forward;
        }

        return dummy.next; // new head
    }
}
