import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        FasterScanner scanner = new FasterScanner();
        int T = scanner.nextInt();
        while (T-- > 0) {
            int N = scanner.nextInt();
            int K = scanner.nextInt();
            long[] A = new long[N];
            for(int i=0; i<N; i++)
                A[i] = scanner.nextLong();

            solve(N,K,A);

        }
    }

    public static int  solveBrute(int N, int K, long[] A){
        return -1;
    }

    public static int  solve(int N, int K, long[] A){
        Arrays.sort(A);
        Map<Long, Integer> map1 = new HashMap<>();
        Map<Integer, List<Long>> map2 = new TreeMap<Integer, List<Long>>(Collections.reverseOrder());

        int bouquetCnt = N / K;

        int cnt = 0;
        long curr = -1;
        for(int i=0; i<N; ){
            if(curr < 0)
                curr = A[i];

            while(i<N && curr == A[i]) {
                i++;
                cnt++;
            }

            bouquetCnt -= cnt / K;
            cnt %= K;

            map1.put(A[i-1], cnt % K);
            List<Long> cnts = map2.getOrDefault(cnt, new LinkedList<>());
            cnts.add(cnt, A[i-1]);
            map2.put(cnt, cnts);

            cnt = 0;
            curr = -1;
        }

        for(Integer i : map2.keySet()){
            for(Long l : map2.get(i)){
                
            }
        }





        return -1;
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