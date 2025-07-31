class Solution {
    // BRUTE FORCE
    // T: O(n), S: O(1)
    int idx = 0;

    public int findMin(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n - 1; i++) {
            // pivot point is the answer
            if (nums[i] > nums[i + 1]) {
                idx = i + 1;
                break;
            }
        }
        // if nums is already sorted, idx=0, so smallest
        // else idx has the index of smallest element
        return nums[idx];
    }
}

class Solution {
    // OPTIMAL
    // refer STRIVER's video
    // T: O(log n), S: O(1)
    public int findMin(int[] nums) {
        int lo = 0, hi = nums.length - 1;
        int ans = Integer.MAX_VALUE;

        while (lo <= hi) {
            if (nums[lo] <= nums[hi]) {
                ans = Math.min(ans, nums[lo]);
                break;
            }

            int mid = (lo + hi) / 2;

            if (nums[lo] <= nums[mid]) {
                ans = Math.min(ans, nums[lo]);
                lo = mid + 1;
            } else {
                ans = Math.min(ans, nums[mid]);
                hi = mid - 1;
            }
        }
        return ans;
    }
}
