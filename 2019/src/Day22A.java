import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day22A {

    static final int DECKSIZE = 10007; //very prime

    enum techniques{
        DEAL,
        CUT,
        INCREMENT
    }

    public static void main(String[] args){

        File file = new File("2019/22.txt");
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<techniques> techniqueCommands = new ArrayList<techniques>();
        ArrayList<Integer> params = new ArrayList<Integer>();

        while (sc.hasNextLine()){
            String[] split = sc.nextLine().split(" ");
            if (split[0].equals("cut")) {
                techniqueCommands.add(techniques.CUT);
                params.add(Integer.parseInt(split[split.length-1]));
            } else if (split[1].equals("with")) {
                techniqueCommands.add(techniques.INCREMENT);
                params.add(Integer.parseInt(split[split.length-1]));
            } else {
                techniqueCommands.add(techniques.DEAL);
                params.add(0);
            }
        }

        int slope = 1;
        int offset = 0;

        for (int i = 0; i<techniqueCommands.size(); i++){
            switch(techniqueCommands.get(i)){
                case DEAL:
                    slope = -slope;
                    offset = DECKSIZE - 1 - offset;
                    break;
                case CUT:
                    offset = offset - params.get(i);
                    break;
                case INCREMENT:
                    slope = (slope * params.get(i)) % DECKSIZE;
                    offset = (offset * params.get(i)) % DECKSIZE;
                    break;
            }
            System.out.println(slope);
            System.out.println(offset);
        }

        int pos2019 = ((((slope * 2019) + offset) % DECKSIZE) + DECKSIZE) % DECKSIZE;

        System.out.println(pos2019);

    }
}
