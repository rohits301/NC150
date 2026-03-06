class Solution {
    // refer STRIVER - Subset Sum II
    // BRUTE FORCE 
    // T: O(n * 2^n); nlogn for Array sorting, set keeps the order, so O(n * 2^n) for set as it contains all subsets
    // S: O(n * 2^n); n for data structure and n for stack space
    /* 
     * Approach - 
     * 1. Building upon the Subset-I recursion
     * 2. To avoid duplicates leading to repitition in subsets, we use Set to store the subsets
     * This is then converted to List<List<Integer>> 
     * 3. But, this is not enough, after this as well, a problem we face is - 
     * the subsets are unique but they also need to be ordered
     * hence, we sort the input array beforehand.
     */
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        Set<List<Integer>> ans = new HashSet<>();

        dfs(0, nums, new ArrayList<>(), ans);
        return new ArrayList<>(ans);
    }

    private void dfs(int i, int[] nums, List<Integer> ds, Set<List<Integer>> ans){
        if(i == nums.length){
            ans.add(new ArrayList<>(ds));
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
    // refer STRIVER - Subset Sum II
    // OPTIMAL
    // T: O(n * 2^n); O(nlogn) for sorting + O(2^n) subsets * O(n) to copy each subset to ans list
    // S: O(k * 2^n); O(2^n) subsets * O(k) avg. length of each subset + O(n) - auxiallary space 
    /*
     * APPROACH - 
     * 1. Since there are duplicates, we sort the array first to get all duplicates adjacent to each other.
     * 2. A Duplicate is considering the same element again at the same level.
     * 3. To avoid this, we have to skip duplicates, so at every level, except the first element,
     * if there is any other occurence of that element on the same level, we skip it.
     * 4. Logic in code: if `j>i`, that is, this is not the first element in this recursion call
     * and it is a duplicate, that is, `nums[j] == nums[j-1]` -> continue;
     * 5. In case of subsets, the answer is generated in every dfs call.
     * i.e., every invocation is a valid subset.
     * Hence, we add `ds` to `ans` to every time when we enter the `dfs()`.
     * 6. In the recursion tree, at level 0 - the starting, we have 0-length subsets
     * at level = 1, we have 1-length subsets
     * at level = 2, we have 2-length subsets and so on.
     * 7. So, the reason for skipping at same level is that at same level, the state of the data structure is same, 
     * hence, the states/decision that branch out of this are also going to look alike. Therefore, to avoid that, we skip.
     * 8. We do not need a base case, because, we are looping in the array, so array index will stop if index is out of bounds.
     */
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();

        dfs(0, nums, new ArrayList<>(), ans);
        return ans;
    }

    private void dfs(int start, int[] nums, List<Integer> ds, List<List<Integer>> ans){
        ans.add(new ArrayList<>(ds));

        for(int i = start; i < nums.length; i++){
            // skip duplicates if not the first time in the recursion call
            if(i > start && nums[i] == nums[i - 1]){
                continue;
            }

            ds.add(nums[i]);
            dfs(i + 1, nums, ds, ans);
            ds.remove(ds.size() - 1);
        }
    }
}
