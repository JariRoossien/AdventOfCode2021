package nl.jariroossien.aoc.days;

import java.util.ArrayList;
import java.util.List;

public class Day13 extends Day {
    List<Instruction> instructionList = new ArrayList<>();
    Paper paper;
    @Override
    public long solveOne() {
        Instruction a = instructionList.get(0);
//        printPaper(paper);
        paper.fold(a.horizontal, a.row);
//        System.out.println();
//        printPaper(paper);
        int counter = 0;
        for (int y = 0; y < paper.length; y++) {
            for (int x = 0; x < paper.width; x++) {
                if (paper.grid[y][x]) counter++;
            }
        }
        return counter;
    }

    @Override
    public long solveTwo() {
        setup();
        for (Instruction a : instructionList) {
            paper.fold(a.horizontal, a.row);
            printPaper(paper);
            System.out.println();
        }
        printPaper(paper);
        return 0;
    }

    @Override
    public void setup() {
        super.setup();
        boolean grid[][] = new boolean[2000][2000];
        boolean isInstruction = false;
        int maxX = 0;
        int maxY = 0;
        for (String s : input) {
            if (s.length() == 0) {
                isInstruction = true;
                continue;
            }
            if (isInstruction) {
                String[] inst = s.replace("fold along ", "").split("=");
                boolean horizontal;
                horizontal = inst[0].equals("y");
                int row = Integer.parseInt(inst[1]);
                instructionList.add(new Instruction(horizontal, row));
            } else {
                String[] input = s.split(",");
                int x = Integer.parseInt(input[0]);
                if (maxX < x) maxX = x;
                int y = Integer.parseInt(input[1]);
                if (maxY < y) maxY = y;

                grid[y][x] = true;
            }
        }
        paper = new Paper(grid, maxY, maxX);
    }

    private void printPaper(Paper p) {
        for (int y = 0; y < p.length; y++) {
            for (int x = 0; x < p.width; x++) {
                System.out.print((p.grid[y][x]) ? "#" :  " ");
            }
            System.out.println();
        }
    }
    
    private static class Instruction {
        boolean horizontal;
        int row;

        public Instruction(boolean horizontal, int row) {
            this.horizontal = horizontal;
            this.row = row;
        }
    }
    private static class Paper {
        boolean grid[][];

        int length;
        int width;
        public Paper(boolean grid[][], int length, int width) {
            this.grid = grid;
            this.length = length + 1;
            this.width = width + 1;
        }

        public void fold(boolean horizontal, int row) {
            if (horizontal) {
                for (int y = 1; y <= length - row; y++) {
                    for (int x = 0; x < width; x++) {
                        boolean b = grid[row + y - 1][x];
                        if (b) grid[row - y + 1][x] = true;
                    }
                }
                length /= 2;
            } else {
                for (int y = 0; y < length; y++) {
                    for (int x = 1; x <= width - row; x++) {
                        boolean b = grid[y][row + x - 1];
                        if (b) grid[y][row - x + 1] = true;

                    }
                }
                width /= 2;
            }
        }
    }
}
