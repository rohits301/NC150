class Solution {
    // refer STRIVER
    // BRUTE FORCE - TLE
    // T: O(2^n * n); O(2^n) possibilities * O(n) for copying everytime.
    // S: O(2^n * n); (including the space for output) and O(n) auxiliary space for recursion
    /*
     * Building upon the Combination Sum - 1
     * this time, we cannot pick same element twice, so `i+1` in both calls
     * Observe the answer is in sorted order, hence we sort the array beforehand.
     * Also, since, we want to avoid duplicate combinations, we take a HashSet. 
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
    // T: O(2^n * n); O(2^n) subsequences * O(n) for copying everytime.
    // S: O(2^n * n); (including the space for output) and O(n) auxiliary space for recursion
    /*
     * Similar to Subset-II, here we need to avoid duplicates at each level.
     * We sort the array in the beginning, so that all duplicates are adjacent to each other.
     * At every level, we consider only one the first unique element and ignore its duplicates.
     * Base case: when `target == 0`.
     * Since, the recursion is inside a loop, we do not need `i == candidates.length` check.
     * Eg. candidates = [1,1,1,2,2]
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {

        Arrays.sort(candidates);
        List<List<Integer>> ans = new ArrayList<>();
        dfs(0, candidates, target, new ArrayList<>(), ans);
        return ans;
    }

    private void dfs(int i, int[] candidates, int target, List<Integer> ds, List<List<Integer>> ans){
        if(target == 0){
            ans.add(new ArrayList<>(ds));   
            return;
        }

        

        for(int j=i; j<candidates.length; j++){
            if(j > i && candidates[j] == candidates[j-1]){
                continue;
            }

            if(candidates[j] > target){
                break;
            }

            ds.add(candidates[j]);
            dfs(j + 1, candidates, target - candidates[j], ds, ans);
            ds.remove(ds.size() - 1);
        }
    }
}
