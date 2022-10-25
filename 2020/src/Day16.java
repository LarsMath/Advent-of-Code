import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day16 {

	public static void main(String[] args) throws FileNotFoundException {

		Scanner sc = new Scanner(new File("2020/Input/16.txt"));

		int resultA = 0;

		int[] lowerBound, lowerBreak, upperBreak, upperBound, ownTicket;
		lowerBound = new int[20];
		lowerBreak = new int[20];
		upperBreak = new int[20];
		upperBound = new int[20];
		ownTicket = new int[20];

		int ticketProperty = 0;
		boolean atNearbyTickets = false;

		boolean[][] notPossible = new boolean[20][20];

		while(sc.hasNextLine()){
			String line = sc.nextLine();
			if(line.isEmpty()){
				continue;
			}
			if(line.contains("your")) {
				ownTicket = parseLine(sc.nextLine());
				sc.nextLine();
				sc.nextLine();
				atNearbyTickets = true;
			} else if (!atNearbyTickets){
				line = line.replaceAll("[^\\d]", " ").replaceAll("\\s+"," ").trim();
				int[] bounds = parseLine(line);
				lowerBound[ticketProperty] = bounds[0];
				lowerBreak[ticketProperty] = bounds[1];
				upperBreak[ticketProperty] = bounds[2];
				upperBound[ticketProperty] = bounds[3];
				ticketProperty++;
			} else {
				int[] fields = parseLine(line);
				int validness = validTicket(fields);
				resultA += validness;
				if(validness == 0){
					for(int fieldIndex = 0; fieldIndex < 20; fieldIndex++){
						for(int property = 0; property < 20; property++){
							int value = fields[fieldIndex];
							if(value < lowerBound[property] || value > upperBound[property] || (
									value > lowerBreak[property] && value < upperBreak[property])){
								notPossible[property][fieldIndex] = true;
							}
						}
					}
				}
			}
		}

		System.out.println(resultA);
		for(int i = 0; i < 20; i++){
			for(boolean[] line : notPossible){
				int countFalse = 0;
				int index = 0;
				for (int j = 0; j < 20; j++){
					if(!line[j]){
						countFalse++;
						index = j;
					}
				}
				if(countFalse == 1){
					for(boolean[] changeLine : notPossible){
						if(!changeLine.equals(line)){
							changeLine[index] = true;
						}
					}
				}
			}
		}
		for(boolean[] line : notPossible){
			for (boolean part : line){
				if(part){
					System.out.print(0 + " ");
				} else {
					System.out.print(1 + " ");
				}
			}
			System.out.println();
		}

		long resultB = 1;
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 20; j++){
				if(!notPossible[i][j]){
					resultB *= ownTicket[j];
				}
			}
		}
		System.out.println(resultB);
	}


	public static int[] parseLine(String line){
		String[] stringNumbers = line.replaceAll(","," ").split(" ");
		int[] numbers = new int[stringNumbers.length];
		for(int i = 0; i < numbers.length; i++){
			numbers[i] = Integer.parseInt(stringNumbers[i]);
		}
		return numbers;
	}

	public static int validTicket(int[] fields){
		int result = 0;
		for(int field : fields){
			if(25 > field || field > 972){
				result += field;
			}
		}
		return result;
	}
}
