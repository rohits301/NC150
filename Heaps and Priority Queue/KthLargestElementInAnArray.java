class Solution {
    // BRUTE FORCE
    // T: O(nlogn)
    // S: O(1)
    public int findKthLargest(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }
}

class Solution {
    // refer NEETCODE
    // BETTER
    // T: O(nlogk)
    // S: O(k)
    /*
     * 1. k-largest -> use Min heap. Because if we maintain heap of size k, only k largest will be remaining in the end, and rest all will be polled.
     * 2. Min heap ensures smallest is at peek.
     * 3. Hence, when heap has only k elements, then the peek is the smalles of the them.
     */
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int i = 0; i < nums.length; i++) {
            minHeap.offer(nums[i]);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        return minHeap.peek();
    }
}

// QUICK SELECT SOLUTION
