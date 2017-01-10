package December2016.KiritoInLabyrinth;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by enkhbold on 12/6/16.
 */
public class KiritoInLabyrinth {

    private static int[] max_primes;

    public static void main(String[] args) {
        generatePrimes(10000000);

        FasterScanner scanner = new FasterScanner();
        int T = scanner.nextInt();
        while (T-- > 0) {
            int N = scanner.nextInt();
            int[] A = new int[N];
            for(int i=0; i<N; i++)
                A[i] = scanner.nextInt();
            solve(N, A);
        }
    }

    // max_primes contains max number of prime factors
    public static void generatePrimes(int N){
        max_primes = new int[N+1];
        for(int i=2; i<=N; i++){
            if(max_primes[i] == 0) {
                max_primes[i] = i;
                for (int j = 2 * i; j <= N; j += i) {
                    max_primes[j] = i;
                }
            }
        }
    }

    public static void solve(int N, int[] A){
        int[] dp = new int[10000000]; // dp[i] = longest list length with last element is divisible by prime 'i'
        int maxLength = 1;
        for(int i=0; i<N; i++){
            if(A[i] != 1){ // 1 can't be in any list
                Set<Integer> factors = primeFactors(A[i]);

                int length = 1;

                for(Integer factor : factors){
                    length = Math.max(dp[factor] +1, length);
                }

                for(Integer factor : factors){
                    dp[factor] = length;
                }

                maxLength = Math.max(maxLength, length);
            }
        }
        System.out.println(maxLength);
    }

    public static Set<Integer> primeFactors(int n){
        Set<Integer> factors = new HashSet<>();

        while(n > 1){
            int prime = max_primes[n];
            factors.add(prime);
            while(n % prime == 0)
                n /= prime;
        }

        return factors;
    }
}


class FasterScanner {
    private InputStream mIs;
    private byte[] buf = new byte[1024];
    private int curChar;
    private int numChars;

    public FasterScanner() {
        this(System.in);
    }

    public FasterScanner(InputStream is) {
        mIs = is;
    }

    public int read() {
        if (numChars == -1)
            throw new InputMismatchException();
        if (curChar >= numChars) {
            curChar = 0;
            try {
                numChars = mIs.read(buf);
            } catch (IOException e) {
                throw new InputMismatchException();
            }
            if (numChars <= 0)
                return -1;
        }
        return buf[curChar++];
    }

    public String nextLine() {
        int c = read();
        while (isSpaceChar(c))
            c = read();
        StringBuilder res = new StringBuilder();
        do {
            res.appendCodePoint(c);
            c = read();
        } while (!isEndOfLine(c));
        return res.toString();
    }

    public String nextString() {
        int c = read();
        while (isSpaceChar(c))
            c = read();
        StringBuilder res = new StringBuilder();
        do {
            res.appendCodePoint(c);
            c = read();
        } while (!isSpaceChar(c));
        return res.toString();
    }

    public long nextLong() {
        int c = read();
        while (isSpaceChar(c))
            c = read();
        int sgn = 1;
        if (c == '-') {
            sgn = -1;
            c = read();
        }
        long res = 0;
        do {
            if (c < '0' || c > '9')
                throw new InputMismatchException();
            res *= 10;
            res += c - '0';
            c = read();
        } while (!isSpaceChar(c));
        return res * sgn;
    }

    public int nextInt() {
        int c = read();
        while (isSpaceChar(c))
            c = read();
        int sgn = 1;
        if (c == '-') {
            sgn = -1;
            c = read();
        }
        int res = 0;
        do {
            if (c < '0' || c > '9')
                throw new InputMismatchException();
            res *= 10;
            res += c - '0';
            c = read();
        } while (!isSpaceChar(c));
        return res * sgn;
    }

    public boolean isSpaceChar(int c) {
        return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
    }

    public boolean isEndOfLine(int c) {
        return c == '\n' || c == '\r' || c == -1;
    }
}