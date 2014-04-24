import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MyVeryLongCake {

	public int cut(int n) {
		List<Integer> divisors = uniquePrimeDivisors(n);
		int total = 0;
		boolean positive = true;
		for(int i=1; i<=divisors.size();i++){
			List<List<Integer>> combination = calcCombination(divisors, i);
			
			for(List<Integer> c : combination){
				int m = 1;
				for(int j : c){
					m *= j;
				}
				if(positive){
					total += (n / m);
				}else{
					total -= (n / m);
				}
			}		
			if(positive){
				positive = false;
			}else{
				positive = true;
			}
			
		}

		
		return total;
	}

	public List<Integer> uniquePrimeDivisors(int number) {
		int n = number;
		int limit = (int)Math.sqrt(number)+1;
		List<Integer> divisors = new LinkedList<>();
		int i = 2;
		while(n > 1 && i <= limit){
			if (n % i == 0){
				divisors.add(i);
				while (n % i == 0) {
					n /= i;
				}
			}	
			i++;
		}
		if(n > 1){
			divisors.add(n);
		}
		return divisors;
	}
	
	public static <T> List<List<T>> calcCombination(List<T> values, int size) {

	    if (0 == size) {
	        return Collections.singletonList(Collections.<T> emptyList());
	    }

	    if (values.isEmpty()) {
	        return Collections.emptyList();
	    }

	    List<List<T>> combination = new LinkedList<List<T>>();

	    T actual = values.iterator().next();

	    List<T> subSet = new LinkedList<T>(values);
	    subSet.remove(actual);

	    List<List<T>> subSetCombination = calcCombination(subSet, size - 1);

	    for (List<T> set : subSetCombination) {
	        List<T> newSet = new LinkedList<T>(set);
	        newSet.add(0, actual);
	        combination.add(newSet);
	    }

	    combination.addAll(calcCombination(subSet, size));

	    return combination;
	}

}
