package nl.jariroossien.aoc.days;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Day10 extends Day {

    char[] open = new char[]{'[', '{', '<', '('};

    @Override
    public long solveOne() {
        List<Character> illegals = new ArrayList<>();

        for (String s : input) {
            char[] chars = s.toCharArray();
            Stack<Character> characterStack = new Stack<>();
            for (char c : chars) {
                if (isOpen(c)) {
                    characterStack.add(c);
                    continue;
                }

                char opening = characterStack.pop();
                if (!isPair(opening, c)) {
                    illegals.add(c);
                    break;
                }
            }
        }

        return illegals.stream().mapToInt((character) -> {
            switch (character) {
                case ')':
                    return 3;
                case ']':
                    return 57;
                case '}':
                    return 1197;
                case '>':
                    return 25137;
            }
            return 0;
        }).reduce(Integer::sum).orElse(0);
    }

    private boolean isOpen(char c) {
        for (char test : open) {
            if (test == c) return true;
        }
        return false;
    }

    private boolean isPair(char open, char end) {
        if (end == ')')
            return (end - open) == 1;
        return (end - open) == 2;
    }

    @Override
    public long solveTwo() {

        List<Long> results = new ArrayList<>();

        for (String s : input) {
            char[] chars = s.toCharArray();
            Stack<Character> characterStack = new Stack<>();
            boolean illegal = false;
            for (char c : chars) {
                if (isOpen(c)) {
                    characterStack.add(c);
                    continue;
                }

                char opening = characterStack.pop();
                if (!isPair(opening, c)) {
                    illegal = true;
                    break;
                }
            }

            if (!illegal) {
                char[] endingMissed = new char[characterStack.size()];
                int j = 0;
                while (!characterStack.isEmpty()) {
                    char toCompare = characterStack.pop();
                    endingMissed[j++] = getEnd(toCompare);
                }

                long score = 0;

                for (char c : endingMissed) {
                    score = 5 * score + getValue(c);
                }

                results.add(score);
            }
        }

        Collections.sort(results);
        return results.get((results.size() - 1) / 2);
    }


    private static int getValue(char c) {
        switch (c) {
            case ']':
                return 2;
            case '>':
                return 4;
            case '}':
                return 3;
            case ')':
                return 1;
        }
        return 0;
    }
    private char getEnd(char c) {
        switch (c) {
            case '[':
                return ']';
            case '<':
                return '>';
            case '{':
                return '}';
            case '(':
                return ')';
        }
        return '.';
    }
    @Override
    public void setup() {
        super.setup();
    }

}
