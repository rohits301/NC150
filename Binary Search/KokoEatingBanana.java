class Solution {
    /**
     * BRUTE FORCE
     * T: O(m*n); m = maximum possible eating speed, n = number of piles
     * S: O(1)
     * Approach:
     * 1. We need to find the smallest `k` such that the total time to
     * eat all bananas is no more than `h`. The time to eat a single pile of `p`
     * bananas with speed `k` is calculated as `ceil(p / k)`.
     *
     * This method iterates through every possible speed, starting from `k = 1`.
     * For each speed, it calculates the total time required to consume all piles.
     * The first speed `k` that allows Koko to finish within the `h` hour limit
     * is guaranteed to be the minimum possible speed, so it is returned immediately.
     */
    public int minEatingSpeed(int[] piles, int h) {
        // Start checking for the eating speed k from 1, the smallest possible speed.
        int k = 1;

        while (true) {
            // Use 'long' for totalHours to prevent potential overflow if piles are large.
            long totalHours = 0;

            // For the current speed k, calculate the hours needed to eat all piles.
            for (int pile : piles) {
                // Calculate hours for the current pile using ceiling division.
                // The formula (pile + k - 1) / k is a common integer arithmetic
                // trick to compute ceil(pile / k).
                totalHours += (long)(pile + k - 1) / k;
            }

            // Check if the calculated hours are within the allowed time.
            if (totalHours <= h) {
                // Since we are checking k in increasing order (1, 2, 3, ...),
                // the first k that satisfies the condition is the minimum possible speed.
                return k;
            }

            // If the current speed k is too slow, increment it and try again.
            k++;
        }
    }
}

class Solution {
    // refer STRIVER
    // T: O(nlog n); `n` for loop on arr and `log n` for binary search.
    // S: O(1)
    /*
     * 1. The reason for ceil -> (val + k - 1) / k
     * in case of `val` not divisible by `k`
     * 2. the addition of `k-1` to val makes the entire division round up to the next integer
     * 3. e.g. k = 4, val = 9
     * (9 + 4 - 1) / 4 = 12 / 4 = 3
     * in case of larger number, val = 11
     * (11 + 4 - 1) / 4 = 14 / 4 = 3
     * so ceil is calculated correctly in both cases, when number is slightly big and
     * when it is really big.
     * 4. The min rate of eating bananas = 1, and the max rate is the largest pile of bananas.
     * 5. If the `time taken to eat bananas is <= h`, then it is possible to eat, so we find our ans in this condition.
     * 6. else, we have to increase the rate of eating.
     * Also, we want the minimum rate of eating among all possible answers, so `hi = mid - 1` to shorten the search area.
     * 7. The binary search is happening on the values of time/rate. 
     * 8. Since the rate ranges from 1 to `max`, so it is a sorted set of values and we can execute binary search here.
     */
    public int minEatingSpeed(int[] piles, int h) {
        int max = -1;
        for (int pile : piles) {
            if (pile > max) {
                max = pile;
            }
        }

        int lo = 1; // min rate of eating
        int hi = max; // max rate
        int ans = 1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int time = eatBananas(piles, mid, h);

            if (time <= h) {
                ans = mid;
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }

        return ans;
    }

    private int eatBananas(int[] arr, int k, int h) {
        int time = 0;
        for (int val : arr) {
            time += (val + k - 1) / k;
            if (time > h) {
                // THIS IS TO PREVENT OVERFLOW FOR LARGE NUMBERS
                // TAKING `LONG` datatype, CAN SOLVE THIS, BUT NOT IN INTERVIEW
                // COZ. THESE ARE REAL WORLD CONSTRAINTS
                break;
            }
        }

        return time;
    }
}
