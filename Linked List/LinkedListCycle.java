/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    // BRUTE FORCE
    // T: O(n), S: O(n) for set
    // create HashSet of nodes as values can be duplicate
    public boolean hasCycle(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        ListNode temp = head;

        while(temp != null){
            if(set.contains(temp)){
                return true;
            } else {
                set.add(temp);
                temp = temp.next;
            }
        }
        return false;
    }
}

public class Solution {
    // OPTIMAL
    // refer STRIVER or Neetcode
    // T: O(n), S: O(1) - each node is traversed atleast once
    public boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while(fast != null && fast.next != null){
            slow = slow.next;
            fast =  fast.next.next;

            if(slow == fast){
                return true;
            }
        }
        return false;
    }
}
