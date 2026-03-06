class Solution {
    // BRUTE FORCE
    // T: O(n)
    // S: O(1)
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
    // T: O(log n)
    // S: O(1)
    /*
     * Approach:
     * 1. Since the array is sorted, think Binary search.
     * 2. If the entire array is sorted, it means it was not rotated, then the minimum element is `nums[0]`.
     * 3. Else, the array is rotated.
     * 4. Since, the array is rotated around the pivot point, we use binary search to find the pivot as it is the minimum value in the array.
     * Why? Because the pivot point is the point of rotation and the smallest element in the array. This is the foundation of the rotated sorted array. The pivot is the element where there is a sharp drop.
     * 5. We divide the array into two halves and check which half is sorted.
     * 6. If the left half is sorted, update the minimum element and search in the right half.
     * 7. If the right half is sorted, update the minimum element and search in the left half.
     */
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
