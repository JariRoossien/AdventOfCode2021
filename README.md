# AdventOfCode2021
My solutions to AdventOfCode 2021 edition. [Link to 2021 Advent of Code.](https://adventofcode.com/2021)

## Project Setup
In the Main class I am using Reflection to retrieve the dates I have completed. 

For the class of Day itself we use an abstract class Day, that has 3 functions. solveOne, solveTwo, setup.
The setup() function will have default code running that reads the lines from the corresponding file,
and then puts it in an ArrayList called input.
 
 If we have to alter those lines to be useful, for example parse them, we can do the following
in a DayXX class.

```java
public void setup() {
    // This will read the file and put it in input.
    super.setup();
    
    // Alter the lines to what we need, for example integers.
    numbers = input.stream()
    .map(Integer::parseInt)
    .collect(Collectors.toList());
}
```

## Current Output:

Run from slow laptop

```
Day 1 challenge 1: 1266. solved in 1ms
Day 1 challenge 2: 1217. solved in 0ms

Day 2 challenge 1: 1762050. solved in 4ms
Day 2 challenge 2: 1855892637. solved in 3ms

Day 3 challenge 1: 3847100. solved in 0ms
Day 3 challenge 2: 4105235. solved in 4ms

Day 4 challenge 1: 63552. solved in 2ms
Day 4 challenge 2: 9020. solved in 3ms

Day 5 challenge 1: 5632. solved in 14ms
Day 5 challenge 2: 22213. solved in 11ms
```
