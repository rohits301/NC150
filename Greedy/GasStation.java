class Solution {
    // refer NEETCODE for code and expln.
    // T: O(n^2); TLE
    // S: O(1);
    /**
     * 1. Problem is we have gas[] and cost[]. gas[] = the gas I get in my tank at `i` (gas earned).
     * cost[] = the gas spent in travelling from `i` to `i+1`.
     * To find if there is any `i` from which I start and I can reach the same starting point, `i`.
     * 2. So, if the difference, `gas[i]-cost[i] < 0` -> I cannot reach the next station. 
     * 3. Brute Force - Try all indices. Start from all indices and try if it is possible to reach. Since answer is unique, so only one index will satisfy this.
     * 4. So, try all indices -> Nested loops
     * If for any `j` we can reach `i` again, return that `i`.
     * Else, try for others.
     * If not possile, return -1.
     * 5. To go around the array again after `n-1` means, we need to `n-1+1=0`.
     * 6. Logic: `(j+1)%n`, this does the trick.
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;

        for (int i = 0; i < n; i++) {
            int tank = gas[i] - cost[i];

            if (tank < 0) {
                continue;
            }

            int j = (i + 1) % n;
            while (j != i) {
                tank += gas[j] - cost[j];
                if (tank < 0) {
                    tank = 0;
                    break;
                }
                j = (j + 1) % n;
            }

            if (j == i) {
                return i;
            }
        }
        return -1;
    }
}

class Solution {
    // refer NEETCODE for code and expln.
    // CodeStorywithmik also has a simple explanation
    // T: O(n); GREEDY
    // S: O(1);
    /**
     * 1. We can start only from the index where the tank has gas remaining. So, totalGasRemaining at `i` should be >= 0.
     * 2. Also, the solution to the problem does not exist if Sum(gas) - Sum(cost) < 0, because in this case we don't have enough gas to cover the entire array back to starting point.
     * 3. Hence, greedily start only from index `i`, then travel. Since, the answer is unique, so only index would result in completing the entire trip.
     * 4. Start from all indicies, but if there is an index with which where the totalGasRemaining till there is >= 0, then we can reach the next index.
     * Since, only one such index, so we don't need to check by going around the array again to validate the index found.
     * 5. If it is not possible to start from an index, reset the totalGasRemaining and the start index.
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;

        int totalGas = 0, totalCost = 0;
        for (int i = 0; i < n; i++) {
            totalGas += gas[i];
        }
        for (int i = 0; i < n; i++) {
            totalCost += cost[i];
        }
        if (totalGas - totalCost < 0) {
            return -1;
        }

        int totalGasRemaining = 0;
        int start = 0;
        for (int i = 0; i < n; i++) {
            totalGasRemaining += gas[i] - cost[i];

            if (totalGasRemaining < 0) {
                totalGasRemaining = 0;
                start = i + 1;
            }
        }

        return start;
    }
}
