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
    // T: O(2n); finding kth node = n, reverse = n
    // S: O(1);
    /*
     * a) `kth node` is the new `head` and `temp` is the new tail
     * b) `prevNode` is tail of the reversed list, so it links with new head, that is `kth node`
     * c) check for size = 0,1,n
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode temp = head;
        ListNode prevNode = null;

        while (temp != null){
            ListNode kthNode = getKthNode(temp, k);

            if(kthNode == null){
                // when there are less than `k` nodes in the list
                if(prevNode != null){
                    // `prevNode` will be null for the very first case
                    // when the initial list itself is less than `k`
                    prevNode.next = temp;
                }
                break;
            }

            ListNode nextNode = kthNode.next;
            kthNode.next = null; // break the connection
            reverse(temp); // returns `head`, but not required as the final `head` is same as `kth Node` in first iteration

            if(temp == head){
                // `temp = head` indicates this is the first iteration
                head = kthNode;
            } else {
                prevNode.next = kthNode;
            }

            prevNode = temp;
            temp = nextNode;
        }

        return head;
    }

    private ListNode getKthNode(ListNode temp, int k){
        k -= 1; // necessary
        while (temp != null && k > 0){
            temp = temp.next;
            k--;
        }
        return temp;
    }

    private ListNode reverse(ListNode head){
        ListNode curr = head, prev = null, temp = null;

        while(curr != null){
            temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }
        return prev; // new head
    }
}
