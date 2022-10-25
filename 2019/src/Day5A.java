import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day5A {

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("2019/02.txt");
        Scanner sc = new Scanner(file);
        sc.useDelimiter("\\D");

        int[] opcodeBackup = new int[169];
        int[] opcode = new int[169];

        int scanCount = 0;
        while (sc.hasNextInt()){
            opcodeBackup[scanCount] = sc.nextInt();
            scanCount++;
        }

        for(int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++){
                opcode = opcodeBackup.clone();
                opcode[1] = i;
                opcode[2] = j;

                if (opcodeMachine(opcode) == 19690720){
                    System.out.println(100*i + j);
                }
            }
        }
    }

    public static int opcodeMachine(int[] opcode){
        int command = opcode[0];
        int step = 0;

        while (command != 99){
            int inputAdress1 = opcode[4 * step + 1];
            int inputAdress2 = opcode[4 * step + 2];
            int outputAdress = opcode[4 * step + 3];
            //System.out.println(inputAdress1 + "," + inputAdress2 + "," + outputAdress);
            if (command == 1) {
                opcode[outputAdress] = opcode[inputAdress1] + opcode[inputAdress2];
            } else if (command == 2){
                opcode[outputAdress] = opcode[inputAdress1] * opcode[inputAdress2];
            } else {
                System.out.println("something went wrong");
                System.out.println(opcode[4 * step]);
            }
            //System.out.println(command + "," + opcode[inputAdress1] + "," + opcode[inputAdress2] + "," + opcode[outputAdress]);
            step++;
            command = opcode[step * 4];

        }
        return opcode[0];
    }
}
