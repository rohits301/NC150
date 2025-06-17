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
    /**
     * Approach:
     * 1. Initialize a dummy node to help build the result linked list.
     * 2. Maintain a carry variable to handle sums greater than 9.
     * 3. In each iteration, sum the values of the current nodes of l1 and l2 along with the carry.
     * 4. Create a new node with the value of the `sum % 10` and add it to the result linked list.
     * 5. Update the carry to be the `sum / 10`.
     * 6. Move the pointers of l1 and l2 to their next nodes.
     * 7. Continue until both linked lists are fully traversed.
     * 8. If there's any carry left after the last addition, create a new node with that carry value.
     * 9. Return the next node of the dummy node, which is the head of the result linked list.
     */
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
