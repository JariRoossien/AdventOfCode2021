package nl.jariroossien.aoc.days;

public class Day02 extends Day {
    @Override
    public long solveOne() {
        long depth = 0;
        long forward = 0;

        for (String s : input) {
            long val = Long.parseLong(s.split(" ")[1]);
            if (s.startsWith("forward")) {
                forward += val;
            } else if (s.startsWith("up")) {
                depth -= val;
            } else {
                depth += val;
            }
        }

        return depth * forward;
    }

    @Override
    public long solveTwo() {
        long aim = 0;
        long depth = 0;
        long forward = 0;

        for (String s : input) {
            long val = Long.parseLong(s.split(" ")[1]);
            if (s.startsWith("forward")) {
                forward += val;
                depth += (val * aim);
            } else if (s.startsWith("up")) {
                aim -= val;
            } else {
                aim += val;
            }
        }

        return depth * forward;
    }
}
