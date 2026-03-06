public class Solution {
    // BRUTE FORCE
    // T: O(n^2), S: O(1)
    public int findDuplicate(int[] nums) {
        for(int i = 0; i < nums.length; i++){
            for(int j = i + 1; j < nums.length; j++){
                if(nums[i] == nums[j]){
                    return nums[i];
                }
            }
        }
        return -1; // no duplicate found
    }
}

public class Solution {
    // BETTER
    // T: O(n), S: O(n)
    // use HashSet or frequency array[n+1] to find duplicate
    public int findDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for(int i = 0; i < nums.length; i++){
            if(set.contains(nums[i])){
                return nums[i];
            } else {
                set.add(nums[i]);
            }
        }
        return -1; // no duplicate found
    }
}

class Solution {
    // refer STRIVER
    // OPTIMAL - T: O(n), S: O(1)
    /*
     * Tortoise-Hare algorithm 
     * 1. Detect cycle in Linked List and then find the starting point 
     * of the cycle.
     * 2. Proof in Striver video
     * 3. This can be done without actually creating a linked list.
     * 4. We use the fact that numbers are in range [1,n]
     * so, imagine a list with nodes as nums[i]
     * linked like -> nums[i].next = nums[nums[i]];
     * 5. This works because according to the problem, the numbers are in the range
     * [1,n], where length of nums is n+1.
     * So, nums[i] will always point to a valid index in the array.
     */
    public int findDuplicate(int[] nums) {
        int slow = nums[0];
        int fast = nums[0];

        // for cycle
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while(slow != fast);

        // for starting point
        // reset fast and move both by 1
        // can reset any one pointer because both are at same point.
        // For the algorithm to work, we need any one pointer to start
        // from head.
        fast = nums[0];
        while(slow != fast){
            slow = nums[slow];
            fast = nums[fast];
        }

        return slow;
    }
}
