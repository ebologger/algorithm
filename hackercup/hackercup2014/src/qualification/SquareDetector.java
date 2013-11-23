package qualification;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SquareDetector {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int T,N,i,j;
		String[] input;
		BufferedReader br = null;
		BufferedWriter bw = null;
		
		try {
			br = new BufferedReader(new FileReader("input.txt"));
			bw = new BufferedWriter(new FileWriter("output.txt"));
			T = Integer.parseInt(br.readLine());
			
			for(i = 1; i<=T; i++){
				N = Integer.parseInt(br.readLine());
				input = new String[N];
				
				for(j=0;j<N;j++){
					input[j] = br.readLine();
				}
				bw.write("Case #" + i + ": " + checkSquare(input));
				bw.newLine();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				br.close();
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String checkSquare(String[] input){
		Integer startIdx, endIdx, length;
		
		for(int i=0; i<input.length; i++){
			if(input[i].indexOf("#") != -1){
				startIdx = input[i].indexOf("#");
				endIdx = input[i].lastIndexOf("#");
				length = endIdx - startIdx + 1;
				if (!checkLine(input[i], startIdx, endIdx)){
					return "NO";
				}
				for(int j=i+1; j<i+length; j++){
					if (!checkLine(input[j], startIdx, endIdx)){
						return "NO";
					}
				}
				for(int j=i+length; j<input.length; j++){
					if(input[j].indexOf("#") != -1){
						return "NO";
					}
				}
				return "YES";
			}			
		}
		
		return "NO";
	}
	
	public static boolean checkLine(String line, int startIdx, int endIdx){
		return line.indexOf("#") == startIdx && line.lastIndexOf("#") == endIdx 
				&& line.substring(startIdx, endIdx).indexOf(".") == -1;
	}

}
