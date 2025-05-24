class Solution {
    // refer LeetCode Editorial
    // Greedy with TreeMap
    // T: O(nlogn); n = hand.length, k = groupSize, m = number of distinct values in map
    // nlogm for TreeMap creation and 
    // firstKey() -> invoked n/k times, so O(logm)
    // containsKey, get, remove, put - invoked once for each value in map
    // hence, O(nlogm)
    // in worst case, m=n.
    // S: O(n);
    /**
     * 1. Problem: check if it is possible to form groups of consecutive sequences with the given numbers in hand[].
     * The sequence would be of length = groupSize.
     * 2. if the hand.length is not divisible by groupSize, cannot make groups. Return false.
     * 3. Approach - 
     * a) We need to have a sorted order of elements in order to form sequences because the sequence is consecutive.
     * Since there can be duplicates we need to track them.
     * b) Hence, a hashmap for maintaing count of cards.
     * And a sorted order, so we use a TreeMap to solve  both purpose.
     * 4. Prepare the sorted frequency map. key - hand[i], value = frequency
     * 5. Iterate until the map is empty, and for each card, check whether a group of size = groupSize can be formed.
     * 6. So, we check, if elements are consecutive.
     * If not, return false as not possible.
     * 7. `nextCard = currentCard + i`, since `i` starts from 0, so we check whether the currentCard, currentCard + 1, ... current+groupSize()-1 form a group.
     * 9. If the form a group, then we reduce the frequency of the `nextCard` by 1.
     * 10. If any card has frequency = 0, then it is exhausted and we remove from map.
     * NOTE: 
     * a) TreeMap implements the SortedMap interface.
     * We can create the reference variable of type SortedMap, but LC wants me to import and then use it. So, I went ahead with having the reference as TreeMap only.
     * Now, with this, we can make use of method - `firstKey()` to find the smallest key from the map.
     * b) Removal is possible because we are querying the map every time in while loop. If we use, Map.Entry<>, we get a `ConcurrentModificationException`.
     */
    public boolean isNStraightHand(int[] hand, int groupSize) {
        int n = hand.length;
        if (n % groupSize != 0) {
            return false;
        }

        TreeMap<Integer, Integer> freqMap = new TreeMap<>();
        for (int ele : hand) {
            freqMap.put(ele, freqMap.getOrDefault(ele, 0) + 1);
        }

        while (!freqMap.isEmpty()) {
            int currentCard = freqMap.firstKey();

            for (int i = 0; i < groupSize; i++) {
                int nextCard = currentCard + i;
                if (!freqMap.containsKey(nextCard)) {
                    return false;
                }

                freqMap.put(nextCard, freqMap.get(nextCard) - 1);

                if (freqMap.get(nextCard) == 0) {
                    freqMap.remove(nextCard);
                }
            }
        }
        return true;
    }
}

class Solution {
    // refer LC editorial
    // OPTIMAL (This specific HashMap approach aims for O(N) amortized time,
    // but worst-case can be higher without pre-sorting or sorted iteration.
    // TreeMap or sorting hand first guarantees O(N log N) or O(N log M_distinct)).
    // T: O(n); amortized O(n) (Assuming average case behavior for HashMap operations
    // and amortized analysis of the nested loops)
    // S: O(n); (For the HashMap storing card frequencies)
    /**
     * Problem: Check if it's possible to rearrange the cards in `hand` into groups of `groupSize` consecutive cards.
     * Approach Intuition:
     * 1. Initial Check: If the total number of cards isn't divisible by `groupSize`,
     * it's impossible to form valid groups.
     * 2. Frequency Count: Use a HashMap to store the counts of each card. This helps manage duplicates.
     * 3. Finding Sequence Starts: The core idea is to identify the "true" starting card of any potential sequence. For any card `c` encountered, if `c-1` exists and is available, then `c-1` is a better candidate to start a sequence.
     * We backtrack from `c` to find the smallest `s` such that `s-1` is not available.
     * This `s` is then a valid starting point for forming new groups.
     * This is similar apporach to Longest Consecutive Sequence.
     * 4. Group Formation: Once a valid starting card `s` is identified, form as many groups of `groupSize` as possible starting with `s`.
     * 5. Iteration Strategy: The outer loop iterates through the original `hand` array.
     * Each card from `hand` "triggers" the process of finding its true sequence start and then attempting to clear out all groups within a relevant range.
     *
     * 6. Detailed Algorithm Steps:
     * a) Populate a frequency map with all cards from `hand`.
     * b) Iterate through each `card` in the input `hand` array.
     * c) For the current `card`, initialize `startCard = card`.
     * d) Backtrack: While `startCard - 1` exists in the map with a count > 0,
     * decrement `startCard`. This finds the smallest card of a currently
     * available consecutive block that `card` is part of.
     * e) Process identified block: Iterate a temporary `currentSequenceStarter` from the
     * found `startCard` up to the original `card`. For each `currentSequenceStarter`:
     * While `currentSequenceStarter` is still available in the map (count > 0):
     * Attempt to form a group of `groupSize` starting with `currentSequenceStarter`.
     * This involves checking for `groupSize` consecutive cards and decrementing their counts.
     * f) If at any point a required card for a group is not available, return `false`.
     * 7. Card Availability: Instead of removing cards when their count is zero,
     * we rely on checking `map.getOrDefault(card, 0) > 0` to determine if a card is available to start or continue a group. Counts are just decremented.
     */
    public boolean isNStraightHand(int[] hand, int groupSize) {
        // If the total number of cards cannot be evenly divided into groups
        // of groupSize, it's impossible to form the straights.
        if (hand.length % groupSize != 0) {
            return false;
        }

        // Use a HashMap to store the frequency of each card.
        Map<Integer, Integer> map = new HashMap<>();
        for (int card : hand) {
            map.put(card, map.getOrDefault(card, 0) + 1);
        }

        // Iterate through each card in the original (potentially unsorted) hand.
        // This loop serves to ensure that every card gets a chance to initiate
        // the process of finding and forming straights from its "true" sequence start.
        for (int card : hand) {
            // If the card we picked from 'hand' has already been completely used up by forming previous groups, we can skip it.
            if (map.getOrDefault(card, 0) == 0) {
                continue;
            }

            // For the current 'card', find the actual smallest starting card ('startCard')
            // of a potential consecutive sequence this 'card' belongs to.
            // This is done by "backtracking" from 'card' as long as 'startCard - 1' exists and is available.
            int startCard = card;
            while (map.getOrDefault(startCard - 1, 0) > 0) {
                startCard--;
            }

            // Now, 'startCard' is the smallest card in a run of available cards connected to 'card'.
            // The loop `while(currentProcessHead <= card)` iterates from this 'startCard'
            // up to the original 'card' that triggered this search.
            // For each `currentProcessHead` in this range: if it's available, it *must*
            // be the start of new straights because we've established `currentProcessHead - 1` is not available.
            int currentProcessHead = startCard;
            while (currentProcessHead <= card) {
                // As long as there are available instances of 'currentProcessHead',
                // use them to start new straights of 'groupSize'.
                while (map.getOrDefault(currentProcessHead, 0) > 0) {
                    // Attempt to form one straight of 'groupSize' cards
                    // starting from 'currentProcessHead'.
                    for (int nextCardInSequence = currentProcessHead; nextCardInSequence < currentProcessHead + groupSize; nextCardInSequence++) {
                        // Check if the required card for the current straight is available.
                        if (map.getOrDefault(nextCardInSequence, 0) == 0) {
                            // If a card needed for the straight is missing (count is 0),
                            // then we cannot form valid groups.
                            return false;
                        }
                        // Decrement the count of the used card.
                        map.put(nextCardInSequence, map.get(nextCardInSequence) - 1);
                    }
                }
                // Move to the next potential starting card in the identified block.
                currentProcessHead++;
            }
        }

        // If all cards from the hand have been processed and successfully grouped
        // (i.e., all card counts in the map effectively became zero through group formation
        // without returning false earlier), then it's a valid arrangement.
        return true;
    }
}
