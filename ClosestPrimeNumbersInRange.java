class Solution {
    public int[] closestPrimes(int left, int right) {
        // 1. count primes till right
        boolean[] isPrime = new boolean[right+1];
        Arrays.fill(isPrime, true);
        isPrime[0] = false;
        isPrime[1] = false;

        for(int p=2; p * p <= right; p++){
            if(isPrime[p]){
                for(int i=p*p; i <= right; i += p){
                    isPrime[i] = false;
                }
            }
        }

        // 2. collect prime numbers in the range
        List<Integer> primes = new ArrayList<>();
        for(int i=left; i<=right; i++){
            if(isPrime[i]){
                primes.add(i);
            }
        }
        // 11,13,17,19
        // 3. given an array find the smallest (value1) pair with minimum difference 
        int[] ans = {Integer.MAX_VALUE, Integer.MAX_VALUE};
        if(primes.size() < 2){
            return new int[]{-1,-1};
        }

        int i=0,j=1;
        int min = Integer.MAX_VALUE;
        while(j < primes.size()){
            int diff = primes.get(j) - primes.get(i);
            if(diff < min){
                min = diff;
                // if(primes.get(i) < ans[0]){ //-- not required
                    ans[0] = primes.get(i);
                    ans[1] = primes.get(j);
                // }
            }
            i++;
            j++;
        }

        return ans;
    }
}

class Solution {
    // T: O(n*loglogn); nested loops for sieve of eratosthenes (n*loglogn), 1 iteration to find minDiff primes (approx. n)
    // S: O(n)
    // n = right
    // Based on find prime numbers
    public int[] closestPrimes(int left, int right) {
        // 1. count primes till right
        boolean[] isPrime = new boolean[right + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = false;
        isPrime[1] = false;

        // Sieve logic
        for (int p = 2; p * p <= right; p++) {
            if (isPrime[p]) {
                for (int i = p * p; i <= right; i += p) {
                    isPrime[i] = false;
                }
            }
        }

        // 2. pairs of primes between left and right with min diff with num1 smaller than num2
        int lastPrime = -1;
        int[] ans = new int[] { -1, -1 };
        int minDiff = Integer.MAX_VALUE;

        for (int i = left; i <= right; i++) {
            if (isPrime[i]) {
                if (lastPrime != -1) {
                    int diff = i - lastPrime;
                    if (diff <= minDiff) {
                        minDiff = diff;
                        ans[0] = lastPrime;
                        ans[1] = i;
                    }
                    // the least difference between any two primes numbers after is 2.
                    // hence, return when we find first such pair as we require smallest num1 pair.
                    if (minDiff <= 2) {
                        return ans;
                    }
                }
                lastPrime = i;
            }
        }

        return ans;
    }
}
