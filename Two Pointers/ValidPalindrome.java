class Solution {
    // 1st Solution, using String methods of alphanumeric and lowercase
    // T: O(n), S: O(n)
    public boolean isPalindrome(String s) {
        if(s.length() == 1) return true;

        // to remove non-alphanumeric characters and then convert to lowercase
        s = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        
        int i=0, j=s.length()-1;
        while(i <= j){
            char chi = s.charAt(i);
            char chj = s.charAt(j);
            if(chi != chj){
                return false;
            } 
            i++;
            j--;
        }
        return true;
    }
}

class Solution {
    // 2nd Solution, better, without using any external library
    // T: O(n), S: O(1) as no extra space because we do not use lower case method
    public boolean isPalindrome(String s) {
        
        int i=0,j=s.length()-1;
        while(i<=j){
            char ch1 = s.charAt(i);
            char ch2 = s.charAt(j);

            if(!isAlphanumeric(ch1)) {
                i++;
            } else if(!isAlphanumeric(ch2)){
                j--;
            } else {
                // both i and j are alphanumeric
                // convert ch1 and ch2 to lowercase if they are uppercase
                // ONLY WORKS FOR ASCII CHARACTERS
                if(ch1 >= 'A' && ch1 <= 'Z'){
                    ch1 += 'a' - 'A';
                }
                if(ch2 >= 'A' && ch2 <= 'Z'){
                    ch2 += 'a' - 'A';
                }

                if(ch1 != ch2){
                    return false;
                }
                i++;
                j--;
            }
        }
        return true;
    }

    private boolean isAlphanumeric(char ch){
        if(ch >= 'A' && ch <= 'Z'){
            return true;
        } else if (ch >= 'a' && ch <= 'z'){
            return true;
        } else if (ch >= '0' && ch <= '9'){
            return true;
        }
        return false;
    }
}

class Solution {
    // 3rd Solution, suggested improvements by ChatGPT that maintain the time and space complexity
    // Time: O(n), Space: O(1) (no extra space for lowercase conversion)
    public boolean isPalindrome(String s) {
        int i = 0, j = s.length() - 1;

        while (i <= j) {
            char ch1 = s.charAt(i);
            char ch2 = s.charAt(j);

            if (!Character.isLetterOrDigit(ch1)) {
                i++;
            } else if (!Character.isLetterOrDigit(ch2)) {
                j--;
            } else {
                // Both i and j are alphanumeric
                // Convert to lowercase for case-insensitive comparison
                if (Character.toLowerCase(ch1) != Character.toLowerCase(ch2)) {
                    return false;
                }
                i++;
                j--;
            }
        }

        return true;
    }
}
