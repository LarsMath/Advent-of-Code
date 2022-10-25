import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day10 {


	public static void main(String[] args){

		Integer[] joltages = InputParser.readInts("2020/Input/10.txt","\n");
		ArrayList<Integer> joltagesList = new ArrayList<>();
		joltagesList.addAll(Arrays.asList(joltages));
		joltagesList.add(0);
		Collections.sort(joltagesList);
		joltagesList.add(joltagesList.get(joltagesList.size() - 1) + 3);

		int differenceTriple = 0;
		int lastJump = 0;
		long resultB = 1;

		for(int i = 0; i < joltages.length + 1; i++){
			if(joltagesList.get(i+1) - joltagesList.get(i) == 3){
				differenceTriple++;
				long times = countWays(joltagesList.subList(lastJump, i+1));
				resultB *= times;
				lastJump = i+1;
			}
		}
		int resultA = (joltages.length + 1 - differenceTriple) * differenceTriple;
		System.out.println(resultA);
		System.out.println(resultB);
	}

	public static long countWays(List<Integer> joltageList){
		long totalWays = 0;
		if(joltageList.size() <= 3){
			totalWays = 1;
		} else {
			totalWays += countWays(joltageList.subList(1, joltageList.size()));
			if(joltageList.get(3) - joltageList.get(0) == 3){
				totalWays += countWays(joltageList.subList(3, joltageList.size()));
			}
		}
		return totalWays;
	}
}
