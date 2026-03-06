class Solution {
    // refer STRIVER
    // BRUTE FORCE
    // RECURSION
    // T: O(2^n); going through all possible subsequences
    // S: O(n); stack space
    /*
     * Approach:
     * 1. We use a recursive function `dfs` that takes the current index `idx` and the previous index `prevIdx` of the last included element in the subsequence.
     * 2. At each step, we have two choices: either include the current element in the subsequence (if it is greater than the last included element) or skip it.
     * 3. Take or Not Take:
     * - Not Take: We simply move to the next index without changing `prevIdx`.
     * - Take: We include the current element in the subsequence (if valid) and move to the next index, updating `prevIdx` to the current index.
     * 4. We return the maximum length obtained from both choices.
     * 5. The base case is when we reach the end of the array, at which point we return 0.
     * 6. The main function initializes the recursion with the first index and a previous index of -1 (indicating no previous element).
     */
    public int lengthOfLIS(int[] nums) {
        return dfs(nums, 0, -1);
    }
    
    private int dfs(int[] nums, int idx, int prevIdx){
        if(idx == nums.length){
            return 0;
        }

        // not take
        int len = 0 + dfs(nums, idx+1, prevIdx);
        // take
        if(prevIdx == -1 || nums[idx] > nums[prevIdx]){
            len = Math.max(len, 1 + dfs(nums, idx + 1, idx));
        }
        return len;
    }

}

class Solution {
    // refer STRIVER
    // BETTER
    // TOP-DOWN (MEMOIZATION)
    // T: O(n^2);
    // S: O(n^2) + O(n); dp size + stack space
    /*
     * Approach:
     * 1. Memoize the recursive function.
     * 2. Create a 2D dp array where dp[i][j] represents the length of the longest increasing subsequence starting from index i with the previous index j.
     * 3. Initialize the dp array with -1 to indicate uncomputed states.
     * 4. Since, we cannot have negative indices in the array, we shift the previous index by 1 (i.e., prevIdx -> prevIdx + 1).
     * 5. The rest of the logic remains the same as in the recursive approach, but we store and 
     * reuse results from the dp array to avoid redundant calculations.
     */
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n + 1][n + 1];
        for (int[] ar : dp) {
            Arrays.fill(ar, -1);
        }

        return dfs(nums, 0, -1, dp);
    }

    private int dfs(int[] nums, int idx, int prevIdx, int[][] dp) {
        if (idx == nums.length) {
            return 0; // no element here, so no subsequence to consider
        }

        if (dp[idx][prevIdx + 1] != -1) {
            return dp[idx][prevIdx + 1];
        }
        // not take
        int len = 0 + dfs(nums, idx + 1, prevIdx, dp);
        // take
        if (prevIdx == -1 || nums[idx] > nums[prevIdx]) {
            len = Math.max(len, 1 + dfs(nums, idx + 1, idx, dp));
        }
        return dp[idx][prevIdx + 1] = len;
    }

}

class Solution {
    // refer STRIVER
    // BETTER
    // BOTTOM-UP TABULATION
    // T: O(n^2);
    // S: O(n^2); dp size
    /*
     * Approach:
     * 1. Same as memoization, we need two variables to define a state => 2D dp[][]
     * 2. Since we cannot have negative indices in the array, we shift the previous index by 1 (i.e., prevIdx -> prevIdx + 1).
     * 3. dp[i][prevIdx+1] represents the length of the longest increasing subsequence starting from index i with the previous index prevIdx.
     * 4. We fill the dp table in a bottom-up manner, starting from the end of the array and moving to the beginning.
     * 5. The outer loop iterates over the indices of the array in reverse order
     *   (from n-1 to 0), and the inner loop iterates over the possible previous indices (from i-1 to -1).
     * 6. The answer will be found in dp[0][0] which corresponds to starting at index 0 with no previous element.
     */
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n + 1][n + 1];

        // by defn. prevIdx = idx - 1
        // so, loops are ->
        // i -> n-1 to 0
        // prevIdx -> i - 1 -> -1
        for (int i = n - 1; i >= 0; i--) {
            for (int prevIdx = i - 1; prevIdx >= -1; prevIdx--) {
                // not take
                int len = 0 + dp[i + 1][prevIdx + 1]; // becoz. prevIdx is stored in "+1" state
                // take
                if (prevIdx == -1 || nums[i] > nums[prevIdx]) {
                    len = Math.max(len, 1 + dp[i + 1][i + 1]);
                }
                dp[i][prevIdx + 1] = len;
            }
        }
        return dp[0][-1 + 1]; // dp[0][prevIdx+1]
    }

}

class Solution {
    // refer STRIVER
    // BETTER
    // BOTTOM-UP TABULATION - SPACE OPTIMISED
    // T: O(n^2);
    // S: O(n); dp size
    /*
     * Approach:
     * 1. We can optimize the space used in the bottom-up tabulation approach.
     * 2. Instead of maintaining a full 2D dp array, we can use two 1D arrays: `curr` and `next`.
     * 3. `next` represents the results for the next index (i+1), and `curr` represents the results for the current index (i).
     * 4. After processing each index, we update `next` to be `curr` for the next iteration.
     * 5. This reduces the space complexity from O(n^2) to O(n).
    */
    public int lengthOfLIS(int[] nums) {
        // APPROACH -
        // we need two variables to define a state => 2D dp[][]
        // since we cannot have -ve indices in array
        // shift the index to right
        // => prevIdx -> prevIdx + 1

        // dp[i][prevIdx+1] -> LIS till i, prevIdx

        int n = nums.length;
        int[] curr = new int[n + 1];
        int[] next = new int[n + 1];

        // by defn. prevIdx = idx - 1
        // so, loops are ->
        // i -> n-1 to 0
        // prevIdx -> i - 1 -> -1
        for (int i = n - 1; i >= 0; i--) {
            for (int prevIdx = i - 1; prevIdx >= -1; prevIdx--) {
                // not take
                int len = 0 + next[prevIdx + 1]; // becoz. prevIdx is stored in "+1" state
                // take
                if (prevIdx == -1 || nums[i] > nums[prevIdx]) {
                    len = Math.max(len, 1 + next[i + 1]);
                }
                curr[prevIdx + 1] = len;
            }
            int[] temp = curr;
            curr = next;
            next = temp;
        }
        return next[-1 + 1]; // dp[0][prevIdx+1]
    }

}

class Solution {
    // refer STRIVER
    // T: O(n log n) - due to binary search in lowerBound
    // S: O(n) - for the temp list storing LIS sequence
    // the LIS sequence stored is just a sorted sequence, it is not the correct LIS sequence
    /*
     * Approach:
     * 1. We maintain a list `temp` that will store the smallest possible end elements of increasing subsequences of different lengths.
     * 2. We iterate through each number in the input array `nums`.
     * 3. If the current number is greater than the last element in `temp`, it means we can extend the longest increasing subsequence found so far, so we append it to `temp`.
     * 4. If the current number is not greater, we find the position in `temp` where this number can replace an existing element to maintain the smallest possible end element for subsequences of that length. This is done using a binary search (lower bound).
     * 5. The length of the `temp` list at the end of the iteration gives the length of the longest increasing subsequence.
     * 6. Note that the actual elements in `temp` do not represent the longest increasing subsequence from the original array, but the length of `temp` is correct.
     * 7. The `lowerBound` function is a helper function that performs a binary search to find the first index in `temp` where the element is greater than or equal to the given number.
     * 
     * Intuition:
     * The idea is to keep track of the smallest tail for all increasing subsequences with different lengths. By doing this, we ensure that we have the best chance of extending these subsequences as we encounter new elements in the array.
     */
    public int lengthOfLIS(int[] nums) {
        // List to store the smallest possible end elements of increasing subsequences
        List<Integer> temp = new ArrayList<>();
        temp.add(nums[0]);  // Initialize with the first element
        int count = 1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > temp.get(temp.size() - 1)) {
                // If nums[i] extends the LIS, append it to temp
                temp.add(nums[i]);
                count++;
            } else {
                // If nums[i] can replace an element in temp
                // Find the first index where temp[idx] >= nums[i] (lower bound)
                int lb = lowerBound(temp, nums[i]);
                temp.set(lb, nums[i]);  // Replace it to maintain the smallest possible LIS
            }
        }
        return count; // Length of temp is the length of LIS
    }

    /**
     * Finds the index of the first element in arr that is >= x
     * This is essentially the "lower bound" function using binary search.
     * If no such element exists, it returns the index where x can be placed.
     */
    private int lowerBound(List<Integer> arr, int x) {
        int lo = 0, hi = arr.size() - 1;
        int ans = arr.size(); // Default to size if x is larger than all elements

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;

            if (arr.get(mid) >= x) {
                ans = mid;  // Possible position for x
                hi = mid - 1; // Search in the left half
            } else {
                lo = mid + 1; // Search in the right half
            }
        }
        return ans;
    }
}
