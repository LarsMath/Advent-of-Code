import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day2A {


    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("2019/02.txt");
        Scanner sc = new Scanner(file);
        sc.useDelimiter("\\D");

        int[] opcode = new int[169];

        int i = 0;
        while (sc.hasNextInt()){
            opcode[i] = sc.nextInt();
            i++;
        }

        opcode[1] = 12;
        opcode[2] = 2;

        int command = opcode[0];
        int step = 0;

        while (command != 99){
            int inputAdress1 = opcode[4 * step + 1];
            int inputAdress2 = opcode[4 * step + 2];
            int outputAdress = opcode[4 * step + 3];
            System.out.println(inputAdress1 + "," + inputAdress2 + "," + outputAdress);
            if (command == 1) {
                opcode[outputAdress] = opcode[inputAdress1] + opcode[inputAdress2];
            } else if (command == 2){
                opcode[outputAdress] = opcode[inputAdress1] * opcode[inputAdress2];
            } else {
                System.out.println("something went wrong");
                System.out.println(opcode[4 * step]);
            }
            System.out.println(command + "," + opcode[inputAdress1] + "," + opcode[inputAdress2] + "," + opcode[outputAdress]);
            step++;
            command = opcode[step * 4];

        }
        System.out.println(opcode[0]);
    }
}