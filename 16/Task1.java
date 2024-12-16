import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Consumer;

class Grid {
    String grid;
    int size;
    int[][] shortestCost;

    Grid() throws IOException {
        grid = new String(System.in.readAllBytes());
        size = (int) grid.chars().filter(ch -> ch == '\n').count();
        shortestCost = new int[4][grid.length()];
        for (int i = 0; i < 4; i++) {
            Arrays.fill(shortestCost[i], Integer.MAX_VALUE);
        }
    }

    char get(int x, int y) {
        return grid.charAt(x + y * (size + 1));
    }

    int compact(int x, int y) {
        return x + y * (size + 1);
    }

    boolean setShortestCost(int x, int y, int direction, int cost) {
        if (cost <= shortestCost[direction][compact(x, y)]) {
            shortestCost[direction][compact(x, y)] = cost;
            return true;
        }
        return false;
    }

    int[] expand(int index) {
        return new int[] { index % (size + 1), index / (size + 1) };
    }

    int[] find(char ch) {
        int index = grid.indexOf(ch);
        if (index == -1) {
            return null;
        }
        return expand(index);
    }
}

class Node implements Comparable<Node> {
    int x, y;
    int dx, dy;
    int cost;
    Node parent;

    Node(int x, int y, int dx, int dy, int cost, Node parent) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.cost = cost;
        this.parent = parent;
    }

    int direction() {
        if (dx == 0) {
            return dy == 1 ? 0 : 1;
        }
        return dx == 1 ? 2 : 3;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.cost, other.cost);
    }

    Node step(Grid grid) {
        return new Node(x + dx, y + dy, dx, dy, cost + 1, this);
    }

    Node turn(int side) {
        return new Node(x, y, -dy*side, dx*side, cost + 1000, this.parent);
    }

    boolean contains(int x, int y) {
        if (this.x == x && this.y == y) {
            return true;
        }
        return parent != null && parent.contains(x, y);
    }


    void tryStep(Node node, Grid grid, Consumer<Node> consumer) {
        boolean isWall = grid.get(node.x + node.dx, node.y + node.dy) == '#';
        boolean isOnPath = contains(node.x + node.dx, node.y + node.dy);
        if (!isWall && !isOnPath) {
            consumer.accept(node.step(grid));
        }
    }
    
    void forEachMove(Grid grid, Consumer<Node> consumer) {
        tryStep(this, grid, consumer);

        var left = turn(1);
        var right = turn(-1);
        tryStep(left, grid, consumer);
        tryStep(right, grid, consumer);
        tryStep(left.turn(1), grid, consumer);
    }

    void forEachStep(Grid grid, Consumer<Integer> consumer) {
        consumer.accept(grid.compact(x, y));
        if (parent != null) {
            parent.forEachStep(grid, consumer);
        }
    }
}

class Task1 {
    static int solve(Grid grid, int x, int y) {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        Set<Integer> onBestPath = new HashSet<>();
        queue.add(new Node(x, y, 1, 0, 0, null));
        int best = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            Node node = queue.poll();

            if (grid.get(node.x, node.y) == 'E') {
                if (node.cost <= best) {
                    best = node.cost;
                    node.forEachStep(grid, onBestPath::add);
                }
                else {
                    System.out.println("onBestPath=" + onBestPath.size());
                    return best;
                }
            }
            if (grid.setShortestCost(node.x, node.y, node.direction(), node.cost)) {
                node.forEachMove(grid, queue::add);
            }
        }
        return best;
    }

    public static void main(String[] args) throws IOException {
        var grid = new Grid();
        var start = grid.find('S');
        System.out.println(solve(grid, start[0], start[1]));
    }
}

