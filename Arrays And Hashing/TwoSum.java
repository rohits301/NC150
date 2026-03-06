class Solution {
    // BRUTE FORCE
    // T: O(n^2), S: O(n)
    /*
     * Approach:
     * 1. Iterate through the array with two nested loops.
     * 2. For each pair of numbers, check if their sum equals the target.
     * 3. If a pair is found, return their indices.
     */
    public int[] twoSum(int[] nums, int target) {
        int[] ans = new int[2];

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    ans[0] = i;
                    ans[1] = j;
                    break;
                }
            }
        }
        return ans;
    }
}

class Solution {
    // BETTER
    // T: O(n), S: O(n) - two pass
    /*
     * Approach:
     * 1. Create a HashMap to store the numbers and their indices.
     * 2. Iterate through the array and store each number and their index in the map.
     * 3. Iterate through the array again and for each number, check if the complement exists in the map and is not the same index as the current number.
     * 4. If found, return the indices.
     * Note: Since there is only one solution, we can return the result as soon as we find it.
     */
    public int[] twoSum(int[] nums, int target) {
        int[] ans = new int[2];
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement) && map.get(complement) != i) {
                ans[0] = i;
                ans[1] = map.get(complement);
                break;
            }
        }
        return ans;
    }
}

class Solution {
    // OPTIMAL
    // T: O(n), S: O(n) - one pass
    /*
     * Approach:
     * 1. Create a HashMap to store the numbers and their indices.
     * 2. Iterate through the array and for each number, if the complement exists in the map and is not the same index as the current number.
     * 3. If found, return the indices.
     * 4. Else, store the number and the index in the map because it might be needed for future complements.
     * 5. Directly checking for the complement in the map allows us to find the solution in one pass.
     */
    public int[] twoSum(int[] nums, int target) {
        int[] ans = new int[2];
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                ans[0] = i;
                ans[1] = map.get(target - nums[i]);
                break;
            } else {
                map.put(nums[i], i);
            }
        }
        return ans;
    }
}
