package nl.jariroossien.aoc.days;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day03 extends Day {
    @Override
    public long solveOne() {
        int bitsize = input.get(0).length();
        int[] bits = new int[bitsize];

        // Count the bits.
        for (String s : input) {
            for (int i = 0; i < bitsize; i++) {
                if (s.charAt(i) == '1') {
                    bits[i]++;
                }
            }
        }
        long gamma = 0;
        long eps = 0;

        // translate the bit array into a number.
        for (int i = 0; i < bitsize; i++) {
            if (bits[i] > input.size() / 2) {
                gamma = gamma * 10 + 1;
                eps = eps * 10;
            } else {
                gamma = gamma * 10;
                eps = eps * 10 + 1;
            }
        }

        // Parse the numbers from binary to decimal.
        gamma = Long.parseLong(String.valueOf(gamma), 2);
        eps = Long.parseLong(String.valueOf(eps), 2);

        return gamma * eps;
    }

    @Override
    public long solveTwo() {
        long gamma = Long.parseLong(getMatchingBitString(input, true), 2);
        long eps = Long.parseLong(getMatchingBitString(input, false), 2);
        return gamma * eps;
    }

    public String getMatchingBitString(List<String> inputs, boolean mostCommon) {
        int bitsize = inputs.get(0).length();
        int[] bits = new int[bitsize];
        List<String> filteredInputs = new ArrayList<>(input);
        for (int i = 0; filteredInputs.size() > 1; i++) {

            // get the amount of bits in the left over strings
            for (String s : filteredInputs) {
                if (s.charAt(i) == '1') bits[i]++;
            }
            int finalI = i;
            int filteredSize = filteredInputs.size();

            // Filter all strings out that don't match the bits.
            filteredInputs = filteredInputs.stream()
                    .filter(s -> {
                        boolean b = bits[finalI] >= Math.round(filteredSize / 2.0d);
                        if (mostCommon)
                            return s.charAt(finalI) == (b ? '1' : '0');
                        else
                            return s.charAt(finalI) == (b ? '0' : '1');
                    })
                    .collect(Collectors.toList());
        }

        return filteredInputs.get(0);
    }
}
