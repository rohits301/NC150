class Solution {
    // BRUTE FORCE
    // T: O(m + n)
    // S: O(m + n)
    /*
     * Approach:
     * 1. Merge the two sorted arrays.
     * 2. Find the median from the merged array.
     * 3. For even, the median is average of `merged[x/2 - 1] and merged[x/2]`.
     * 4. For odd, the median is `merged[x/2]`.
     */
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
    // refer GEMINI
    // BETTER 
    // T: O(n + m)
    // S: O(1)
    /*
     * 1. Building upon the brute force solution, the core idea is to simulate the merge
     * process just enough to find the middle element(s).
     * 2. We only need to identify the element(s) at index (totalLen / 2) and, if needed, 
     * the one before it at (totalLen / 2) - 1.
     * 3. We use two pointers, `i` and `j`, to traverse nums1 and nums2, respectively.
     * 4. `m1` will hold the element at the current median position, and `m2` will
     * hold the element just before it.
     * 5. The main loop runs up to (totalLen / 2) times.
     * 6. In each iteration, we advance the pointer corresponding to the smaller element between the two arrays, just like in a standard merge algorithm. 
     * Before we update `m1` with the new smaller element, we save its old value to `m2`.
     * 7. After the loop finishes, `m1` will hold the element at the conceptual
     * index (totalLen / 2), and `m2` will hold the element at index
     * (totalLen / 2) - 1.
     * 9. Finally,
     * a) If `totalLen` is odd, the median is simply `m1`.
     * b) If `totalLen` is even, the median is the average of `m1` and `m2`.
     * NOTE: In the `if` condition, we need to ensure that we are not accessing out of bounds.
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int totalLen = m + n;

        int i = 0;
        int j = 0;

        int m1 = -1, m2 = -1;

        for (int count = 0; count <= totalLen / 2; count++) {
            m2 = m1;
            if (i < m && (j >= n || nums1[i] <= nums2[j])) {
                m1 = nums1[i];
                i++;
            } else if (j < n) {
                m1 = nums2[j];
                j++;
            }
        }

        if (totalLen % 2 == 0) {
            return (m1 + m2) / 2.0;
        } else {
            return (double) m1;
        }
    }
}

class Solution {
    // refer Lord STRIVER
    // OPTIMAL
    // T: O(min(log m, log n))
    // S: O(1)
    /*
     * 1. We perform a binary search on the smaller array to find the valid partition 
     * where every element in the left half is less than or equal to every element in the right half.
     * 2. There will exist only one solution, only one valid symmetry as array can have only one median.
     * 3. The search space for the partition in an array of size `m` is [0, m], as there are `m+1` possible cuts. 
     * 4. Binary search to try all partitions. So, we partition the array and define l1,l2,r1,r2 around the partition.
     * 5. Since the arrays are sorted in themselves, so we just need to check in cross-direction that `l1 <= r2` && `l2 <= r1` to validate symmetry.
     * 6. If cross-conditions are satisifed => correct partition, return as only one valid symmetry.
     * 7. If `l1 > r2`, the partition in nums1 is too large. We move the cut left by setting `hi = mid1 - 1`.
     * If `l2 > r1`, the partition is too small. We move the cut right by setting `lo = mid1 + 1`.
     * 8. The left partition is designed to hold one extra element for odd total lengths.
     * - For even lengths: `median = (max(l1, l2) + min(r1, r2)) / 2.0`
     * - For odd lengths: `median = max(l1, l2)`
     * NOTE: Reason for dividing like this -> this way the left half is larger.
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
