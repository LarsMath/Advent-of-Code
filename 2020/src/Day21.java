import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day21 {

	public static void main(String[] args){

		ArrayList<Set<String>> foodIngredients = new ArrayList<>();
		ArrayList<Set<String>> foodAllergens = new ArrayList<>();
		Map<String, String> allergenMap= new HashMap<>();

		String[] foods = InputParser.readStrings("2020/Input/21.txt","\n");

		for(String food : foods){
			String[] words = food.replaceAll("(\\(|,|\\)|\r)", "").split(" ");
			HashSet<String> ingredients = new HashSet<>();
			HashSet<String> allergens = new HashSet<>();
			boolean contains = false;
			for (String word : words){
				if(word.equals("contains")){
					contains = true;
				} else if (contains){
					allergens.add(word);
				} else {
					ingredients.add(word);
				}
			}
			foodIngredients.add(ingredients);
			foodAllergens.add(allergens);
		}

		for(int gen = 0; gen < 2; gen++){
			for(int i = 0; i < foodAllergens.size(); i++){
				HashSet foundAllergens = new HashSet<>();
				for(String allergen: foodAllergens.get(i)){
					Set<String> possibleTranslations = new HashSet<>(foodIngredients.get(i));
					for(int j = 0; j < foodIngredients.size(); j++){
						if(foodAllergens.get(j).contains(allergen)){
							Set<String> impossibleTranslations = new HashSet<>();
							for(String translation : possibleTranslations){
								if(!foodIngredients.get(j).contains(translation)){
									impossibleTranslations.add(translation);
								}
							}
							possibleTranslations.removeAll(impossibleTranslations);
						}
					}
					if(possibleTranslations.size()==1){
						String translation = possibleTranslations.iterator().next();
						allergenMap.put(allergen, translation);
						for(int j = 0; j < foodIngredients.size(); j++){
							if(i==j){
								foundAllergens.add(allergen);
							} else {
								foodAllergens.get(j).remove(allergen);
							}
							if(foodIngredients.get(j).contains(translation)){
								foodIngredients.get(j).remove(translation);
							}
						}
					}
				}
				foodAllergens.get(i).removeAll(foundAllergens);
			}
		}

		int resultA = 0;
		for(Set<String> ingredients : foodIngredients){
			resultA += ingredients.size();
		}
		System.out.println(resultA);

		String resultB = "";
		ArrayList<String> sortedAllergens = new ArrayList<>(allergenMap.keySet());
		Collections.sort(sortedAllergens);
		for(String allergen : sortedAllergens){
			resultB += allergenMap.get(allergen) + ",";
		}
		System.out.println(resultB);

	}
}
