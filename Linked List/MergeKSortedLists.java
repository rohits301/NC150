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
    // refer STRIVER's video
    // T: O(n*k*(k+1)/2), S: O(1)
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) {
            return null;
        }

        ListNode head = lists[0];
        for (int i = 1; i < lists.length; i++) {
            head = mergeTwoSortedLists(head, lists[i]);
        }
        return head;
    }

    private ListNode mergeTwoSortedLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(-1);
        ListNode temp = dummy;

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
        return dummy.next;
    }
}

class Solution {
    // BETTER
    // refer STRIVER video
    // T: O(n*k*logk) = O(k*logk+ n*k*logk), S: O(k)
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) {
            return null;
        }
        ListNode dummy = new ListNode(-1);
        ListNode temp = dummy;

        // min heap
        PriorityQueue<ListNode> pq = new PriorityQueue<>(lists.length, (a, b) -> a.val - b.val);
        for (int i = 0; i < lists.length; i++) {
            if (lists[i] != null) { // check for case when any list in the array does not have a head
                pq.add(lists[i]);
            }
        }

        while (!pq.isEmpty()) {
            ListNode node = pq.poll();
            temp.next = node;
            temp = temp.next;
            if (node.next != null) {
                pq.add(node.next);
            }
        }
        return dummy.next;
    }
}

class Solution {
    // BETTER
    // T: O(n*k*logk), S: O(n*k) - recursive stack
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        return mergeKSortedListsHelper(lists, 0, lists.length - 1);
    }

    public ListNode mergeKSortedListsHelper(ListNode[] lists, int start, int end) {
        if (start == end) {
            return lists[start];
        }

        int mid = start + (end - start) / 2;
        ListNode left = mergeKSortedListsHelper(lists, start, mid);
        ListNode right = mergeKSortedListsHelper(lists, mid + 1, end);
        return merge(left, right);
    }

    public ListNode merge(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(-1);
        ListNode temp = dummy;

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
        return dummy.next;
    }
}
