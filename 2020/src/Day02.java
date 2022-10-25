public class Day02 {

    public static void main(String[] args) {

        String[] passwordList = InputParser.readStrings("2020/Input/02.txt","\n");
        int resultA = 0;
        int resultB = 0;

        for (String line : passwordList){
            String[] lineParts = line.split(" ");
            int lowerBound = Integer.parseInt(lineParts[0].split("-")[0]);
            int upperbound = Integer.parseInt(lineParts[0].split("-")[1]);
            char testChar = lineParts[1].charAt(0);
            String password = lineParts[2];

            if(lowerBound <= countOccurrences(password, testChar) && countOccurrences(password, testChar) <= upperbound){
                resultA++;
            }
            if(password.charAt(lowerBound - 1) == testChar ^ password.charAt(upperbound - 1) == testChar){
                resultB++;
            }
        }
        System.out.println(resultA);
        System.out.println(resultB);
    }

    public static int countOccurrences(String word, char letter) {
        int count = 0;
        for (char x : word.toCharArray()) {
            if (x == letter) {
                count++;
            }
        }
        return count;
    }

}
