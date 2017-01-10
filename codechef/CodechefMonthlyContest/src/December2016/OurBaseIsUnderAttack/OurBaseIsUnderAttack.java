package December2016.OurBaseIsUnderAttack;

import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;

/**
 * Created by enkhbold on 12/7/16.
 */
public class OurBaseIsUnderAttack {
    public static void main(String[] args) {

        FasterScanner scanner = new FasterScanner();
        int T = scanner.nextInt();
        while (T-- > 0) {
            long N = scanner.nextLong();
            solve(N);
        }
    }

    public static long solve2(long N){
        long cnt = 0;
        for(long i=2; i<=N; i++){
            if(convert(N, i).charAt(0) == '1')
                cnt++;
        }
        return cnt;
    }

    public static String convert(long number, long base)
    {
        long quotient = number / base;
        long remainder = number % base;

        if (quotient == 0) // base case
        {
            return remainder < 10 ? Long.toString(remainder) : "z";
        }
        else
        {
            return convert(quotient, base) + (remainder < 10 ? Long.toString(remainder) : "z");
        }
    }

    public static void solve(long N){

        if(N == 1){
            System.out.println("INFINITY");
            return;
        }

        long cnt = 0;
        long base = 1;
        while(N >= Math.pow(2,base)){
            Double right = root(N, base);
            Double left = root(N / 2, base);

            long l = left.longValue() - 1;
            long r = right.longValue() + 1;

            while(new Double(Math.pow(l, base) + 0.0001).longValue() * 2 <= N)
                l++;

            while(new Double(Math.pow(r, base) + 0.0001).longValue() > N)
                r--;


            long diff = r - l + 1;
            //System.out.println(N + " " + left + "(" + l + ")" + " " + right + "(" + r + ")" + " " + diff);

            cnt += diff;
            base++;
        }
        System.out.println(cnt);
    }

    public static double root(double num, double root){
        return Math.pow(num, 1 / root);
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