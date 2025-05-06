class Solution {
    // refer STRIVER
    // BRUTE FORCE
    // T: O(n*k); 
    // S: O(n-k+1); answer array
    // Iterate over the array and find max for each window of size k
    /*
     * 1. For every `k`-length window, we need the maximum. There are `n-k+1` such windows.
     * 2. Two loops - outer loop for iterating over the array, i.e., the start of the window.
     * 3. Inner loop to find the max in the `k` elements.
     * 4. Update answer for all `k`-length windows.
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] ans = new int[n - k + 1];
        for (int i = 0; i < (n - k + 1); i++) {
            ans[i] = nums[i];
            for (int j = i; j < i + k; j++) {
                ans[i] = Math.max(ans[i], nums[j]);
            }
        }

        return ans;
    }
}

class Solution {
    // refer STRIVER, old video
    // Array Deque approach
    // T: O(n); n + n = 2n, maximum `n` offer and `n` poll invocations are made
    // S: O(k); Queue size at any time is `k`
    /**
     * Approach:
     * 1. Use ArrayDeque - it is a Doubly Linked List (DLL) internally.
     * 2. Intuition - we need to keep track of max for `k` elements. This has to be done as we discover the elements. So, it NGE(next greater element) on right. 
     * As soon as we discover a `nums[i]` that is smaller or equal to last element in queue (`q.peekLast()`), we push it to queue.
     * Another way to think about this - we remove all elements from the end of the queue that are smaller than nums[i]. This way we maintain strictly decreasing order.
     * Hence, the greatest is always in the beginning of the queue.
     * 3. We need to clean-up the queue as well to make sure, stale window entries are removed. So remove from front, when the front index in queue is `i-k`. 
     * Because, for every index `i`, `i-k` is the first element outside of window of size `k` ending at `i`.
     * 4. We store indices in queue to enable so that the clean-up for non-window elements is efficient.
     * 5. The size of the answer array = the number of windows of size `k` possible in the array => `n-k+1`.
     *
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] ans = new int[n - k + 1];

        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!q.isEmpty() && q.peekFirst() == (i - k)) {
                q.pollFirst(); // default poll is pollFirst()
            }

            while (!q.isEmpty() && nums[q.peekLast()] < nums[i]) {
                q.pollLast();
            }

            q.offer(i);

            if (i >= k - 1) {
                // k elements are discovered
                // add to array
                ans[i - k + 1] = nums[q.peekFirst()];
            }
        }
        return ans;
    }
}
