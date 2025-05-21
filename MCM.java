import java.util.* ;
import java.io.*; 

public class Solution {
	// refer STRIVER DP 48.
	// T: O(exponential);
	// S: O(n*n); stack space
	/**
	 * 1. Partition DP - whenever the problem can be solved in multiple
	 * patterns, we go for partition dp. E.g. (10*20)+ 30, 10*(20+30)
	 * both result in different answers
	 * 2. Question: given array of size n, so there are `n-1` matrices.
	 * Find the minimum number of operation to multiple these matrices.
	 * 3. Why does this problem arise?
	 * There are multiple ways to multiple 3 matrices.(e.g)
	 * A(BC) or (AB)C
	 * Suppose, matrices A=10*5, B=5*20, C=20*30
	 * A(BC) and (AB)C both result in same matrix of size 10*30.
	 * But, the number of operations is different in both.
	 * The number of operations to multiply 2 matrices of size
	 * x*y and y*z = x*y*z.
	 * Hence, operations for A(BC) = 10*5*30 + 5*20*30 = 1500+3000 = 4500
	 * operations for (AB)C = 10*5*20 + 10*20*30 = 1000 + 9000 = 10000
	 * 4. Rules to write recursion for PARTITION DP / MCM DP
	 * a) Start with the entire block/array. Define start(i) and end(j).
	 * b) Try all partitions - Run a loop to try all partitions
	 * c) Return the best possible 2 partitions.
	 * d) Base case - smallest problem
     * 5. Base case: if there is only one matrix, no multiplication reqd.
	 * hence, min. operations = 0. (i==j) case.
	 * 6. Start and end -> 1, n-1
	 * because, the first matrix is of dimension arr[0]*arr[1].
	 * 7. Loop for partitions ->
	 * An inner loop to partition the array, so for `n` size array, we have `n-1` partitions.
	 * But here, the number of matrices are `n-1`, so number of partitions = `n-2`.
	 * `k` runs from `i` to `j-1`
	 * Hence, partitions are like - (i,k) and (k+1,j).
	 * 8. Recurrence ->
	 * The number of steps/operations = 
	 * the operations in final matrix + number of operations in partition 1 + number of operations in partition 2
	 * 9. Result is minimum of all steps.
	 */
	public static int matrixMultiplication(int[] arr , int N) {
		// Write your code here
		return dfs(1,N-1, arr);
	}

	private static int dfs(int i, int j, int[] arr){
		if(i == j){
			return 0;
		}
		
		int min = Integer.MAX_VALUE;
		for(int k = i; k < j; k++){
			int steps = (arr[i-1] * arr[k] * arr[j]) +
						dfs(i, k, arr) + 
						dfs(k+1, j, arr); 

			min = Math.min(min, steps);
		}
		return min;
	}
}

import java.util.* ;
import java.io.*; 

public class Solution {
	// refer STRIVER DP 48.
	// T: O(n*n*n);
	// S: O(n*n) + O(n*n); dp array + stack space
	/**
	 * 1. Memoize the recursion.
	 */
	public static int matrixMultiplication(int[] arr , int N) {
		// Write your code here
		int[][] dp = new int[N][N];
		for(int[] ar: dp){
			Arrays.fill(ar, -1);
		}
		return dfs(1,N-1, arr, dp);
	}

	private static int dfs(int i, int j, int[] arr, int[][] dp){
		if(i == j){
			return 0;
		}
		
		if(dp[i][j] != -1){
			return dp[i][j];
		}

		int min = Integer.MAX_VALUE;
		for(int k = i; k < j; k++){
			int steps = (arr[i-1] * arr[k] * arr[j]) +
						dfs(i, k, arr, dp) + 
						dfs(k+1, j, arr, dp); 

			min = Math.min(min, steps);
		}
		return dp[i][j] = min;
	}
}

import java.util.* ;
import java.io.*; 

public class Solution {
	// refer STRIVER DP 48.
	// OPTIMAL - TABULATION
	// T: O(n*n*n);
	// S: O(n*n); dp array + stack space
	/**
	 * 1. Copy the base case.
	 * 2. Number of nested loops = number of changing parameters in recursion.
	 * 3. Iterate in opposite order of recursion.
	 * 4. Copy the recurrence
	 * 5. Result at same indices as invocation of recursion.
	 * 6. ONE CHANGE - we start the inner loop for `j` from `i+1` 
	 * because our idea of `i == j` is already covered in base case
	 * so, partition is only possible if `j > i`. So, `j=i+1`.
	 */
	public static int matrixMultiplication(int[] arr , int N) {
		// Write your code here
		int[][] dp = new int[N][N];
		// base case
		for(int i=0; i<N; i++){
			dp[i][i] = 0;
		}

		for(int i=N-1; i>=1; i--){
			for(int j=i+1; j<N; j++){
				int min = Integer.MAX_VALUE;
				for(int k = i; k < j; k++){
					int steps = (arr[i-1] * arr[k] * arr[j]) +
								dp[i][k] + 
								dp[k+1][j]; 

					min = Math.min(min, steps);
				}
				dp[i][j] = min;
			}
		}
		return dp[1][N-1];
	}
}
