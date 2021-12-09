package nl.jariroossien.aoc.days;

import java.util.*;
import java.util.stream.Collectors;

public class Day09 extends Day {

    static char[][] grid;

    @Override
    public long solveOne() {
        List<Integer> ints = new ArrayList<>();
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (isNotLowestPoint(y, x)) continue;
                ints.add(Integer.parseInt(grid[y][x] + "") + 1);

            }
        }

        return ints.stream().reduce(Integer::sum).orElse(0);
    }

    @Override
    public long solveTwo() {
        List<Coord> coords = new ArrayList<>();

        // Find all coords that are at a bottom.
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (isNotLowestPoint(y, x)) continue;
                coords.add(new Coord(y, x));
            }
        }

        Map<Coord, Integer> basinSize = new HashMap<>();

        // Go over each coord, scan their nearby neighbours, and use recursion for that neighbour to scan it's neighbours.
        for (Coord c : coords) {
            Set<Coord> checked = new HashSet<>();
            checkNearbyCoord(c, checked);
            basinSize.put(c, checked.size());
        }

        // Sort the HashMap on the values, and get the top 3 multiplied.
        List<Map.Entry<Coord, Integer>> sorted = basinSize.entrySet().stream().sorted((a, b) -> b.getValue() - a.getValue()).collect(Collectors.toList());

        return sorted.get(0).getValue() * sorted.get(1).getValue() * sorted.get(2).getValue();
    }

    private boolean isNotLowestPoint(int y, int x) {
        if (y != 0) {
            if (grid[y - 1][x] <= grid[y][x]) return true;
        }
        if (x != 0) {
            if (grid[y][x - 1] <= grid[y][x]) return true;
        }
        if (y != grid.length - 1) {
            if (grid[y + 1][x] <= grid[y][x]) return true;
        }
        if (x != grid[0].length - 1) {
            if (grid[y][x + 1] <= grid[y][x]) return true;
        }
        return false;
    }

    /**
     * Check for given coord and check the surrounding 4 around it.
     *
     * @param c Coord to check.
     * @param checked The set of coords we already have checked.
     */
    private static void checkNearbyCoord(Coord c, Set<Coord> checked) {
        char gridVal = grid[c.y][c.x];
        if (gridVal == '9') return;
        if (checked.contains(c)) return;
        checked.add(c);
        if (c.y - 1 >= 0) {
            checkNearbyCoord(new Coord(c.y - 1, c.x), checked);
        }
        if (c.x - 1 >= 0) {
            checkNearbyCoord(new Coord(c.y, c.x - 1), checked);
        }
        if (c.y + 1 < grid.length) {
            checkNearbyCoord(new Coord(c.y + 1, c.x), checked);
        }
        if (c.x + 1 < grid[0].length) {
            checkNearbyCoord(new Coord(c.y, c.x + 1), checked);
        }
    }

    private static class Coord {
        int y;
        int x;

        public Coord(int y, int x) {
            this.y = y;
            this.x = x;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coord coord = (Coord) o;
            return y == coord.y && x == coord.x;
        }

        @Override
        public int hashCode() {
            return Objects.hash(y, x);
        }
    }
    @Override
    public void setup() {
        super.setup();
        grid = new char[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i++) {
            char[] arr = input.get(i).toCharArray();
            System.arraycopy(arr, 0, grid[i], 0, arr.length);
        }
    }
}
