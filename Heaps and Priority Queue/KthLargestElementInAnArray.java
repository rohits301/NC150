class Solution {
    // BRUTE FORCE
    // T: O(nlogn)
    // S: O(1)
    public int findKthLargest(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }
}
