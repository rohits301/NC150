class Solution {
    // refer Code With Alisha - expln and NEETCODE for code
    // BRUTE FORCE
    // T: O(n^2)
    // S: O(1)
    /*
     * Iterate over the entire array for each value to find it's next greater.
     */
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] ans = new int[n];

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (temperatures[j] > temperatures[i]) {
                    ans[i] = j - i;
                    break;
                }
            }
        }
        return ans;
    }
}

class Solution {
    // refer Code With Alisha - expln and NEETCODE for code
    // BETTER
    // T: O(n)
    // S: O(n)
    /*
     * Intuition -
     * I need a data structure to keep track of elements after current.
     * So, a stack can help us do that.
     * Every time, we need a warmer temp., so a value > current.
     * That is, we want to keep track of next greater element (NGE).
     * Also, we store indices in stack as a rule of thumb, because for arrays
     * we can get values if we have indices.
     * This is a monotonic decreasing stack, i.e, the elements from bottom to top
     * are in a non-increasing order or strictly decreasing order.
     * 
     * NOTE: in `st.pop()`, we pop all values smaller and equal.
     */
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] ans = new int[n];
        Stack<Integer> st = new Stack<>();

        for (int i = n - 1; i >= 0; i--) {
            if (st.isEmpty()) {
                st.push(i);
                ans[i] = 0;
            } else {

                while (!st.isEmpty() && temperatures[st.peek()] <= temperatures[i]) {
                    st.pop();
                }

                if (st.isEmpty()) {
                    ans[i] = 0;
                } else {
                    ans[i] = st.peek() - i;
                }
                st.push(i);
            }
        }

        return ans;
    }
}

class Solution {
    // refer Code With Alisha - expln and NEETCODE for code
    // OPTIMAL
    // T: O(n)
    // S: O(1)
    /*
     * Instead of going over all values in a brute force fasion, checking every
     * single element, we can jump indices.
     * e.g. if curr = 75 and curr + 1 = 72, ans for 72 = 2
     * that means, ans for 75 is either index of (72) or index of (72) + 2.
     * because, the number > 72 is after 2 places
     * hence, if answer exists for 75, it will be after 2 places only.
     * So we can avoid the redundant checking.
     * 
     * This is an improvement on the BRUTE FORCE approach.
     */
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] ans = new int[n];

        for (int i = n - 2; i >= 0; i--) {
            int j = i + 1; // next potentially warmer day
            while (j < n && temperatures[j] <= temperatures[i]) {
                if (ans[j] == 0) {
                    // no such day exist
                    j = n;
                    break;
                }
                j += ans[j]; // jump to next warmer day
            }

            if (j < n) {
                // a valid warmer day found for i
                ans[i] = j - i;
            }
        }

        return ans;
    }
}
