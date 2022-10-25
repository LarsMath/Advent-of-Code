public class Day01 {

    public static void main(String[] args) {

        int resultA = 0;
        int resultB = 0;

        Integer[] list = InputParser.readInts("2020/Input/01.txt", "\n");

        for(int i : list){
            for (int j : list){
                if(i + j == 2020){
                    resultA = i * j;
                }
                for(int k : list){
                    if (i + j + k == 2020){
                        resultB = i * j * k;
                    }
                }
            }
        }

        System.out.println(resultA);
        System.out.println(resultB);
    }
}
