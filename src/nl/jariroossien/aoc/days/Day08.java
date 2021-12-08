package nl.jariroossien.aoc.days;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A quick note how I solved part 2 up here.
 *
 * I have tried to find a difference in how a previously determined string was able to match the strings later.
 * We can split the numbers into a few different categories.
 *
 * 1: 2
 *
 * 7: 3
 *
 * 4: 4
 *
 * 2: 5 - 6 // 6 cannot be placed in 5, however we use this to determine the difference from 5 and 2. 5 can go in 6, but 2 cannot go in 6.
 * 3: 5 - 1 7
 * 5: 5 -
 *
 * 0: 6 - 1 7
 * 6: 6 -
 * 9: 6 - 1 7 4
 *
 * 8: 7
 *
 * The number behind the : is the amount of lights are lit up.
 *
 * Behind the - is the number we can use to match it with. Therefor we can take these subgroups,
 * check if the numbers behind it have the required lights, and if not, go down 1 that has less checks.
 */
public class Day08 extends Day {

    List<Digits> digits;

    @Override
    public long solveOne() {
        AtomicInteger sum = new AtomicInteger();
        for (Digits d : digits) {
            d.second.forEach(s -> {
                // If this string has a unique length that is 1, 4, 7 or 8, we place it place 1.
                if (s.length() == 2 || s.length() == 4 || s.length() == 7 || s.length() == 3) {
                    sum.getAndIncrement();
                }
            });
        }
        return sum.get();
    }

    @Override
    public long solveTwo() {
        int totalSum = 0;
        for (Digits d : digits) {
            int temp = 0;
            Pattern p = d.pattern;
            for (String s : d.second) {
                temp = temp * 10 + p.patternMap.get(s);
            }
            totalSum += temp;
        }
        return totalSum;
    }

    @Override
    public void setup() {
        super.setup();
        digits = input.stream().map(Digits::new).collect(Collectors.toList());
    }

    private static class Digits {
        private final Pattern pattern;
        private final List<String> second;

        public Digits(String input) {
            String[] inputSplit = input.replace('|', '_').split("_");

            // We get the first part of the string, and sort each element in the string on alphabetical order.
            // We sort it alphabetically to make the pattern matching easier.
            List<String> first = (Stream.of(inputSplit[0].strip().split(" ")).map(Digits::sort).collect(Collectors.toList()));

            // We build up the pattern from the first string.
            pattern = new Pattern(first);

            // We split the second part, and sort the chars in each string alphabetically again for matching with pattern.
            second = (Stream.of(inputSplit[1].strip().split(" ")).map(Digits::sort).collect(Collectors.toList()));
        }

        private static String sort(String s) {
            char[] sc = s.toCharArray();
            Arrays.sort(sc);
            return String.valueOf(sc);
        }
    }

    private static class Pattern {
        public Map<String, Integer> patternMap = new HashMap<>();

        public Pattern(List<String> strings) {
            // We order the list so that the items with the smallest string length are up front.

            strings.sort(Comparator.comparingInt(String::length));

            // We can determine the following 4 numbers simply by size.
            patternMap.put(strings.get(0), 1); // size of 2.
            patternMap.put(strings.get(1), 7); // Size of 3.
            patternMap.put(strings.get(2), 4); // size of 4.
            patternMap.put(strings.get(9), 8); // size of 7

            // We keep track of the index that contains six to determine if it matches with 5.
            int six_index = 0;

            // We go through the values that have a size of 6, these are the digits
            // 0: 6 - 1 7
            // 6: 6 -
            // 9: 6 - 1 7 4
            //
            // We can find 9 by making sure it matches both 1, 7 and 4.
            // Else we can see if it matches 0 by matching it with just 1 and 7.
            // Otherwise we know it's 6 because 6 doesn't match with anything we've determined.
            for (int i = 6; i <= 8; i++) {
                String toCompare = strings.get(i);

                // We determine if the i'th string is 9.
                if (matches(toCompare, strings.get(0)) && matches(toCompare, strings.get(1)) && matches(toCompare, strings.get(2))) {
                    patternMap.put(toCompare, 9);

                    // Else we check if it's 0.
                } else if (matches(toCompare, strings.get(0)) && matches(toCompare, strings.get(1))) {
                    patternMap.put(toCompare, 0);

                    // Otherwise it's 6.
                } else {
                    patternMap.put(toCompare, 6);
                    six_index = i;
                }
            }

            // The trickiest check, because it contains the following numbers.
            // 2: 5 - 6
            // 3: 5 - 1 7
            // 5: 5 -
            //
            // We can easily determine 3 by checking if it matches 1 and 7.
            // To determine the difference between 2 and 5, we check if the string
            // is allowed to be in 6, rather than checking if a number can be put in the string.
            for (int i = 3; i <= 5; i++) {
                String toCompare = strings.get(i);
                if (matches(toCompare, strings.get(0)) && matches(toCompare, strings.get(1))) {
                    patternMap.put(toCompare, 3);
                } else if (matches(strings.get(six_index), toCompare)) {
                    patternMap.put(toCompare, 5);
                } else {
                    patternMap.put(toCompare, 2);
                }
            }
        }

        /**
         * This function checks if all items in t are able to placed in s.
         *
         * @param s The string we want to put T in.
         * @param t The string we want to put in S.
         * @return If it's possible to place T in S.
         */
        private static boolean matches(String s, String t) {
            for (char c : t.toCharArray()) {
                if (s.contains(c + "")) continue;
                return false;
            }
            return true;
        }
    }
}
