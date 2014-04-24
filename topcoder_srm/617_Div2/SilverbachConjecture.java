public class SilverbachConjecture {

	public int[] solve(int n) {
		for(int i=4; i<=n/2; i++){
			if(isComposite(i) && isComposite(n-i)){
				return new int[]{i, n-i};
			}
		}
		return null;
	}
	
	public boolean isComposite(int n){
		if (n < 4)
			return false;
		
		for(int i=2; i<=n/2; i++){
			if (i * (n/i) == n)
				return true;
		}
		return false;
	}

}
