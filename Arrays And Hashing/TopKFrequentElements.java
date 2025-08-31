class Solution {
    // BRUTE FORCE
    // T: O(k*n), S: O(n)
    /*
     * Approach and Intuition:
     * 1. Use a HashMap to count the frequency of each element in the array.
     * 2. Iterate over the HashMap 'K' times to find the top K elements.
     * 3. After each iteration, remove the maximum frequency element from the HashMap.
     * 4. This way, in the subsequent iterations, we can find the next maximum frequency element.
     */
    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        int[] ans = new int[k];

        for (int i = 0; i < k; i++) {
            int maxVal = -1;
            int maxKey = -1;
            
            for(Map.Entry<Integer, Integer> entry: map.entrySet()){
                if(entry.getValue() > maxVal){
                    maxVal = entry.getValue();
                    maxKey = entry.getKey();
                }
            }
            ans[i] = maxKey;
            map.remove(maxKey);
        }
        return ans;
    }
}

class Solution {
    // BETTER
    // HARD TO REMEMBER AND A BIT UNUSUAL
    // IGNORE
    // T: O(n logn), S: O(n)
    public int[] topKFrequent(int[] nums, int k) {
        int[] ans = new int[k];

        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        // Convert map to list of entries
        List<Map.Entry<Integer, Integer>> entryList = new ArrayList<>(map.entrySet());

        // Sort entries based on frequency in descending order
        entryList.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        for (int i = 0; i < k; i++) {
            ans[i] = entryList.get(i).getKey();
        }

        return ans;
    }
}

class Solution {
    // refer Aditya Verma
    // BETTER
    // T: O(n * log k), S: O(n + k)
    /**
     * Approach: Min Heap (Priority Queue)
     * 
     * 1. Build a frequency map  
     *    - Example:  
     *      Input: [10, 20, 20, 20, 20, 20, 30, 30, 10, 20, 30]  
     *      Frequency Map: {10 -> 2, 20 -> 6, 30 -> 3}  
     *
     * 2. Use a Min Heap (PriorityQueue) to store the top K elements  
     *    - The heap stores numbers sorted by their frequency in ascending order.  
     *    - The heap only keeps k elements, removing the least frequent ones as new elements are added.  
     *    - Example:  
     *      - Insert 10 (freq: 2) → heap: [10]  
     *      - Insert 20 (freq: 6) → heap: [10, 20]  
     *      - Insert 30 (freq: 3) → heap: [10, 30] (removes 10, since freq 2 < freq 3)  
     *
     * 3. Extract the top K elements from the heap  
     *    - Since the heap stores only k elements, the top K frequent numbers are extracted.  
     *
     * Time Complexity Analysis:  
     * - Building frequency map → O(n), where n is the number of elements in nums.  
     * - Adding elements to Min Heap → O(n log k):  
     *   - Each insertion in a heap takes O(log k).  
     *   - Since we insert n elements into the heap (removing when size > k), it results in O(n log k).  
     * - Extracting elements from the heap → O(k log k):  
     *   - Extracting k elements takes O(k log k).  
     * - Overall Complexity → O(n log k).  
     *
     * Space Complexity Analysis:  
     * - HashMap stores frequencies → O(n).  
     * - Min Heap stores k elements → O(k).  
     * - Output array stores k elements → O(k).  
     * - Overall Complexity → O(n + k).  
     * 
     * offer(E e) vs add(E e)
     * - Use offer() as it doesn't throw an exception and instead returns false when the queue is full.
     * - add() throws an IllegalStateException if the queue has a capacity limit and is full.
     *
     * poll() vs remove()
     * - Use poll() as it doesn't throw an exception when the queue is empty. It returns null instead.
     * - remove() throws a NoSuchElementException if the queue is empty.
     */

    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();

        // Step 1: Build the frequency map
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        // Step 2: Min Heap to store top k frequent elements
        PriorityQueue<Integer> minHeap = new PriorityQueue<>((a, b) -> map.get(a) - map.get(b));

        // Step 3: Add elements to the heap
        for (int key : map.keySet()) {
            minHeap.offer(key);
            if (minHeap.size() > k) {
                minHeap.poll(); // Remove the element with the lowest frequency
            }
        }

        // Step 4: Extract top k elements from the heap
        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = minHeap.poll();
        }

        return res;
    }
}

class Solution {
    // OPTIMAL
    // REFER NEETCODE
    // T: O(n), S: O(n)
    // Bucket Sort
    /**
     * APPROACH:
     * 
     * 1. Use a HashMap to count the frequency of each element:
     *    - Example:
     *      Input: [10,20,20,20,20,20,30,30,10,20,30]
     *      Frequency Map: {10 -> 2, 20 -> 6, 30 -> 3}
     * 
     * 2. Use a frequency bucket (Array of Lists) to store numbers with the same frequency:
     *    - Create an array `freq` where `freq[i]` holds a list of numbers that appear exactly `i` times.
     *    - The maximum possible frequency of any element is `nums.length`, so `freq` has `nums.length + 1` buckets.
     *    - Example after processing:
     *      `freq[2] -> {10}, freq[3] -> {30}, freq[6] -> {20}`
     * 
     * 3. Collect the top K frequent elements:
     *    - Iterate from the highest possible frequency (`nums.length`) down to `1`.
     *    - Extract elements from `freq[i]` and add them to the result array until `k` elements are collected.
     */
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        List<Integer>[] freq = new List[nums.length + 1];

        // Initialize the bucket array with empty lists
        for (int i = 0; i < freq.length; i++) {
            freq[i] = new ArrayList<>();
        }

        // Step 1: Build the frequency map
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        // Step 2: Populate the frequency bucket
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            freq[entry.getValue()].add(entry.getKey());
        }

        // Step 3: Collect top k frequent elements
        int[] res = new int[k];
        int idx = 0;
        
        // Iterate from highest frequency to lowest
        for (int i = freq.length - 1; i > 0 && idx < k; i--) {
            for (int f : freq[i]) {
                res[idx++] = f;
                if (idx == k) {
                    return res; // Return early if we have found k elements
                }
            }
        }

        return res;
    }
}
