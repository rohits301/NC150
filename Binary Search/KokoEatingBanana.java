class Solution {
    /*
     * The reason for ceil -> (val + k - 1) / k
     * in case of `val` not divisible by `k`
     * the addition of `k-1` to val makes the entire division round up to the next integer
     * e.g. k = 4, val = 9
     * (9 + 4 - 1) / 4 = 12 / 4 = 3
     * in case of larger number
     * (10 + 4 - 1) / 4 = 13 / 4 = 3
     * so ceil is calculated correctly in both cases, when number is slight big and
     * when it is really big
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
                // LONG CAN SAVE BUT NOT IN INTERVIEW
                // COZ. THESE ARE REAL CONSTRAINTS
                break;
            }
        }

        return time;
    }
}
