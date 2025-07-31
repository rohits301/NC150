/*
 * Premium problem
 * Approach Explanation:
 * 
 * encode(List<String> strs):
 * - For each string in the input list, we append its length, a separator "#", and the string itself to a StringBuilder.
 * - This creates a unique representation for each string, allowing us to know exactly where one string ends and the next begins.
 * - Example: ["hello", "world"] becomes "5#hello5#world".
 * - This method ensures that any characters in the string (even "#" or digits) do not interfere with the encoding.
 * 
 * decode(String str):
 * - We iterate through the encoded string, looking for the "#" separator.
 * - The substring before "#" gives us the length of the next string.
 * - We extract that number of characters immediately after "#" as the original string.
 * - We repeat this process until we've decoded all strings.
 * 
 * Time Complexity: O(n) and Space Complexity: O(n), where n is the total number of characters in all strings combined.
 */
// From LintCode
class Solution {

    public String encode(List<String> strs) {
        StringBuilder sb = new StringBuilder();

        for(String str: strs){
            sb.append(str.length()).append("#").append(str);
        }
        return sb.toString();
    }

    // "i" is always on the number
    // "j" is on "#"
    // and "j+1" on first character of string
    public List<String> decode(String str) {
        List<String> strs = new ArrayList<>();
        int i = 0, j = 0;

        while(j < str.length()){
            while(str.charAt(j) != '#'){
                j++;
            }

            // now str.charAt(j) = '#'
            int len = Integer.parseInt(str.substring(i, j));
            i = j + 1 + len;
            strs.add(str.substring(j + 1, i));

            // setup for next iteration
            j = i;
        }
        return strs;
    }
}
