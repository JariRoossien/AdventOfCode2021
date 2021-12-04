package nl.jariroossien.aoc.days;

import java.util.*;
import java.util.stream.Collectors;

public class Day04 extends Day {

    List<Integer> pulledNumbers = new ArrayList<>();
    List<Bingo> bingoCards = new ArrayList<>();

    @Override
    public long solveOne() {
        for (Integer i : pulledNumbers) {
            for (Bingo b : bingoCards) {
                b.markInt(i);
                if (b.isValid()) {
                    return b.getUnmarkedSum() * i;
                }
            }
        }
        return 0;
    }

    @Override
    public long solveTwo() {
        Bingo lastToWin = null;
        int lastNumberPulled = 0;
        Set<Bingo> bingoSet = new HashSet<>();
        for (Integer i : pulledNumbers) {
            for (Bingo b : bingoCards) {
                if (!bingoSet.contains(b)) b.markInt(i);
                if (b.isValid()) {
                    if (!bingoSet.contains(b)) {
                        bingoSet.add(b);
                        lastToWin = b;
                        lastNumberPulled = i;
                    }
                }
            }
        }
        return lastToWin.getUnmarkedSum() * lastNumberPulled;

    }

    private static class Bingo {

        int[][] grid;
        boolean[][] completed;
        public Bingo() {
            grid = new int[5][5];
            completed = new boolean[5][5];
            for (int i = 0; i < 25; i++) {
                completed[i / 5][i % 5] = false;
            }
        }


        public void addInt(int i, int pos) {
            grid[pos / 5][pos % 5] = i;
        }

        public void markInt(int x) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (grid[i][j] == x) {
                        completed[i][j] = true;
                    }
                }
            }
        }


        public int getUnmarkedSum() {
            int sum = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (!completed[i][j]) {
                        sum += grid[i][j];
                    }
                }
            }
            return sum;
        }

        boolean isValid() {
            for (int i = 0; i < 5; i++) {
                if (completed[i][0] && completed[i][1] && completed[i][2] && completed[i][3] && completed[i][4]) {
                    return true;
                } else if (completed[0][i] && completed[1][i] && completed[2][i] && completed[3][i] && completed[4][i]) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public void setup() {
        super.setup();

        // Parse the first line as a list of all pulled numbers.
        pulledNumbers = Arrays.stream(input.get(0).split(",")).map(Integer::parseInt).collect(Collectors.toList());

        // Keep going over the lines and add them to the current bingo instance.
        Bingo tempBingo = new Bingo();
        int counter = 0;
        for (int i = 2; i < input.size(); i++) {

            // If a line is empty, it means we are between 2 cards so we place it in the list and create a new card.
            if (input.get(i).length() == 0) {
                bingoCards.add(tempBingo);
                tempBingo = new Bingo();
                counter = 0;
            } else {

                // Add all 5 numbers to the bingo card.
                String[] numbers = input.get(i).strip().replace("  ", " ").split(" ");
                for (int j = 0; j < 5; j++) {
                    tempBingo.addInt(Integer.parseInt(numbers[j]), counter++);
                }
            }
        }
        // The last line isn't scanned, so add that to the list manually.
        bingoCards.add(tempBingo);
    }
}
