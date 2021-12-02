package nl.jariroossien.aoc;


import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class Main {
    private final static boolean DEBUG = false;

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        int totalDays = Objects.requireNonNull(new File("input").listFiles()).length;
        if (DEBUG) {
            String dayNumber;
            if (totalDays <= 9) {
                dayNumber = "0" + totalDays;
            } else {
                dayNumber = String.valueOf(totalDays);
            }
            Challenge challenge = (Challenge) Class.forName("nl.jariroossien.aoc.days.Day" + dayNumber).getDeclaredConstructor().newInstance();
            challenge.setup();
            System.out.println("part 1: " + challenge.solveOne());
            System.out.println("part 2: " + challenge.solveTwo());
        } else {
            for (int i = 1; i <= totalDays; i++) {

                String dayNumber;
                if (i <= 9) {
                    dayNumber = "0" + i;
                } else {
                    dayNumber = String.valueOf(i);
                }

                Challenge challenge = (Challenge) Class.forName("nl.jariroossien.aoc.days.Day" + dayNumber).getDeclaredConstructor().newInstance();
                challenge.setup();
                long timer = System.nanoTime();
                long lOne = challenge.solveOne();
                long challOneSolveTime = System.nanoTime() - timer;

                System.out.println("Day " + i + " challenge 1: " + lOne + ". solved in " + challOneSolveTime / 1000000 + "ms");


                timer = System.nanoTime();
                long lTwo = challenge.solveTwo();
                long challengeSolveTime = System.nanoTime() - timer;
                System.out.println("Day " + i + " challenge 2: " + lTwo + ". solved in " + challengeSolveTime / 1000000 + "ms");
                System.out.println();
            }

        }
    }
}
