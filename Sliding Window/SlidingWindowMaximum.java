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
    // refer GEMINI
    // BETTER - Sub-optimal Heap approach
    // T: O(n log n); Each of the n elements is offered and polled at most once.
    // S: O(n); worst case, all elements are in the heap.
    /**
     * 1. The Challenge with a Naive Heap:
     * A standard heap (PriorityQueue) does not support efficient removal of an arbitrary element.
     * When our window slides, we need to discard the element that falls off the back (e.g., `nums[i-k]`).
     * Finding and removing this specific element from a heap is an `O(k)` operation, 
     * which would degrade the total time complexity to `O(n*k)`.
     *
     * 2. The Solution - "Lazy Removal" Intuition:
     * Instead of immediately paying the `O(k)` cost to remove an element, we leave it in the heap.
     * An old, out-of-window ("stale") element only matters if it's the maximum. 
     * If a newer, larger element is in the window, the old one is irrelevant, even if it's still in the heap.
     * We only clean up these stale elements when they are at the very top (`heap.peek()`), 
     * allowing us to use the efficient `poll()` operation.
     *
     * 3. Data Structure:
     * Use a Max-Heap that stores pairs of `[value, index]`. 
     * The index is crucial for knowing if a top element is stale (no longer in the current window).
     *
     * 4. Algorithm Steps:
     * a. Initialize the heap with the first `k` elements.
     * b. The maximum for the first window is at the top of the heap. Add it to the results.
     * c. Slide the window from `i = k` to the end. For each new element:
     * i. Add the new element `[nums[i], i]` to the heap.
     * ii. Check the heap's top element. If its index is outside the current window bounds (`index <= i - k`), it's stale.
     * iii. Keep polling stale elements from the top until the `peek()` element is valid (within the window).
     * iv. The valid top element is the maximum for the current window. Add it to the results.
     *
     * 5. Performance Caveat:
     * This "lazy" approach can cause the heap size to grow up to `n` in worst-case scenarios (like a sorted array), 
     * resulting in an `O(n log n)` time complexity.
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] ans = new int[n - k + 1];
        
        // Max-heap storing pairs of [value, index]
        // We sort in descending order of value.
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> b[0] - a[0]);

        // Initialize the first window
        for (int i = 0; i < k; i++) {
            heap.offer(new int[]{nums[i], i});
        }

        // The max of the first window is at the top
        ans[0] = heap.peek()[0];

        // Slide the window for the rest of the array
        for (int i = k; i < n; i++) {
            // Add the new element to the heap
            heap.offer(new int[]{nums[i], i});
            
            // "Lazy Removal": Remove stale elements if they surface on the top of the heap.
            // A stale element is one whose index is outside the current window [i - k + 1, i].
            while (heap.peek()[1] <= i - k) {
                heap.poll();
            }

            // The top of the heap is now the max for the current window
            ans[i - k + 1] = heap.peek()[0];
        }
        
        return ans;
    }
}

class Solution {
    // refer STRIVER - old video
    // OPTIMAL - Monotonic Deque Approach
    // T: O(n); n + n = 2n, maximum `n` offer and `n` poll invocations are made, so deque is Amortized O(n) for n operations.
    // S: O(k); Queue size at any time is `k`

    /**
     * Approach:
     * 1. Use a Deque (Double Ended Queue) to store indices of elements from the input array.
     * 2. The deque will be maintained such that the indices in it correspond to values in `nums`
     * that are in strictly decreasing order. This ensures the index of the maximum element in the
     * current window is always at the front of the deque (q.peekFirst()).
     * 3. Iterate through the array with index `i`:
     * a. **Clean the deque:** Before adding a new element, remove indices from the front that
     * are no longer in the current window. An index `j` is out of the window `[i-k+1, i]`
     * if `j <= i-k`.
     * b. **Maintain decreasing order:** Before adding `i`, remove all indices from the back of
     * the deque that correspond to values smaller than `nums[i]`. 
     * These elements can never be the maximum in any future window that also includes `nums[i]`.
     * c. **Add current index:** Add the current index `i` to the back of the deque.
     * 4. Once the window is full (i.e., `i >= k-1`), the maximum for that window is `nums[q.peekFirst()]`.
     * Add this to the result array.
     *
     * 
     * Intuition:
     * The intuition is to maintain a deque of indices where the corresponding values in nums are in strictly decreasing order. 
     * This ensures that the index at the front of the deque (q.peekFirst()) always corresponds to the largest element in the current window. 
     * When we consider a new element nums[i], we remove all indices from the end of the deque that correspond to values smaller than nums[i]. 
     * These smaller elements can never be the maximum in any future window that includes nums[i], so they are discarded. 
     * This way, the deque efficiently keeps track of potential maximums for the current and future windows.
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        if(nums == null || nums.length == 0 || nums.length < k){
            return new int[0];
        }
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

            q.offerLast(i); // default offer is offerLast()

            if (i >= k - 1) {
                // k elements are discovered
                // add to array
                ans[i - k + 1] = nums[q.peekFirst()];
            }
        }
        return ans;
    }
}
