package nl.jariroossien.aoc.days;

import nl.jariroossien.aoc.Challenge;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public abstract class Day implements Challenge {

    List<String> input = new ArrayList<>();

    @Override
    public void setup() {
        File file = new File("input/" + getClass().getSimpleName().toLowerCase() + ".txt");
        try {
            input = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
