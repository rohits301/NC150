class Solution {
    // T: O(log n)
    // S: O(1)
    public int search(int[] nums, int target) {
        int n = nums.length;
        int lo = 0, hi = n - 1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] < target) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return -1;
    }
}
