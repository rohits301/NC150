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
    /**
     * 1. Floyd's Cycle-Finding Algorithm (Tortoise and Hare):
     * Why this works:
     * 2. If there is a cycle, then the fast pointer will eventually meet the slow pointer.
     * 3. If there is no cycle, the fast pointer will reach the end of the list.
     * 4. Mathematically, if the fast pointer moves twice as fast as the slow pointer, it will cover the same distance in half the time.
     * 5. If there is a cycle, the fast pointer will eventually catch up to the slow pointer.
     * 6. STARTING POINT - To find the start of the cycle, you can reset one pointer to the head and move both pointers one step at a time until they meet again. Why?
     * Mathematical proof: Refer Gemini 2.5 Pro
     */
    public boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;

            if(slow == fast){
                return true;
            }
        }
        return false;
    }
}
