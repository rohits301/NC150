/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    // BRUTE FORCE
    // T: O(n), S: O(n)
    /*
     * Intuition:
     * The idea is to use a list to store all the nodes of the linked list.
     * Then we can easily reorder the list by alternating between the front
     * and back of the list.
     * Approach: 
     * 1. Traverse the linked list and store each node in a list.
     * 2. Initialize two pointers, one at the start of the list and one at the end.
     * 3. Create a new ListNode to build the reordered list.
     * 4. While the start pointer is less than or equal to the end pointer:
     *  a. Set the next of the current node to the node at the start pointer.
     *  b. Set the next of next of the current node to the node at the end pointer, that is, the next of start to the end pointer. This links start and end nodes.
     *  c. Since both start and end pointers are set, move current node two steps forward.
     *  d. Move the start pointer forward and end pointer backward.
     * 5. Finally, set the next of the current node to null to terminate the list.
     */
    public void reorderList(ListNode head) {
        List<ListNode> nodes = new ArrayList<>();
        ListNode temp = head;
        while (temp != null) {
            nodes.add(temp);
            temp = temp.next;
        }

        ListNode curr = new ListNode(-1);
        int start = 0, end = nodes.size() - 1;

        while (start <= end) {
            curr.next = nodes.get(start);
            curr.next.next = nodes.get(end);
            curr = curr.next.next;
            start++;
            end--;
        }
        curr.next = null;
    }
}

class Solution {
    // OPTIMAL
    // refer NEETCODE
    // T: O(n), S: O(1)
    /*
     * Intuition:
     * The idea is to find the middle of the linked list, reverse the second half,
     * and then merge the two halves together.
     * Approach:
     * 1. Use the slow and fast pointer technique to find the middle of the linked list.
     * 2. Reverse the second half of the linked list.
     * 3. Merge the two halves together by alternating nodes from the first half and the reversed second half.
     * Note: 
     * a) In this approach, we modify the original linked list in place.
     * Also, we ensure that the second half is always smaller or equal to the first half,
     * so we don't need to handle the case where the first half is larger.
     * b) Edge cases: size = 1 and 2 and odd and even size lists.
     */
    public void reorderList(ListNode head) {
        ListNode slow = head;
        ListNode fast = head.next;

        // find mid of list
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode curr = slow.next; // current head for second half of list
        slow.next = null; // break the list into two halves
        ListNode prev = null;

        // Reverse the second half
        while(curr != null){
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }

        // Merge the two lists
        ListNode first = head;
        ListNode second = prev;

        while(second != null){
            ListNode temp1 = first.next;
            ListNode temp2 = second.next;
            first.next = second;
            second.next = temp1;
            first = temp1;
            second = temp2;
        }
    }
}
