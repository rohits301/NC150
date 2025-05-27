class Solution {
    // refer NEETCODE explanation, Gemini for code
    // Djikstra Algorithm
    // shortest path from a source to all nodes in a positive weight graph
    // maximum number of Edges = V^2.
    // T: O(ElogV)
    // S: O(V+E)
    public static final int INF = Integer.MAX_VALUE;

    public int networkDelayTime(int[][] times, int n, int k) {
        Map<Integer, List<int[]>> adj = new HashMap<>();
        for (int[] time : times) {
            int source = time[0];
            int destination = time[1];
            int weight = time[2]; //weight/time
            adj.computeIfAbsent(source, key -> new ArrayList<>()).add(new int[] { destination, weight });
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]); // sort on smaller time
        pq.offer(new int[] { 0, k });

        int[] minTimes = new int[n + 1];
        Arrays.fill(minTimes, INF);
        minTimes[k] = 0; // given source

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int currentNodeTime = current[0];
            int node = current[1];

            if (adj.containsKey(node)) {
                for (int[] nbr : adj.get(node)) {
                    int nbrNode = nbr[0];
                    int nbrTime = nbr[1];

                    // if shorter path to `nbrNode` exists through `node`, then update the path and add to `pq`
                    if (minTimes[node] + nbrTime < minTimes[nbrNode]) {
                        minTimes[nbrNode] = minTimes[node] + nbrTime;
                        pq.offer(new int[] { minTimes[nbrNode], nbrNode });
                    }
                }
            }
        }
        int maxDelay = -1;
        for (int i = 1; i <= n; i++) {
            if (minTimes[i] == INF) {
                return -1;
            }
            maxDelay = Math.max(maxDelay, minTimes[i]);
        }
        return maxDelay;
    }
}

/**
 * Striver Like code: using ArrayList for adjacency list and Pair class
 */
class Solution {
    // single source shortest path - Dijikstra
    // PQ approach - O(E*logV)
    public static final int INF = Integer.MAX_VALUE;

    public int networkDelayTime(int[][] times, int n, int k) {
        // 1. construct graph
        // Initialisation
        List<List<Pair>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<Pair>());
        }
        // construction
        for (int[] time : times) {
            int u = time[0];
            int v = time[1];
            int wt = time[2];
            adj.get(u).add(new Pair(wt, v));
        }

        // 2. Run dijikstra from source node `k`
        int[] minTimes = new int[n + 1];
        Arrays.fill(minTimes, INF);
        minTimes[k] = 0; // source

        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> a.distance - b.distance);
        pq.offer(new Pair(0, k));

        while (!pq.isEmpty()) {
            Pair rp = pq.poll();
            int distance = rp.distance;
            int srcNode = rp.node;

            for (Pair nbr : adj.get(srcNode)) {
                int edgeWt = nbr.distance;
                int adjNode = nbr.node;

                if (distance + edgeWt < minTimes[adjNode]) {
                    minTimes[adjNode] = distance + edgeWt;
                    pq.offer(new Pair(minTimes[adjNode], adjNode));
                }
            }
        }

        int max = -1;
        // REMEMBER -> START FROM 1 TO N
        for (int i = 1; i <= n; i++) {
            int val = minTimes[i];
            if (val == INF) {
                return -1;
            }
            max = Math.max(max, val);
        }
        return max;
    }

    private class Pair {
        int distance;
        int node;

        Pair(int distance, int node) {
            this.distance = distance;
            this.node = node;
        }
    }
}
