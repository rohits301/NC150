class Solution {
    // Nucleus OA Question 1
    // SIEVE OF ERATOSTHENES 
    // T: O(n*log(logn))
    // S: O(n)
    // More practice: ClosestPrimeNumbersInRange.java
    /*
     * TIME COMPLEXITY ANALYSIS: O(N * log(log N))
     * -------------------------------------------
     * Derivation:
     * 1. The outer loop iterates through prime numbers 'p' up to sqrt(N).
     * 2. For each prime 'p', the inner loop marks multiples.
     * - Technically, it runs from p*p to N, performing roughly (N/p) operations.
     * 3. Total operations = Sum of (N/p) for all primes p <= N.
     * = N * (1/2 + 1/3 + 1/5 + 1/7 + ... + 1/p)
     *
     * 4. Harmonic Series of Primes (Mertens' 2nd Theorem):
     * The sum of reciprocals of primes approaches ln(ln(N)).
     * Therefore, Total Work = N * ln(ln(N)).
     *
     * NOTE ON OPTIMIZATION (j = p * p):
     * Starting the inner loop at p*p skips (p-1) steps. However, this does not change
     * the Time Complexity class because the runtime is dominated by small primes 
     * (2, 3, 5...) where (N/p) is massive and the skipped portion (p-1) is negligible.
     *
     * SPACE COMPLEXITY: O(N)
     * We need an array of size N+1 to store the boolean flags.
     */
    public int countPrimes(int n) {
        boolean[] isPrime = new boolean[n];
        // mark all numbers as prime
        for (int i = 2; i < n; i++) {
            isPrime[i] = true;
        }

        // sieve logic
        for (int p = 2; p * p < n; p++) {
            if (isPrime[p]) {
                // mark all multiples of p after p*p as non-prime
                for (int j = p * p; j < n; j += p) {
                    isPrime[j] = false;
                }
            }
        }

        int count = 0;
        for (int i = 2; i < n; i++) {
            if (isPrime[i]) {
                count++;
            }
        }
        return count;
    }
}
