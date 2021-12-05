package nl.jariroossien.aoc.days;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day05 extends Day {
    List<Line> lineList = new ArrayList<>();
    int maxX = 0;
    int maxY = 0;

    @Override
    public long solveOne() {
        int[][] grid = new int[maxY + 1][maxX + 1];
        for (Line l : lineList.stream().filter(Line::isSolveOne).collect(Collectors.toList())) {
            l.iterate(grid);
        }
        return determineTotalOverlap(grid);
    }

    private long determineTotalOverlap(int[][] grid) {
        int totalAboveTwo = 0;
        for (int x = 0; x <= maxX; x++) {
            for (int y = 0; y <= maxY; y++) {
                if (grid[x][y] > 1) totalAboveTwo++;
            }
        }
        return totalAboveTwo;
    }

    @Override
    public long solveTwo() {
        int[][] grid = new int[maxY + 1][maxX + 1];
        for (Line l : lineList) {
            l.iterate(grid);
        }
        return determineTotalOverlap(grid);
    }

    private static class Line {
        private final int x1;
        private final int y1;
        private final int x2;
        private final int y2;

        public Line(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
        }

        public boolean isSolveOne() {
            return x1 == x2 || y1 == y2;
        }

        public void iterate(int[][] grid) {
            int[] slope = new int[2];

            // Determine slope, probably better way to do this
            // int[] starts at 0 so we dont have to set that manually with slope[x] = 0
            if (x1 > x2) {
                slope[0] = -1;
            } else if (x1 < x2) {
                slope[0] = 1;
            }

            if (y1 > y2) {
                slope[1] = -1;
            } else if (y1 < y2) {
                slope[1] = 1;
            }

            int length;

            // Get the length you need to move.
            // Since it's ony horizontal, vertical, or diagonal, we can just get the absolute difference.
            if (y1 == y2 || x1 != x2)
                length = Math.abs(x1 - x2);
            else
                length = Math.abs(y1 - y2);

            for (int i = 0; i <= length; i++) {
                grid[y1 + slope[1] * i][x1 + slope[0] * i]++;
            }
        }
    }

    @Override
    public void setup() {
        super.setup();
        for (String s : input) {
            String[] split = s.split(" -> ");
            Line temp = new Line(
                    Integer.parseInt(split[0].split(",")[0]),
                    Integer.parseInt(split[0].split(",")[1]),
                    Integer.parseInt(split[1].split(",")[0]),
                    Integer.parseInt(split[1].split(",")[1]));
            lineList.add(temp);

            // Determine the grid size.
            if (maxX < temp.x1) maxX = temp.x1;
            if (maxX < temp.x2) maxX = temp.x2;
            if (maxY < temp.y1) maxY = temp.y1;
            if (maxY < temp.y2) maxY = temp.y2;
        }
    }
}
