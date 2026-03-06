class Solution {
    // refer NEETCODE - all approaches
    // BRUTE FORCE - TLE
    // T: O(n^2)
    // S: O(1)
    /**
     * Approach:
     * 1. Use two nested loops to consider all pairs of lines.
     * 2. For each pair, calculate the area formed by the lines and the x-axis.
     * 3. Keep track of the maximum area found.
     * 
     * Intuition:
     * 1. The area is determined by the shorter line, so we want to maximize the height.
     * 2. Moving the shorter line might help find a taller line, potentially increasing the area.
     */
    public int maxArea(int[] height) {
        int n = height.length;
        int max = 0;

        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                int area = Math.min(height[i], height[j]) * (j - i);
                max = Math.max(max, area);
            }
        }
        return max;
    }
}

class Solution {
    // OPTIMAL
    // T: O(n)
    // S: O(1)
    /**
     * Intuition: 
     * Logic for shifting indices: 
     * 1. For equal heights (height[i] == height[j]), either index can be shifted. 
     * 2. For unequal heights, shift the index with the smaller height to maximize the chance of finding a larger area. 
     * 3. This is because a taller height at the new index could increase the area, even
     * with reduced width. 
     * 4. Shifting the larger height reduces the width without improving the limiting height, resulting in a smaller area.
     */
    public int maxArea(int[] height) {
        int n = height.length;
        int max = 0;
        int i = 0, j = n - 1;

        while (i < j) {
            int area = Math.min(height[i], height[j]) * (j - i);
            max = Math.max(max, area);

            // by shifting the idx with smaller height, there is greater possibility of getting maximum area container
            if (height[i] < height[j]) {
                i++;
            } else {
                j--;
            }
        }
        return max;
    }
}
