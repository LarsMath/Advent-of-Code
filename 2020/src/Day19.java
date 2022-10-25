import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Day19 {

	public static final int VALUE_A = -1;
	public static final int VALUE_B = -2;

	public static void main(String args[]) throws FileNotFoundException {

		Scanner sc = new Scanner(new File("2020/Input/19.txt"));

		HashMap<Integer,ProductionRule> rules = new HashMap<>();

		while (sc.hasNextLine()){
			String line = sc.nextLine();
			if(line.isEmpty()){
				break;
			}
			ProductionRule rule = new ProductionRule(line);
			rules.put(rule.getLeftHandSide(), rule);
		}
		rules.remove(0);
		rules.remove(11);
		rules.remove(8);

		for (ProductionRule rule : rules.values()){
			rules.get(42).applyProductionRule(rule);
			rules.get(31).applyProductionRule(rule);
			for(ProductionRule updateRule : rules.values()){
				updateRule.applyProductionRule(rule);
			}
		}

		int partSize = rules.get(31).minLength();

		Set<ArrayList<Integer>> language42 = rules.get(42).getAlternatives();
		Set<ArrayList<Integer>> language31 = rules.get(31).getAlternatives();

		int resultA = 0;
		int resultB = 0;
		while(sc.hasNextLine()){
			char[] word = sc.nextLine().toCharArray();
			ArrayList<Integer> wordCode = new ArrayList<>();
			for(char letter : word){
				if(letter == 'a'){
					wordCode.add(VALUE_A);
				} else if (letter == 'b'){
					wordCode.add(VALUE_B);
				}
			}
			int length = word.length / partSize;
			int index = 0;
			while(index < length - 1 && language42.contains(wordCode.subList(index * partSize, (index + 1) * partSize))){
				index++;
			}
			if (index > length/2) {
				while (index < length && language31.contains(wordCode.subList(index * partSize, (index + 1) * partSize))) {
					index++;
				}
			}
			if(index == length){
				resultB++;
				if(length == 3){
					resultA++;
				}
			}
		}
		System.out.println(resultA);
		System.out.println(resultB);
	}

	public static class ProductionRule{

		private int leftHandSide;
		private Set<ArrayList<Integer>> alternatives;

		public ProductionRule(String rule){
			alternatives = new HashSet<>();
			String[] decomposition = rule.replaceAll(":", " :").split(" ");
			leftHandSide = Integer.parseInt(decomposition[0]);
			ArrayList<Integer> alternative = new ArrayList<>();
			for(int i = 2; i < decomposition.length; i++){
				if(decomposition[i].matches("\\d+")){
					alternative.add(Integer.parseInt(decomposition[i]));
				} else if (decomposition[i].contains("a")){
					alternative.add(VALUE_A);
				} else if(decomposition[i].contains("b")){
					alternative.add(VALUE_B);
				} else {
					alternatives.add(alternative);
					alternative = new ArrayList<>();
				}
			}
			alternatives.add(alternative);
		}

		public ProductionRule(int leftHandSide, Set<ArrayList<Integer>> alternatives){
			this.leftHandSide = leftHandSide;
			this.alternatives = alternatives;
		}

		public ProductionRule(int leftHandSide){
			this.leftHandSide = leftHandSide;
			alternatives = new HashSet<>();
		}

		public String toString(){
			String result = leftHandSide + ": ";
			for(ArrayList<Integer> alternative : alternatives){
				for(int rhs : alternative){
					result += rhs + " ";
				}
				result += "| ";
			}
			result = result.substring(0, result.length() - 2);
			return result;
		}

		public int getLeftHandSide(){
			return leftHandSide;
		}

		public Set<ArrayList<Integer>> getAlternatives(){
			return alternatives;
		}

		public int minLength(){
			int minLength = 100;
			for(ArrayList<Integer> list : alternatives){
				if (list.size() < minLength){
					minLength = list.size();
				}
			}
			return minLength;
		}

		public int maxLength(){
			int maxLength = 0;
			for(ArrayList<Integer> list : alternatives){
				if (list.size() > maxLength){
					maxLength = list.size();
				}
			}
			return maxLength;
		}

		public boolean isTerminal(){
			for(ArrayList<Integer> alternative : alternatives){
				for(int rhs : alternative){
					if(rhs != VALUE_A && rhs != VALUE_B){
						return false;
					}
				}
			}
			return true;
		}

		public void applyProductionRule(ProductionRule rule){
			int lhs = rule.getLeftHandSide();
			Set<ArrayList<Integer>> result = new HashSet<>();
			for(ArrayList<Integer> alternative : alternatives){
				Set<ArrayList<Integer>> intermediateResult = new HashSet<>();
				intermediateResult.add(new ArrayList<>());
				for(int rhs : alternative){
					if(rhs == lhs){
						Set<ArrayList<Integer>> newResult = new HashSet<>();
						for(ArrayList<Integer> alt : intermediateResult) {
							for(ArrayList<Integer> ruleAlternative : rule.getAlternatives()){
								ArrayList<Integer> newList = new ArrayList<>(alt);
								newList.addAll(ruleAlternative);
								newResult.add(newList);
							}
						}
						intermediateResult = newResult;
					} else {
						for(ArrayList<Integer> alt : intermediateResult){
							alt.add(rhs);
						}
					}
				}
				result.addAll(intermediateResult);
			}
			alternatives = result;
		}
	}
}
