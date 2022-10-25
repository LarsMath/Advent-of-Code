import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class InputParser {

    public static Scanner getScanner(String filename) {
        Scanner sc = null;
        try {
            sc = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sc;
    }

    public static String[] readStrings(String filename, String delimiter){
        Scanner sc = getScanner(filename);
        sc.useDelimiter(delimiter);
        ArrayList<String> result = new ArrayList<>();
        while (sc.hasNext()){
            result.add(sc.next());
        }
        return result.toArray(new String[0]);
    }

    public static Integer[] readInts(String filename, String delimiter){
        Scanner sc = getScanner(filename);
        sc.useDelimiter(delimiter);
        ArrayList<Integer> result = new ArrayList<>();
        while (sc.hasNextInt()){
            result.add(sc.nextInt());
        }
        return result.toArray(new Integer[0]);
    }

    public static Long[] readLongs(String filename, String delimiter){
        Scanner sc = getScanner(filename);
        sc.useDelimiter(delimiter);
        ArrayList<Long> result = new ArrayList<>();
        while (sc.hasNextLong()){
            result.add(sc.nextLong());
        }
        return result.toArray(new Long[0]);
    }

    public static char[][] readCharArrays(String filename, String delimiter){
        Scanner sc = getScanner(filename);
        sc.useDelimiter(delimiter);
        ArrayList<char[]> result = new ArrayList<>();
        while (sc.hasNext()) {
            result.add(sc.next().replace(delimiter, "").toCharArray());
        }
        return result.toArray(new char[0][0]);
    }
}
