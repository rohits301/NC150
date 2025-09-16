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
    // T: O(n log k); Each of the n elements is offered and polled at most once.
    // S: O(k); The heap size is at most k.
    /**
     * Intuition:
     * The core problem is that a standard heap does not support efficient removal of an arbitrary element. When our window slides forward, we add the new element (nums[i]) but also need to discard the element that just fell off the back (nums[i-k]). Searching for and removing this specific element from a heap takes O(k) time, which brings us back to the O(n*k) brute-force complexity.
     *
     * The Solution: "Lazy Removal"
     * Instead of actively removing the element that falls out of the window, we use a "lazy" approach. We leave the out-of-window elements in the heap and only deal with them when they surface at the very top.
     * 
     * 1. Create a Max-Heap that stores pairs of [value, index]. We need the index to know if an element is "stale" (i.e., no longer in the current window).
     * 2. Add the first k elements to the heap.
     * 3. The maximum for the first window is at the top of the heap. Add it to our results.
     * 4. Now, slide the window from `i = k` to the end: 
     * a. Add the new element `[nums[i], i]` to the heap. 
     * b. Look at the top element of the heap, `heap.peek()`. Is its index still valid for the current window `[i-k+1, i]`? 
     * c. If not, it's a stale element. Poll it from the heap and repeat step (b) until the element at the top is within the current window's bounds. 
     * d. If the top element is valid, it's the maximum for the current window. Add it to our results.
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
            
            // "Lazy Removal": Remove all stale elements from the top of the heap.
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

            q.offer(i); // this is offerLast() operation by default

            if (i >= k - 1) {
                // k elements are discovered
                // add to array
                ans[i - k + 1] = nums[q.peekFirst()];
            }
        }
        return ans;
    }
}
