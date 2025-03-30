class Solution {
    // BRUTE FORCE
    // T: O(n^2 * logn)
    // S: O(n)
    /*
     * 1. Copy array to list.
     * 2. Sort list everytime to get first and second elements.
     * 3. We sort in asc. order and get the last two elements.
     * 4. If diff != 0, add to list, else don't.
     * 5. In the end, if list has elements then return, else return 0.
     */
    public int lastStoneWeight(int[] stones) {
        List<Integer> list = new ArrayList<>();
        for(int stone : stones ){
            list.add(stone);
        }

        while(list.size() > 1){
            Collections.sort(list);
            int first = list.remove(list.size() - 1);
            int second = list.remove(list.size() - 1);
            int diff = first - second;

            if(diff != 0){
                list.add(diff);
            }
        }

        return list.size() == 0? 0 : list.get(0);
    }
}

class Solution {
    // BETTER
    // T: O(n * log n)
    // S: O(n)
    /*
     * 1. Maintain a Max heap. This makes sure we have largest at peek.
     * 2. Poll the first and second elements to get largest and second largest.
     * 3. If diff != 0, add to heap.
     * 4. In the end, if heap has elements then return, else return 0.
     */
    public int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder()); // max heap in java
        for(int stone : stones ){
            maxHeap.offer(stone);
        }

        while(maxHeap.size() > 1){
            
            int first = maxHeap.poll();
            int second = maxHeap.poll();
            int diff = first - second;

            if(diff != 0){
                maxHeap.offer(diff);
            }
        }

        return maxHeap.size() == 0? 0 : maxHeap.poll();
    }
}

// BUCKET SORT WAY
