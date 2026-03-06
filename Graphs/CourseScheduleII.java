class Solution {
    // Similar to Course Schedule I
    // T: O(V + E) (V = numCourses, E = prerequisites.length)
    // S: O(V + E)
    // NOTE: 
    // the code automatically handles the case for empty prerequisites array
    // in that case, the indegree of all nodes will be 0

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>();
        int v = numCourses;

        // Create adjacency list for each course (node)
        for(int i = 0; i < v; i++) {
            adj.add(new ArrayList<>());
        }

        for(int i = 0; i < prerequisites.length; i++) {
            int ai = prerequisites[i][0];
            int bi = prerequisites[i][1];
            adj.get(bi).add(ai); // bi must be completed before ai
        }

        // Array to store in-degrees (number of prerequisites for each course)
        int[] indegree = new int[v];
        for(int i = 0; i < v; i++) {
            for(int nbr: adj.get(i)) {
                indegree[nbr]++; // Increment indegree for all neighbors
            }
        }

        // Array to store the topological order of courses
        int[] topo = new int[v];
        int idx = 0;

        // Queue for BFS traversal to process courses with no prerequisites (indegree = 0)
        Queue<Integer> q = new LinkedList<>();
        for(int i = 0; i < v; i++) {
            if(indegree[i] == 0) {
                q.offer(i); // Add course to the queue if it has no prerequisites
            }
        }

        // BFS to determine topological order
        while(!q.isEmpty()) {
            int val = q.poll();
            topo[idx++] = val; // Add course to topological order

            // Process all neighbors of the current course
            for(int nbr: adj.get(val)) {
                indegree[nbr]--; // Reduce indegree for each neighbor
                if(indegree[nbr] == 0) {
                    q.offer(nbr); // If indegree becomes 0, it can be processed
                }
            }
        }

        // If we have processed all courses (idx == v), return the topological order
        // If there's a cycle (not all courses were processed), return an empty array
        return (idx == v) ? topo : new int[]{};
    }
}
