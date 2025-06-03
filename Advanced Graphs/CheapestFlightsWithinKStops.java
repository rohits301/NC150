class Solution {
    private class Pair {
        int node;
        int price;

        Pair(int node, int price) {
            this.node = node;
            this.price = price;
        }
    }

    private class Triplet {
        int stops;
        int node;
        int price;

        Triplet(int stops, int node, int price) {
            this.stops = stops;
            this.node = node;
            this.price = price;
        }
    }
    

    // refer STRIVER 
    // BRUTE/BETTER/OPTIMAL
    // T: O(k*E);where E = flights.length, each path can be added at max k times.
    // S: O(V + E)
    // Dijkstra without PQ -> Bellman Ford
    /**
     * 1. Use Queue instead of PQ as stops increase linearly, by 1 everytime.
     * 2. So, both will give same result but Queue has less TC compared to PQ.
     * 3. If stops > k, no point in checking as stops is a critical condition.
     * 4. Add in the queue only if current stops are less than k.
     * 5. Pair class represents a Flight leg.
     * 6. Triplet represents a Path state.
     */
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        // construct graph
        List<List<Pair>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int[] flight : flights) {
            int from = flight[0];
            int to = flight[1];
            int price = flight[2];
            adj.get(from).add(new Pair(to, price));
        }

        // dijkstra - without PQ
        int[] minCost = new int[n];
        Arrays.fill(minCost, Integer.MAX_VALUE);
        minCost[src] = 0;

        Queue<Triplet> q = new LinkedList<>();
        q.offer(new Triplet(0, src, 0));

        while (!q.isEmpty()) {
            Triplet triplet = q.poll();
            int stops = triplet.stops;
            int node = triplet.node;
            int cost = triplet.price;

            if (stops > k) {
                continue;
            }

            for (Pair p : adj.get(node)) {
                int adjNode = p.node;
                int edgeWeight = p.price;

                if (cost + edgeWeight < minCost[adjNode] && stops <= k) {
                    minCost[adjNode] = cost + edgeWeight;
                    q.offer(new Triplet(stops + 1, adjNode, minCost[adjNode]));
                }
            }
        }

        if (minCost[dst] == Integer.MAX_VALUE) {
            return -1; // cannot reach
        }

        return minCost[dst];
    }
}
