class Solution {
    // BRUTE FORCE - TLE
    // T: O(n * m)
    // S: O(1)
    public int[] minInterval(int[][] intervals, int[] queries) {
        int n = intervals.length;
        int m = queries.length;
        int[] ans = new int[m];

        for (int j = 0; j < m; j++) {
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                int left = intervals[i][0];
                int right = intervals[i][1];
              
                if (queries[j] >= left && queries[j] <= right) {
                    min = Math.min(min, right - left + 1);
                }
            }
            ans[j] = (min == Integer.MAX_VALUE) ? -1 : min;
        }
        return ans;
    }
}

class Solution {
    // refer NEETCODE
    // T: O(nlogn + qlogq) - sort the intervals and query indices
    // also, offer and poll from heap for n values takes -> n*logn each. Where each operation of offer() and poll() takes O(logn).
    // S: O(n)

    /*
     * 1) Intuition - if we sort the intervals in asc. order of starting point and
     * sort the queries in asc. order, then, for each smaller query we find
     * the answer faster, that is, it is lying in the start of the intervals array.
     * 2) The order of `queries[]` determines the order of `ans[]`.
     * Sorting will change that, hence, we need to form a mapping to ensure retrieval of the original order after sorting.
     * 3) We can use a HashMap or a 2D array (faster) - `qindices[][]`.
     * 4) `qindices[][] => [qVal][qIdx]`
     * 5) sort asc. based on `qVal`.
     * 6) so, `qVal` is the value for comparison and `qIdx` is for index.
     * 7) Idea - store all the intervals in a `minHeap`. This ensure we the peek has
     * the smallest length intervals.
     * 8) Store the interval like - `{length of interval, right}`
     * 9) This ensures in case of same length, we want to remove the interval with
     * `right < qVal`, because it is invalid.
     * 10) If heap is empty -> no valid interval, hence -1.
     * 11) e.g. intervals = [[1,4],[2,4],[3,6],[4,4]], queries = [2,6,4,5]
     */
    public int[] minInterval(int[][] intervals, int[] queries) {
        int[][] qindices = new int[queries.length][2];
        for (int i = 0; i < queries.length; i++) {
            qindices[i][0] = queries[i];
            qindices[i][1] = i;
        }

        Arrays.sort(intervals, (i1, i2) -> i1[0] - i2[0]);
        Arrays.sort(qindices, (q1, q2) -> q1[0] - q2[0]);

        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        int[] ans = new int[queries.length];
        int i = 0;

        for (int[] q : qindices) {
            int qVal = q[0];
            int qIdx = q[1]; 

            while (i < intervals.length && intervals[i][0] <= qVal) {
                int left = intervals[i][0];
                int right = intervals[i][1];

                minHeap.offer(new int[] { right - left + 1, right });
                i++;
            }

            while (!minHeap.isEmpty() && minHeap.peek()[1] < qVal) {
                minHeap.poll();
            }

            ans[qIdx] = (minHeap.isEmpty()) ? -1 : minHeap.peek()[0];
        }

        return ans;
    }
}
