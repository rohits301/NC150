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
