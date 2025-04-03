// refer STRIVER old videos for examples and expln.
// T: Average O(1) - get, put, insert, remove
// S: O(capacity) - map size and List size
/**
 * Requirement -
 * maintain the insertion order
 * at the time of access (get call), the cache is updated
 * a data structure to store key-value pairs => HashMap
 * to insert and remove frequently, we need access to the nodes in O(1)
 * hence, a Doubly Linked List
 * head and tail pointers are mainted to ease the handling of 0 and 1 length cases. 
 */
/*
 * APPROACH -
 * 1. We need a data structure to insert and delete in O(1) and preserve the order of elements.
 * 2. Another data structure to find the given key in the cache. 
 * Hence, a Map for lookup and a doubly linked list for keeping order and insertion and deletion in O(1).
 * 3. The `Node class` is static because it's a self-contained data structure with no dependency on LRUCache instances, improving memory efficiency and clarity. It avoids unnecessary links to the outer class.
 * 4. The LRU Cache `Map, capacity, head and tail` are set as Final.
 * Final makes sure the references for `Map, Node head and tail` are immutable, so for map, `put()` and `remove()` are possible but `new HashMap<>()` cannot be done again, 
 * similarly, `next` and `prev` can be changed for head and tail
 * and the value of `capacity` cannot be changed once assigned.
 * 5. Inititalize the list with `head` and `tail` as two dummy nodes linked to each other. 
 * 6. Insertions happen at `head`, so most recently used is at `head.next` and LRU is at `tail.prev`.
 * 7. get() -> if key is present, find the node from map, remove it and insert it as it is now the most recently used node
 * hence, it should be inserted after `head`. If key is absent, return -1.
 * 8. put() -> if map has the key, then it is an update operation. Hence, remove the existing key as it will be replaced by new key since most recently accessed.
 * if map size hits the capacity, remove LRU, it is at `tail.prev`.
 * now, insert the new node in map as well as in the DLL.
 * 9. insert() -> put the (key, Node) pair in map and insert at `head.next`.
 * 10. remove() -> remove the key from map and link the node's previous to node's next.
 */
class LRUCache {
    private static class Node {
        int key;
        int value;
        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.prev = null;
            this.next = null;
        }
    }

    private final Map<Integer, Node> map;
    private final int capacity;
    private final Node head;
    private final Node tail;

    public LRUCache(int capacity) {
        map = new HashMap<>();
        this.capacity = capacity;
        head = new Node(0, 0); // dummy node
        tail = new Node(0, 0); // dummy node
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            remove(node);
            insert(node);
            return node.value;
        }
        return -1;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            remove(map.get(key)); // in case of update
        }

        if (map.size() == capacity) {
            remove(tail.prev); // LRU
        }

        insert(new Node(key, value));
    }

    private void insert(Node node) {
        map.put(node.key, node);
        Node headNext = head.next;
        head.next = node;
        node.next = headNext;
        node.prev = head;
        headNext.prev = node;
    }

    private void remove(Node node) {
        map.remove(node.key);
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
