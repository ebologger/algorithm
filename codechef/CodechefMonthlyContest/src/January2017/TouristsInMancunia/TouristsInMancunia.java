package January2017.TouristsInMancunia;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by enkhbold on 1/14/17.
 */
public class TouristsInMancunia {

    public static void main(String[] args) {
        FasterScanner scanner = new FasterScanner();
        int N = scanner.nextInt();
        int E = scanner.nextInt();

        Graph g = new Graph();
        for(int i=0;i<E;i++){
            Vertex v1 = g.addVertex(scanner.nextInt());
            Vertex v2 = g.addVertex(scanner.nextInt());
            g.addEdge(v1,v2);
        }

        if(g.hasEulerCircuit()){
            System.out.println("YES");
            for(Edge e : g.edges) {
                if (!e.visited)
                    g.visit(e, e.vertex1);

                System.out.println(e.vertex1.value + " " + e.vertex2.value);
            }


        }else{
            System.out.println("NO");
        }
    }
}

class Graph {
    Map<Integer, Vertex> vertexes;
    List<Edge> edges;
    int totalVisitedVertex;

    public Graph() {
        vertexes = new HashMap<>();
        edges = new LinkedList<>();
        totalVisitedVertex = 0;
    }

    public boolean hasEulerCircuit(){
        if(!DFS(vertexes.get(1)))
            return false;

        return totalVisitedVertex == vertexes.values().size();
    }

    public boolean DFS(Vertex v){

        if(v.edges.size() % 2 != 0)
            return false;

        totalVisitedVertex++;
        v.visited = true;

        for(Edge e : v.edges){
            Vertex u = e.otherOne(v);
            if(!u.visited)
                if(!DFS(u))
                    return false;
        }

        return true;
    }

    public void visit(Edge current, Vertex from){
        current.visited = true;

        if(current.vertex1.value != from.value)
            current.reverse();

        current.vertex1.edges.remove(current);
        current.vertex2.edges.remove(current);


        for(Edge e : current.vertex2.edges){
            if(!e.visited) {
                visit(e, current.vertex2);
                break;
            }
        }
    }

    public Vertex addVertex(int value) {
        if (vertexes.containsKey(value)) {
            return vertexes.get(value);
        }
        Vertex v = new Vertex(value);
        vertexes.put(value, v);
        return v;
    }

    public Edge addEdge(Vertex vertex1, Vertex vertex2) {
        Edge e = new Edge(vertex1, vertex2);

        vertex1.addEdge(e);
        vertex2.addEdge(e);

        edges.add(e);

        return e;
    }
}

class Vertex {
    int value;
    List<Edge> edges;
    boolean visited;

    public Vertex(int value) {
        this.value = value;
        this.edges = new LinkedList<>();
        visited = false;
    }

    public void addEdge(Edge e) {
        edges.add(e);
    }
}

class Edge {
    Vertex vertex1, vertex2;
    boolean visited;

    public Edge(Vertex vertex1, Vertex vertex2) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.visited = false;
    }

    public void reverse(){
        Vertex tmp = vertex1;
        vertex1 = vertex2;
        vertex2 = tmp;
    }

    public Vertex otherOne(Vertex v){
        return v.value == vertex1.value ? vertex2 : vertex1;
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