class Solution {
    // refer Gemini 2.5 Pro for code and explanation
    // NEETCODE - DFS approach - To be done
    // T: O(E log E)
    // S: O(E)
    // using a PQ because we need lexicographically smaller string to be processed first
    // Adding the airport in itinerary in post order
    // because this is to ensure all outgoing flights from
    // that airport are processed. So, we use LinkedList addFirst() to achieve this. 
    public List<String> findItinerary(List<List<String>> tickets) {
        Map<String, PriorityQueue<String>> adj = new HashMap<>();

        // construction of graph
        for(List<String> ticket: tickets){
            String from = ticket.get(0);
            String to = ticket.get(1);
            adj.computeIfAbsent(from, key -> new PriorityQueue<>()).offer(to);
        }

        LinkedList<String> res = new LinkedList<>();
        dfs("JFK", adj, res);
        return res;
    }

    private void dfs(String airport, Map<String, PriorityQueue<String>> adj, LinkedList<String> res){
        PriorityQueue<String> pq = adj.get(airport);

        while(pq != null && !pq.isEmpty()){
            String destination = pq.poll();
            dfs(destination, adj, res);
        }

        res.addFirst(airport);
    }
}
