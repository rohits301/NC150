class Solution {
    // refer NEETCODE for code and explanation
    // Gemini for code
    // Problem is minimum cost to connect all points
    // so minCostPath/shortestPath from all nodes to all nodes.
    // Hence, MST - Prim's/Kruskal's
    // Here, I used Prim's
    // T: O(n^2 log n)
    // S: O(n^2)
    public int minCostConnectPoints(int[][] points) {
        int n = points.length;
        Map<Integer, List<int[]>> adj = new HashMap<>();
        for (int i = 0; i < n; i++) {
            adj.put(i, new ArrayList<>());
        }

        // construct the graph
        for (int i = 0; i < points.length; i++) {
            int x1 = points[i][0];
            int y1 = points[i][1];
            for (int j = i + 1; j < points.length; j++) {
                int x2 = points[j][0];
                int y2 = points[j][1];
                int dist = Math.abs(x1 - x2) + Math.abs(y1 - y2);
                adj.get(i).add(new int[] { j, dist });
                adj.get(j).add(new int[] { i, dist });
            }
        }

        // Prim's
        int[] minCost = new int[n];
        Arrays.fill(minCost, Integer.MAX_VALUE);

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]); //min heap
        //start from 0
        minCost[0] = 0;
        pq.offer(new int[] { 0, 0 });

        boolean[] visited = new boolean[n];

        int totalCost = 0;
        int pointsInMST = 0;

        while (!pq.isEmpty() && pointsInMST < n) {
            int[] current = pq.poll();
            int cost = current[0];
            int u = current[1]; // vertex

            if (visited[u]) {
                continue;
            }

            visited[u] = true;
            totalCost += cost;
            pointsInMST++;

            if (pointsInMST == n) {
                break;
            }

            if (adj.containsKey(u)) {
                for (int[] edge : adj.get(u)) {
                    int v = edge[0];
                    int weightUV = edge[1];

                    if (!visited[v] && weightUV < minCost[v]) {
                        minCost[v] = weightUV;
                        pq.offer(new int[] { minCost[v], v });
                    }
                }
            }
        }
        return totalCost;
    }
}
