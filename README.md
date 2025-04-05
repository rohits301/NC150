# NeetCode 150

This repository contains Java solutions to NeetCode 150 problems at [neetcode.io](https://neetcode.io/practice?tab=neetcode150). <br>
I have included <em>brute, better and optimal</em> solution for all the problems. My aim is to cover all the problems in the NeetCode150 list with a clean and readable code solution.

## 15 Standard Problems List

1.  **Two Sum** - [LeetCode](https://leetcode.com/problems/two-sum/)
    * Given an array of integers `nums` and an integer `target`, return indices of the two numbers such that they add up to `target`.
    * **Solution:** Uses a hash map to store seen numbers and their indices, allowing for efficient lookup.

2.  **Product of Array Except Self** - [LeetCode](https://leetcode.com/problems/product-of-array-except-self/)
    * Given an integer array `nums`, return an array `answer` such that `answer[i]` is equal to the product of all the elements of `nums` except `nums[i]`.
    * **Solution:** Calculates prefix and postfix products to avoid division.

3.  **3 Sum** - [LeetCode](https://leetcode.com/problems/3sum/)
    * Given an integer array `nums`, return all the triplets `[nums[i], nums[j], nums[k]]` such that `i != j`, `i != k`, and `j != k`, and `nums[i] + nums[j] + nums[k] == 0`.
    * **Solution:** Sorts the array and uses two pointers to find triplets.

4.  **Longest Repeating Character Replacement** - [LeetCode](https://leetcode.com/problems/longest-repeating-character-replacement/)
    * You are given a string `s` and an integer `k`. You can choose any character of the string and change it to any other uppercase English character no more than `k` times. Return the length of the longest substring containing the same letter you can get after performing the above operations.
    * **Solution:** Uses a sliding window with a character count map.

5.  **Daily Temperatures** - [LeetCode](https://leetcode.com/problems/daily-temperatures/)
    * Given an array of integers `temperatures` represents the daily temperatures, return an array `answer` such that `answer[i]` is the number of days you have to wait after the `ith` day to get a warmer temperature. If there is no future day for which this is possible, keep `answer[i] == 0` instead.
    * **Solution:** Uses a stack to keep track of decreasing temperatures.

6.  **Search in a Rotated Sorted Array** - [LeetCode](https://leetcode.com/problems/search-in-rotated-sorted-array/)
    * Given a rotated sorted array `nums`, find the index of a given target.
    * **Solution:** Modified binary search to handle the rotation.

7.  **Koko Eating Bananas** - [LeetCode](https://leetcode.com/problems/koko-eating-bananas/)
    * Koko loves to eat bananas. There are `n` piles of bananas, the `ith` pile has `piles[i]` bananas. The guards have gone and will come back in `h` hours. Koko can decide her bananas-per-hour eating speed of `k`. Each hour, she chooses some pile of bananas and eats `k` bananas from that pile. If the pile has less than `k` bananas, she eats all of them instead, and won't eat any more bananas during this hour. Koko likes to eat slowly but still wants to finish eating all the bananas before the guards come back.
    * **Solution:** Binary search to find the minimum eating speed.

8.  **LRU Cache** - [LeetCode](https://leetcode.com/problems/lru-cache/)
    * Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.
    * **Solution:** Uses a doubly linked list and a hash map.

9.  **Same Binary Tree** - [LeetCode](https://leetcode.com/problems/same-tree/)
    * Given the roots of two binary trees `p` and `q`, write a function to check if they are the same or not.
    * **Solution:** Recursive or iterative traversal to compare nodes.

10. **Serialize and Deserialize a Binary Tree** - [LeetCode](https://leetcode.com/problems/serialize-and-deserialize-binary-tree/)
    * Design an algorithm to serialize and deserialize a binary tree.
    * **Solution:** Uses a breadth-first search (BFS) with a queue.

11. **Median in Data Stream** - [LeetCode](https://leetcode.com/problems/find-median-from-data-stream/)
    * Design a data structure that supports adding new numbers and finding the median of all numbers added so far.
    * **Solution:** Uses two heaps (min-heap and max-heap).

12. **Combination Sum** - [LeetCode](https://leetcode.com/problems/combination-sum/)
    * Given an array of distinct integers `candidates` and a target integer `target`, return a list of all unique combinations of `candidates` where the chosen numbers sum to `target`.
    * **Solution:** Backtracking algorithm.

13. **Course Schedule** - [LeetCode](https://leetcode.com/problems/course-schedule/)
    * Given the number of courses and their prerequisites, determine if it is possible to finish all courses.
    * **Solution:** Topological sort using Kahn's algorithm or DFS.

14. **Rotting Oranges** - [LeetCode](https://leetcode.com/problems/rotting-oranges/)
    * Given a grid of oranges, where `0` represents an empty cell, `1` represents a fresh orange, and `2` represents a rotten orange, return the minimum number of minutes that must elapse until no cell has a fresh orange. If this is impossible, return `-1`.
    * **Solution:** BFS to simulate the rotting process.

15. **Longest Common Subsequence** - [LeetCode](https://leetcode.com/problems/longest-common-subsequence/)
    * Given two strings `text1` and `text2`, return the length of their longest common subsequence.
    * **Solution:** Dynamic programming approach.
