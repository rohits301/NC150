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
    // T: O(len) = len+len = 2*len = O(len), S: O(1)
    /*
     * Approach:
     * 1. Find the length of the linked list.
     * 2. If n is equal to the length, return head.next (deleting the head).
     * 3. Traverse to the (len - n)th node and adjust pointers to skip the nth node from the end.
     * 4. Return the modified head.
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        int len = 0;
        ListNode temp = head;
        while (temp != null) {
            len++;
            temp = temp.next;
        }
        // deletion of head - case
        if (len == n) {
            ListNode newHead = head.next;
            return newHead;
        }

        int counter = len - n;
        temp = head;
        while (temp != null) {
            counter--;
            if (counter == 0) {
                break;
            }
            temp = temp.next;
        }
        temp.next = temp.next.next;
        return head;
    }
}

class Solution {
    // OPTIMAL
    // refer STRIVER, both STRIVER and Neetcode have similar approach
    // T: O(length), S: O(1)
    /*
     * Intuition:
     * Maintain a gap of n nodes between the fast and slow pointers. 
     * When the fast pointer reaches the end, the slow pointer will be at the node just before the one we want to remove.
     * Approach:
     * 1. Use two pointers, fast and slow.
     * 2. Move the fast pointer n steps ahead.
     * 3. If fast is null after moving, it means we need to remove the head.
     * 4. Move both pointers until fast reaches the end of the list.
     * 5. Adjust the slow pointer's next to skip the nth node from the end.
     * 6. Return the modified head.
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode fast = head;
        ListNode slow = head;
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }

        if (fast == null) {
            return head.next;
        }

        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next;
        }
        slow.next = slow.next.next;
        return head;
    }
}