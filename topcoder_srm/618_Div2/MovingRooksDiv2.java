//Not finished
public class MovingRooksDiv2 {

	public String move(int[] Y1, int[] Y2) {
		int N = Y1.length;
		int[] distance = new int[N];
		
		for(int i=0; i<N; i++){
			for(int j=0; j<N; j++){
				if(Y1[i] == Y2[j])
					distance[Y1[i]] = j - i;
			}
		}
		
		if (!checkValidDistance(N, distance))
			return "Impossible";
		
		while(!checkCompleted(N, distance)){
			int tmp = 0;
			for(int i=0; i<N; i++){
				tmp += distance[i];
				if(tmp > 0){
					
				}
			}
		}
		
		return "Possible";
	}
	
	public boolean checkValidDistance(int N, int[] distance){
		int tmp = 0;
		for(int i=N-1; i>=0; i--){
			tmp += distance[i];
			if(tmp < 0)
				return false;			
		}
		return true;
	}
	
	public boolean checkCompleted(int N, int[] distance){
		for(int i=0; i<N; i++){
			if(distance[i] != 0)
				return false;			
		}
		return true;
	}

}
