import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Day07 {

	public static void main(String[] args) throws FileNotFoundException {

		String[] bagRules = InputParser.readStrings("2020/Input/07.txt", "\n");
		HashMap<String, Boolean> containsShinyGold = new HashMap<>();
		for (String rule : bagRules){
			containsShinyGold.put(rule.split(" ")[0] + " " + rule.split(" ")[1], false);
		}
		containsShinyGold.put("shiny gold", true);
		int resultA = 0;

		ArrayList<String> added = new ArrayList<>();
		added.add("shiny gold");

		while (added.size() != 0){
			ArrayList<String> newlyAdded = new ArrayList<>();
			for(String rule : bagRules){
				String ruleBag = rule.split(" ")[0] + " " + rule.split(" ")[1];
				if(containsShinyGold.get(ruleBag)) {
					continue;
				}
				for(String color : added){
					if(rule.contains(color)){
						resultA++;
						newlyAdded.add(ruleBag);
						containsShinyGold.put(ruleBag, true);
						break;
					}
				}
			}
			added = newlyAdded;
		}
		System.out.println(resultA);
		System.out.println(recursiveBagCount("shiny gold", parseRulesIntoGiantHashMapOfHashMaps(bagRules)));
	}

	public static HashMap<String, HashMap<String, Integer>> parseRulesIntoGiantHashMapOfHashMaps(String[] bagRules) {
		HashMap<String, HashMap<String, Integer>> megaHashMap = new HashMap<>();
		for (String rule : bagRules) {
			String[] splittedRule = rule.split(" ");
			String colorBag = splittedRule[0] + " " + splittedRule[1];
			HashMap<String, Integer> miniHashMap = new HashMap<>();
			int numberOfRules = (splittedRule.length / 4) - 1;
			for (int ruleNumber = 0; ruleNumber < numberOfRules; ruleNumber++) {
				miniHashMap.put(splittedRule[(ruleNumber + 1) * 4 + 1] + " " + splittedRule[(ruleNumber + 1) * 4 + 2],
						Integer.parseInt(splittedRule[(ruleNumber + 1) * 4]));
			}
			megaHashMap.put(colorBag, miniHashMap);
		}
		return megaHashMap;
	}
	public static int recursiveBagCount(String color, HashMap<String, HashMap<String, Integer>> megaHashMap) {
		int total = 0;
		HashMap<String, Integer> miniHashMap = megaHashMap.get(color);
		for (String key : miniHashMap.keySet()) {
			total += miniHashMap.get(key) * (recursiveBagCount(key, megaHashMap) + 1);
		}
		return total;
	}
}
