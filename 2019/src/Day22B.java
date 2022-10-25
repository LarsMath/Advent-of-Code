import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class Day22B {

    // Answer: 1644352419829

    static final BigInteger DECKSIZE = new BigInteger("119315717514047"); //very prime
    static final BigInteger APPLYTIMES = new BigInteger("101741582076661");

    enum techniques{
        FLIP,
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

        ArrayList<techniques> techniqueCommands = new ArrayList<>();
        ArrayList<BigInteger> params = new ArrayList<>();

        // Store commands in arrays
        while (sc != null && sc.hasNextLine()){
            String[] split = sc.nextLine().split(" ");
            if (split[0].equals("cut")) {
                techniqueCommands.add(techniques.CUT);
                params.add(new BigInteger(split[split.length-1]));
            } else if (split[1].equals("with")) {
                techniqueCommands.add(techniques.INCREMENT);
                params.add(new BigInteger(split[split.length-1]));
            } else {
                techniqueCommands.add(techniques.FLIP);
                params.add(BigInteger.ZERO);
            }
        }

        // Initialize parameters that could together determine any shuffle combination (Aff(F_p))
        BigInteger slope = BigInteger.ONE;
        BigInteger offset = BigInteger.ZERO;

        // Go through list of commands and change parameters accordingly
        for (int i = 0; i<techniqueCommands.size(); i++){
            switch(techniqueCommands.get(i)){
                case FLIP:
                    slope = slope.negate();
                    offset = DECKSIZE.subtract(BigInteger.ONE).subtract(offset);
                    break;
                case CUT:
                    offset = offset.subtract(params.get(i));
                    break;
                case INCREMENT:
                    slope = slope.multiply(params.get(i)).remainder(DECKSIZE);
                    offset = offset.multiply(params.get(i)).remainder(DECKSIZE);
                    break;
            }
        }

        // Take the inverse shuffle (affine transformation) to the card at a positions instead of position of
        offset = offset.negate().multiply(slope.modInverse(DECKSIZE));
        slope = slope.modInverse(DECKSIZE);

        // Calculate the parameters if applying the shuffle APPLYTIMES times
        BigInteger slopeApplyTimes = slope.modPow(APPLYTIMES, DECKSIZE);
        BigInteger slopeMinusOneInverse = slope.subtract(BigInteger.ONE).modInverse(DECKSIZE);
        BigInteger offsetApplyTimes = offset.multiply(slopeApplyTimes.subtract(BigInteger.ONE)).multiply(slopeMinusOneInverse);

        // Insert the position (2020) into the affine transformation and do some modulo magic
        BigInteger BI2020 = new BigInteger("2020");
        BigInteger atPos2020 = BI2020.multiply(slopeApplyTimes).add(offsetApplyTimes).remainder(DECKSIZE).add(DECKSIZE).remainder(DECKSIZE);

        System.out.println("result:");
        System.out.println(atPos2020);

    }
}
