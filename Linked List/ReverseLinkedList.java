/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    // BRUTE FORCE
    // T: O(n), S: O(n)
    // only value changes, not the node 
    public ListNode reverseList(ListNode head) {
        Stack<Integer> st = new Stack<>();
        ListNode temp = head;

        while(temp != null){
            st.push(temp.val);
            temp = temp.next;    
        }
        temp = head;

        while(temp != null){
            temp.val = st.pop();
            temp = temp.next;
        }
        return head;
    }
}

class Solution {
    // ITERATIVE - Optimal
    // T: O(n), S:O(1)
    public ListNode reverseList(ListNode head) {
        ListNode prev = null, temp = null;
        ListNode curr = head;

        while(curr != null){
            temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }
        return prev;
    }
}

class Solution {
    // Recursive solution
    // refer STRIVER's video
    // T: O(n), S: O(n) - space of recursive stack
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode newHead = reverseList(head.next);
        ListNode temp = head.next;
        temp.next = head;
        head.next = null;

        return newHead;
    }
}
