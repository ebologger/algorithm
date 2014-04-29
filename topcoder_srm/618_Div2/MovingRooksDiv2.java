import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

//Not finished
public class MovingRooksDiv2 {

	public String move(int[] Y1, int[] Y2) {
		int N = Y1.length;
		
		Set<String> visited = new HashSet<>();
		
		if(checkPossible(N, Y1, Y2, visited)){
			return "Possible";
		}
		
		return "Impossible";
	}
	
	public boolean checkPossible(int N, int[] Y1, int[] Y2, Set<String> visited){
		
		if(Arrays.equals(Y1, Y2))
			return true;
		
		visited.add(Arrays.toString(Y1));
		
		System.out.println(Arrays.toString(Y1));
				
		for(int i=0;i<N-1;i++){
			for(int j=i+1; j<N;j++){
				if(Y1[i] > Y1[j]){
					int[] Y1_Copy = Arrays.copyOf(Y1, N);
					int tmp = Y1_Copy[i];
					Y1_Copy[i] = Y1_Copy[j];
					Y1_Copy[j] = tmp;
					if(!visited.contains(Arrays.toString(Y1_Copy)))
						if(checkPossible(N,Y1_Copy,Y2, visited))
							return true;
				}
			}
		}
		
		
		return false;
	}
	
}
