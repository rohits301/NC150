class Solution {
    // refer NEETCODE
    // T: O(n), S: O(1)
    // `xor` of same numbers is 0
    // 0 is the identity of `xor`
    // i.e., `x^0 = x`
    public int singleNumber(int[] nums) {
        int res = 0;
        for(int num: nums){
            res ^= num;
        }
        return res;
    }
}
