import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day13 {

	public static void main(String[] args) throws FileNotFoundException {

		Scanner sc = new Scanner(new File("2020/Input/13.txt"));
		sc.useDelimiter(",");

		ArrayList<Integer> busIDS = new ArrayList<>();

		while(sc.hasNext()){
			String addition = sc.next();
			if(addition.equals("x")){
				busIDS.add(1);
			} else {
				busIDS.add(Integer.parseInt(addition));
			}
		}

		System.out.println(busIDS.toString());

		long characteristic = 1;
		long result = 0;

		long startTime = System.nanoTime();

		for (int i = 0; i < busIDS.size(); i++){
			int busID = busIDS.get(i);
			while(((-i % busID) + busID) % busID != result % busID){
				result += characteristic;
			}
			characteristic *= busID;
		}
		System.out.println(result);

		System.out.println(System.nanoTime() - startTime);

	}

}
