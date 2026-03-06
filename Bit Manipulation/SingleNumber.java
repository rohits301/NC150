class Solution {
    // refer NEETCODE
    // T: O(n);
    // S: O(1);
    /*
    * Intuition: The `xor` operation will cancel out all numbers that appear twice, 
    * leaving only the number that appears once.
     * Approach: Use XOR operation
     * 1) `xor` of same numbers is 0
     * 2) 0 is the identity of `xor` i.e., `x^0 = x`
     */
    public int singleNumber(int[] nums) {
        int res = 0;
        for(int num: nums){
            res ^= num;
        }
        return res;
    }
}
