class Solution {
    // BRTUE/BETTER
    // refer NEETCODE
    // T: O(m * nlogn), n = strs.length, m = avg. length of strs[i] 
    // S : O(m * n)
    /**
     * Approach 1: Sorting
     * 1. The basic approach to determine anagram is to compare the sorted order of two strings.
     * 2. If it matches -> anagrams, else -> no.
     * 3. Similarly, here, we sort every string. 
     * 4. Since, the sorted string is unique, hence, it can be used as a key in Hashmap.
     * 5. We need a map to store anagrams corresponding to each string.
     * 6. So, we construct a `Hashmap(String, List<String>)` with sorted string as key and add the current string into the ArrayList.
     * Eg. str = "nat", keyStr = "ant", (key, value) -> ("ant", ["nat"])
     * 7. In the end, construct arraylist from map values as `map.values()`. This returns us the arraylist of anagrams.
     * NOTE: `putIfAbsent()` -> Puts the key-value pair into the map only if the key is not already present or is present but mapped to null.
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, List<String>> map = new HashMap<>();

        for (String s : strs) {
            //sorting the individual string from strs array to get unique key
            char[] arr = s.toCharArray();
            Arrays.sort(arr); 
            String keyStr = new String(arr);

            //For each key, create a new arraylist if it is absent and add string corresponding to key
            map.putIfAbsent(keyStr, new ArrayList<>());
            map.get(keyStr).add(s);
        }
        // map.values() returns a collection, so we use constructor of ArrayList class in Java to create AL with elements in the collection
        return new ArrayList<>(map.values());
    }
}

class Solution {
    // OPTIMAL
    // T: O(n * m), n = strs.length, m = avg. length of strs[i] 
    // S : O(n * m), since all strings are stored in map, so `n` strings of `m` length 
    /**
     * Detailed complexity analysis -> 
     * T: O(N * M) where N = number of strings, M = max length of a string
     * K = 26 (alphabet size)
     * Inner loop: O(M) for counting + O(K) for key building + O(K) for map operations = O(M+K)
     * Total: O(N * (M+K)) = O(N*M) since K is constant.
     * S: O(N * M + N * K) = O(N*M) - For storing the result lists (N*M chars) and map keys (N*K max)
     */
    /**
     * Approach 2: Frequency Array
     * SAME APPROACH AS ABOVE
     * ONLY CHANGE: instead of sorting, use frequency array of size=26.
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        if(strs == null || strs.length == 0){
            return Collections.emptyList();
        }
        Map<String, List<String>> map = new HashMap<>();
        
        for(String str: strs){
            char[] frequencyArr = new char[26];
            for(int i = 0; i < str.length(); i++){
                frequencyArr[str.charAt(i) - 'a']++;
            }
            
            // cannot use `.toString()` because `char[].toString()` calls `Object.toString()`, which returns a memory address-like format ([C@hashcode])
            String key = new String(frequencyArr);             
            List<String> temp = map.getOrDefault(key, new ArrayList<String>());
            temp.add(str);
            map.put(key, temp);
        }
        return new ArrayList<>(map.values());
    }
}
