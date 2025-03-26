class Solution {
    // refer STRIVER
    // BRUTE FORCE - T: O(nlogn), S: O(1)
    // sort the array and check if nums[i] == nums[i-1]
    // BETTER - T: O(n), S: O(n)
    // use HashSet or frequency array[n+1] to find duplicate
    // OPTIMAL - T: O(n), S: O(1)
    /*
     * Tortoise-Hare algorithm 
     * Detect cycle in Linked List and then find the starting point 
     * of the cycle.
     * Proof in Striver video
     * This can be done without actually creating a linked list.
     * We use the fact that numbers are in range [1,n]
     * so, Imaging a list with nodes as nums[i]
     * linked like -> nums[i].next = nums[nums[i]];
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
        // can rest any one pointer btw.
        fast = nums[0];
        while(slow != fast){
            slow = nums[slow];
            fast = nums[fast];
        }

        return slow;
    }
}
