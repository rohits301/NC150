class Solution {
    // refer NEETCODE
    // BRUTE FORCE - RECURSION
    // T: O(2^n * n); 2^n is outer loop and n is inner loop
    // S: O(2^n * n); 2^n - subsets and each subset in worst case is of length = n + O(n) - stack space
    /*
     * For every index, I have two choices, either pick or not pick.
     * For "pick", add it to my data structure and go to next index. 
     * Remember to remove it from data structure while backtracking to generate other answers.
     * For "don't pick", I move to next index, data structure doesn't change.
     * Base case: `i == nums.length`, at this moment, all options are explored.
     */
    public List<List<Integer>> subsets(int[] nums) {
        int n = nums.length;
        List<List<Integer>> ans = new ArrayList<>();

        dfs(0, nums, new ArrayList<>(), ans);
        return ans;
    }

    private void dfs(int i, int[] nums, List<Integer> ds, List<List<Integer>> ans){
        if(i == nums.length){
            ans.add(new ArrayList<>(ds)); // copy the list to a new list
            return;
        }

        // pick
        ds.add(nums[i]);
        dfs(i + 1, nums, ds, ans);
        ds.remove(ds.size() - 1);
        
        // not pick
        dfs(i + 1, nums, ds, ans);
    }
}

class Solution {
    // refer STRIVER
    // OPTIMAL - without the stack space
    // T: O(2^n * n); 2^n is outer loop and n is inner loop
    // S: O(2^n * n); 2^n - subsets and each subset in worst case is of length = n
    // BIT - MANIPULATION SOLUTION
    /*
     * The subsets are modeled to the binary numbers possible for a given `n`, so 2^n such subsets.
     * Hence, to generate subsets, we use the idea that if a bit is on, select that `nums[idx]` from the array. 
     * If bit is off, don't select the number.
     * To check if `idx` bit is on in the array, calculate the bit corresponding to `idx`. 
     * `bit = (1 << idx)` and then, check `if (i & bit) > 0`.
     * A `2^n` can be written as `(1 << n)`.
     */
    public List<List<Integer>> subsets(int[] nums) {
        int n = nums.length;
        List<List<Integer>> ans = new ArrayList<>();
        
        for (int i = 0; i < (1 << n); i++) {
            List<Integer> ds = new ArrayList<>();
            // Check each bit position to decide whether to include nums[idx]
            for (int idx = 0; idx < n; idx++) {
                // Create a bit mask with the bit at position idx set to 1.
                int bit = (1 << idx);

                // If the bit is set in i, add the corresponding element from nums.
                if ((i & bit) > 0) {
                    ds.add(nums[idx]);
                }
            }
            ans.add(ds);
        }
        return ans;
    }
}
