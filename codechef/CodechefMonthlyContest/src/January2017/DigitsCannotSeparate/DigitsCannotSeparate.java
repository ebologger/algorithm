package January2017.DigitsCannotSeparate;

import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;

/**
 * Created by enkhbold on 1/17/17.
 *
 * brute/backtrack... not solved...
 */
public class DigitsCannotSeparate {

    public static void main(String[] args) {
        FasterScanner scanner = new FasterScanner();
        int T = scanner.nextInt();
        while (T-- > 0) {
            int N = scanner.nextInt();
            String S = scanner.nextLine();
            int M = scanner.nextInt();
            int X = scanner.nextInt();
            int Y = scanner.nextInt();

            DigitsCannotSeparate m = new DigitsCannotSeparate();
            System.out.println(m.solve(-1, 0, N, S, M, X, Y));
        }
    }

    public long max = 1;

    public long solve(long gcd, int startIdx, int N, String S, int M, int X, int Y){

        if(gcd <= max && gcd > 0)
            return 1;

        long max_gcd = 1;

        if(X + 1 > N - startIdx)
            return 1;

        if(M*(Y + 1) < N - startIdx)
            return 1;

        if(startIdx == N){
            if(X > 0) // still need to put more separator
                return 1;
            else{
                max = Math.max(gcd,max);
                return gcd;
            }
        }

        if(Y == 0){ // no more separator to place
            if(N - startIdx > M) // last piece is longer than M
                return 1;

            long l = Long.parseLong(S.substring(startIdx, N));
            long tmp = gcd(gcd, l);
            max = Math.max(tmp, max);
            return tmp;
        }

        for(int i=1; i<=M; i++){
            if(startIdx + i <= N){
                long l = Long.parseLong(S.substring(startIdx, startIdx + i));
                long new_gcd = l;
                if(gcd > 0){
                    new_gcd = gcd(gcd, l);
                }
                if(new_gcd > 1)
                    max_gcd = Math.max(max_gcd, solve(new_gcd, startIdx + i, N, S, M, X - 1, Y - 1));
            }
        }
        return max_gcd;
    }

    public static long gcd(long a, long b)
    {
        for(;;) {
            if(a==0) return b;
            b%=a;
            if(b==0) return a;
            a%=b;
        }
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