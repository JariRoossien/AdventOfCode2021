package nl.jariroossien.aoc.days;

import java.util.ArrayList;
import java.util.List;

public class Day11 extends Day {

    int[][] grid;

    @Override
    public long solveOne() {
        int flashes = 0;
        for (int i = 0; i < 100; i++) {
            while (hasNines(grid)) {
                for (int y = 0; y < grid.length; y++) {
                    for (int x = 0; x < grid[0].length; x++) {
                        if (grid[y][x] == 9) {
                            explode(grid, y, x);
                        }
                    }
                }
            }
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
        }
        return flashes;
    }

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
            while (hasNines(grid)) {
                for (int y = 0; y < grid.length; y++) {
                    for (int x = 0; x < grid[0].length; x++) {
                        if (grid[y][x] == 9) {
                            explode(grid, y, x);
                        }
                    }
                }
            }
            allFlashed = hasAllFlashed(grid);
            for (int y = 0; y < grid.length; y++) {
                for (int x = 0; x < grid[0].length; x++) {
                    if (grid[y][x] == 10) {
                        grid[y][x] = 0;
                    } else {
                        grid[y][x]++;
                    }
                }
            }
        }
        return i;
    }

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
