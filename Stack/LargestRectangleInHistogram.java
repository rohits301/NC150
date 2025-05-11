class Solution {
    // BRUTE FORCE
    // T: O(n^2); Worst case: a sorted array or an array with all same heights
    // S: O(1);
    /**
     * 1. For every element, the rectangle can be formed by finding the left and right boundary.
     * 2. The left boundary is the index of first element on left of `i` that is smaller than `i`.
     * 3. The right boundary is the index of first element on right of `i` that is smaller than `i`.
     * 4. If there is no such element on left, set `leftSmaller = -1`.
     * Similarly, for right, `rightSmaller = n`. This is to denote we didn't find any elements which are smaller than `i` in the bounds of the array.
     * 5. So, `width = rightSmaller - leftSmaller - 1`.
     * 6. `Area = width * heights[i]`.
     */
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int area = 0;

        for (int i = 0; i < n; i++) {
            int leftSmaller = i;
            for (int j = i - 1; j >= 0; j--) {
                if (heights[j] < heights[leftSmaller]) {
                    leftSmaller = j;
                    break;
                }
            }
            leftSmaller = (leftSmaller == i) ? -1 : leftSmaller;

            int rightSmaller = i;
            for (int j = i + 1; j < n; j++) {
                if (heights[j] < heights[rightSmaller]) {
                    rightSmaller = j;
                    break;
                }
            }
            rightSmaller = (rightSmaller == i)? n : rightSmaller;

            int width = (rightSmaller - leftSmaller - 1);
            area = Math.max(area, width * heights[i]);
        }
        return area;
    }
}

class Solution {
    // refer Subhesh Bhaiya approach - similar to Striver Old Video - 1
    // T: O(n); left array and stack (n) + right array and stack (n) + finding max area (n), total = 3n, three-pass
    // S: O(n); stack + 2 arrays of size=n, total = 3n
    /**
     * 1. For any bar, the rectangle is extended between (it's left boundary and right boundary) * (its height).
     * 2. Left boundary = first bar on the left of `heights[i]` that is less than `heights[i]`
     * 3. Right boundary = first bar on the right of `heights[i]` that is less than `heights[i]`
     * 4. So, the above becomes Next Smaller on Left (NSE Left) and Next Smaller on Right (NSE Right).
     * 5. NSE can be achieved with a variation in NGE (next greater element).
     * 6. Idea of NSE - For every element in `heights[i]`, we will find the index of the smallest value in its left.
     * 6.1 We maintain a MONTONIC INCREASING STACK because that enables us to compare with only top of stack. 
     * 6.2 We store indices in `stack` and `ls[i]` and `rs[i]`
     * 6.3 Traversing from left, if we find that current element (`heights[i]`) is <= `heights[st.peek()]`, that means, 
     * the stack has bigger values than the current element, so `pop` all the bigger values.
     * 6.4 Then, If no smaller exists on left, and stack is empty, hence `ls[i] = -1`,
     * else, `ls[i] = st.peek()`, the index which remains is of the value that is smaller than `heights[i]`.
     * 6.5 Everytime, we push to the stack because the current `height[i]` can become some other's NSE left.
     * 7. SIMILARLY, we calculate NSE on right. - here, if stack is empty, then we take `n` as the smallest on right.
     * 8. In the end, rectangle formed is between left smaller and right smaller indices,
     * so we take excluding both `ls[i]` and `rs[i]`. So, `width = rs[i]-ls[i]-1`.
     * 9. Calculate area and find max.
     */
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int area = 0;
        // Next smaller on left -> ls[i]
        // Next smaller on right -> rs[i]
        // area = (ls[i] - rs[i] - 1) * heights[i]

        Stack<Integer> st = new Stack<>();
        // NSE Left
        int[] ls = new int[n];
        for (int i = 0; i < n; i++) {
            while (!st.isEmpty() && heights[st.peek()] >= heights[i]) {
                st.pop();
            }

            if (st.isEmpty()) {
                ls[i] = -1;
            } else {
                ls[i] = st.peek();
            }
            st.push(i);
        }

        st.clear(); // remove old data to reuse stack
        // NSE Right
        int[] rs = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            while (!st.isEmpty() && heights[st.peek()] >= heights[i]) {
                st.pop();
            }

            if (st.isEmpty()) {
                rs[i] = n;
            } else {
                rs[i] = st.peek();
            }
            st.push(i);
        }

        for (int i = 0; i < n; i++) {
            int width = rs[i] - ls[i] - 1;
            area = Math.max(area, width * heights[i]);
        }
        return area;
    }
}

class Solution {
    // refer STRIVER old videos - 1 and 2
    // OPTIMAL
    // T: O(n);
    // S: O(n);
    // One-Pass
    /**
     * 1. Improving upon previous solution to achieve the same with less iterations and without storing ls[] and rs[] arrays. Stack still stores the indices only.
     * 2. The right boundary or right smaller if not found is `n` and left boundary or left smaller if not found is `-1`.
     * 3. We calculate `ls` and `rs` on the fly. The stack stores indices of bars such that their heights are in monotonically increasing order from bottom to top. This means that as we go from the top of the stack downwards, we encounter indices of bars with progressively smaller heights.
     * 4. Considering the current element as the right boundary, we try to find the largest rectangle it can make.
     * 5. So, if the stack is currently empty, then there is no element to process, do nothing and add the current index to stack.
     * 6. If the stack is non-empty, check whether we reached the end of the array (`i == n`) and still have some elements unprocessed OR the current element (`heights[i]`) is smaller or equal to the top of the stack. 
     * 7. If `heights[st.peek()] >= heights[i]`, then, make the current as the right smaller because `i` is the first element on the right of `st.peek()` that is smaller than or equal to it.
     * 8. So, pop this peek element from the stack and store its height (`h = heights[st.pop()]`).
     * 9. Now, if stack is not empty, left smaller is the one below the `st.pop()` element, that is, the new peek, so `ls=st.peek()`.
     * 10. Area, as we know from earlier is same = `(rs-ls-1) * h`;
     * NOTE: 
     * a) the case of `st.isEmpty()` is triggered when we are processing the last element, so after it stack is empty, so left smaller is correctly set as `-1` for it because no one is smaller than it on its left. 
     * b) loops runs an extra iteration -> `i = n`, to process all the remaining elements in stack which do not have a right smaller. 
     */
    public int largestRectangleArea(int[] heights) {
        Stack<Integer> st = new Stack<>();
        int n = heights.length;
        int rs = 0, ls = 0; // rs = rightSmallerIndex and ls = leftSmallerIndex
        int area = 0;

        for (int i = 0; i <= n; i++) {
            while (!st.isEmpty() && (i == n || heights[st.peek()] >= heights[i])) {
                int h = heights[st.pop()];
                rs = i;
                
                if (st.isEmpty()) {
                    ls = -1;
                } else {
                    ls = st.peek();
                }
                
                area = Math.max(area, (rs - ls - 1) * h);
            }
            st.push(i);
        }
        return area;
    }
}
