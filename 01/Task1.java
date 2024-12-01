import java.util.ArrayList;
import java.util.List;

class Task1 {
    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] elements = line.split("   ");
            int num1 = Integer.parseInt(elements[0]);
            int num2 = Integer.parseInt(elements[1]); 
            left.add(num1);
            right.add(num2);
        }
        scanner.close();
        left.sort(Integer::compareTo);
        right.sort(Integer::compareTo);
        int ret = 0;
        for (int i = 0; i < left.size(); i++) {
            ret += Math.abs(left.get(i) - right.get(i));
        }
        System.out.println(ret);
    }
}
