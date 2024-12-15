import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
class MapElement {
    int x, y;
    int width;
    boolean isWall;

    MapElement(int x, int y, int width, boolean isWall) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.isWall = isWall;
    }

    boolean overlaps(int x, int y, int width) {
        if (this.y != y) {
            return false;
        }
        // for (int x1 = this.x; x1 < this.x + this.width; x1++) {
        //     for (int x2 = x; x2 < x + width; x2++) {
        //         if (x1 == x2) {
        //             return true;
        //         }
        //     }
        // }
        // return false;
        return this.x + this.width > x && this.x < x + width;
    }

    @Override
    public String toString() {
        return "MapElement{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", isWall=" + isWall +
                '}';
    }
}

class Map {
    List<MapElement> elements;
    int robotX, robotY;
    int size;

    Map() {
        this.elements = new ArrayList<>();
    }

    void readLine(int y, String line) {
        int x = 0;
        int dx = 2;
        for (char c : line.toCharArray()) {
            switch (c) {
                case '#' -> elements.add(new MapElement(x, y, dx, true));
                case '@' -> {
                    robotX = x;
                    robotY = y;
                }
                case 'O' -> elements.add(new MapElement(x, y, dx, false));
                default -> {
                }
            }
            x += dx;
        }
        size = y + 1;
    }

    void forEachAt(int x, int y, Consumer<MapElement> consumer) {
        for (MapElement element : elements) {
            if (element.overlaps(x, y, 1)) {
                consumer.accept(element);
            }
        }
    }

    void forEachNext(MapElement element, int dx, int dy, Consumer<MapElement> consumer) {
        MapElement shifted;
        if (dy == 0) {
            shifted = new MapElement(element.x + dx, element.y, element.width, element.isWall);
        } else {
            shifted = new MapElement(element.x, element.y + dy, element.width, element.isWall);
        }

        for (MapElement next : elements) {
            if (next != element && next.overlaps(shifted.x, shifted.y, shifted.width)) {
                consumer.accept(next);
            }
        }
    }

    List<MapElement> getElements(int x, int y, int dx, int dy) {
        List<MapElement> ret = new ArrayList<>();
        forEachAt(x, y, ret::add);

        List<MapElement> current = ret;
        while (true) {
            List<MapElement> next = new ArrayList<>();
            for (MapElement element : current) {
                forEachNext(element, dx, dy, next::add);
            }
            if (next.isEmpty()) {
                break;
            }
            if (next.stream().anyMatch(element -> element.isWall)) {
                return null;
            }
            current = next;
            ret.addAll(current);
        }


        if (ret.stream().anyMatch(element -> element.isWall)) {
            return null;
        }
        ret = ret.stream().distinct().collect(Collectors.toList());
        return ret;
    }

    void print() {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < 2*size; x++) {
                List<MapElement> el = new ArrayList<>();
                forEachAt(x, y, el::add);
                if (el.size() > 1) {
                    throw new RuntimeException("Multiple elements at " + x + ", " + y);
                }
                if (robotX == x && robotY == y) {
                    System.out.print("@");
                    if (!el.isEmpty()) {
                        throw new RuntimeException("Overlapping robot and element");
                    }
                } else if (el.isEmpty()) {
                    System.out.print(".");
                } else if (el.get(0).isWall) {
                    System.out.print("#");
                } else {
                    System.out.print("[]");
                    x ++;
                }
            }
            System.out.println();
        }
    }

    void sanityCheck() {
        for (var element : elements) {
            for (var other : elements) {
                if (element != other && element.overlaps(other.x, other.y, other.width)) {
                    throw new RuntimeException("Overlapping elements: " + element + " and " + other);
                }
            }
        }
    }
}

class Task2 {
    static Map readMap(Scanner scanner) {
        Map map = new Map();
        String line = scanner.nextLine();
        map.readLine(0, line);
        for (int i = 1; i < line.length(); i++) {
            map.readLine(i, scanner.nextLine());
        }
        return map;
    }

    static int execute(Map map, String commands) {
        for (char command : commands.toCharArray()) {
            // map.print();
            int dx = 0, dy = 0;
            switch (command) {
                case 'v' -> dy = 1;
                case '^' -> dy = -1;
                case '>' -> dx = 1;
                case '<' -> dx = -1;
            }
            var elements = map.getElements(map.robotX + dx, map.robotY + dy, dx, dy);
            if (elements == null) {
                continue;
            }
            // if (elements.size() > 1) {
            //     map.print();
            //     System.out.println(command + ": " + elements);
            // }
            for (var element : elements) {
                element.x += dx;
                element.y += dy;
            }
            map.robotX += dx;
            map.robotY += dy;
            // map.sanityCheck();
        }
        // map.print();
        int ret = 0;
        for (var element : map.elements) {
            if (!element.isWall) {
                ret += element.x + 100 * element.y;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map map = readMap(scanner);
        String commands = scanner.nextLine();
        while (scanner.hasNextLine()) {
            commands += scanner.nextLine();
        }
        System.out.println(execute(map, commands));
    }
}
