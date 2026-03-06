class TimeMap {
    // refer NEETCODE
    // BRUTE - TLE
    // T: set - O(1), get - O(n) 
    // S: O(m)
    /*
     * Linear search for the values in the list of pairs.
     * We need pairs (timestamp, value) for each key.
     */
    Map<String, List<Pair>> map;

    public TimeMap() {
        map = new HashMap<>();
    }
    
    public void set(String key, String value, int timestamp) {
        if (!map.containsKey(key)) {
            map.put(key, new ArrayList<>());
        }
        map.get(key).add(new Pair(timestamp, value));
    }
    
    public String get(String key, int timestamp) {
        String res = "";
        if(!map.containsKey(key)){
            return res;
        }

        // Linear Search for values
        List<Pair> values = map.get(key);
        for(Pair p: values){
            if(p.getTime() <= timestamp){
                res = p.getValue();
            }
        }
        return res;
    }

    static class Pair {
        int time;
        String value;

        Pair(int time, String value){
            this.time = time;
            this.value = value;
        }

        private int getTime(){
            return this.time;
        }

        private String getValue(){
            return this.value;
        }
    }
}

class TimeMap {
    // OPTIMAL
    // T: O(1) - set, O(log n) - get
    // S: O(n)
    /**
     * 1. We can binary search the value as timestamps are always strictly increasing as per the question.
     * 2. We want a data structure to store a Pair object for a key. And there can be many pairs for a key, so `List<Pair>`.
     * 3. At the time of get(), for a key, it's list of pairs are sorted by timestamp.
     * 4. So, we can binary search the timestamp, such that the target (i.e., `timestamp`) is less than equal to `midPair.timestamp`.
     * 5. We want to find ceil of all the `midPair.timestamp` that satisfy the condition `midPair.timestamp <= timestamp`, hence,
     * if the condition is true, we shift to greater values that satisfy the condition, so shift `lo`
     * if the condition is false, we shift to smaller values, so shift `hi`.
     */
    Map<String, List<Pair>> map;

    public TimeMap() {
        map = new HashMap<>();
    }

    public void set(String key, String value, int timestamp) {
        List<Pair> pairList = map.getOrDefault(key, new ArrayList<>());
        pairList.add(new Pair(value, timestamp));
        map.put(key, pairList);
    }

    public String get(String key, int timestamp) {
        String ans = "";
        if (!map.containsKey(key)) {
            return ans;
        }
        
        // binary search the value in pairList
        List<Pair> pairList = map.get(key);
        int lo = 0, hi = pairList.size() - 1;
        // find ceil for timestamp
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            Pair midPair = pairList.get(mid);
            
            if (midPair.timestamp <= timestamp) {
                ans = midPair.value;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return ans;
    }
    
    class Pair {
        String value;
        int timestamp;
    
        Pair(String value, int timestamp) {
            this.value = value;
            this.timestamp = timestamp;
        }
    }
}

/**
 * Your TimeMap object will be instantiated and called as such:
 * TimeMap obj = new TimeMap();
 * obj.set(key,value,timestamp);
 * String param_2 = obj.get(key,timestamp);
 */
