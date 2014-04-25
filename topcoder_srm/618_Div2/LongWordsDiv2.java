import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LongWordsDiv2 {

	public String find(String word) {
		
		int[] letters_count = new int[26];
		List<Character> potential = new LinkedList<>();
		char prevLetter = '-';
		for(int i=0; i<word.length(); i++){
			String substr = word.substring(0, i);
			if(word.charAt(i) > 'Z' || word.charAt(i) < 'A')
				return "Dislikes";
			if(prevLetter == word.charAt(i))
				return "Dislikes";
			prevLetter = word.charAt(i);
			
			letters_count[word.charAt(i) - 'A']++;
			if(letters_count[word.charAt(i) - 'A'] >= 4)
				return "Dislikes";
			
			if(letters_count[word.charAt(i) - 'A'] >= 2){
				
				if(potential.size() > 0){
					String[] parts = substr.split("" + word.charAt(i));
					
					for(Character c : potential){
						
						int cnt = 0;
						for(String part : parts){
							if(part.contains("" + c)){
								cnt++;
							}
						}
						
						if(cnt >= 2)
							return "Dislikes";
					}
				}
				potential.add(word.charAt(i));
			}
				
		}

		
		
		
		return "Likes";
	}

}
