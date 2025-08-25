class Solution {
    // refer NEETCODE
    // BRUTE FORCE 
    // T: O(log n) - base 10; everytime number is transformed into sumOfSquares.
    // S: O(log n) - base 10; storing all numbers in set.
    /* 
     * 1. Similar to LinkedList cycle detection, we use hashset.
     * 2. It keeps a track of visited numbers, if a number is visited again, it indicates cycle.
     * 3. If `temp == 1`, then it is Happy Number
     */
    public boolean isHappy(int n) {
        Set<Integer> visited = new HashSet<>();
        int temp = n;

        while(!visited.contains(temp)){
            visited.add(temp);
            temp = sumOfSquares(temp);
            if(temp == 1){
                return true;
            }
        } 
        return false;
    }

    private int sumOfSquares(int n){
        int sum = 0;

        while(n > 0){
            int digit = n % 10;
            sum += (digit) * (digit);
            n = n / 10;
        }
        return sum;
    }
}

class Solution {
    // refer NEETCODE
    // BETTER  
    // T: O(log n) - base 10; everytime number is transformed into sumOfSquares.
    // S: O(1); no need of set.
    /* 
     * 1. Similar to LinkedList cycle detection, we can optimize by using Floyd Cycle detection.
     * 2. Hence, when `slow == fast`, this means cycle.
     * 3. Here, the linked list nodes are - n, n1=sumOfSquares(n), n2=sumOfSquares(n1) ...
     * 4. We construct the list as we go and move slow and fast using Floyd's Tortoise and Hare algorithm.
     * 5. Code - always exit when `slow == fast`.
     * 6. But it can happen that both are equal to 1, indicating happy number.
     * Hence, before returning whether happy, we check if `fast == 1`.
     * NOTE: we start with `fast = sumOfSquares(n)` because if slow and fast are initially equal
     * the algorithm wrongly detects a cycle.
     */
    public boolean isHappy(int n) {
        int slow = n, fast = sumOfSquares(n);

        while(slow != fast){
            fast = sumOfSquares(fast);
            fast = sumOfSquares(fast);
            slow = sumOfSquares(slow);
        } 
        return (fast == 1);
    }

    private int sumOfSquares(int n){
        int sum = 0;

        while(n > 0){
            int digit = n % 10;
            sum += (digit) * (digit);
            n = n / 10;
        }
        return sum;
    }
}

// using BRENT CYCLE DETECTION
// I WILL AVOID THIS FOR INTERVIEWS BECAUSE OF COMPLEXITY
/*
 * Intuition:
 * Brent's Cycle Detection is an efficient algorithm for detecting cycles in sequences,
 * similar to Floyd's Tortoise and Hare, but can be more memory and step efficient.
 * It is well-suited for problems like Happy Number, where we repeatedly transform a number
 * and want to detect if we enter a cycle (not happy) or reach 1 (happy).
 *
 * Approach:
 * 1. Initialize two pointers: slow and fast. Slow starts at n, fast at sumOfSquares(n).
 * 2. Use two variables: power (controls when slow moves forward) and length (cycle length counter).
 * 3. Move fast forward one step at a time, incrementing length.
 * 4. When length equals power, move slow to fast, double power, and reset length.
 * 5. If slow meets fast, a cycle is detected.
 * 6. If fast reaches 1, the number is happy.
 *
 * When to use:
 * - Use Brent's algorithm when you want O(1) space and potentially fewer steps than Floyd's.
 * - Useful for cycle detection in functional graphs or repeated transformations.
 *
 * Why it works:
 * - By controlling the movement of slow and fast pointers with power and length,
 *   Brent's method efficiently finds cycles without extra space.
 * - If a cycle exists, slow and fast will eventually meet.
 * - If the sequence reaches 1, it is a happy number.
 */
// T: O(log n)
// S: O(1)
class Solution {
    public boolean isHappy(int n) {
        int power = 1, length = 1;
        int slow = n, fast = sumOfSquares(n);

        while (slow != fast) {
            if (power == length) {
                slow = fast;
                power <<= 1; // double the power
                length = 0;
            }
            fast = sumOfSquares(fast);
            length++;
        }
        return fast == 1;
    }

    private int sumOfSquares(int n) {
        int sum = 0;
        while (n > 0) {
            int digit = n % 10;
            sum += digit * digit;
            n /= 10;
        }
        return sum;
    }
}
