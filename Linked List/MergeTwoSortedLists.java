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
    // Better
    // refer STRIVER video -> Merge Two Sorted Lists | Microsoft | Yahoo | Amazon
    // T: O(n1+n2), S: O(n1+n2)
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(-1); // this handles edge cases
        ListNode temp = dummy;

        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                ListNode node = new ListNode(list1.val);
                temp.next = node;
                temp = temp.next;
                list1 = list1.next;
            } else {
                ListNode node = new ListNode(list2.val);
                temp.next = node;
                temp = temp.next;
                list2 = list2.next;
            }
        }

        while (list1 != null) {
            ListNode node = new ListNode(list1.val);
            temp.next = node;
            temp = temp.next;
            list1 = list1.next;
        }
        while (list2 != null) {
            ListNode node = new ListNode(list2.val);
            temp.next = node;
            temp = temp.next;
            list2 = list2.next;
        }

        return dummy.next;
    }
}

class Solution {
    // OPTIMAL
    // T: O(n1+n2), S: O(1) 
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummyNode = new ListNode(-1);
        ListNode temp = dummyNode;

        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                temp.next = list1;
                temp = list1;
                list1 = list1.next;
            } else {
                temp.next = list2;
                temp = list2;
                list2 = list2.next;
            }
        }

        if (list1 != null) {
            temp.next = list1;
        } else {
            temp.next = list2;
        }
        return dummyNode.next;
    }
}
