class Solution {
    // refer NEETCODE
    // OPTIMAL
    // T: O(n) 
    // S: O(1)
    /*
     * Approach:
     * 1. Use two pointers, one at the beginning (i) and one at the end (j) of the array.
     * 2. Calculate the sum of the elements at these two pointers.
     * 3. If the sum is equal to the target, return the indices (1-based).
     * 4. If the sum is less than the target, move the left pointer (i) to the right.
     * 5. If the sum is greater than the target, move the right pointer (j) to the left.
     */
    public int[] twoSum(int[] numbers, int target) {
        int n = numbers.length;
        int[] ans = new int[2];

        int i = 0, j = n - 1;
        while (i < j) {
            int sum = numbers[i] + numbers[j];

            if (sum < target) {
                i++;
            } else if (sum > target) {
                j--;
            } else {
                // sum == target
                ans[0] = i + 1;
                ans[1] = j + 1;
                break; // since only one solution, we can break
            }
        }

        return ans;
    }
}
