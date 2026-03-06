class Solution {
    // refer STRIVER
    // BRUTE FORCE
    // T: O(n^3), S: O(1)
    // generate all subarrays and calculate the max sum sub-array among them
    public int maxSubArray(int[] nums) {
        int max = Integer.MIN_VALUE;
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int sum = 0;
                for (int k = i; k <= j; k++) {
                    sum += nums[k];
                }
                max = Math.max(max, sum);
            }
        }
        return max;
    }
}

class Solution {
    // BETTER
    // T: O(n^2), S: O(1)
    // subarray sum can be calculated by adding the current element
    // to the previous subarray sum
    public int maxSubArray(int[] nums) {
        int max = Integer.MIN_VALUE;
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = i; j < n; j++) {
                sum += nums[j];
                max = Math.max(max, sum);
            }
        }
        return max;
    }
}

class Solution {
    // refer NEETCODE FOR OTHER APPROACHES
    // OPTIMAL (KADANE'S ALGO.)
    // T: O(n), S: O(1)
    // to find the maximum sum, we will not add any number that makes overall sum = -ve, 
    // as by including it we can never achieve max total sum, 
    // hence, we neglect that sum and reset it.
    // print the subarray (commented code) - STRIVER
    public int maxSubArray(int[] nums) {
        int max = Integer.MIN_VALUE;
        int n = nums.length;
        // int start = 0, ansStart = -1, ansEnd = -1;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            // if (sum == 0) {
            //     start = i;
            // }
            sum += nums[i];

            if (sum > max) {
                max = sum;
                // ansStart = start;
                // ansEnd = i;
            }

            if (sum < 0) {
                sum = 0;
            }
        }
        // printSubarray(nums, ansStart, ansEnd);
        return max;
    }

    private void printSubarray(int[] nums, int start, int end) {
        for (int i = start; i <= end; i++) {
            System.out.print(nums[i]);

            // If it's not the last element, print a comma
            if (i < end) {
                System.out.print(",");
            }
        }
        System.out.println();
    }
}
