class Solution {
    // refer STRIVER
    // BRUTE/BETTER/OPTIMAL
    // T: O(N^(target/M)), where N is the number of candidates and M is the value of the smallest candidate; EXPONENTIAL
    // S: O(k*x + target); x = no. of combinations, k = avg. size of data structure list; SIZE OF RESULT LIST + RECURSION STACK SPACE
    /*
     * 1. We can pick a number multiple times, and we want to reach the target.
     * 2. Intuition - try all possible ways, think recursion.
     * 3. We start with index, `i = 0` of the `candidates` array.
     * 4. At every index, we have two choices, either pick it or not pick it. pick => consider the `candidates[i]`.
     * 5. Pick - reduce the target by `candidates[i]`, but stay at the index as this can be considered again.
     * 6. Not pick - target remains same, move to next index, `i+1`.
     * 7. We need a data structure to store the generated combination, so we consider an arraylist. In every pick, add and while backtracking
     * remove the last added value to restore the state of the recursion.
     * 8. Base case - the recursion stops when we reach end of the array. If `target == 0` here, then add the data structure to ans.
     * 9. `return;` is executed after the inner `if` for target, because whenver we hit the base case, we want the recursion to stop
     * regardless whether the target is achieved or not as all possible outcomes have been explored.
     *
     * NOTE: Memoization is not a suitable or practical optimization for this specific problem, even though it has overlapping subproblems.
     * The reason is we have to constantly create new list objects, copy combinations from sub-solutions into them, 
     * and merge them before storing them in the cache. And that is memory intensive process.
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        dfs(candidates, 0, target, ans, new ArrayList<>());
        return ans;
    }

    private void helper(int[] arr, int i, int target, List<List<Integer>> ans, List<Integer> list) {
        if (i == arr.length) {
            if (target == 0) {
                ans.add(new ArrayList<>(list));
            }
            return;
        }

        // pick
        if (arr[i] <= target) {
            list.add(arr[i]);
            dfs(arr, i, target - arr[i], ans, list);
            list.remove(list.size() - 1);
        }
        // not pick
        dfs(arr, i + 1, target, ans, list);
    }
}
