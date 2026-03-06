class Solution {
    // refer NEETCODE
    // T: O(N); where N is the length of s (two passes over s).
    // S: O(1); (or O(26) for lowercase alphabets in map).
    /**
     * Problem: Partition string `s` into the maximum number of parts such that
     * each letter appears in at most one part.
     *
     * Approach: Greedy
     * 1. Preprocessing: Determine the last occurrence index for every character in `s`.
     * This tells us how far a partition must extend if it includes that character.
     *
     * 2. Iteration and Partitioning:
     * - Iterate through the string, maintaining `current_max_reach`, which is the
     * farthest last occurrence index of any character encountered in the current
     * developing partition.
     * - Also, track `current_partition_size`.
     * - For each character `s[i]`:
     * - Update `current_max_reach = max(current_max_reach, last_occurrence[s[i]])`.
     * - Increment `current_partition_size`.
     * - If the current index `i` equals `current_max_reach`:
     * - It means all characters within the current segment [start_of_partition, i]
     * do not appear beyond `i`.
     * - This is the earliest point we can close the current partition.
     * - Add `current_partition_size` to results and reset it for the next partition.
     *
     * Why this works:
     * The greedy strategy of closing a partition as soon as all its characters' global last
     * occurrences are contained within it ensures each partition is as small as possible,
     * thus maximizing the total number of partitions.
     */
    public List<Integer> partitionLabels(String s) {
        List<Integer> res = new ArrayList<>();

        // maintains the last index of the characters
        // if key is already present the map updates the value
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), i);
        }

        int size = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            size++;
            end = Math.max(end, map.get(s.charAt(i)));

            if (i == end) {
                // new partition
                res.add(size);
                size = 0;
            }
        }
        return res;
    }
}
