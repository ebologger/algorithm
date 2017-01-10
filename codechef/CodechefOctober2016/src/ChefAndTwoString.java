import java.io.IOException;

public class ChefAndTwoString {
	
	public static final int MODULO = 1000000007;

	public static void main(String[] args) throws IOException {
		java.io.BufferedReader r = new java.io.BufferedReader (new java.io.InputStreamReader (System.in));
		int T = Integer.valueOf(r.readLine());
		while(T-- > 0){
			String a = r.readLine();
			String b = r.readLine();
			Calc(a,b);
		}

	}
	
	public static void Calc(String a, String b){
		
		// N=2,3 ved svvliin orongoos busad ni 2 baij bolohgvi
		if(a.length() == 2){
			if(a.startsWith("1") && b.startsWith("1")){
				System.out.println(4);
			}else{
				System.out.println(0);
			}
			return;
		}
		
		if(a.length() == 3){
			if(a.startsWith("11") && b.startsWith("11")){
				System.out.println(8);
			}else{
				System.out.println(0);
			}
			return;
		}
		
		// Svvleesee 2 dahi oron 2 baij bolohgvi
		if(a.length() > 1 && (a.charAt(a.length() - 2) == '2' || b.charAt(b.length() - 2) == '2')){
			System.out.println(0);
			return;
		}
		
		long numberOfCombination = 2; // svvliin too hed ch baij bolno.
		for(int i=0; i< a.length() - 1; i++){
			if(a.charAt(i) == '2' || b.charAt(i) == '2'){
				
				int countOfContinuousTwo = 0;
				int countOfTwo = 0;
				int j = i;
				while (a.charAt(j) == '2' || b.charAt(j) == '2'){
					countOfContinuousTwo++;
					if (a.charAt(j) == '2')
						countOfTwo++;
					
					if(b.charAt(j) == '2')
						countOfTwo++;
					
					j++;			
				}
				
				if(countOfContinuousTwo == 1){
					System.out.println(0);
					return;
				}
				
				if(countOfTwo % 2 != 0){
					System.out.println(0);
					return;
				}
				
				char[] min_array = convertToMin(a,b,i,j);
				if (countOfContinuousTwo > 2){					
					// ehlel buguud tugsgul ni davhar 2 baij bolohgvi
					if(min_array[0] == '2' || min_array[countOfContinuousTwo - 1] == '2'){
						System.out.println(0);
						return;
					}
					
					for(int k=1; k < countOfContinuousTwo - 1; k++){
						if(min_array[k] == '2'){
							if( min_array[k-1] == '2' || k >= 2 && min_array[k-2] == '2'){
								System.out.println(0);
								return;
							}
							if( min_array[k+1] == '2' || k < countOfContinuousTwo - 2 && min_array[k+2] == '2'){
								System.out.println(0);
								return;
							}
							
							numberOfCombination = (numberOfCombination * 2) % MODULO;
						}
					}
				}else{
					if(min_array[0] == '2' && min_array[1] == '2'){
						numberOfCombination = (numberOfCombination * 2) % MODULO;
					}
				}
				
				i = j - 1;
				
				numberOfCombination = (numberOfCombination * 2) % MODULO;
			}else{
				numberOfCombination = (numberOfCombination * 2) % MODULO;
			}
		}
		
		System.out.println(numberOfCombination);
	}
	
	public static char[] convertToMin(String a, String b, int startIdx, int endIdx){
		char[] converted = new char[endIdx - startIdx];
		for(int i=startIdx; i< endIdx; i++){
			if(a.charAt(i) == '1' || b.charAt(i) == '1')
				converted[i - startIdx] = '1';
			else
				converted[i - startIdx] = '2';
		}
		return converted;
	}

}