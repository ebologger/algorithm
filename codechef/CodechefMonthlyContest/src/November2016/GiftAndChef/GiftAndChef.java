package November2016.GiftAndChef;

import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;

public class GiftAndChef {
	
	public static final int MODULO = 1000000007;

	public static void main(String[] args) {
		FasterScanner scanner = new FasterScanner();
		int T = scanner.nextInt();
		while (T-- > 0) {
			char[] S = scanner.nextString().toCharArray();
			char[] F = scanner.nextString().toCharArray();
			
			long cnt = 0;
			int[] dp = new int[S.length];
			
			int[] lps = new int[F.length];
	        computeLPSArray(F,F.length,lps);
	        
	        int i = 0, j = 0;
	        while (i < S.length) {
	            if (F[j] == S[i]){
	                j++;
	                i++;
	            }
	            if (j == F.length){
	            	
	            	// pattern found
	            	cnt += 1;
					if(i-j-1 >= 0)
						cnt += dp[i-j-1];
					
	                j = lps[j-1];
	            }else if (i < S.length && F[j] != S[i]){
	                if (j != 0)
	                    j = lps[j-1];
	                else
	                    i = i+1;
	            }
	            
	            
	            if(cnt > MODULO)
					cnt -= MODULO;
				dp[i-1] = (int) cnt;
	        }
			
			System.out.println(cnt);
			
		}
	}
	
	public static void computeLPSArray(char[] pat, int M, int lps[])
    {
        int len = 0;
        int i = 1;
        lps[0] = 0;
 
        while (i < M){
            if (pat[i] == pat[len]){
                len++;
                lps[i] = len;
                i++;
            }else{
                if (len != 0){
                    len = lps[len-1];
                }else{
                    lps[i] = len;
                    i++;
                }
            }
        }
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