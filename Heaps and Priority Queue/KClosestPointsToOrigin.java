class Solution {
    // refer NEETCODE
    // BRUTE FORCE
    // T: O(nlogn); sorting
    // S: O(1); excluding answer array
    /*
     * 1. Sort the array based on Euclidean Distance.
     * 2. we don't actually need square root (sqrt). Because, if `v1 < v2`, then `sqrt(v1) < sqrt(v2)`.
     * 3. So, we can directly sort the array based on the squares of distance from origin.
     * 4. Copy first k-elements to result and return.
     */
    public int[][] kClosest(int[][] points, int k) {

        Arrays.sort(points, (a, b) -> (a[0] * a[0] + a[1] * a[1]) - (b[0] * b[0] + b[1] * b[1]));

        int[][] res = new int[k][2];
        for (int i = 0; i < k; i++) {
            int x = points[i][0];
            int y = points[i][1];
            res[i] = new int[]{ x, y };
        }
        return res;
    }
}

class Solution {
    // refer NEETCODE
    // BETTER
    // T: O(n * log k); n operations, each offer(), poll() - O(log k).
    // S: O(k); heap of size = k.
    /*
     * 1. K-closest = K-smallest points. Smallest in terms of their euclidean distance. Hence, MAX HEAP.
     * 2. Create a max heap and maintain size as k. 
     * 3. With this, the k-smallest remain in heap every time and the largest are polled.
     * 4. copy the k-elements to result and return.
     */
    public int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>(
                (a, b) -> Integer.compare((b[0] * b[0] + b[1] * b[1]), (a[0] * a[0] + a[1] * a[1])));

        for (int i = 0; i < points.length; i++) {
            maxHeap.offer(points[i]);
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }

        int[][] res = new int[k][2];
        int i = 0;
        while (!maxHeap.isEmpty()) {
            res[i++] = maxHeap.poll();
        }
        return res;
    }
}

// OPTIMAL - QUICK SELECT
