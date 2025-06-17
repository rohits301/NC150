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
    public void reorderList(ListNode head) {
        ListNode slow = head;
        ListNode fast = head.next;

        // find mid of list
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode curr = slow.next; // current head for second half of list
        slow.next = null;
        ListNode prev = null;

        // Revese the second half
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
