class Solution {
    // refer STRIVER video solution for Brute, Better and Optimal
    // BRUTE FORCE
    // T: O(n^3)
    // S: O(2 * no. of unique triplets) - space of set + answer list
    /**
     * Approach: Three nested loops
     * 1.  To handle duplicate triplets (e.g., [-1, 0, 1] and [0, 1, -1]), we sort each valid triplet before adding it to a HashSet.
     * 2. This ensures, lists which have same sorted order are counted only once.
     * 3. In the end, create List from Set.
     * 4. We require the extra space of a HashSet to ensure we only store unique triplets.
     * Note: Sorting will change order, but that won't affect the answer.
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        Set<List<Integer>> set = new HashSet();

        int n = nums.length;
        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                for (int k = j + 1; k < n; k++) {

                    if (nums[i] + nums[j] + nums[k] == 0) {
                        List<Integer> temp = new ArrayList<>();
                        Collections.addAll(temp, nums[i], nums[j], nums[k]);
                        Collections.sort(temp);
                        set.add(temp);
                    }
                }
            }
        }

        for (List<Integer> list : set) {
            ans.add(list);
        }
        return ans;
    }
}

class Solution {
    // BETTER
    // T: O(n^2) 
    // S: O(n + no. of unique triplets); `n` is for hashset, no. of unique triplets -> first is for set, second is for the answer list
    // using set, an external Data structure to store the answer
    /**
     * Approach:
     * 1. Instead of three loops, this can be done in two.
     * 2. This is possible if we use hashset to search the third element in array. So searching reduces from O(n) to O(1).
     * 3. So, `third = -(nums[i] + nums[j])`
     * 4. If found in set, then we have a triplet.
     * 5. In every iteration, we add `nums[j]` to set because we have visited it and it can form a triplet later.
     * 6. The Set is created again for every `i`.
     */
    public List<List<Integer>> threeSum(int[] nums) {
        int n = nums.length;
        Set<List<Integer>> set = new HashSet<>();

        for (int i = 0; i < n - 1; i++) {
            Set<Integer> hashset = new HashSet<>();
            for (int j = i + 1; j < n; j++) {

                int third = -(nums[i] + nums[j]);
                if (hashset.contains(third)) {
                    List<Integer> temp = new ArrayList<>();
                    Collections.addAll(temp, nums[i], nums[j], third);
                    Collections.sort(temp);
                    set.add(temp);
                }
                hashset.add(nums[j]); // why? because we have visited it and it can form a triplet later
            }
        }
        return new ArrayList<>(set); // the answer list
    }
}

class Solution {
    // OPTIMAL
    // T: O(n^2); n^2 - for loops + nlogn - for sorting
    // S: O(no. of triplets) 
    /**
     * Approach:
     * 1. To get rid of sorting the triplets, we can sort the entire array in the beginning itself.
     * 2. Then, we can use two-pointer such that, `i < j < k`.
     * 3. Fix `i`, move `j` and `k`.
     * 4. Since array is sorted, we can find the triplet using two-pointer on `j` and `k`.
     * 5. We skip duplicates, whenever we find a triplet for given `j` and `k`.
    */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        int n = nums.length;

        // 1. Sort the array 
        Arrays.sort(nums);
        for (int i = 0; i < n; i++) {
            // to avoid duplicate i
            if (i > 0 && nums[i] == nums[i - 1])
                continue;
            }

            // 2. two-pointer on i and j 
            // for a given i, find j and k such that all sum to zero
            int j = i + 1, k = n - 1;
            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];
                if(sum < 0){
                    j++;
                } else if(sum > 0){
                    k--;
                } else {
                    // sum == 0, found the triplet
                    List<Integer> list = new ArrayList<>();
                    Collections.addAll(list, nums[i], nums[j], nums[k]);
                    ans.add(list);
                    j++;
                    k--;

                    // to avoid duplicate j and duplicate k
                    while(j < k && nums[j] == nums[j-1]) {
                        j++;
                    }
                    // for k, the previous element is k+1
                    while(j < k && nums[k] == nums[k+1]) {
                        k--;
                    }
                }
            }
        }
        return ans;
    }
}
