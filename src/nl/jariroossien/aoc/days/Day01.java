package nl.jariroossien.aoc.days;

import nl.jariroossien.aoc.Challenge;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day01 extends Day {

    List<Integer> numbers  = new ArrayList<>();

    public Day01() {
        setup();
    }

    @Override
    public long solveOne() {
        long answer = 0;
        for (int i = 1; i < numbers.size(); i++) {
            if (numbers.get(i) - numbers.get(i - 1) > 0) {
                answer++;
            }
        }
        return answer;
    }

    @Override
    public long solveTwo() {
        long answer = 0;
        int previousSum = numbers.get(0) + numbers.get(1) + numbers.get(2);
        for (int i = 1; i < numbers.size() - 2; i++) {
            int sum = numbers.get(i) + numbers.get(i+1) + numbers.get(i+2);
            if (sum > previousSum) {
                answer++;
            }
            previousSum = sum;
        }
        return answer;

    }

    @Override
    public void setup() {
        super.setup();
        numbers = input.stream().map(Integer::parseInt).collect(Collectors.toList());
    }
}
