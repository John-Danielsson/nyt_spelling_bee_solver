// import java.applet.*;
// import java.awt.*;
// This program solves the NYT's Spelling Bee Puzzle in a text-based UI.
// I might make it into a browser plugin.
import java.util.*;
import java.io.*;

public class SpellingBee {
    private static Scanner console;
    private Map<String, Set<Character>> wordMap;
    public int MIN_WORD_LENGTH = 4;
    public int MAX_ALLOWED_CHARACTERS = 7;

    public SpellingBee(Set<String> dictionary) throws FileNotFoundException {
        wordMap = new HashMap<String, Set<Character>>();
        console = new Scanner(System.in);
        for (String word : dictionary) {
            if (word.length() >= MIN_WORD_LENGTH) {
                Set<Character> set = new HashSet<Character>();
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
        letters = letters.toLowerCase();
        Set<Character> charSet = new HashSet<>();
        for (int i = 0; i < letters.length(); i++) {
            charSet.add(letters.charAt(i));
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

    public static boolean hasDuplicateLetters(String s) {
        Set<Character> charSet = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (charSet.contains(c)) {
                return true;
            }
            charSet.add(c);
        }
        return false;
    } 

    public static void interact(SpellingBee bee) {
        console = new Scanner(System.in);
        System.out.print("Letters? ");
        String response = console.next();
        while (response.length() != 7 || hasDuplicateLetters(response)) {
            System.out.println("Invalid input (n != 7 or duplicate letters).");
            System.out.print("Letters? ");
            response = console.next();
        }
        System.out.print("Middle character? ");
        String ch = console.next();
        while (ch.length() != 1) {
            System.out.println("Invalid input.");
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
        Set<String> dictionarySet = new HashSet<String>();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            dictionarySet.add(scanner.nextLine());
        }
        scanner.close();
        interact(new SpellingBee(dictionarySet));
    }
}
