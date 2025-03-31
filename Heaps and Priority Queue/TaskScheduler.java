class Solution {
    // refer codestorywithMIK + ChatGPT o3 for code
    // BRUTE/BETT/OPTIMAL
    // can be done with Greedy as well.
    // T: O(m + 26log26); m = tasks.length
    // S: O(26); 
    /*
     * 1. Intuition - If we pick the maximum frequency task first everytime, then overall time will reduce as CPU will remain less idle.
     * 2. So, we need to greedily choose the highest frequency task in every iteration.
     * 3. In every cycle of cpu, we are processing for n+1 time.
     * 4. Because, after processing a task for 1 unit, we have to wait for `n` to process it again. Hence, we process another task if available in that time.
     * 5. Code - a freq[] of size 26 for tasks, a maxHeap.
     * Add all non-zero frequency to the heap initially.
     * 6. Loop over the heap and process the non-zero freq. elements.
     * 7. In this, process the elements in a cycle. The cycle length = n+1. So process until the current cycle is complete. And add the non-zero frequency elements to a list.
     * 8. After the cycle is complete, add the elements to the heap for processing in next cycle.
     * 9. If there are no tasks left, then heap is empty, so break.
     * 10. Add the remaining cycle to the total time because it indicates the idle time.
     */
    public int leastInterval(char[] tasks, int n) {
        int[] freq = new int[26];
        for (char task : tasks) {
            freq[task - 'A']++;
        }

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        for (int f : freq) {
            if (f > 0) {
                maxHeap.offer(f);
            }
        }

        int time = 0;
        while (!maxHeap.isEmpty()) {
            int cycle = n + 1;
            List<Integer> temp = new ArrayList<>();

            while (cycle > 0 && !maxHeap.isEmpty()) {
                int count = maxHeap.poll();
                // execute task
                time++;
                cycle--;
                count--;

                if (count > 0) {
                    temp.add(count);
                }
            }

            for (int val : temp) {
                maxHeap.offer(val);
            }

            if (maxHeap.isEmpty()) {
                break;
            }
            time += cycle; // idle count
        }
        return time;
    }
}

// MATH solution for this
