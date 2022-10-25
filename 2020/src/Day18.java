import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Day18 {

	public static void main(String[] args) throws FileNotFoundException {

		Scanner sc = new Scanner(new File("2020/Input/18.txt"));

		long resultA = 0;
		long resultB = 0;

		while(sc.hasNextLine()){
			char[] line = sc.nextLine().replace(" ", "").toCharArray();
			resultB += evaluateExpression(line);
		}
		System.out.println("ResultA: " + resultA);
		System.out.println("ResultB: " + resultB);
	}

	public static long evaluateExpression(char[] expression){
		ArrayDeque<Long> factors = new ArrayDeque<>();
		factors.push(1L);
		char lastOperation = '*';
		for(int i = 0; i < expression.length; i++){
			char nextChar = expression[i];
			if(Character.isDigit(nextChar)) {
				long intermediate = Character.getNumericValue(nextChar);
				if (lastOperation == '+') {
					intermediate += factors.pop();
					lastOperation = '*';
				}
				factors.push(intermediate);
			} else if (nextChar == '('){
				int openBrackets = 1;
				int j = i + 1;
				while (openBrackets != 0){
					if(expression[j] == '('){
						openBrackets++;
					}
					if(expression[j] == ')'){
						openBrackets--;
					}
					j++;
				}
				long intermediate = evaluateExpression(Arrays.copyOfRange(expression, i + 1, j - 1));
				i = j - 1;
				if (lastOperation == '+') {
					intermediate += factors.pop();
					lastOperation = '*';
				}
				factors.push(intermediate);
			} else {
				lastOperation = nextChar;
			}
		}
		long result = 1;
		for(long factor : factors){
			result *= factor;
		}
		return result;
	}
}
