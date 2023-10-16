class SpellingBeePangramFinder:
    def __init__(self):
        with open("dict.txt", 'r') as file:
            content = file.read()
        self.dict = list(filter(lambda w: len(w) > 6, content.split("\n")))

    def find_pangrams(self, letters):
        char_set = set(letters)
        return list(filter(lambda w: set(w) == char_set, self.dict))


if __name__ == "__main__":
    sb = SpellingBeePangramFinder()
    print(sb.find_pangrams("animato"))