import java.io.IOException;

public class FenwickIteration {

	public static void main(String[] args)  throws IOException {
		
		java.io.BufferedReader r = new java.io.BufferedReader (new java.io.InputStreamReader (System.in));
		int T = Integer.valueOf(r.readLine());
		while(T-- > 0){
			String[] inArray = r.readLine().split(" ");
			String L1 = inArray[0];
			String L2 = inArray[1];
			String L3 = inArray[2];
			int N = Integer.parseInt(inArray[3]);
			int cnt = 1;
			
			if(L3.lastIndexOf('0') >= 0){ // L3 contains last 0
				String s = L3.substring(0, L3.lastIndexOf('0'));
				cnt += countOccurrencesOf(s,"1");
				cnt += countOccurrencesOf(L2,"1") * N;
				cnt += countOccurrencesOf(L1,"1");
			}else if(L2.lastIndexOf('0') >= 0){ // L2 contains last 0
				String s = L2.substring(0, L2.lastIndexOf('0'));
				cnt += countOccurrencesOf(s,"1");
				cnt += countOccurrencesOf(L2,"1") * (N-1);
				cnt += countOccurrencesOf(L1,"1");
			}else if(L1.lastIndexOf('0') >= 0){ // L1 contains last 0
				String s = L1.substring(0, L1.lastIndexOf('0'));
				cnt += countOccurrencesOf(s,"1");
			}
			
			System.out.println(cnt);
		}
	}
	
	public static int countOccurrencesOf(String s, String s2){
		return s.length() - s.replace(s2,"").length();
	}

}
