class Solution {
    // refer NEETCODE - all approaches
    // BRUTE FORCE - TLE
    // T: O(n^2), S: O(1)
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
    // T: O(n), S: O(1)
    /**
     * Logic for shifting indices:
     * For equal heights (height[i] == height[j]), either index can be shifted.
     * For unequal heights, shift the index with the smaller height to maximize the chance of finding a larger area.
     * This is because a taller height at the new index could increase the area, even with reduced width.
     * Shifting the larger height reduces the width without improving the limiting height, resulting in a smaller area.
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
