package February2017.ChefAndTriangles;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by enkhbold on 2/7/17.
 */
public class ChefAndTriangles {

    public static void main(String[] args) {
        FasterScanner scanner = new FasterScanner();
        int N = scanner.nextInt();
        long L = scanner.nextLong();
        long R = scanner.nextLong();
        long[] strands = new long[N];

        for(int i=0; i<N; i++)
            strands[i] = scanner.nextLong();

        Arrays.sort(strands);

        long sol = 0;

        for(int i=N-1; i>0; i--){
            long min = strands[i] - strands[i-1] + 1;
            long max = strands[i] + strands[i-1] - 1;

            if(max < L)
                break;

            if(min > R)
                continue;

            sol += (Math.min(max,R) - Math.max(min,L) + 1);
            R = Math.max(min,L) - 1;
        }

        System.out.println(sol);
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