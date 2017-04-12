package February2017.IntervalGame;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.InputMismatchException;

/**
 * Created by enkhbold on 2/10/17.
 */
public class IntervalGame {

    private final static long[] sum = new long[300001];
    private final static long[] res = new long[300001];
    private final static int[] B = new int[201];
    private final static Deque<Integer> Q = new ArrayDeque<>();

    public static void main(String[] args) throws IOException {
        FasterScanner scanner = new FasterScanner();
        int T = scanner.nextInt();
        OutputStream out = new BufferedOutputStream( System.out );
        while (T-- > 0) {
            int N = scanner.nextInt();
            int M = scanner.nextInt();

            for(int i=0; i<N; i++){
                sum[i] = scanner.nextInt();
                if(i > 0)
                    sum[i] += sum[i-1];
            }

            for(int i=1; i<=M; i++)
                B[i] = scanner.nextInt();

            // start

            long solution = Long.MIN_VALUE;

            for(int round = M; round > 0; round --){
                boolean isChefTurn = round % 2 == 1;

                long[] minmax = new long[N - 2*round - B[round] + 3];

                if(round < M){
                    Q.clear();
                    int w = B[round] - B[round+1] - 1; // max_min window size
                    for (int i = round; i < round + w; i++) { // first window
                        while (!isChefTurn && !Q.isEmpty() && res[i] >= res[Q.peekLast()]) // if it's chefu's turn then maximum sliding window
                            Q.pollLast();

                        while (isChefTurn && !Q.isEmpty() && res[i] <= res[Q.peekLast()]) // if it's chef's turn then minimum sliding window
                            Q.pollLast();

                        Q.addLast(i);
                    }
                    int n = N - round - B[round+1] + 1;
                    for (int i = round + w; i < n; i++) {
                        minmax[i-w-round] = res[Q.peekFirst()];
                        while (!isChefTurn && !Q.isEmpty() && res[i] >= res[Q.peekLast()])
                            Q.pollLast();

                        while (isChefTurn && !Q.isEmpty() && res[i] <= res[Q.peekLast()])
                            Q.pollLast();

                        while (!Q.isEmpty() && Q.peekFirst() <= i-w)
                            Q.pollFirst();

                        Q.addLast(i);
                    }
                    minmax[n-w-round] = res[Q.peekFirst()];
                }
                for(int startIdx = round-1, endIdx = startIdx + B[round] - 1; endIdx <= N - round; startIdx++, endIdx++){
                    long s = getSum(startIdx, endIdx) * (isChefTurn ? 1 : -1);

                    if(round < M)
                        s += minmax[startIdx+1-round];

                    res[startIdx] = s;

                    if(round == 1)
                        solution = Math.max(s, solution);
                }

                //System.out.println("round " + round + " : " + Arrays.toString(res));
            }

            out.write((solution + "\n").getBytes());
        }
        out.flush();
    }

    // inclusive
    public static long getSum(int startIdx, int endIdx){
        return startIdx > 0 ? sum[endIdx] - sum[startIdx-1] : sum[endIdx];
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
