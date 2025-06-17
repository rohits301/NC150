class Solution {
    // refer STRIVER's Video - all approaches
    // BRUTE FORCE - TLE
    // T: O(n^3)
    // S: O(1) 
    /**
     * Linear search every consecutive element for every nums[i]
     */
    public int longestConsecutive(int[] nums) {
        int longest = 0;
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            int count = 1;
            int x = nums[i];
            while (linearSearch(nums, x + 1) == true) {
                x = x + 1;
                count = count + 1;
            }
            if (count > longest) {
                longest = count;
            }
        }
        return longest;
    }
    
    private boolean linearSearch(int[] A, int key){
        for(int ele: A){
            if(ele == key){
                return true;
            }
        }
        return false;
    }
}

class Solution {        
    // BETTER
    // T: O(nlogn)
    // S: O(1)
    /**
     * 1. Sort the array
     * 2. Iterate through the array and check for consecutive elements
     * 3. If the current element is one more than the previous element, increment the count -> `nums[i] - 1 == lastSmaller`, it is a continuation of the sequence
     * 4. If the current element is not equal to the previous element, reset the count to 1 and update lastSmaller -> `nums[i] != lastSmaller`
     * 5. If the current element is equal to the previous element, skip it (to avoid duplicates) -> `nums[i] == nums[i-1]`
     * 6. Update the longest count if the current count is greater than the longest count
     */
    public int longestConsecutive(int[] nums) {
        Arrays.sort(nums);

        int lastSmaller = Integer.MIN_VALUE;
        int longest = 0, n = nums.length, count = 0;

        for (int i = 0; i < n; i++) {
            // always update last smaller in case of sequence or no sequence
            // and skip duplicates
            if (nums[i] - 1 == lastSmaller) {
                count += 1;
                lastSmaller = nums[i];
            } else if (nums[i] != lastSmaller) {
                count = 1;
                lastSmaller = nums[i];
            } else if (nums[i] == nums[i-1]){ //skip when duplicate
                continue;
            }
            longest = Math.max(longest, count);
        }
        return longest;
    }
}

class Solution {
    // OPTIMAL
    // Optimal 
    // T: O(n)
    // S: O(n)
    // the iteration in inner while is maximum `n` so O(n+n+n) = O(n) time
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int longest = 0;

        // Store all the elements in a set to avoid duplicates as they do not affect our calculation.
        for(int num : nums){
            set.add(num);
        }
        
        // iterate thru all elements in set
        for(int num : set){
            int x = 1, count = 1;
            // check if num is a valid starting point, i.e., there is no 'num-1' in the set
            if(!set.contains(num - 1)){
                while(set.contains(num + x)){
                    x += 1;
                    count += 1;
                }
                longest = Math.max(count, longest);
            }
        }
        return longest;
    }
}
