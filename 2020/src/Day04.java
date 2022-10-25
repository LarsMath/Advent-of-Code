import java.util.regex.Pattern;

public class Day04 {

	enum eyeColor{
		amb, blu, brn, gry, grn, hzl, oth,
	}

	public static void main(String[] args){

		int resultA = 0;
		int resultB = 0;

		String[] passports = InputParser.readStrings("2020/Input/04.txt", "\n\n");
		
		for (String passport : passports){
			if(passport.contains("byr:") && passport.contains("iyr:") && passport.contains("eyr:")
					&& passport.contains("hgt:") && passport.contains("hcl:")
					&& passport.contains("ecl:")&& passport.contains("pid:")){
				resultA++;
			} else {
				continue;
			}
			boolean byr = false, iyr = false, eyr = false, hgt = false, hcl = false, ecl = false, pid = false;
			passport = passport.replaceAll("\n"," ");
			String[] passFields = passport.split(" ");
			for (String field : passFields){
				String name = field.substring(0,3);
				String entry = field.substring(4);
				switch (name){
					case "byr" : {
						int birthYear = Integer.parseInt(entry);
						if(birthYear >= 1920 && birthYear <= 2002){
							byr = true;
						}
						break;
					}
					case "iyr" : {
						int issueYear = Integer.parseInt(entry);
						if(issueYear >= 2010 && issueYear <= 2020){
							iyr = true;
						}
						break;
					}
					case "eyr" : {
						int expirationYear = Integer.parseInt(entry);
						if(expirationYear >= 2020 && expirationYear <= 2030){
							eyr = true;
						}
						break;
					}
					case "hgt" : {
						if (Pattern.matches("^\\d{3}cm$", entry)) {
							int length = Integer.parseInt(entry.substring(0, 3));
							if(length >= 150 && length <= 193){
								hgt = true;
							}
						} else if (Pattern.matches("^\\d{2}in$", entry)) {
							int length = Integer.parseInt(entry.substring(0, 2));
							if(length >= 59 && length <= 76){
								hgt = true;
							}
						}
						break;
					}
					case "hcl" : {
						hcl = Pattern.matches("^#[0-9a-f]{6}$", entry);
						break;
					}
					case "ecl" : {
						for(eyeColor c : eyeColor.values()){
							if(c.name().equals(entry)){
								ecl = true;
							}
						}
						break;
					}
					case "pid" : {
						pid = Pattern.matches("^\\d{9}$", entry);
						break;
					}
					default:{
						break;
					}
				}
			}
			if (byr && iyr && eyr && hgt && hcl && ecl && pid){
				resultB++;
			}
		}
		System.out.println(resultA);
		System.out.println(resultB);
	}

}
