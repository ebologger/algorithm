package February2017.ChefAndYoda;

import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;

/**
 * Created by enkhbold on 2/15/17.
 */
public class ChefAndYoda {

    private final static double log2 = Math.log(2);
    private static double[] logs = new double[1000001];

    public static void main(String[] args) throws IOException {
        FasterScanner scanner = new FasterScanner();
        int T = scanner.nextInt();
        while (T-- > 0) {
            int N = scanner.nextInt();
            int M = scanner.nextInt();
            int P = scanner.nextInt();
            int K = scanner.nextInt();

            boolean canChefWonGame1 = canChefWon(N, M, 0);
            boolean canChefWonGame2 = canChefWon(N, M, 1);

            if(P == 0 || canChefWonGame1 && canChefWonGame2)
                System.out.println("1.000000");
            else if(!canChefWonGame1 && !canChefWonGame2)
                System.out.println("0.000000");
            else{
                System.out.printf ("%.6f%n", solve(P, K));
            }
        }
    }

    public static double solve(int P, int K){
        double res = 0;

        double tmp  =0;
        tmp -= K * log2;
        res += Math.exp(tmp);

        for(int i = 1; i<P; i++){
            tmp -= log(i);
            tmp += log(K - i + 1);
            res += Math.exp(tmp);
        }
        return 1 - res;
    }

    public static double log(int x){
        if(logs[x] > 0)
            return logs[x];

        logs[x] = Math.log(x);
        return logs[x];
    }

    public static boolean canChefWon(int N, int M, int rule){
        return rule == 0 && (N % 2 == 0 || M % 2 == 0) || rule == 1 && (N % 2 == 0 && M % 2 == 0);
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