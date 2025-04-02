class MedianFinder {
    // Brute Force - by NEETCODE
    // TLE in most situations
    // T: O(n) for every add operation n = no. of elements in the list
    // findMedian - O(1)
    // S: O(n)
    private static ArrayList<Integer> list;

    public MedianFinder() {
        list = new ArrayList<>();
    }

    public void addNum(int num) {
        int i = 0;
        while (i < list.size() && num >= list.get(i)) {
            i++;
        }

        // add(index, num) -> takes O(n) time, shifts current element at the index to
        // the right
        list.add(i, num);
    }

    public double findMedian() {
        int n = list.size();
        if (n % 2 == 0) {
            return ((double) list.get((n / 2) - 1) + (double) list.get(n / 2)) / 2;
        }
        return (double) list.get(n / 2);
    }
}

class MedianFinder {
    // refer NEETCODE
    // OPTIMAL
    // T: O(log n) for each addNum() call - n = no. of elements in the heap
    // S: O(n)
    PriorityQueue<Integer> maxHeap; // to store small numbers
    PriorityQueue<Integer> minHeap; // to store large numbers

    public MedianFinder() {
        maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        minHeap = new PriorityQueue<>();
    }
    
    // T: O(logn)
    public void addNum(int num) {
        maxHeap.offer(num);

        // first rebalance to make sure heaps are valid
        // every element in maxHeap should be <= every element in minHeap
        if(!maxHeap.isEmpty() && !minHeap.isEmpty() && 
            maxHeap.peek() > minHeap.peek()){
            
            minHeap.offer(maxHeap.poll());
        }
        // the order of below conditions doesn't matter
        // whichever heap has more elements (difference >= 2)
        // then, rebalance 
        if(maxHeap.size() > minHeap.size() + 1){
            minHeap.offer(maxHeap.poll());
        }
        if(minHeap.size() > maxHeap.size() + 1){
            maxHeap.offer(minHeap.poll());
        }
    }
    
    // T: O(1)
    public double findMedian() {
        // whichever heap has more elements has the median at its peek
        if(maxHeap.size() > minHeap.size()){
            return (double) maxHeap.peek();
        } else if(minHeap.size() > maxHeap.size()){
            return (double) minHeap.peek();
        } else {
            // equal size => even number of elements
            return ((double) maxHeap.peek() + (double) minHeap.peek()) / 2;
        }
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
