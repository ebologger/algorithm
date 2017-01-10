package December2016.KthMaxSubarray;

/**
 * Created by enkhbold on 12/9/16.
 */
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class KthMaxSubarray {

    public static void main(String[] args) {
        FasterScanner scanner = new FasterScanner();
        int T = scanner.nextInt();
        while (T-- > 0) {
            int N = scanner.nextInt();
            int M = scanner.nextInt();
            long[] A = new long[N];
            for(int i=0; i<N; i++)
                A[i] = scanner.nextLong();
            KthMaxSubarray m = new KthMaxSubarray(A);

            for(int i=0; i<M; i++)
                m.solve(scanner.nextLong());

        }
    }

    private long[] A;
    private List<SubArrayGroup> groups;
    private Integer[] sortedIdx;
    private List<int[]> slices;

    public KthMaxSubarray(long[] A){
        this.A = A;
        sortedIdx = new Integer[A.length];
        for (int i = 0; i<A.length; i++)
            sortedIdx[i] = i;

        Arrays.sort(sortedIdx, new Comparator<Integer>() {
            public int compare(Integer i1, Integer i2) {
                if(i1 == i2)
                    return 0;
                else if(A[i1] > A[i2])
                    return -1;
                else if(A[i2] > A[i1])
                    return 1;
                else
                    return i1 > i2 ? 1 : -1;
            }
        });

        long k=1;
        groups = new ArrayList<>();
        slices = new ArrayList<>();
        slices.add(new int[]{0,A.length - 1});


        for(int i=0; i<A.length;){
            int si = 0;
            long curr = A[sortedIdx[i]];
            long cnt = 0;

            while(i<A.length && A[sortedIdx[i]] == curr){
                int sliceIdx = findSlice(sortedIdx[i], si, slices.size() - 1);
                int[] slice = slices.get(sliceIdx);
                cnt += 1l * (sortedIdx[i] - slice[0] + 1) * (slice[1] - sortedIdx[i] + 1);

                if(slice[0] == slice[1])
                    slices.remove(sliceIdx);
                else if(sortedIdx[i] == slice[0])
                    slices.set(sliceIdx, new int[]{slice[0] + 1, slice[1]});
                else if(sortedIdx[i] == slice[1]){
                    slices.set(sliceIdx, new int[]{slice[0], slice[1] - 1});
                    sliceIdx++;
                }else{
                    slices.set(sliceIdx,new int[]{slice[0], sortedIdx[i] - 1}); // split slice into 2 slice
                    slices.add(++sliceIdx, new int[]{sortedIdx[i] + 1,slice[1]});
                }
                si = sliceIdx;

                i++;
            }

            groups.add(new SubArrayGroup(k, k + cnt - 1, A[sortedIdx[i-1]]));
            k += cnt;
        }
    }



    public void solve(long p){
        SubArrayGroup g = findGroup(p, 0, groups.size() - 1);
        System.out.println(g.max);
    }

    public SubArrayGroup findGroup(long p, int si, int ei){
        int mid = si + (ei - si) / 2;
        SubArrayGroup g = groups.get(mid);
        if(g.i <= p && g.j >= p)
            return g;
        else if(g.i > p)
            return findGroup(p, si, mid - 1);
        else
            return findGroup(p, mid + 1, ei);
    }

    public int findSlice(int i, int si, int ei){
        int mid = si + (ei - si) / 2;
        int[] s = slices.get(mid);
        if(s[0] <= i && s[1] >= i)
            return mid;
        else if(s[0] > i)
            return findSlice(i, si, mid - 1);
        else
            return findSlice(i, mid + 1, ei);
    }

}

class SubArrayGroup {
    long i, j;
    long max;
    public SubArrayGroup(long i, long j, long max){
        this.i = i;
        this.j = j;
        this.max = max;
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