// refer STRIVER's Video - all approaches
class Solution {
    /*
     * Approach 1: Brute Force (Linear Search) - TLE
     * ----------------------------------------
     * 1. For every number in the array, use linear search to check if the next consecutive element exists.
     * 2. Start with nums[i], repeatedly search for x+1 in the array until it's not found, counting the length of the sequence.
     * 3. Update the maximum length found.
     * 
     * Intuition:
     * 1. This approach checks every possible starting point for a sequence and tries to build the longest sequence by checking for each next consecutive number.
     * 2. However, searching the array for each next number makes this solution inefficient (O(n^3) time complexity), especially for large arrays.
     * 
     * Time Complexity: O(n^3)
     * Space Complexity: O(1)
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
    /*
     * Approach 2: Sorting
     * -------------------
     * 1. Sort the array first. Then iterate through the sorted array, counting consecutive sequences.
     * 2. If the current number is one more than the previous, increment the sequence count.
     * 3. If it’s not consecutive (and not a duplicate), reset the count to 1.
     * 4. Skip duplicate numbers to avoid counting them twice.
     * 5. Update the longest length as you go.
     * 
     * Intuition:
     * 1. Sorting brings consecutive numbers next to each other, making it easier to count sequences.
     * 2. Duplicates are ignored as they don’t extend a sequence. This reduces unnecessary checks.
     * 
     * Time Complexity: O(n log n) (for sorting)
     * Space Complexity: O(1)
     */
    public int longestConsecutive(int[] nums) {
        Arrays.sort(nums);

        int lastSmaller = Integer.MIN_VALUE;
        int longest = 0, n = nums.length, count = 0;

        for (int i = 0; i < n; i++) {
            // always update last smaller in case of sequence or no sequence
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
    /*
     * Approach 3: HashSet (Optimal)
     * -----------------------------
     * 1. Store all numbers in a HashSet to allow O(1) lookups and automatically remove duplicates.
     * 2. For each number in the set, check if it’s the start of a sequence (i.e., num-1 is not in the set).
     * 3. If so, count the length of the consecutive sequence starting from that number by checking num+1, num+2, ...
     * 4. Update the longest length found.
     * 
     * Intuition:
     * 1. By only starting sequences from numbers that are not preceded by num-1, we avoid redundant work.
     * 2. HashSet makes checking existence fast and removes duplicate numbers.
     * 3. This guarantees every sequence is counted only once, making it efficient (O(n) time).
     * 
     * Note: Iterate through the hashset instead of array to avoid duplicates.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int longest = 0;

        for(int num : nums){
            set.add(num);
        }
        
        for(int num : set){
            int x = 1, count = 1;
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
