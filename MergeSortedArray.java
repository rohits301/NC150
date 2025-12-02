class Solution {
    // ORACLE INTERVIEW 2025 - ROUND 1
    // T: O(n+m)
    // S: O(1)
    /*
     * 1. Start Backwards - reverse 2 pointer
     * 2. Starting backwards so array is populated from back.
     * 3. We only care if nums2 is finished, so the main condition in while is `j>=0`.
     * 4. This is because - a) nums1 remaining, nums2 finished.
     * Since nums1 is already sorted, do nothing.
     * b) nums1 finished, nums2 remaining
     * All elements of nums2 must be copied over to nums1.
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int k = m + n - 1;
        int i = m - 1, j = n - 1;

        while (j >= 0) {
            if (i >= 0 && nums1[i] >= nums2[j]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }

        // Below is not reqd., check bounds in `while` and `if` condition only
        // while(i >= 0){
        //     nums1[k--] = nums1[i--];
        // }

        // while(j >= 0){
        //     nums1[k--] = nums2[j--];
        // }
    }
}
