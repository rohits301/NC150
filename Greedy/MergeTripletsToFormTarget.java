class Solution {
    // refer NEETCODE
    // his solution won't work because we have case where
    // target = [5,5,5], so `good` will have only one element.
    // The below works because it checks that all the values of target exist in the triplets[] at the required indices.
    // T: O(N), where N is the number of triplets. We iterate through the triplets once.
    // S: O(1), constant extra space.
    public boolean mergeTriplets(int[][] triplets, int[] target) {
        // Flags to track if we found a valid triplet that can contribute
        // to matching each component of the target.
        boolean foundTarget0 = false;
        boolean foundTarget1 = false;
        boolean foundTarget2 = false;

        //triplet[0] is equivalent to triplets[i][0]
        for (int[] triplet : triplets) {
            // Check if this triplet is "valid" - i.e., none of its components
            // exceed the corresponding target component.
            if (triplet[0] > target[0] ||
                triplet[1] > target[1] ||
                triplet[2] > target[2]) {
                continue; // This triplet cannot be used, skip it.
            }

            // If the triplet is valid, check if its components match target components.
            // If a component matches, we mark that target component as "achievable".
            if (triplet[0] == target[0]) {
                foundTarget0 = true;
            }
            if (triplet[1] == target[1]) {
                foundTarget1 = true;
            }
            if (triplet[2] == target[2]) {
                foundTarget2 = true;
            }

            // Optimization: if all target components are found to be achievable,
            // we can stop early.
            if (foundTarget0 && foundTarget1 && foundTarget2) {
                return true;
            }
        }

        // Return true only if all three target components were found to be achievable
        // from the set of valid triplets.
        return foundTarget0 && foundTarget1 && foundTarget2;
    }
}
