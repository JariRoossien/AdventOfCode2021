package nl.jariroossien.aoc.days;

import java.util.*;
import java.util.stream.Collectors;

public class Day14 extends Day {

    HashMap<Pair, Long> pairCount = new HashMap<>();
    String originalLine;
    @Override
    public long solveOne() {
        return calculateDifference(10);
    }

    @Override
    public long solveTwo() {
        pairCount = new HashMap<>();
        setup();
        return calculateDifference(40);
    }

    private long calculateDifference(int days) {
        for (int i = 0; i < days; i++) {
            HashMap<Pair, Long> newMap = new HashMap<>();
            for (Pair p : pairCount.keySet()) {
                Pair left = Pair.getNewPair(p.to[0]);
                Pair right = Pair.getNewPair(p.to[1]);
                newMap.merge(left, pairCount.get(p), Long::sum);
                newMap.merge(right, pairCount.get(p), Long::sum);
            }
            pairCount = newMap;
        }

        Map<Character, Long> count = new HashMap<>();
        pairCount.entrySet().stream().forEach(pair -> {
            count.merge(pair.getKey().from.charAt(0), pair.getValue(), Long::sum);
            count.merge(pair.getKey().from.charAt(1), pair.getValue(), Long::sum);
        });
        count.forEach((entry, value) -> {
            count.put(entry, value / 2);
            if (entry == originalLine.charAt(0) || entry == originalLine.charAt(originalLine.length() - 1)) {
                count.merge(entry, 1L, Long::sum);
            }
        });
        List<Map.Entry<Character, Long>> sorted = count.entrySet().stream().sorted((a, b) -> Long.compare(b.getValue(), a.getValue())).collect(Collectors.toList());
        return sorted.get(0).getValue() - sorted.get(sorted.size() - 1).getValue();
    }

    @Override
    public void setup() {
        super.setup();
        originalLine = input.get(0);

        for (int i = 2; i < input.size(); i++) {
            String[] cut = input.get(i).split(" -> ");
            Pair temp = new Pair(cut[0], cut[1]);
            Pair.addPair(temp);
        }
        for (int i = 0; i < originalLine.length() - 1; i++) {
            Pair temp = Pair.getNewPair(originalLine.substring(i, i+2));
            pairCount.merge(temp, 1L, Long::sum);
        }
    }

    private static class Pair {
        private final String from;
        private String[] to;

        private static final Set<Pair> pairSet = new HashSet<>();

        public static void addPair(Pair p) {
            pairSet.add(p);
        }

        public static Pair getNewPair(String pair) {
            for (Pair p : pairSet) {
                if (p.from.equalsIgnoreCase(pair)) {
                    return p;
                }
            }
            return null;
        }

        public Pair(String from, String to) {
            this.from = from;
            this.to = new String[2];
            this.to[0] = from.charAt(0) + to;
            this.to[1] = to + from.charAt(1);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return Objects.equals(from, pair.from);
        }

        @Override
        public int hashCode() {
            return Objects.hash(from);
        }
    }
}
