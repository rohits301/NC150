class Solution {
    // BRUTE FORCE
    // refer STRIVER video and NEETCODE code
    // T: O(n^2) - For each index, we scan both left and right to find max heights.
    // S: O(1)
    /**
     * Approach:
     * 1. For each index, find the maximum height to the left and right.
     * 2. The water trapped at that index is determined by the shorter of the two maximum heights.
     * 
     * Intuition:
     * Water can only be trapped if there are taller bars on both sides.
     */
    public int trap(int[] height) {
        int n = height.length; 
        int ans = 0; 

        for (int i = 0; i < n; i++) {
            int leftMax = height[i]; // Maximum height on the left of index 'i' (including itself)
            int rightMax = height[i]; // Maximum height on the right of index 'i' (including itself)

            // Find the maximum height to the left of index 'i'
            for (int j = 0; j < i; j++) {
                leftMax = Math.max(leftMax, height[j]);
            }

            // Find the maximum height to the right of index 'i'
            for (int j = i + 1; j < n; j++) {
                rightMax = Math.max(rightMax, height[j]);
            }

            /**
             * The amount of water stored at index 'i' is determined by:
             *  - The minimum of the highest bars to the left and right.
             *  - Subtracting the height of the current bar.
             */
            ans += Math.min(leftMax, rightMax) - height[i];
        }

        return ans; 
    }
}

class Solution {
    // BETTER
    // refer STRIVER video and NEETCODE code
    // T: O(n) - Three passes: one for leftMax, one for rightMax, and one for computing trapped water.
    // S: O(n) - Two auxiliary arrays (leftMax, rightMax) of size n each.
    /*
     * Approach:
     * 1. Create two arrays leftMax (prefix array) and rightMax (suffix array).
     * 2. leftMax[i] contains the maximum height to the left of index i (including itself).
     * 3. rightMax[i] contains the maximum height to the right of index i (including itself).
     * 4. The water trapped at index i is min(leftMax[i], rightMax[i]) - height[i].
     * 
     * Intuition:
     * 1. To trap water, we need to know the height of the tallest walls on both sides of each bar.
     * 2. The water that can be trapped on top of a bar is determined by the shorter of the two walls.
     * 3. By precomputing the maximum heights to the left and right of each bar, we can easily calculate the trapped water.
     */
    public int trap(int[] height) {
        int n = height.length; 
        int ans = 0; 
        int[] leftMax = new int[n];  
        int[] rightMax = new int[n]; 

        leftMax[0] = height[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }

        rightMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }

        for (int i = 0; i < n; i++) {
            ans += Math.min(leftMax[i], rightMax[i]) - height[i];
        }

        return ans; 
    }
}

class Solution {
    // OPTIMAL
    // TWO-POINTER
    // refer STRIVER video
    // T: O(n)
    // S: O(1)
    /** 
     * Intuition and Approach:
     * 1. For storing water, I need a left boundary and a right boundary.
     * 2. If it is guaranteed that `height[left] <= height[right]` 
     * then, I have a right boundary in place .
     * 3. So, check whether it is possible to trap water on left.
     * 4. This depends whether the left wall or `leftMax` is larger than the current height.
     * 5. Hence, if `height[left] >= leftMax`, cannot trap water, so, update the `leftMax` 
     * because this is the max seen so far. Else, the `height[left]` is already <= `height[right]`
     * and it is also smaller than `leftMax`, hence, the right and left boundaries are guaranteed.
     * 6. The condition `height[left] <= height[right]` is the key. It guarantees that a boundary on the right is at least as tall as our `leftMax`. This confirms `leftMax` is the true bottleneck, allowing us to calculate the trapped water at the left pointer immediately.
     * 7. The logic is perfectly symmetrical when we process the right pointer.
     * 8. The algorithm cleverly avoids explicitly calculating min(leftMax, rightMax) at each step by moving the pointer of the shorter side.
     */
    public int trap(int[] height) {
        int n = height.length; 

        int left = 0, right = n - 1;
        int leftMax = 0, rightMax = 0; 
        int ans = 0; 

        while (left <= right) { 

            if (height[left] <= height[right]) { 
                if (height[left] >= leftMax) { 
                    // If the current left building's height is greater than or equal to the maximum height seen so far from the left.
                    leftMax = Math.max(leftMax, height[left]); // Update the maximum height seen from the left.
                } else {
                    // If the current left building is shorter than the leftMax, water can be trapped.
                    // The amount of water trapped is the difference between the leftMax (the limiting boundary) and the current building's height.
                    ans += leftMax - height[left];
                }
                left++;
            } else { 
                if (height[right] >= rightMax) { 
                    // If the current right building's height is greater than or equal to the maximum height seen so far from the right.
                    rightMax = Math.max(rightMax, height[right]); // Update the maximum height seen from the right.
                } else {
                    // If the current right building is shorter than the rightMax, water can be trapped.
                    // The amount of water trapped is the difference between the rightMax (the limiting boundary) and the current building's height.
                    ans += rightMax - height[right];
                }
                right--; 
            }
        }

        return ans; 
    }
}
