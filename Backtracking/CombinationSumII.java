class Solution {
    // refer STRIVER
    // BRUTE FORCE - TLE
    // T: O(2^n * n); O(2^n) possibilities * O(n) for copying everytime.
    // S: O(2^n * n); (including the space for output) and O(n) auxiliary space for recursion
    /*
     * 1. Building upon the Combination Sum - 1
     * 2. This time, we cannot pick same element twice, so `i+1` in both calls
     * 3. Observe the answer is in sorted order, hence we sort the array beforehand.
     * 4. Also, since, we want to avoid duplicate combinations, we take a HashSet. 
     * NOTE: If we don't sort the array beforehand, then [1,2] and [2,1] will be generated and considered different objects. 
     * Because, HashSet considers order while checking for duplicates.
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {

        Arrays.sort(candidates);
        Set<List<Integer>> ans = new HashSet<>();
        dfs(0, candidates, target, new ArrayList<>(), ans);
        return new ArrayList<>(ans);
    }

    private void dfs(int i, int[] candidates, int target, List<Integer> ds, Set<List<Integer>> ans){

        if(i == candidates.length){
            if(target == 0){
                ans.add(new ArrayList<>(ds));   
            }
            return;
        }

        // pick
        if(target - candidates[i] >= 0){
            ds.add(candidates[i]);
            dfs(i + 1, candidates, target - candidates[i], ds, ans);
            ds.remove(ds.size() - 1);
        }
        // not pick
        dfs(i + 1, candidates, target, ds, ans);
    }
}

class Solution {
    // refer STRIVER
    // OPTIMAL 
    // worst case complexity is same but practical performance is faster
    // T: O(2^n * n); O(2^n) subsequences * O(n) for copying everytime.
    // S: O(2^n * n); (including the space for output) and O(n) auxiliary space for recursion
    /*
     * 1. Similar to Subset-II, here we need to avoid duplicates at each level.
     * 2. We sort the array in the beginning, so that all duplicates are adjacent to each other.
     * 3. At every level, we consider only one the first unique element and ignore its duplicates.
     * 4. Logic to ignore duplicates: `if (i > start && nums[i] == nums[i-1])` -> continue
     * in the recursion call, we pick the `candidates[i]` and call for `i + 1`. 
     * 5. This is for generating combinations, because we cannot pick the same index twice
     * hence, we invoke `dfs()` for all further indices.
     * 6. Base case: when `target == 0`.
     * 7. Since, the recursion is inside a loop, we do not need `i == candidates.length` check.
     * Eg. candidates = [1,1,1,2,2], target = 4
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();

        Arrays.sort(candidates);
        dfs(0, candidates, target, new ArrayList<>(), ans);
        return ans;
    }

    private void dfs(int start, int[] candidates, int target, List<Integer> ds, List<List<Integer>> ans){
        if(target == 0){
            ans.add(new ArrayList<>(ds));   
            return;
        }

        for(int i = start; i < candidates.length; i++){
            if(i > start && candidates[i] == candidates[i - 1]){
                continue;
            }

            if(candidates[i] > target){
                break;
            }

            ds.add(candidates[i]);
            dfs(i + 1, candidates, target - candidates[i], ds, ans);
            ds.remove(ds.size() - 1);
        }
    }
}
