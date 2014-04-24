public class SlimeXSlimonadeTycoon {

	public int sell(int[] morning, int[] customers, int stale_limit) {
		int N = morning.length;
		
		int[] stale_dt = new int[2*N];
		
		int total_sell = 0;
		
		for (int i=0; i<N; i++){
			stale_dt[i+stale_limit] = morning[i];
			for(int j=i+1; j<=i+stale_limit; j++){
				if(customers[i] > 0){
					int sell = Math.min(customers[i], stale_dt[j]);
					customers[i] -= sell;
					stale_dt[j] -= sell;
					total_sell += sell;
				}else{
					break;
				}
			}
		}
		
		return total_sell;
	}

}
