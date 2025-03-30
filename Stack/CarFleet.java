class Solution {
    // refer NEETCODE - good explanation, stack not reqd.
    // BRUTE/BETTER/OPTIMAL
    // T: O(nlogn); for sorting the array
    // S: O(n); for storing `n` pairs
    /*
     * 1. Imagine the problem in 2D - position and time.
     * 2. equation -> `y = mx+c; y = target (position at t=t), m = speed, t = time, c = intial position at t=0`.
     * 3. Plot the points and lines to see they intersect. 
     * 4. Every intersection represents a fleet.
     * 5. Intuition - if a car is closer to the target, and there is a car behind. Then, for them to be in one fleet, the car behind must move faster and when they meet they will move at speed of the car closer and in a single fleet.
     * 6. So, we need a descending order of `position` and want to keep `position` and `speed` together in a pair because they are linked.
     * 7. Everytime, a car takes time <= time taken by current car, then they move in same fleet. As per the question we only need count of the fleets.
     * 8. We have to process it with the car nearest to target first as for that, we know it will meet and move together at a slower speed. But for cars far from target, we don't know which speed they will move it and hence, cannot calculate time.
     * 9. Edge case: target = 10, position = [6,8], speed = [3,2].
     */
    public int carFleet(int target, int[] position, int[] speed) {
        int n = position.length;
        int[][] pairs = new int[n][2];
        for (int i = 0; i < n; i++) {
            pairs[i][0] = position[i];
            pairs[i][1] = speed[i];
        }

        Arrays.sort(pairs, (a, b) -> Integer.compare(b[0], a[0]));

        double prevTime = 0; /* NOTE: Use DOUBLE, integer division gives wrong answer. */
        int fleets = 0;

        for (int[] p : pairs) {
            double currTime = (double)(target - p[0]) / p[1];
            if (currTime > prevTime) {
                fleets++;
                prevTime = currTime;
            }
        }
        return fleets;
    }
}
