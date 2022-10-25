public class D01 {

    public static void main(String[] args){

        Integer[] modules = InputParser.readInts("2019/Input/01.txt", "\n");

        int resultA = 0;
        int resultB = 0;

        for (int module : modules){
            int fuelAdded = module / 3 - 2;
            resultA += fuelAdded;
            while (fuelAdded > 0){
                resultB += fuelAdded;
                fuelAdded = fuelAdded / 3 - 2;
            }
        }

        System.out.println(resultA);
        System.out.println(resultB);
    }
}
