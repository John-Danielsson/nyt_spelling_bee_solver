import sys
from collections import defaultdict

class SpellingBee:
    MIN_WORD_LENGTH = 4
    MAX_ALLOWED_CHARACTERS = 7

    def __init__(self, dictionary):
        self.word_map = defaultdict(set)
        for word in dictionary:
            if len(word) >= self.MIN_WORD_LENGTH:
                char_set = set(word)
                if len(char_set) <= self.MAX_ALLOWED_CHARACTERS:
                    self.word_map[word] = char_set

    def word_finder(self, letters, middle):
        letters = letters.lower()
        char_set = set(letters)
        result = set()
        for word, word_set in self.word_map.items():
            if middle in word_set and char_set.issuperset(word_set):
                result.add(word)
        return result

    def pangram_finder(self, letters):
        letters = letters.lower()
        char_set = set(letters)
        result = set()
        for word, word_set in self.word_map.items():
            if char_set == word_set:
                result.add(word)
        return result

    def yes_to(self, prompt):
        response = input(prompt + " (y/n)? ").strip().lower()
        while response not in {"y", "n"}:
            print("Only type \"y\" or \"n\" (case-insensitive).")
            response = input(prompt + " (y/n)? ").strip().lower()
        return response == "y"

    @staticmethod
    def has_duplicate_letters(s):
        char_set = set()
        for c in s:
            if c in char_set:
                return True
            char_set.add(c)
        return False

    def interact(self):
        response = input("Letters? ")
        while len(response) != 7 or self.has_duplicate_letters(response):
            print("Invalid input (n != 7 or duplicate letters).")
            response = input("Letters? ")
        ch = input("Middle character? ")
        while len(ch) != 1:
            print("Invalid input.")
            ch = input("Middle character? ")
        if self.yes_to("Pangrams only?"):
            print(self.pangram_finder(response))
        else:
            print(self.word_finder(response, ch[0]))
        if self.yes_to("Do you want to go again?"):
            self.interact()


if __name__ == "__main__":
    # Load the dictionary and create a SpellingBee instance
    dictionary = set()
    with open("dict.txt", "r") as f:
        for line in f:
            dictionary.add(line.strip())
    spelling_bee = SpellingBee(dictionary)
    spelling_bee.interact()
