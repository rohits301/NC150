class Solution {
    // follow-up -> MATH OPERATION, SIMPLEST
    // refer NEETCODE
    // T: O(n)
    // S: O(1)
    // XOR APPROACH
    // 1. xor of two numbers that are same = 0
    // 2. order in xor doesn't matter
    // 3. identity in xor = 0
    public int missingNumber(int[] nums) {
        int res = nums.length; // since this is the only missing number in xor operations
        for(int i = 0; i < nums.length; i++){
            res ^= (i ^ nums[i]);
        }
        return res;
    }
}
