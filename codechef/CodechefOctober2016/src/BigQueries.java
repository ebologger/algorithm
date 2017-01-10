import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;

public class BigQueries {

	public static void main(String[] args) throws IOException {
		FasterScanner scanner = new FasterScanner();
		int T = scanner.nextInt();
		while (T-- > 0) {
			int N = scanner.nextInt();
			int M = scanner.nextInt();
			int[] arr = new int[N];
			for (int i = 0; i < N; i++) {
				arr[i] = scanner.nextInt();
			}
			SegmentTree st2 = new SegmentTree(arr, N, 2);
			SegmentTree st5 = new SegmentTree(arr, N, 5);
			
			int solution = 0;
			while (M-- > 0) {
				int cmd = scanner.nextInt();
				int L = scanner.nextInt() - 1; // 0 based array
				int R = scanner.nextInt() - 1;

				if (cmd == 1) {
					int X = scanner.nextInt();
					st2.command1(N, L, R, X);
					st5.command1(N, L, R, X);
				} else if (cmd == 2) {
					int Y = scanner.nextInt();
					st2.command2(N, L, R, Y);
					st5.command2(N, L, R, Y);
				} else {
					int val2 = st2.command3(N, L, R);
					int val5 = st5.command3(N, L, R);
					solution += Math.min(val2, val5);
				}
			}
			System.out.println(solution);
		}

	}
}

class SegmentTree {
	int type; // 2 or 5
	int st[]; // segment tree
	int lazy1[]; // stores mulitpleOf(X,type)
	int lazy2[][]; // stores [Y,L]

	SegmentTree(int arr[], int n, int type) {
		this.type = type;

		int x = (int) (Math.ceil(Math.log(n) / Math.log(2)));
		int max_size = 2 * (int) Math.pow(2, x) - 1;
		st = new int[max_size];
		lazy1 = new int[max_size];
		lazy2 = new int[max_size][2];

		constructTree(arr, 0, 0, n - 1);
	}

	void command1(int n, int L, int R, int X) {
		command1(0, 0, n - 1, L, R, X);
	}

	void command2(int n, int L, int R, int Y) {
		command2(0, 0, n - 1, L, R, Y);
	}

	int command3(int n, int L, int R) {
		return command3(0, 0, n - 1, L, R);
	}

	void command1(int si, int ss, int se, int L, int R, int X) {
		
		// out of range
		if (ss > se || ss > R || se < L)
			return;
		
		// bvhleeree bagtaj baival
		if (ss >= L && se <= R) {
			if(lazy2[si][0] > 0)
				get(si, ss, se);
			
			lazy1[si] += multipleOf(X, type);
			return;
		}
		
		get(si, ss, se);

		int mid = getMid(ss,se);
		
		command1(si * 2 + 1, ss, mid, L, R, X);
		command1(si * 2 + 2, mid + 1, se, L, R, X);

		st[si] = get(si * 2 + 1, ss, mid) + get(si * 2 + 2, mid + 1, se);
	}

	private void command2(int si, int ss, int se, int L, int R, int Y) {

		// out of range
		if (ss > se || ss > R || se < L)
			return;

		// bvhleeree bagtaj baival
		if (ss >= L && se <= R) {
			lazy1[si] = 0;
			
			lazy2[si][0] = Y;
			lazy2[si][1] = L;
			return;
		}
		
		get(si, ss, se);
		
		int mid = getMid(ss,se);
		command2(si * 2 + 1, ss, mid, L, R, Y);
		command2(si * 2 + 2, mid + 1, se, L, R, Y);
		
		st[si] = get(si * 2 + 1, ss, mid) + get(si * 2 + 2, mid + 1, se);
	}

	private int command3(int si, int ss, int se, int L, int R) {
		
		if (ss > se || ss > R || se < L)
			return 0;

		int ret = get(si, ss, se);
		
		if (L <= ss && R >= se)
			return ret;

		

		int mid = getMid(ss, se);

		return command3(si * 2 + 1, ss, mid, L, R) + command3(si * 2 + 2, mid + 1, se, L, R);
	}
	
	private int constructTree(int arr[], int si, int ss, int se) {
		if (ss == se) {
			st[si] = multipleOf(arr[ss], type);
			return st[si];
		}

		int mid = getMid(ss, se);
		st[si] = constructTree(arr, si * 2 + 1, ss, mid) + constructTree(arr, si * 2 + 2, mid + 1, se);
		return st[si];
	}
	
	// pending vildlvvdee hiigeed tuhain segmentiin jinhene utgiig butsaana
	private int get(int si, int ss, int se) {
		updateLazies(si, ss, se);
		return st[si];
	}
	
	// pending vildlvvdee hiine. child node-uud baival lazy bolgono.
	private void updateLazies(int si, int ss, int se) {
		if (lazy1[si] > 0) {
			st[si] += calculateLazy1(si, ss, se);

			if (ss != se)
				transferLaziesToChild1(si, ss, se, lazy1[si]);
			
			lazy1[si] = 0;
		}

		if (lazy2[si][0] > 0) {
			st[si] = (int) calculateLazy2(si, ss, se);

			if (ss != se){
				int[] lazy = {lazy2[si][0] , lazy2[si][1]};
				transferLaziesToChild2(si, lazy);
			}				
			
			lazy2[si][0] = 0;
			lazy2[si][1] = 0;
		}
	}

	// parent deerh lazy utga calculate hiigdsenii daraa chld-uudiig lazy bolgoh
	private void transferLaziesToChild1(int si, int ss, int se, int lazy) {
		
		if(lazy2[si * 2 + 1][0] > 0)
			updateLazies(si * 2 + 1, ss, getMid(ss, se));
		
		if(lazy2[si * 2 + 2][0] > 0)
			updateLazies(si * 2 + 2, getMid(ss, se) + 1, se);

		lazy1[si * 2 + 1] += lazy;
		lazy1[si * 2 + 2] += lazy;
	}

	// parent deerh lazy utga calculate hiigdsenii daraa chld-uudiig lazy bolgoh
	private void transferLaziesToChild2(int si, int[] lazy) {
		lazy2[si * 2 + 1] = lazy;
		lazy1[si * 2 + 1] = 0;
		lazy2[si * 2 + 2] = lazy;
		lazy1[si * 2 + 2] = 0;
	}

	// Endees garch irsen utgaar segment-iin utgiig nemegdvvlne
	// += (SE - SS + 1) * MultipleOf(X)
	private int calculateLazy1(int si, int ss, int se) {
		return (se - ss + 1) * lazy1[si];
	}

	// Endees garch irsen utgaar segment-iin utgiig replace hiine
	// = MultipleOf( (SE-L+1)! / (SS - L)! * Power(Y, SE-SS+1) )
	private long calculateLazy2(int si, int ss, int se) {
		long cnt = multipleOf(lazy2[si][0], type) * (se - ss + 1);
		cnt += multipleOfFactorial(se - lazy2[si][1] + 1, type);
		cnt -= multipleOfFactorial(ss - lazy2[si][1], type);
		return cnt;
	}

	// X ni Y-d heden udaa huvaagdah ve
	private static int multipleOf(long X, int Y) {
		if (X == 0)
			return 0;
		int cnt = 0;
		while (X % Y == 0) {
			cnt++;
			X /= Y;
		}
		return cnt;
	}

	// X! ni Y-d heden udaa huvaagdah ve
	private static int multipleOfFactorial(long X, int Y) {
		int cnt = 0;
		int tmp = Y;
		while (X / tmp >= 1) {
			cnt += X / tmp;
			tmp *= Y;
		}
		return cnt;
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