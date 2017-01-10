package December2016.TrainPartner;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class TrainPartner {

    public static void main(String[] args) {
        FasterScanner scanner = new FasterScanner();
        int T = scanner.nextInt();
        while (T-- > 0) {
            int N = scanner.nextInt();
            solve(N);
        }
    }

    private static Map<Integer, String> berthNames;
    private static Map<Integer, Integer> berthPairs;

    static {

        berthNames = new HashMap<>();
        berthNames.put(1,"LB");
        berthNames.put(2,"MB");
        berthNames.put(3,"UB");
        berthNames.put(4,"LB");
        berthNames.put(5,"MB");
        berthNames.put(6,"UB");
        berthNames.put(7,"SL");
        berthNames.put(8,"SU");


        berthPairs = new HashMap<>();
        berthPairs.put(1,4);
        berthPairs.put(4,1);

        berthPairs.put(2,5);
        berthPairs.put(5,2);

        berthPairs.put(3,6);
        berthPairs.put(6,3);

        berthPairs.put(7,8);
        berthPairs.put(8,7);
    }

    public static void solve(int N){
        int d = N / 8;
        int r = N % 8;

        if(r == 0){
            r = 8;
            d--;
        }

        int p = berthPairs.get(r);
        System.out.println((d*8 + p) + berthNames.get(p));
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