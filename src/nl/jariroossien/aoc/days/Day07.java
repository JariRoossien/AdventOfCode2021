package nl.jariroossien.aoc.days;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day07 extends Day {

    List<Integer> intList;
    @Override
    public long solveOne() {
        int max = intList.stream().max(Integer::compareTo).orElse(0);
        int distance = 0;
        int smallestDistance = Integer.MAX_VALUE;
        for (int i = 0; i < max; i++) {
            int finalI = i;
            distance = intList.stream().map(il -> Math.abs(il - finalI)).reduce(Integer::sum).orElse(0);
            if (distance < smallestDistance) {
                smallestDistance = distance;
            }
        }
        return smallestDistance;
    }

    @Override
    public long solveTwo() {
        int max = intList.stream().max(Integer::compareTo).orElse(0);
        int distance = 0;
        int smallestDistance = Integer.MAX_VALUE;
        for (int i = 0; i < max; i++) {
            int finalI = i;
            distance = intList.stream().map(il -> calculateFuel(Math.abs(il - finalI))).reduce(Integer::sum).orElse(0);
            if (distance < smallestDistance) {
                smallestDistance = distance;
            }
        }
        return smallestDistance;
    }

    public int calculateFuel(int distance) {
        int temp = 0;
        for (int i = 1; i <= distance; i++) {
            temp += i;
        }
        return temp;
    }

    @Override
    public void setup() {
        super.setup();
        intList = Arrays.stream(input.get(0).split(",")).map(Integer::parseInt).collect(Collectors.toList());
    }
}
