class Solution {
    /*
     * BRUTE - nested loops to find if any element is seen twice
     * BETTER - Sort the array and check if any two adjacent elements are equal
     * OPTIMAL - Use a hashset (extra space) to find if there are any duplicates.
     */
    // T: O(n); n = nums.length
    // S: O(n)
    public boolean containsDuplicate(int[] nums) {
        if(nums.length == 1){ 
            return false;
        }
        
        HashSet<Integer> set = new HashSet<>();
        for(int num : nums){
            if(!set.isEmpty() && set.contains(num)){
                return true;
            } else {
                set.add(num);
            }
        }
        return false;
    }
}
