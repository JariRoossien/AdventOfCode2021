package nl.jariroossien.aoc.days;

import java.util.*;

public class Day12 extends Day {
    Grid grid;
    int paths;

    @Override
    public long solveOne() {
        Stack<Node> toVisit = new Stack<>();
        toVisit.push(grid.start);
        findPath(toVisit);
        return paths;
    }

    /**
     * To find a path, we go over the options that we can go from from the last position
     * in our stack. If a cave is small, we can check if we have visited it, and then skip that.
     * By skipping it we automatically end the stack and it can be cleaned up.
     *
     * If we haven't visited it, we add the new item to the stack. If the last item is the
     * end cave, we can add it to the paths list and finish that stack. Otherwise, we send a
     * copy of the stack to a new recursive call.
     *
     * @param currentPath The current path we have traversed.
     */
    public void findPath(Stack<Node> currentPath) {
        Node lastNode = currentPath.peek();
        for (Node nb : lastNode.connections) {
            if (nb.small && currentPath.contains(nb)) {
                continue;
            }
            Stack<Node> clone = (Stack<Node>) currentPath.clone();
            clone.add(nb);
            if (nb == grid.end) {
                paths++;
            } else {
                findPath(clone);
            }
        }
    }

    @Override
    public long solveTwo() {
        paths = 0;
        Stack<Node> toVisit = new Stack<>();
        toVisit.push(grid.start);
        findPathPartTwo(toVisit);
        return paths;
    }

    /**
     * Copied the below part from part 1 as it's the same approach:
     * To find a path, we go over the options that we can go from from the last position
     * in our stack. If a cave is small, we can check if we have visited it, and then skip that.
     * By skipping it we automatically end the stack and it can be cleaned up.
     *
     * If we haven't visited it, we add the new item to the stack. If the last item is the
     * end cave, we can add it to the paths list and finish that stack. Otherwise, we send a
     * copy of the stack to a new recursive call.
     *
     * The main difference here is that we create a clone directly and then add the item
     * to the clone. We can then check if that item means we have 2 double visited islands.
     * @param currentPath
     */
    public void findPathPartTwo(Stack<Node> currentPath) {
        Node lastNode = currentPath.peek();

        // Go over each connected node to the path.
        for (Node nb : lastNode.connections) {

            // If the node is back to start, we can ignore it.
            if (nb.equals(grid.start)) continue;

            // Add the new node already to a clone.
            Stack<Node> clone = (Stack<Node>) currentPath.clone();
            clone.add(nb);

            // If it's small, check if it means we will visit 2 small caves twice.
            if (nb.small && hasVisitedTwice(clone)) {
                continue;
            }

            // If we are at the end, we can finish it. Otherwise continue with the new path found.
            if (nb == grid.end) {
                paths++;
            } else {
                findPathPartTwo(clone);
            }
        }
    }

    /**
     * Since we need to check if we have 2 duplicates, we can't simply call a Stack#contains.
     * We keep track of the amount of double small nodes we have in our list. If the total doubles
     * is equal to 2, it means it's an illegal path.
     *
     * @param path The current path we are taking.
     * @return If there are 2 duplicate small caves.
     */
    public boolean hasVisitedTwice(Stack<Node> path) {
        Set<Node> smallNodes = new HashSet<>();
        int totalDouble = 0;
        for (Iterator<Node> it = path.elements().asIterator(); it.hasNext(); ) {
            Node node = it.next();
            if (!node.small) continue;
            if (smallNodes.contains(node)) {
                if (++totalDouble == 2)
                    return true;
            }
            smallNodes.add(node);
        }
        return false;
    }

    @Override
    public void setup() {
        super.setup();
        grid = new Grid();
        for (String s : input) {
            String[] connections = s.split("-");
            Node front = Node.getNode(connections[0]);
            Node end = Node.getNode(connections[1]);
            front.connections.add(end);
            end.connections.add(front);
            if (front.key.equalsIgnoreCase("start")) {
                grid.start = front;
            }
            if (end.key.equalsIgnoreCase("end")) {
                grid.end = end;
            }
        }
    }

    private static class Node {
        private static final List<Node> nodes = new ArrayList<>();

        private final boolean small;
        private final String key;
        private final List<Node> connections;

        private Node(String key, boolean small, List<Node> connections) {
            this.key = key;
            this.small = small;
            this.connections = connections;
        }

        public static Node getNode(String key) {
            for (Node node : nodes) {
                if (node.key.equals(key)) {
                    return node;
                }
            }
            boolean small = Character.isLowerCase(key.charAt(0));
            Node node = new Node(key, small, new ArrayList<>());
            nodes.add(node);
            return node;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(key, node.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }
    }

    private static class Grid {
        Node start;
        Node end;
        Stack<Node> nodes;

        public Grid() {
            this.nodes = new Stack<>();
        }
    }

    private static class Path {
        Stack<Node> nodes;

        public Path(Stack<Node> nodes) {
            this.nodes = nodes;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Path path = (Path) o;
            return Objects.equals(nodes, path.nodes);
        }

        @Override
        public int hashCode() {
            return Objects.hash(nodes);
        }
    }
}
