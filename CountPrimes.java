class Solution {
    // Nucleus OA Question 1
    // SIEVE OF ERATOSTHENES 
    // T: O(n*log(logn))
    // S: O(n)
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
                for (int i = p * p; i < n; i += p) {
                    isPrime[i] = false;
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
