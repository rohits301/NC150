// refer NEETCODE
// BRUTE / BETTER
// T: add() -> O(1), count() -> O(n)
// S: O(n); for map and points list.
/*
 * Idea:
 * 1. Maintain both a `pointsList` and a `pointsCount` map.
 * 2. pointsList -> stores all points (including duplicates).
 * 3. pointsCount -> hashmap with key = Point, value = frequency.
 * 
 * add():
 * - Insert the point into both list and map, update its frequency.
 * 
 * count(point):
 * - To form a square with (px, py), we need diagonal point (x, y).
 * - For (px, py) and (x, y) to form diagonal:
 *      a) |px - x| == |py - y|  (equal sides)
 *      b) x != px && y != py   (not same row/col)
 * - The other 2 points must be (x, py) and (px, y).
 * - Total # of such squares = freq(x, y) * freq(x, py) * freq(px, y).
 * - Iterate over pointsList to find valid diagonals.
 * 
 * Note:
 * - Duplicates handled by storing frequency in map.
 * - Custom Point object used since list keys must be immutable.
 * - We multiply the three frequencies because each occurrence of the diagonal point can form a square with every occurrence of the two adjacent points. This is essentially the Cartesian product of the three sets of points, so the total number of distinct squares is f1 × f2 × f3.
 * - Also, we don't multiply by freq(px, py) because the query point is fixed and not chosen from the data structure.
 */
class DetectSquares {
    Map<Point, Integer> pointsCount;
    List<Point> pointsList;

    public static class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() { return x; }
        public int getY() { return y; }

        public int hashCode() {
            int prime = 31;
            int result = 1;
            result = prime * result + x;
            result = prime * result + y;
            return result;
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Point)) return false;
            Point other = (Point) obj;
            return x == other.x && y == other.y;
        }
    }

    public DetectSquares() {
        pointsCount = new HashMap<>();
        pointsList = new ArrayList<>();
    }

    public void add(int[] point) {
        Point pt = new Point(point[0], point[1]);
        pointsList.add(pt);
        pointsCount.put(pt, pointsCount.getOrDefault(pt, 0) + 1);
    }

    public int count(int[] point) {
        int res = 0;
        int px = point[0], py = point[1];

        for (Point pt : pointsList) {
            int x = pt.getX(), y = pt.getY();

            // check diagonal condition
            if (Math.abs(px - x) != Math.abs(py - y) ||
                (x == px) || 
                (y == py)) {
                continue;
            }

            // freq(x, y) is implicitly handled by iteration over list
            res += (pointsCount.getOrDefault(new Point(px, y), 0) *
                    pointsCount.getOrDefault(new Point(x, py), 0));
        }
        return res;
    }
}

// refer NEETCODE
// OPTIMAL
// T: add() -> O(1), count() -> O(n)
// S: O(n); only map is used.
/*
 * Improvement:
 * - We don’t need pointsList, since pointsCount already tracks frequencies.
 * - Iterate over pointsCount instead of pointsList.
 * 
 * add():
 * - Insert point into map and update frequency.
 * 
 * count(point):
 * - Same diagonal logic as brute.
 * - But now we explicitly multiply by freq(x, y) from map (since list is gone).
 * - For each valid diagonal (x, y):
 *      res += freq(x, y) * freq(x, py) * freq(px, y)
 * 
 * Benefit:
 * - Saves space (no list).
 * - Iteration on map avoids duplicate entries in list.
 */
class DetectSquares {
    Map<Point, Integer> pointsCount;

    public static class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() { return x; }
        public int getY() { return y; }

        public int hashCode() {
            int prime = 31;
            int result = 1;
            result = prime * result + x;
            result = prime * result + y;
            return result;
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Point)) return false;
            Point other = (Point) obj;
            return x == other.x && y == other.y;
        }
    }

    public DetectSquares() {
        pointsCount = new HashMap<>();
    }

    public void add(int[] point) {
        Point pt = new Point(point[0], point[1]);
        pointsCount.put(pt, pointsCount.getOrDefault(pt, 0) + 1);
    }

    public int count(int[] point) {
        int res = 0;
        int px = point[0], py = point[1];

        for (Point key : pointsCount.keySet()) {
            int x = key.getX(), y = key.getY();
            int countPt = pointsCount.get(key);

            // check diagonal condition
            if (Math.abs(px - x) != Math.abs(py - y) ||
                (x == px) || (y == py)) {
                continue;
            }

            int count1 = pointsCount.getOrDefault(new Point(x, py), 0);
            int count2 = pointsCount.getOrDefault(new Point(px, y), 0);

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
