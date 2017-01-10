import java.io.IOException;

public class ChefAndDogs {
	
	public static void main(String[] args) throws IOException {
		java.io.BufferedReader r = new java.io.BufferedReader (new java.io.InputStreamReader (System.in));
		int T = Integer.valueOf(r.readLine());
		while(T-- > 0){
			String[] inArray = r.readLine().split(" ");
			int s = Integer.valueOf(inArray[0]);
			int v = Integer.valueOf(inArray[1]);
			System.out.printf ("%.12f%n", s / (1.5 * v));
		}
	}

}
