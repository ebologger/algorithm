package November2016.FriendsMeeting;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FriendsMeeting {

	public static void main(String[] args) {
		FasterScanner scanner = new FasterScanner();
		int T = scanner.nextInt();
		while (T-- > 0) {
			long N = scanner.nextInt();
			long M = scanner.nextInt();

			Set<Integer> A = new HashSet<>();
			int V,U,L;
			Graph g = new Graph();
			for(int i=1;i<N;i++){
				V = scanner.nextInt();
				U = scanner.nextInt();
				L = scanner.nextInt();

				Node vNode = g.addVertex(V);
				Node uNode = g.addVertex(U);
				g.addEdge(vNode,uNode,L);
			}
			for(int i=0;i<M;i++){
				A.add(scanner.nextInt());
			}

			if(M == 1){
				System.out.println("0 1");
				continue;
			}

			g.calcSumDistance(A);
			long gcd = gcd(g.sum * 2, M*M);
			g.sum = g.sum * 2 / gcd;
			System.out.println(g.sum + " " + M*M/gcd);
		}

	}

	public static long gcd(long a, long b)
	{
		for(;;) {
			if(a==0) return b;
			b%=a;
			if(b==0) return a;
			a%=b;
		}
	}

}

class Graph {
	Map<Integer, Node> nodes;
	long sum;

	public Graph() {
		nodes = new HashMap<>();
	}

	public Node addVertex(int vertex) {
		if (nodes.containsKey(vertex)) {
			return nodes.get(vertex);
		}
		Node n = new Node(vertex);
		nodes.put(vertex, n);
		return n;
	}

	public Edge addEdge(Node node1, Node node2, int weight) {
		Edge e = new Edge(node1, node2, weight);

		node1.addEdge(e);
		node2.addEdge(e);

		return e;
	}

	public void calcSumDistance(Set<Integer> A){
		Edge e = nodes.get(1).edges.get(0);

		sum = 0;

		long cnt1 = e.countMs(A, e.node1.vertex, this);
		long cnt2 = e.countMs(A, e.node2.vertex, this);

		sum += cnt1*cnt2*e.weight;
	}
}

class Node {
	int vertex;
	List<Edge> edges;

	public Node(int vertex) {
		this.vertex = vertex;
		this.edges = new ArrayList<>();
	}

	public void addEdge(Edge e) {
		edges.add(e);
	}
}

class Edge {
	Node node1, node2;
	int weight;

	public Edge(Node node1, Node node2, int weight) {
		this.node1 = node1;
		this.node2 = node2;
		this.weight = weight;
	}

	public long countMs(Set<Integer> A, int vertex, Graph g){
		long cnt = 0;
		if(node1.vertex == vertex){ // coming from node1
			if(A.contains(node2.vertex))
				cnt++;
			for(Edge e : node2.edges){
				if(e.node1.vertex == node1.vertex || e.node2.vertex == node1.vertex)
					continue;

				long subCnt = e.countMs(A, node2.vertex, g);

				g.sum += subCnt * (A.size() - subCnt) * e.weight;
				cnt += subCnt;
			}

		}else{ // coming from node2
			if(A.contains(node1.vertex))
				cnt++;
			for(Edge e : node1.edges){
				if(e.node1.vertex == node2.vertex || e.node2.vertex == node2.vertex)
					continue;

				long subCnt = e.countMs(A, node1.vertex, g);
				g.sum += subCnt * (A.size() - subCnt) * e.weight;
				cnt += subCnt;
			}
		}
		return cnt;
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