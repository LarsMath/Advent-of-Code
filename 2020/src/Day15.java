import java.util.HashMap;
import java.util.Map;

public class Day15 {

	public static void main(String[] args){

		long time = System.nanoTime();
		Map<Integer, Integer> lastSeen = new HashMap<>();

		lastSeen.put(0,1);
		lastSeen.put(8,2);
		lastSeen.put(15,3);
		lastSeen.put(2,4);
		lastSeen.put(12,5);
		lastSeen.put(1,6);

		int lastNumber = 4;

		for (int turn = 7; turn < 30000000; turn++){
			if(lastSeen.containsKey(lastNumber)){
				int oldPosition = lastSeen.get(lastNumber);
				lastSeen.put(lastNumber, turn);
				lastNumber = turn - oldPosition;
			} else {
				lastSeen.put(lastNumber, turn);
				lastNumber = 0;
			}
		}
		System.out.println(lastNumber);

		System.out.println((System.nanoTime() - time)/1000000);
	}
}
