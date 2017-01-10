import java.io.IOException;

public class ChefKey {

	public static void main(String[] args) throws IOException {
		java.io.BufferedReader r = new java.io.BufferedReader (new java.io.InputStreamReader (System.in));
		int T = Integer.valueOf(r.readLine());
		while(T-- > 0){
			String[] inArray = r.readLine().split(" ");
			int n = Math.min(Integer.valueOf(inArray[0]),Integer.valueOf(inArray[1]));
			int m = Math.max(Integer.valueOf(inArray[0]),Integer.valueOf(inArray[1]));
			int c = Integer.valueOf(inArray[2]);
			int cnt = 0;
			for(int i=n; i>0; i--){
				if(c % i == 0 && c / i <= m)
					cnt++;
				
			}
			System.out.println(cnt);
		}
	}
}