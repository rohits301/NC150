class Solution {
    // refer code with Alisha
    // OPTIMAL
    // use STACK
    // T: O(n)
    // S: O(n)
    /*
     * Reverse Polish Notation = POSTFIX
     * acc. to question, the order will always be valid,
     * so no need for check before popping is stack is non-empty
     */
    public int evalRPN(String[] tokens) {
        Stack<Integer> st = new Stack<>();

        for (String token : tokens) {
            if (token.equals("+")) {
                int a = st.pop();
                int b = st.pop();
                st.push(b + a);
            } else if (token.equals("-")) {
                int a = st.pop();
                int b = st.pop();
                st.push(b - a);
            } else if (token.equals("*")) {
                int a = st.pop();
                int b = st.pop();
                st.push(b * a);
            } else if (token.equals("/")) {
                int a = st.pop();
                int b = st.pop();
                st.push(b / a);
            } else {
                // it is a number
                st.push(Integer.parseInt(token));
            }
        }
        return st.pop(); // stack will have answer in the end
    }
}
