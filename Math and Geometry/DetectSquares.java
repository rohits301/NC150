// refer NEETCODE
// BRUTE/BETTER
// T: add - O(1), count - O(n)
// S: O(n); or map and points list.
/*
 * 1. We can maintain an array list to store all the points because we don't know the size of the incoming stream. 
 * 2. For points, also, store it in an ArrayList, because we need to use them as keys in our hashmap.
 * 3. Idea - 
 * Maintain a `List<List<Integer>> points` and hashmap with key as point and value as it's frequency.
 * 4. add() - add point to list and map and increase frequency.
 * 5. count() - 
 * For detecting square, we can check the diagonal. If x and y difference with diagonal is equal, implies sides are equal, hence square.
 * So, we iterate over the list and check - 
 a) if the point qualifies to be a diagonal. 
 b) it is not the same point, i.e., its x and y coordinates are different from given (px, py).
 * Total sqaures = product of frequencies of both points (x, py) and (px, y).
 * Note - For duplication in x and y, this operation is performed again as list can have duplicates.
 */
class DetectSquares {
    Map<List<Integer>, Integer> pointsCount;
    List<List<Integer>> points;

    public DetectSquares() {
        pointsCount = new HashMap<>();
        points = new ArrayList<>();
    }

    public void add(int[] point) {
        List<Integer> pt = Arrays.asList(point[0], point[1]);
        points.add(pt);
        pointsCount.put(pt, pointsCount.getOrDefault(pt, 0) + 1);
    }

    public int count(int[] point) {
        int res = 0;

        int px = point[0];
        int py = point[1];
        for (List<Integer> pts : points) {
            int x = pts.get(0);
            int y = pts.get(1);

            if (Math.abs(px - x) != Math.abs(py - y) ||
                (x == px) ||
                (y == py)) {
                continue;
            }

            res += (pointsCount.getOrDefault(Arrays.asList(x, py), 0) *
                    pointsCount.getOrDefault(Arrays.asList(px, y), 0));
        }
        return res;
    }
}

// refer NEETCODE
// OPTIMAL
// T: add - O(1), count - O(n)
// S: O(n); or map and points list.
/*
 * 1. Same approach conceptually.
 * 2. Change - instead of iterating over List that has duplicates, we iterate over the map, this saves time, but the overall worst case complexity remains same.
 * 3. Since, `x` and `y` can also be duplicates, so we multiply with their frequency as well in the count of squares as well.
 */
class DetectSquares {
    Map<List<Integer>, Integer> pointsCount;
    List<List<Integer>> points;

    public DetectSquares() {
        pointsCount = new HashMap<>();
        points = new ArrayList<>();
    }

    public void add(int[] point) {
        List<Integer> pt = Arrays.asList(point[0], point[1]);
        points.add(pt);
        pointsCount.put(pt, pointsCount.getOrDefault(pt, 0) + 1);
    }

    public int count(int[] point) {
        int res = 0;

        int px = point[0];
        int py = point[1];
        for (List<Integer> key : pointsCount.keySet()) {
            int x = key.get(0);
            int y = key.get(1);
            int countPt = pointsCount.get(key);

            if (Math.abs(px - x) != Math.abs(py - y) ||
                    (x == px) ||
                    (y == py)) {
                continue;
            }

            int count1 = pointsCount.getOrDefault(Arrays.asList(x, py), 0);
            int count2 = pointsCount.getOrDefault(Arrays.asList(px, y), 0);
            res += (countPt) * (count1 * count2);
        }
        return res;
    }
}

/**
 * Your DetectSquares object will be instantiated and called as such:
 * DetectSquares obj = new DetectSquares();
 * obj.add(point);
 * int param_2 = obj.count(point);
 */
