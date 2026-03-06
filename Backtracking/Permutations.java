class Solution {
    // refer STRIVER
    // BRUTE/BETTER
    // T: O(n! * n); n! permutations * looping till n every time
    // S: O(n); O(n) for ds, O(n) for visited[]
    /*
     * For permutations, we have all non-visited options available at every level.
     * i.e., if 2 was chosen, it is marked in the array and will not take part in further decisions in the decision tree.
     * Also, we need a data structure to store the result.
     * When we are done exploring, we want to remove the value from our data structure as well. So, while backtracking, removal happens.
     * The base case is when `ds.size() == n` as this indicates we have explored all options.
     */
    public List<List<Integer>> permute(int[] nums) {
        int n = nums.length;
        List<List<Integer>> ans = new ArrayList<>();
        boolean[] visited = new boolean[n];

        dfs(nums, new ArrayList<>(), n, visited, ans);
        return ans;
    }

    private void dfs(int[] nums, List<Integer> ds, int n, boolean[] visited, List<List<Integer>> ans){
        if(ds.size() == n){
            ans.add(new ArrayList<>(ds)); // cannot store directly, need to make copy as otherwise it will just store the reference
            return;
        }

        for(int i=0; i<n; i++){

            if(!visited[i]){
                visited[i] = true;
                ds.add(nums[i]);
                dfs(nums, ds, n, visited, ans);
                ds.remove(ds.size() - 1);
                visited[i] = false;
            }
        }
    }
}

class Solution {
    // refer NEETCODE FOR BIT-MANIPULATION CODE
    // BETTER - WITHOUTH VISITED ARRAY
    // T: O(n! * n); n! permutations * looping till n every time
    // S: O(n); O(n) for ds
    /*
     * Logic is same, we eliminate extra space by using bit masks.
     * We create a mask. The mask is such that, we set the ith bit as on.
     * So, if it already on, then it is visited, else not.
     * This works because - every ith bit is unique.
     * Also, because when we go to next level in dfs, it has the result from the previous level because the number has changed.
     */
    public List<List<Integer>> permute(int[] nums) {
        int n = nums.length;
        List<List<Integer>> ans = new ArrayList<>();

        dfs(nums, new ArrayList<>(), n, 0, ans);
        return ans;
    }

    private void dfs(int[] nums, List<Integer> ds, int n, int mask, List<List<Integer>> ans) {

        if (ds.size() == n) {
            ans.add(new ArrayList<>(ds)); // cannot store directly, need to make copy as otherwise it will just store the reference
            return;
        }

        for (int i = 0; i < n; i++) {

            if ((mask & (1 << i)) == 0) {
                ds.add(nums[i]);
                dfs(nums, ds, n, (mask | (1 << i)), ans);
                ds.remove(ds.size() - 1);
            }
        }
    }
}

class Solution {
    // refer STRIVER
    // OPTIMAL - NO EXTRA SPACE
    // T: O(n! * n); n! permutations * looping till n every time
    // S: O(1); no extra ds
    /*
     * To generate permutations, we require, that all elements of array should get a chance to be at every index. 
     * So, for an array - nums = [1,2,3]
     * We want all (1,2,3) to be at 0th position and rearrange the remaining numbers.
     * Similarly, all of them at 1st position and the rest of the array rearranged.
     * This can be achieved by swapping.
     * We swap - first at the same position, then with position after the current index. Eg. at idx = 0, swap with 0, then with 1 and 2 and so on.
     * Base case: when idx == n, we have reached the end of array and have a final permutation.
     * Backtracking - restore the state of the array by swapping again in post order.
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        dfs(nums, 0, ans);
        return ans;
    }

    private void dfs(int[] nums, int idx, List<List<Integer>> ans) {
        if (idx == nums.length) {
            // copy the array to list and store in ans
            List<Integer> ds = new ArrayList<>();
            for(int num: nums){
                ds.add(num);
            }
            ans.add(new ArrayList<>(ds));
            return;
        }

        for (int i = idx; i < nums.length; i++) {
            swap(i, idx, nums);
            dfs(nums, idx + 1, ans);
            swap(i, idx, nums); //swapping again restores the state
        }
    }

    private void swap(int i, int j, int[] arr){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
