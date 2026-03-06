class Solution {
    // BRUTE FORCE
    // Linear Search
    // T: O(n) 
    // S: O(1)
    public int search(int[] nums, int target) {
        int ans = -1;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                ans = i;
                break;
            }
        }
        return ans;
    }
}

class Solution {
    // refer Neetcode's video
    // OPTIMAL
    // T: O(log n)
    // S: O(1)
    /*
     * 1. Since array is sorted, think Binary search.
     * 2. Left sorted half -> [lo, mid] - Why include mid here? and not in the other half
     * Because, we use <= to include mid in this check. This correctly handles
     * cases with 1 or 2 elements, where `lo` can be equal to `mid`.
     * 3. Right sorted half -> [mid + 1, hi]
     * 4. If the target lies in left sorted half, go left and reduce `hi` to `mid-1`, indicating that our new search area is only upto `mid`.
     * 5. else, increase `lo` to `mid + 1`, as the target is not in left sorted half.
     * 6. Similarly, for right. If it is in the right sorted half, we set `lo = mid + 1`, as new search area is after `mid`.
     * 7. Condition for finding the target is same as in binary search. `nums[mid] == target`.
     */
    public int search(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;

        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            if (nums[mid] == target) {
                return mid;
            }

            // left sorted half
            if (nums[lo] <= nums[mid]) {
                if (target < nums[lo] || target > nums[mid]) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            } else {
                // right sorted half
                if (target > nums[hi] || target < nums[mid]) {
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }
        }
        return -1;
    }
}
