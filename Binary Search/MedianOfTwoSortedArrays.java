class Solution {
    // BRUTE FORCE
    // T: O(m+n)
    // S: O(m+n)
    // merge the two sorted arrays and find median
    // even -> A[x/2 - 1] + A[x/2]
    // odd -> A[x/2]
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int x = m + n;
        int[] merged = mergeTwoSortedArrays(nums1, nums2);
        if (x % 2 == 0) {
            return (merged[x / 2 - 1] + merged[x / 2]) / 2.0;
        }
        return merged[x / 2] * 1.0;
    }

    private int[] mergeTwoSortedArrays(int[] a, int[] b) {
        int m = a.length, n = b.length;
        int x = m + n;
        int[] merged = new int[x];
        int i = 0, j = 0, k = 0;
        
      while (i < m && j < n) {
            if (a[i] < b[j]) {
                merged[k++] = a[i++];
            } else {
                merged[k++] = b[j++];
            }
        }

        while (i < m) {
            merged[k++] = a[i++];
        }
        while (j < n) {
            merged[k++] = b[j++];
        }

        return merged;
    }
}

class Solution {
    // refer Lord STRIVER
    // T: O(min(log m, log n))
    // S: O(1)
    /*
     * 1. We perform a binary search on the smaller array to find the valid partition 
     * where every element in the left half is less than or equal to every element in the right half.
     * 2. There will exist only one solution, only one valid symmetry as array can have only one median.
     * 3. The search space for the partition in an array of size 'm' is [0, m], as there are `m+1` possible cuts. 
     * 4. Binary search to try all partitions. So, we partition the array and define l1,l2,r1,r2 around the partition.
     * 5. Since the arrays are sorted in themselves, so we just need to check in cross-direction that `l1 <= r2` && `l2 <= r1` to validate symmetry.
     * 6. If cross-conditions are satisifed => correct partition, return as only one valid symmetry.
     * 7. If `l1 > r2`, the partition in nums1 is too large. We move the cut left by setting `hi = mid1 - 1`.
     * If `l2 > r1`, the partition is too small. We move the cut right by setting `lo = mid1 + 1`.
     * 8. The left partition is designed to hold one extra element for odd total lengths.
     * - For even lengths: `median = (max(l1, l2) + min(r1, r2)) / 2.0`
     * - For odd lengths: `median = max(l1, l2)`
     * NOTE: Reason for dividing like this - this way, left half is larger.
     * This works for both even and odd length cases.
     * `countLeft = (m + n + 1)/2;` 
     * for m = 2, n = 4 -> countLeft = 7/2 = 3, both halves have equal elements.
     * for m = 2, n = 3 -> countLeft = 6/2 = 3, so left half here has more elements.
     * eg. nums1 = [1,3,4,7,10,12], nums2 = [2,3,6,15] 
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if(nums1.length > nums2.length){
            // swap the references
            int[] temp = nums1;
            nums1 = nums2;
            nums2 = temp;
        }
        // nums1 is always the smaller array in our case
        int m = nums1.length;
        int n = nums2.length;

        int countLeft = (m + n + 1)/2;
        int lo = 0, hi = m;
        double ans = 0;
        
        while(lo <= hi){
            int mid1 = (lo + hi) >> 1; // another way for divide by 2
            int mid2 = countLeft - mid1;
            int l1 = Integer.MIN_VALUE, l2 = Integer.MIN_VALUE;
            int r1 = Integer.MAX_VALUE, r2 = Integer.MAX_VALUE;
            
            if(mid1 < m){
                r1 = nums1[mid1];
            }
            if(mid2 < n){
                r2 = nums2[mid2];
            }
            if(mid1 - 1 >= 0){
                l1 = nums1[mid1 - 1];
            }
            if(mid2 - 1 >= 0){
                l2 = nums2[mid2 - 1];
            }
            
            if(l1 <= r2 && l2 <= r1){
                if((m + n) % 2 == 1){
                    // odd
                    ans = Math.max(l1, l2) * 1.0;
                } else {
                    // even
                    ans = (Math.max(l1, l2) + Math.min(r1, r2))/ 2.0;
                }
                break;
            } else if(l1 > r2){
                hi = mid1 - 1;
            } else {
                // l2 > r1
                lo = mid1 + 1;
            }
        }
        return ans;
    }
}
