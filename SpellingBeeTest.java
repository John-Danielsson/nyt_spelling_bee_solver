import java.util.*;
import java.io.*;

public class SpellingBeeTest {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("dict.txt");
        if (!file.exists()) throw new FileNotFoundException("file not found");
        Set<String> dictionarySet = new HashSet<String>();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            dictionarySet.add(scanner.nextLine());
        }
        scanner.close();
        SpellingBee bee = new SpellingBee(dictionarySet);
        bee.interact();
    }
}

