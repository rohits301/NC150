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
    // T: O(min (log m), (log n))
    // S: O(1)
    /*
     * 1) We will try the partitioning with smaller length array. 
     * 2) Only one possible solution - by observation
     * 3) Binary search can be done on smaller length, so on `min(m, n)`
     * 4) condition for valid split -> `l1 <= r2 && l2 <= r1` (cross-checks)
     * 5) Initially, lo = 0, hi = m; (assuming m is always smaller length). 
     * Imagine that nums1 is always the smaller length array and we do Binary Search on it.
     * 6) `l1 > r2 => hi = mid1 - 1;` Binary search is on nums1 and here as l1 is large, so right half is to eliminated. 
     * 7) `r1 < l2 => lo = mid1 - 1;` since left half is too small, so increase lo.
     * 8) for Even -> `median = (max(l1, l2) + min(r1, r2)) / 2.0;`
     * 9) for Odd -> we split the array such that, left half always has more number of elements. so, `median = max(l1,l2)`.
     * 10) Reason for dividing like, left half is larger ->
     * because it works for both even and odd length cases.
     * 11) `countLeft = (m + n + 1)/2;` 
     * 12) for m = 2, n = 4 -> countLeft = 7/2 = 3, both halves have equal elements.
     * 13) for m = 2, n = 3 -> countLeft = 6/2 = 3, so left half here has more elements.
     * 14) eg. nums1 = [1,2,3,4,9,11], nums2 = [7,12,14,15]
     * 15) eg. nums1 = [1,3,4,7,10,12], nums2 = [2,3,6,15]
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if(nums1.length > nums2.length){
            // swap the references
            int[] temp = nums1;
            nums1 = nums2;
            nums2 = temp;
        }
        
        int m = nums1.length;
        int n = nums2.length;

        int countLeft = (m + n + 1)/2;
        int lo = 0, hi = m;
        double ans = 0;
        
        while(lo <= hi){
            int mid1 = (lo + hi) >> 1; // another way of binary division
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
                    ans = (Math.max(l1, l2) + Math.min(r1, r2))/ 2.0;
                }
                break;
            } else if(l1 > r2){
                hi = mid1 - 1;
            } else {
                // r1 < l2
                lo = mid1 + 1;
            }
        }
        return ans;
    }
}
