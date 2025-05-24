class Solution {
    // refer NC
    // copied solution
    // dijkstra algorithm
    // T: O((n+m)*k); m = flights.length
    // S: O(n*k);
    public static final int INF = Integer.MAX_VALUE;

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        List<int[]>[] adj = new ArrayList[n];

        int[][] dist = new int[n][k + 5];
        for (int[] ar : dist) {
            Arrays.fill(ar, INF);
        }

        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] flight : flights) {
            int s = flight[0]; // src
            int d = flight[1]; // dest
            int p = flight[2]; // price

            adj[s].add(new int[] { d, p });
        }

        dist[src][0] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        pq.offer(new int[] { 0, src, -1 });

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int cost = current[0];
            int node = current[1];
            int stops = current[2];

            if (node == dst) {
                return cost;
            }
            if (stops == k || dist[node][stops + 1] < cost) {
                continue;
            }

            for (int[] nbr : adj[node]) {
                int v = nbr[0];
                int price = nbr[1];

                int nextCost = cost + price;
                int nextStops = stops + 1;

                if (dist[v][nextStops + 1] > nextCost) {
                    dist[v][nextStops + 1] = nextCost;
                    pq.offer(new int[] { nextCost, v, nextStops });
                }
            }
        }
        return -1;
    }
}
