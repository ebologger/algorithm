package February2017.Gerrymander;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.InputMismatchException;

/**
 * Created by enkhbold on 2/11/17.
 */
public class Gerrymander {

    public static void main(String[] args) throws IOException {
        FasterScanner scanner = new FasterScanner();
        int T = scanner.nextInt();
        OutputStream out = new BufferedOutputStream( System.out );
        while (T-- > 0) {
            int o1 = scanner.nextInt();
            int o2 = scanner.nextInt();
            int N = o1*o2;
            int[] d = new int[N];
            for(int i=0; i<N; i++)
                d[i] = scanner.nextInt();

            int[] cnts = new int[o2];

            int solution = 0;

            int cnt = 0;
            for(int i=0; i<o2; i++)
                cnt += d[i];

            if(cnt > o2 / 2)
                cnts[0]++;

            for(int start=1, end=o2; start<N; start++, end++){
                end = end % N;
                cnt -= d[start-1];
                cnt += d[end];
                if(cnt > o2 / 2) {
                    cnts[start % o2]++;
                    if(cnts[start % o2] > o1 / 2){
                        solution = 1;
                        break;
                    }
                }
            }

            out.write((solution + "\n").getBytes());
        }
        out.flush();
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