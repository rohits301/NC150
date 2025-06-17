class Solution {
    // refer NEETCODE
    // OPTIMAL
    // T: O(n) 
    // S: O(1)
    // indices are 1-based so we need to return i+1 and j+1
    public int[] twoSum(int[] numbers, int target) {
        int n = numbers.length;
        int[] ans = new int[2];
        
        int i = 0, j = n-1;
        while (i < j){
            int sum = numbers[i] + numbers[j];

            if(sum < target){
                i++;
            } else if (sum > target){
                j--;
            } else {
                // sum == target
                ans[0] = i + 1;
                ans[1] = j + 1;
                break; // since only one solution, we can break
            }
        }

        return ans;
    }
}
