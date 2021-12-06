package nl.jariroossien.aoc.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day06 extends Day {
    List<Integer> intList = new ArrayList<>();

    @Override
    public long solveOne() {
        for (int day = 0; day < 80; day++) {
            for (int i = 0; i < intList.size(); i++) {
                Integer integer = intList.get(i);
                if (integer == 0) {
                    intList.set(i, 6);
                    intList.add(9);
                } else {
                    intList.set(i, integer - 1);
                }
            }
        }
        return intList.size();
    }

    @Override
    public long solveTwo() {

        // We alter intlist in solverOne, so redoing setup.
        setup();

        //Fish groups go from 0 to 8 days.
        long[] fish = new long[9];
        long[] new_fish;

        // Put the fishes in their group.
        for (int i : intList) {
            fish[i]++;
        }

        for (int day = 0; day < 256; day++) {
            new_fish = new long[9];
            for (int i = 0; i < 9; i++) {

                // If a fish is in 0, add them to both 6 and 8.
                if (i == 0) {
                    new_fish[6] += fish[0];
                    new_fish[8] += fish[0];
                } else {
                    // Put the group into the previous group.
                    new_fish[i - 1] += fish[i];
                }
            }
            fish = new_fish;
        }
        return Arrays.stream(fish).sum();
    }

    @Override
    public void setup() {
        super.setup();
        intList = Arrays.stream(input.get(0).split(",")).map(Integer::parseInt).collect(Collectors.toList());
    }
}
