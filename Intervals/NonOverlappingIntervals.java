class Solution {
    // BRUTE/BETTER/OPTIMAL
    // T: O(nlogn); nlogn + n
    // S: O(1);
    /**
     * 1. Sort the intervals on end time because by keeping earlier ending interval first,
     * we can maximize the number of intervals we can accomodate without overlapping.
     * 2. Initialize `lastEndTime` with the end of the first interval.
     * 3. For each interval from 2nd onwards:
     *    a. If start >= lastEndTime, there is NO overlap:
     *        - we can safely keep this interval.
     *        - update lastEndTime to this interval's end.
     *    b. Else, there is an overlap:
     *        - increment the removal count.
     *        - we do not update lastEndTime, so we effectively keep the previous interval (with the earlier end).
     * 4. At the end, the number of overlaps we found = the number of intervals to remove.
     */
    public int eraseOverlapIntervals(int[][] intervals) {
        if(intervals.length == 0){
            return 0;
        }

        Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
        int lastEndTime = intervals[0][1];
        int i = 1;
        int overlaps = 0;

        while (i < intervals.length) {
            if (intervals[i][0] >= lastEndTime) {
                lastEndTime = intervals[i][1];
            } else {
                overlaps++;
            }
            i++;
        }
        return overlaps;
    }
}


class Solution {
    // refer STRIVER
    // BRUTE/BETTER/OPTIMAL
    // GREEDY
    // T: O(nlogn + n); sorting + iteration
    // S: O(1);
    // similar to GFG - [N meetings in one room]
    // ans is just the inverse of the above problem
    public int eraseOverlapIntervals(int[][] intervals) {
        int n = intervals.length;
        // 1. sort the intervals on end time in asc. order
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));

        // 2. if intervals[i][0] (start time) <= lastEndTime, then we can have the
        // current meeting
        // 3. so increment count and change lastEndTime to the end of this
        // for this particular question, we take "<=" as mentioned in Note.

        int count = 1;
        int lastEndTime = intervals[0][1];
        for (int i = 1; i < n; i++) {
            if (intervals[i][0] >= lastEndTime) {
                count++;
                lastEndTime = intervals[i][1];
            }
        }
        // max. meetings we can have = count
        // so the min. number of intervals to remove so as to have max meetings
        // is (n - count)
        return (n - count);
    }
}
