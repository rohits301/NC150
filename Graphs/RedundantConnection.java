class Solution {
    // refer NEETCODE for code and explanation
    // refer STRIVER DSU also for explanation
    // T: O(V+ E*(alpha V))
    // S: O(V)
    /**
     * 1. If a graph has n nodes, then it will have exactly n-1 edges to  be a tree.
     * 2. Since there is only one cycle in the given graph, so it has n nodes and n edges.
     * We have to find the edge that if removed will make the graph a tree.
     * 3. Also, since there can be many edges, we have to return the last edge that occurs in input.
     * 4. So, this problem becomes cycle detection and return the edge. DFS, Kahn's algorithm can do the job.
     * 5. But, this problem hints towards DSU.
     * 6. Because if a graph has an extra edge, then that means, two nodes which are already connected and now there is a redundant edge trying to connect them. This can be found using Union-find.
     * 7. In the below approach, we do - 
     * union by rank and path compression in `find`.
     */
    public int[] findRedundantConnection(int[][] edges) {

        int n = edges.length;
        int[] par = new int[n + 1];
        int[] rank = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            par[i] = i;
            rank[i] = i;
        }

        for (int[] edge : edges) {
            if (!union(edge[0], edge[1], par, rank)) {
                return new int[] { edge[0], edge[1] };
            }
        }
        return new int[0];
    }

    private int find(int[] par, int x) {
        int p = par[x];

        while (p != par[p]) {
            // path compression
            par[p] = par[par[p]];
            p = par[p];
        }
        return p;
    }

    private boolean union(int a, int b, int[] par, int[] rank) {
        int p1 = find(par, a);
        int p2 = find(par, b);

        if (p1 == p2) {
            return false;
        }
        if (rank[p1] > rank[p2]) {
            par[p2] = p1;
            rank[p1] += rank[p2];
        } else {
            par[p1] = p2;
            rank[p2] += rank[p1];
        }
        return true;
    }
}

// KAHN'S, DFS REMAINING
