// refer STRIVER Word Ladder - I
// T: O(n*m*26); average word length in wordList = m
// n = wordList.length
// S: O(n); queue will have atmost n words
// BFS
// trying all 26 characters for all positions of the word
// if they exist in the set
// set works as visited array for word strings
class Solution {
    public class Pair {
        String first;
        int second;

        Pair(String first, int second) {
            this.first = first;
            this.second = second;
        }
    }

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> set = new HashSet<>();
        for (String word : wordList) {
            set.add(word);
        }

        Queue<Pair> q = new LinkedList<>();
        q.offer(new Pair(beginWord, 1));

        if (set.contains(beginWord)) {
            set.remove(beginWord);
        }

        while (!q.isEmpty()) {
            Pair p = q.poll();
            String word = p.first;
            int count = p.second;

            if (word.equals(endWord)) {
                return count;
            }

            for (int i = 0; i < word.length(); i++) {
                for (char ch = 'a'; ch <= 'z'; ch++) {
                    char[] wordArray = word.toCharArray();
                    wordArray[i] = ch;
                    String modifiedString = new String(wordArray);
                    if (set.contains(modifiedString)) {
                        q.offer(new Pair(modifiedString, count + 1));
                        set.remove(modifiedString);
                    }
                }
            }
        }
        return 0;
    }
}
