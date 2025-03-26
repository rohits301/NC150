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
    // refer STRIVER
    // BRUTE/BETTER/OPTIMAL
    // T: O(Max(n, m));
    // S: O(Max(n, m)); - space for answer, no extra space
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(-1);
        ListNode curr = dummy;
        ListNode t1 = l1, t2 = l2; // best practice to avoid changing given pointers
        int carry = 0;

        // traverse until both are empty
        while(t1 != null || t2 != null){
            int sum = carry;

            if(t1 != null){
                sum += t1.val;
            }
            if(t2 != null){
                sum += t2.val;
            }   

            carry = sum / 10;

            ListNode node = new ListNode(sum % 10);
            curr.next = node;
            curr = curr.next;

            if(t1 != null){
                t1 = t1.next;
            }
            if(t2 != null){
                t2 = t2.next;
            }
        }

        if(carry > 0){
            ListNode node = new ListNode(carry);
            curr.next = node;
            curr = curr.next;
        }

        return dummy.next;
    }
}
