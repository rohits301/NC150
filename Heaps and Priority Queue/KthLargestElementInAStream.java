// refer NEETCODE
// BRUTE FORCE
// T: O(m * nlogn); `m` calls made to add method, and nlog n for sorting n length array.
// S: O(m); O(m) - list space, O(n) - space taken by sorting algorithm.
/*
 * 1. We copy array elements to array list.
 * 2. In add, we sort array in desc. order and return the kth largest.
 * 3. Acc. to question, `k <= nums.length + 1`, so no need to worry about array having `< k` elements.
 */
class KthLargest {
    List<Integer> arr;
    int k;

    public KthLargest(int k, int[] nums) {
        this.k = k;
        arr = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            arr.add(nums[i]);
        }
    }

    public int add(int val) {
        arr.add(val);
        Collections.sort(arr, Collections.reverseOrder());

        return arr.get(k - 1);
    }
}

// refer NEETCODE
// OPTIMAL
// T: O(m * log k); `m` calls made to add method, and O(log k) for addition and removal from heap.
// S: O(k); O(k) - heap size
/*
 * 1. Use min heap of size k.
 * 2. In case of k-largest, we always go with min heap. Because, in a min heap of size k, the kth largest will be at peek of the heap.
 * 3. So, add everything to heap and maintain size = k.
 * 4. If size > k, poll() from heap.
 */
class KthLargest {
    PriorityQueue<Integer> minHeap;
    int k;

    public KthLargest(int k, int[] nums) {
        this.k = k;
        minHeap = new PriorityQueue<>();

        for (int i = 0; i < nums.length; i++) {
            minHeap.offer(nums[i]);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }
    }

    public int add(int val) {
        minHeap.offer(val);
        if (minHeap.size() > k) {
            minHeap.poll();
        }
        return minHeap.peek();
    }
}

/**
 * Your KthLargest object will be instantiated and called as such:
 * KthLargest obj = new KthLargest(k, nums);
 * int param_1 = obj.add(val);
 */
