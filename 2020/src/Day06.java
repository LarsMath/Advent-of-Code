public class Day06 {

	public static void main(String[] args){

		int resultA = 0;
		int resultB = 0;

		String[] allAnswers = InputParser.readStrings("2020/Input/06.txt", "\n\n");

		for(String group : allAnswers){
			int[] affirmatives = new int[26];
			String[] persons = group.split("\n");
			for(String person : persons) {
				for(char answer : person.toCharArray()){
					int letter = answer - 97;
					if(letter >=0 && letter < 26){
						affirmatives[letter]++;
					}
				}
			}
			for(int amount : affirmatives){
				if(amount > 0){
					resultA++;
				}
				if(amount == persons.length){
					resultB++;
				}
			}
		}
		System.out.println(resultA);
		System.out.println(resultB);
	}

}
