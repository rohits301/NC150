class TimeMap {
    // refer NEETCODE
    // BRUTE - TLE
    // T: set - O(1), get - O(n) 
    // S: O(m)
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
    // T: set - O(1), get - O(log n) 
    // S: O(m)
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

        List<Pair> values = map.get(key);
        int lo = 0, hi = values.size() - 1;

        while(lo <= hi){
            int mid = lo + (hi - lo)/2;
            if(values.get(mid).getTime() <= timestamp){
                res = values.get(mid).getValue();
                lo = mid + 1;
            } else {
                hi = mid - 1;
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

/**
 * Your TimeMap object will be instantiated and called as such:
 * TimeMap obj = new TimeMap();
 * obj.set(key,value,timestamp);
 * String param_2 = obj.get(key,timestamp);
 */
