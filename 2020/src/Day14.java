import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day14 {

	public static void main(String[] args) throws FileNotFoundException {

		ArrayList<Long> resultA = new ArrayList<>();
		ArrayList<Long> addressesA = new ArrayList<>();

		ArrayList<Long> resultB = new ArrayList<>();
		ArrayList<ArrayList<Long>> addressesB = new ArrayList<>();

		Scanner sc = new Scanner(new File("2020/Input/14.txt"));

		char[] mask = null;
		int linenumber = 1;
		while(sc.hasNext()){
			String[] line = sc.nextLine().split(" ");
			System.out.println("Line: " + linenumber);
			linenumber++;
			if(line[0].equals("mask")){
				mask = line[2].toCharArray();
			} else{
				long address = Long.parseLong(line[0].replaceAll("(m|e|\\[|])", ""));
				long input =  Long.parseLong(line[2]);

				long maskedInput = calculateMaskedInput(mask, input);
				int previousAddress = addressesA.indexOf(address);
				if(previousAddress != -1){
					resultA.remove(previousAddress);
					addressesA.remove(previousAddress);
				}
				addressesA.add(address);
				resultA.add(maskedInput);

				resultB.add(input);
				ArrayList<Long> newAddresses = calculateMaskedAddresses(mask, binaryPadded(address));
				for(long check : newAddresses){
					for(ArrayList<Long> subList : addressesB){
						previousAddress = subList.indexOf(check);
						if(previousAddress != -1){
							subList.remove(previousAddress);
						}
					}
				}
				addressesB.add(newAddresses);
			}
		}
		long totalA = 0;
		for(long i : resultA){
			totalA += i;
		}
		System.out.println(totalA);

		long totalB = 0;
		for(int i = 0; i < resultB.size(); i++){
			totalB += resultB.get(i) * addressesB.get(i).size();
		}
		System.out.println(totalB);
	}

	public static char[] binaryPadded(long input){
		char[] result = new char[36];
		char[] unpaddedInput = Long.toBinaryString(input).toCharArray();
		for(int i = 0; i < 36; i++){
			if(i < unpaddedInput.length){
				result[35 - i] = unpaddedInput[unpaddedInput.length - 1 - i];
			} else {
				result[35 - i] = '0';
			}
		}
		return result;
	}

	public static long calculateMaskedInput(char[] mask, long input){
		long result = 0;
		for(int i = 35; i >= 0; i--){
			if(mask[35-i] == 'X'){
				long nextBit = ((input / ((long)Math.pow(2,i))) % 2);
				result += (long)Math.pow(2,i)*nextBit;
			} else {
				result += (long)Math.pow(2,i)*(mask[35-i] - '0');
			}
		}
		return result;
	}

	public static ArrayList<Long> calculateMaskedAddresses(char[] mask, char[] address){
		ArrayList<Long> addresses = new ArrayList<>();
		boolean noX = true;
		for(int i = 0; i < mask.length; i++){
			if(mask[i] == 'X'){
				char[] mask0 = mask.clone();
				char[] address0 = address.clone();
				char[] address1 = address.clone();
				mask0[i] = '0';
				address0[i] = '0';
				address1[i] = '1';
				addresses.addAll(calculateMaskedAddresses(mask0, address0));
				addresses.addAll(calculateMaskedAddresses(mask0, address1));
				noX = false;
				break;
			} else if (mask[i] == '1'){
				address[i] = '1';
			}
		}
		if(noX){
			addresses.add(charToLong(address));
		}
		return addresses;
	}

	public static long charToLong(char[] address){
		long result = 0;
		for(int i = 0; i < address.length; i++){
			result += (long)Math.pow(2,i) * (address[address.length - 1 - i] - '0');
		}
		return result;
	}
}
