public class Day09 {

	public static void main(String[] args){

		Long[] input = InputParser.readLongs("2020/Input/09.txt","\n");
		boolean[] checks = new boolean[input.length];
		for(int i = 0; i < 25; i++){
			checks[i] = true;
		}
		long checkSum = 0;
		for(int i = 0; i < input.length; i++){
			for(int j = i; j < i + 25 && j < input.length; j++){
				checkSum = input[i] + input[j];
				for(int k = j; k < i + 26 && k < input.length; k++){
					if(checkSum == input[k]){
						checks[k] = true;
					}
				}
			}
		}
		int resultAindex = 0;
		while(checks[resultAindex]){
			resultAindex++;
		}
		System.out.println(input[resultAindex]);

		System.out.println(input.length);

		long sum = 0;
		int lowerHead = 0;
		int upperHead = 0;
		while(sum != input[resultAindex]){
			if(sum < input[resultAindex]){
				sum += input[upperHead];
				upperHead++;
			} else{
				sum -= input[lowerHead];
				lowerHead++;
			}
		}
		long smallest = input[lowerHead];
		long biggest = input[lowerHead];
		for(int i = lowerHead; i < upperHead; i++){
			if(input[i]< smallest){
				smallest = input[i];
			}
			if(input[i]> biggest){
				biggest = input[i];
			}
		}


		System.out.println(smallest + biggest);
	}

}
