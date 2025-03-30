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
     * 4. We construct the list as we go and move slow and fast in tortoise and hare fashion.
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
