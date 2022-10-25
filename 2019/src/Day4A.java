public class Day4A {

    public final static int NUMBER_OF_DIGITS = 6;
    public final static int LOWER_BOUND = 147981;
    public final static int UPPER_BOUND = 691423;

    public static boolean checkAscending(int password){
        for (int i = 0; i < NUMBER_OF_DIGITS; i++) {
            int leftDigit = (password % (int)Math.pow(10,i+2)) / (int) Math.pow(10,i+1);
            int rightDigit = (password % (int)Math.pow(10,i+1)) / (int) Math.pow(10,i);
            if(leftDigit > rightDigit){
                return false;
            }
        }
        return true;
    }

    public static boolean checkDoubles(int password) {
        for (int i = 0; i < NUMBER_OF_DIGITS; i++) {
            if(((password % (int) Math.pow(10,i+2)) / (int) Math.pow(10,i)) % 11 == 0){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args){
        int total = 0;
        for(int i = LOWER_BOUND; i <= UPPER_BOUND; i++){
            if(checkDoubles(i) && checkAscending(i)){
                System.out.println(i);
                total++;
            }
        }
        System.out.println(total);
    }
}
