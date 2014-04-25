public class WritingWords {

	public int write(String word) {
		int count = 0;
		for(int i = 0; i<word.length(); i++){
			count += (word.charAt(i) - 'A' + 1);
		}
		return count;
	}

}
