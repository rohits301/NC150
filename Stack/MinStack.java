class MinStack {
    // refer STRIVER
    // T: O(1)
    // S: O(n)
    /*
     * INTUITION
     * The trick is to store a "modified value" in the stack when the current value
     * is smaller than the current minimum.
     * This helps us track the minimum efficiently.
     * The formula `2 * val - min` ensures that modified values are distinguishable.
     * When a modified value is popped, we can retrieve the previous minimum.
     */
    private Stack<Long> st;
    private long min;

    public MinStack() {
        st = new Stack<>();
        min = Long.MAX_VALUE;
    }

    public void push(int val) {
        if (st.isEmpty()) {
            min = val;
            st.push((long) val);
        } else {
            if (val < min) {
                // update min
                // insert a smaller val
                // point to note: the value `pushVal` is smaller than both current `min` and `val`
                long pushVal = (2L * val - min);
                min = val;
                st.push(pushVal);
            } else {
                st.push((long) val);
            }
        }
    }

    public void pop() {
        // update min
        if (st.peek() < min) {
            // the value is modified, hence min will change
            min = 2 * min - st.peek(); // pushVal is st.peek() and old `min` is the value
        }
        st.pop();
    }

    public int top() {
        long top = st.peek();
        if (top < min) {
            return (int) min;
        } else {
            return (int) top;
        }
    }

    public int getMin() {
        return (int) min;
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(val);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
