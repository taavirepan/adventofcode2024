import java.io.IOException;
import java.util.PriorityQueue;

class Grid {
    String grid;
    int size;
    Grid() throws IOException {
        grid = new String(System.in.readAllBytes());
        size = (int) grid.chars().filter(ch -> ch == '\n').count();
    }

    char get(int x, int y) {
        return grid.charAt(x + y * (size + 1));
    }

    int[] find(char ch) {
        int index = grid.indexOf(ch);
        if (index == -1) {
            return null;
        }
        return new int[] { index % (size + 1), index / (size + 1) };
    }
}

class Node implements Comparable<Node> {
    int x, y;
    int dx, dy;
    int cost;

    Node(int x, int y, int dx, int dy, int cost) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.cost = cost;
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

    Node step() {
        return new Node(x + dx, y + dy, dx, dy, cost + 1);
    }

    Node turn(int side) {
        return new Node(x, y, -dy*side, dx*side, cost + 1000);
    }
}

class Task1 {
    static int solve(Grid grid, int x, int y) {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        boolean[][][] visited = new boolean[grid.size][grid.size][4];
        queue.add(new Node(x, y, 1, 0, 0));
        while (!queue.isEmpty()) {
            Node node = queue.poll();

            if (visited[node.x][node.y][node.direction()]) {
                continue;
            }
            visited[node.x][node.y][node.direction()] = true;
            if (grid.get(node.x, node.y) == 'E') {
                return node.cost;
            }
            if (grid.get(node.x + node.dx, node.y + node.dy) != '#') {
                queue.add(node.step());
            }
            queue.add(node.turn(1));
            queue.add(node.turn(-1));
        }
        return -1;
    }

    public static void main(String[] args) throws IOException {
        var grid = new Grid();
        var start = grid.find('S');
        System.out.println(solve(grid, start[0], start[1]));
    }
}

