// import java.applet.*;
// import java.awt.*;
import java.util.*;
import java.io.*;

public class SpellingBee {
    private static Scanner console;
    private Map<String, Set<Character>> wordMap;
    public int MIN_WORD_LENGTH = 4;
    public int MAX_ALLOWED_CHARACTERS = 7;

    public SpellingBee(List<String> dictionary) throws FileNotFoundException {
        wordMap = new HashMap<String, Set<Character>>();
        console = new Scanner(System.in);
        for (String word : dictionary) {
            Set<Character> set = new HashSet<Character>();
            if (word.length() >= MIN_WORD_LENGTH) {
                for (int i = 0; i < word.length(); i++) {
                    set.add(word.charAt(i));
                }
                if (set.size() <= MAX_ALLOWED_CHARACTERS) {
                    wordMap.put(word, set);
                }
            }
        }
    }

    public Set<String> wordFinder(String letters, char middle) {
        if (letters.length() > MAX_ALLOWED_CHARACTERS) {
            throw new IllegalArgumentException("Too many letters.");
        }
        Set<Character> charSet = new HashSet<>();
        letters = letters.toLowerCase();
        for (int i = 0; i < letters.length(); i++) {
            char c = letters.charAt(i);
            if (!charSet.contains(c)) {
                charSet.add(c);
            } else {
                throw new IllegalArgumentException("Duplicate letters.");
            }
        }
        Set<String> result = new TreeSet<String>();
        for (String word : wordMap.keySet()) {
            Set<Character> set = wordMap.get(word);
            if (set.contains(middle) && charSet.containsAll(set)) {
                result.add(word);
            }
        }
        return result;
    }

    public boolean yesTo(String prompt) {
        System.out.print(prompt + " (y/n)? ");
        console = new Scanner(System.in);
        String response = console.nextLine().trim().toLowerCase();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.println("Only type \"y\" or \"n\" (case-insensitive).");
            System.out.print(prompt + " (y/n)? ");
            response = console.nextLine().trim().toLowerCase();
        }
        return response.equals("y");
    }

    public static void interact(SpellingBee bee) {
        console = new Scanner(System.in);
        System.out.print("Letters? (No more than 1 of the same letter.) ");
        String response = console.next();
        while (response.length() > 7 || response.length() < 7) {
            System.out.println("Type only " + bee.MAX_ALLOWED_CHARACTERS + " letters.");
            System.out.print("Letters? (No more than 1 of the same letter.) ");
            response = console.next();
        }
        System.out.print("Middle character? ");
        String ch = console.next();
        while (ch.length() > 1) {
            System.out.println("Type only 1 character.");
            System.out.print("Middle character? ");
            ch = console.next();
        }
        System.out.println(bee.wordFinder(response, ch.charAt(0)));
        if (bee.yesTo("Do you want to go again?")) {
            interact(bee);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("dict.txt");
        List<String> dictionary = new ArrayList<String>();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            dictionary.add(scanner.nextLine());
        }
        scanner.close();
        interact(new SpellingBee(dictionary));
    }
}
