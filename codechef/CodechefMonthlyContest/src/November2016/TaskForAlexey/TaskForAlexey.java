package November2016.TaskForAlexey;


import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.InputMismatchException;

public class TaskForAlexey {

	public static void main(String[] args) {
		FasterScanner scanner = new FasterScanner();
		int T = scanner.nextInt();
		while (T-- > 0) {
			int N = scanner.nextInt();
			long[] A = new long[N];
			Arrays.sort(A);
			for(int i=0; i<N; i++)
				A[i] = scanner.nextLong();
			
			long min_lcm = Long.MAX_VALUE;
			for(int i=0; i<N-1; i++){
				for(int j=i+1; j<N; j++){
					long lcm = lcm(A[i],A[j],min_lcm);
					min_lcm = Math.min(min_lcm, lcm);
				}
			}
			System.out.println(min_lcm);
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
	
	public static long lcm(long a, long b, long min)
	{
		if(a >= min || b >= min)
			return min;
		
		long tmp=gcd(a,b);
		return tmp!=0 ? (a/tmp * b):0;
		
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