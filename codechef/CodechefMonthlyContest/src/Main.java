import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        FasterScanner scanner = new FasterScanner();
        int T = scanner.nextInt();
        while (T-- > 0) {
            int N = scanner.nextInt();
            int Q = scanner.nextInt();
            long P = scanner.nextLong();

            int[] A = new int[N];
            for(int i=0; i<N; i++)
                A[i] = scanner.nextInt();

            SegmentTree st = new SegmentTree(A, N, P);
            //System.out.println(Arrays.toString(st.st));

            for(int i=0; i<Q; i++){
                int operation = scanner.nextInt();
                if(operation == 1){
                    int X = scanner.nextInt() - 1;
                    int Y = scanner.nextInt();
                    st.update(N,X,Y);
                    //System.out.println(Arrays.toString(st.st));

                }else{
                    int L = scanner.nextInt() - 1;
                    int R = scanner.nextInt() - 1;

                    long prod = st.query(N,L,R);
                    //System.out.println(prod);
                    long[] res = lagrange(prod);
                    //System.out.println("0 0 0 0");

                    if(res == null)
                        System.out.println(-1);
                    else
                        System.out.println(res[0] + " " + res[1] + " " + res[2] + " " + res[3]);
                }
            }
        }
    }

    public static long[] lagrange(long n){
        for (long a = 0, na = n; a * a <= na; a++) {
            for (long b = a, nb = na - a * a; b * b <= nb; b++) {
                for (long c = b, nc = nb - b * b; c * c <= nc; c++) {
                    long nd = nc - c * c;
                    Double dd = Math.sqrt(nd);
                    long d = dd.longValue();
                    if (d * d == nd) {
                        return new long[]{a,b,c,d};
                    }
                }
            }
        }
        return null;
    }
}

class SegmentTree {
    int[] arr;
    long[] st; // segment tree
    long P;
    BigInteger bigIntP;

    SegmentTree(int arr[], int n, long P) {
        int x = (int) (Math.ceil(Math.log(n) / Math.log(2)));
        int max_size = 2 * (int) Math.pow(2, x) - 1;
        this.P = P;
        bigIntP = BigInteger.valueOf(P);
        st = new long[max_size];
        this.arr = arr;
        constructTree(arr, 0, 0, n - 1);
    }

    void update(int n, int X, int Y) {
        //BigInteger bigIntX = BigInteger.valueOf(arr[X]);
        //Long inverse = bigIntX.modInverse(bigIntP).longValue() % P;
        //update(0, 0, n - 1, inverse, X, Y);
        arr[X] = Y;
        constructTree(arr, 0, 0, n - 1);
    }



    private void update(int si, int ss, int se, Long inverse, int X, int Y) {
        if (X < ss || X > se)
            return;
        if(Y % arr[X] == 0){
            st[si] = (st[si] * (Y/arr[X]) ) % P;
        }else{
            st[si] = (st[si] * inverse) % P;
            st[si] = (st[si] * Y) % P;
        }

        if (se != ss) {
            int mid = getMid(ss, se);
            update(2 * si + 1, ss, mid, inverse, X, Y);
            update(2 * si + 2, mid + 1, se, inverse, X, Y);
        }
    }

    long query(int n, int L, int R) {
        return query(0, 0, n - 1, L, R);
    }

    private long query(int si, int ss, int se, int L, int R) {
        if (ss > se || ss > R || se < L)
            return 1;

        if (ss >= L && se <= R)
            return st[si];

        int mid = (ss + se) / 2;
        return (query(2 * si + 1, ss, mid, L, R) *
                query(2 * si + 2, mid + 1, se, L, R)) % P;
    }

    private long constructTree(int arr[], int si, int ss, int se) {
        if (ss == se) {
            st[si] = arr[ss];
            return st[si];
        }

        int mid = getMid(ss, se);
        st[si] = (constructTree(arr, si * 2 + 1, ss, mid) * constructTree(arr, si * 2 + 2, mid + 1, se)) % P ;
        return st[si];
    }

    private static int getMid(int s, int e) {
        return s + (e - s) / 2;
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