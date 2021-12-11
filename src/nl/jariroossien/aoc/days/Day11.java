package nl.jariroossien.aoc.days;

public class Day11 extends Day {

    int[][] grid;

    @Override
    public long solveOne() {
        int flashes = 0;
        for (int i = 0; i < 100; i++) {
            explodeNines();
            flashes += increaseValue();
        }
        return flashes;
    }

    /**
     * After exploding all 9s, we increase the value of each item
     * by 1. We replace all 10's with 0's to reset them.
     *
     * @return Amount of flashes that have happened.
     */
    private int increaseValue() {
        int flashes = 0;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (grid[y][x] == 10) {
                    grid[y][x] = 0;
                        flashes++;
                } else {
                    grid[y][x]++;
                }
            }
        }
        return flashes;
    }

    /**
     * Go over each 9 and make it explode.
     * If the resulted explosion has created a new 9, explode it as well.
     */
    private void explodeNines() {
        while (hasNines(grid)) {
            for (int y = 0; y < grid.length; y++) {
                for (int x = 0; x < grid[0].length; x++) {
                    if (grid[y][x] == 9) {
                        explode(grid, y, x);
                    }
                }
            }
        }
    }

    /**
     * Find the nearby octopuses, and increase by 1. If the octopus is already at 9,
     * ignore it as it will explode later.
     * @param grid Grid of octopuses.
     * @param y Y coordinate
     * @param x X coordinate
     */
    private void explode(int[][] grid, int y, int x) {
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (y + dy < 0) continue;
                if (x + dx < 0) continue;
                if (y + dy >= grid.length) continue;
                if (x + dx >= grid.length) continue;
                if (grid[y + dy][x + dx] >= 9) continue;
                grid[y+dy][x+dx]++;
            }
        }
        grid[y][x] = 10;
    }

    /**
     * Check if the grid has any 9's to explode.
     *
     * @param grid grid to check.
     * @return If any 9's are available.
     */
    private boolean hasNines(int[][] grid) {
        for (int[] ints : grid) {
            for (int x = 0; x < grid[0].length; x++) {
                if (ints[x] == 9) return true;
            }
        }
        return false;
    }


    @Override
    public long solveTwo() {
        setup();
        boolean allFlashed = false;
        long i;
        for(i = 0; !allFlashed; i++) {
            explodeNines();
            allFlashed = hasAllFlashed(grid);
            increaseValue();
        }
        return i;
    }

    /**
     * Checks if every item in the grid has flashed.
     *
     * @param grid Grid to check.
     * @return If there are only 10's in the grid.
     */
    private static boolean hasAllFlashed(int[][] grid) {
        for (int[] ints : grid) {
            for (int x = 0; x < grid[0].length; x++) {
                if (ints[x] != 10) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public void setup() {
        super.setup();
        grid = new int[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                grid[i][j] = Integer.parseInt(String.valueOf(input.get(i).charAt(j)));
            }
        }
    }
}
