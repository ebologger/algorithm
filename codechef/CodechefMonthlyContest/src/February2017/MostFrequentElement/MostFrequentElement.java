package February2017.MostFrequentElement;

import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;

/**
 * Created by enkhbold on 2/11/17.
 */
public class MostFrequentElement {

    public static void main(String[] args) throws IOException {
        FasterScanner scanner = new FasterScanner();
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        int[] A = new int[N];
        for(int i=0; i<N; i++)
            A[i] = scanner.nextInt();

        SegmentTree st = new SegmentTree(A, N);

        for(int i=0; i<M; i++){
            int L = scanner.nextInt();
            int R = scanner.nextInt();
            int k = scanner.nextInt();
            Range r = st.query(0, 0, N - 1, L-1, R-1);
            if(r.frequentCnt >= k)
                System.out.println(r.frequent);
            else{
                System.out.println(-1);
            }
        }

    }
}

class SegmentTree {
    private Range[] st;

    public SegmentTree(int arr[], int n) {
        int x = (int) (Math.ceil(Math.log(n) / Math.log(2)));
        int max_size = 2 * (int) Math.pow(2, x) - 1;
        st = new Range[max_size];
        construct(arr, 0, 0, n - 1);
    }

    public Range query(int si, int ss, int se, int qs, int qe) {
        if (qs <= ss && qe >= se)
            return st[si];

        if (se < qs || ss > qe)
            return null;

        int mid = getMid(ss, se);
        Range l = query(si * 2 + 1, ss, mid, qs, qe);
        Range r = query(si * 2 + 2, mid + 1, se, qs, qe);
        if(l == null)
            return r;
        if(r == null)
            return l;

        return l.join(r);
    }

    private Range construct(int arr[], int si, int ss, int se) {

        if (ss == se) {
            st[si] = new Range(arr[ss], 1, arr[ss], 1, arr[ss], 1, 1);
            return st[si];
        }

        int mid = getMid(ss, se);
        st[si] = construct(arr, si * 2 + 1, ss, mid).join(
                construct(arr, si * 2 + 2, mid + 1, se));
        return st[si];
    }

    private int getMid(int s, int e) {
        return s + (e - s) / 2;
    }
}

class Range{
    int left, leftCnt, right, rightCnt, frequent, frequentCnt, totalCnt;

    public Range(){

    }
    public Range(int left, int leftCnt, int right, int rightCnt, int frequent, int frequentCnt, int totalCnt){
        this.left = left;
        this.leftCnt = leftCnt;
        this.right = right;
        this.rightCnt = rightCnt;
        this.frequent = frequent;
        this.frequentCnt = frequentCnt;
        this.totalCnt = totalCnt;
    }

    public Range join(Range rightRange){
        Range range = new Range();

        range.totalCnt = this.totalCnt + rightRange.totalCnt;
        range.left = this.left;
        range.leftCnt = this.leftCnt;
        range.right = rightRange.right;
        range.rightCnt = rightRange.rightCnt;

        if(range.leftCnt + range.rightCnt == range.totalCnt && range.left == range.right){ // all the numbers in range are same
            range.leftCnt = range.totalCnt;
            range.rightCnt = range.totalCnt;
            range.frequentCnt = range.totalCnt;
            range.frequent = range.left;
            return range;
        }

        if(this.frequentCnt > rightRange.frequentCnt){
            range.frequentCnt = this.frequentCnt;
            range.frequent = this.frequent;
        }else{
            range.frequent = rightRange.frequent;
            range.frequentCnt = rightRange.frequentCnt;
        }

        if(this.right == rightRange.left){ // middle elements are same

            if(this.rightCnt == this.totalCnt){ // leftRange is same numbers
                range.leftCnt += rightRange.leftCnt;

                if(range.leftCnt > range.frequentCnt){
                    range.frequent = range.left;
                    range.frequentCnt = range.leftCnt;
                }
            }else if(rightRange.leftCnt == rightRange.totalCnt){ // rightRange same numbers
                range.rightCnt += this.rightCnt;

                if(range.rightCnt > range.frequentCnt){
                    range.frequent = range.right;
                    range.frequentCnt = range.rightCnt;
                }
            }else{
                if(this.rightCnt + rightRange.leftCnt > range.frequentCnt){
                    range.frequent = this.right;
                    range.frequentCnt = this.rightCnt + rightRange.leftCnt;
                }
            }
        }
        return range;
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