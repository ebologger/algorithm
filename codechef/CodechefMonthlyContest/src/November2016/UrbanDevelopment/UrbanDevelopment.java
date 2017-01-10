package November2016.UrbanDevelopment;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

public class UrbanDevelopment {
	public static void main(String[] args) throws IOException {
		FasterScanner scanner = new FasterScanner();
		int N = scanner.nextInt();
		
		int maxX = 0, maxY = 0; //to reduce segment tree size
		int x1,x2,y1,y2,isHorizontal;
		int[] sol = new int[N];
		long total = 0;
		int[][] roads = new int[N][5];
		Map<Integer,ArrayList<Integer>> SPH = new HashMap<>(); // Starting points for horizontal sweeping. key = x coordinate
		Map<Integer,ArrayList<Integer>> SPV = new HashMap<>();   // Starting points for vertical sweeping. key = y coordinate
		Map<Integer,ArrayList<Integer>> EPH = new HashMap<>(); // Starting points for horizontal sweeping. key = x coordinate
		Map<Integer,ArrayList<Integer>> EPV = new HashMap<>();   // Starting points for vertical sweeping. key = y coordinate
		
		Map<String,Integer> collisionDetection = new HashMap<>();
		
		for(int i=0; i<N; i++){
			roads[i][0] = x1 = scanner.nextInt();
			roads[i][1] = y1 = scanner.nextInt();
			roads[i][2] = x2 = scanner.nextInt();
			roads[i][3] = y2 = scanner.nextInt();
			roads[i][4] = isHorizontal = (y1 == y2 ? 1 : 0);
			
			// TODO sort x & y
			
			ArrayList<Integer> l = SPH.getOrDefault(x1, new ArrayList<Integer>());
			l.add(i);
			SPH.put(x1, l);
			
			l = SPV.getOrDefault(y1, new ArrayList<Integer>());
			l.add(i);
			SPV.put(y1, l);
			
			if(isHorizontal == 1){
				l = EPH.getOrDefault(x2, new ArrayList<Integer>());
				l.add(i);
				EPH.put(x2, l);
			}else{
				l = EPV.getOrDefault(y2, new ArrayList<Integer>());
				l.add(i);
				EPV.put(y2, l);
			}
			
			
			
			
			
			if(isHorizontal == 0){ //vertical
				maxY = Math.max(maxY, y2);
			}else{ // horizontal
				maxX = Math.max(maxX, x2);
			}
			
			if(collisionDetection.containsKey(x1 + "," + y1)){
				Integer j = collisionDetection.get(x1 + "," + y1);
				sol[j]--;
				sol[i]--;
				total -= 2;
			}else{
				collisionDetection.put(x1 + "," + y1, i);
			}
			
			if(collisionDetection.containsKey(x2 + "," + y2)){
				Integer j = collisionDetection.get(x2 + "," + y2);
				sol[j]--;
				sol[i]--;
				total -= 2;
			}else{
				collisionDetection.put(x2 + "," + y2, i);
			}
		}
		
		SegmentTree stVerticalSweep = new SegmentTree(maxX + 1);
		SegmentTree stHorizontalSweep = new SegmentTree(maxY + 1);
		
		//horizontal sweep
		List<Integer> toQuery = new ArrayList<>();
		for(int x=1; x<=maxX; x++){
			for(Integer i : SPH.getOrDefault(x,new ArrayList<Integer>())){
				if(roads[i][4] == 0){ // vertical road
					toQuery.add(i);
				}else{ // horizontal road
					stHorizontalSweep.updateValue(roads[i][1], 1);
					System.out.println("Update +1: " + Arrays.toString(stHorizontalSweep.st));
				}
			}
			
			for(Integer i : toQuery){
				int soll = stHorizontalSweep.query(roads[i][1], roads[i][3]);
				System.out.println("Query: " + soll);
				total += soll;
				sol[i] += soll;
			}
			toQuery.clear();
			
			for(Integer i : EPH.getOrDefault(x,new ArrayList<Integer>())){
				stHorizontalSweep.updateValue(roads[i][1], -1);
				System.out.println("Update -1: " + Arrays.toString(stHorizontalSweep.st));
			}
		}
		
		//vertical sweep
		toQuery.clear();
		
		for(int y=1; y<=maxY; y++){
			for(Integer i : SPV.getOrDefault(y,new ArrayList<Integer>())){
				if(roads[i][4] == 1){ // horizontal road
					toQuery.add(i);
				}else{ // vertical road
					stVerticalSweep.updateValue(roads[i][0], 1);
					System.out.println("UpdateVer +1: " + Arrays.toString(stVerticalSweep.st));
				}
			}
			
			for(Integer i : toQuery){
				int soll = stVerticalSweep.query(roads[i][0], roads[i][2]);
				System.out.println("QueryV: " + soll);
				total += soll;
				sol[i] += soll;
			}
			toQuery.clear();
			
			for(Integer i : EPV.getOrDefault(y,new ArrayList<Integer>())){
				stVerticalSweep.updateValue(roads[i][0], -1);
				System.out.println("UpdateVer -1: " + Arrays.toString(stVerticalSweep.st));
			}
		}
		
		OutputStream out = new BufferedOutputStream ( System.out );
		out.write( ("" + (total/2)).getBytes());
		out.write(("\n").getBytes());
		
		for(int i=0; i<N; i++){
			out.write( (sol[i] + " ").getBytes());
		}
		out.flush();
	}
}

class SegmentTree {
    int st[];
    int n;
    
    SegmentTree(int n){
    	this.n = n;
    	int x = (int) (Math.ceil(Math.log(n) / Math.log(2)));
		int max_size = 2 * (int) Math.pow(2, x) - 1;
		st = new int[max_size];
    }
    
    private void updateValue(int si, int ss, int se, int ui, int diff){
    	if (ui < ss || ui > se)
            return;
     
        st[si] = st[si] + diff;
        if (se != ss)
        {
            int mid = getMid(ss, se);
            updateValue(si * 2 + 1, ss, mid, ui, diff);
            updateValue(si * 2 + 2, mid + 1, se, ui, diff);
        }
	}
	
	void updateValue(int ui, int diff)  {
		updateValue(0, 0, n - 1, ui, diff);
	}
 
    
    private int query(int si, int ss, int se, int qs, int qe){
    	if (ss > se || ss > qe || se < qs)
            return 0;
 
        if (ss >= qs && se <= qe)
            return st[si];
 
        int mid = getMid(ss,se);
        return query(si * 2 + 1, ss, mid, qs, qe) +
        		query(si * 2 + 2, mid + 1, se, qs, qe);
    }
 
    int query(int qs, int qe){
        if (qs < 0 || qe > n - 1 || qs > qe){
            return -1;
        }
 
        return query(0, 0, n - 1, qs, qe);
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