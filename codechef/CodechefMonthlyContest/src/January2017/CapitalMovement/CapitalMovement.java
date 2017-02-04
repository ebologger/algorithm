package January2017.CapitalMovement;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Set;

/**
 * Created by enkhbold on 1/10/17.
 */
public class CapitalMovement {

    public static void main(String[] args) {
        FasterScanner scanner = new FasterScanner();
        int T = scanner.nextInt();
        while (T-- > 0) {
            int N = scanner.nextInt();
            Planet[] P = new Planet[N];

            for(int i=0; i<N; i++)
                P[i] = new Planet(i+1, scanner.nextInt());

            for(int i=1; i<N; i++)
                P[scanner.nextInt()-1].addNeighbour(P[scanner.nextInt()-1]);

            Planet[] planetsUnsorted = P.clone();
            Arrays.sort(P);

            StringBuilder sb = new StringBuilder();
            for(int i=0; i<N; i++){
                sb.append(planetsUnsorted[i].solve(P));
                sb.append(' ');
            }
            System.out.println(sb.toString());
        }
    }
}

class Planet implements Comparable<Planet>{
    public int planetNo, population;
    public Set<Planet> neighbours;

    public Planet(int planetNo, int population){
        this.planetNo = planetNo;
        this.population = population;
        neighbours = new HashSet<>();
    }

    public void addNeighbour(Planet n){
        neighbours.add(n);
        n.neighbours.add(this);
    }

    @Override
    public int compareTo(Planet o) {
        return this.population - o.population;
    }

    public int solve(Planet[] P){
        for(int i=P.length-1; i>=0; i--)
            if(P[i].planetNo != this.planetNo && !neighbours.contains(P[i]))
                return P[i].planetNo;

        return 0;
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