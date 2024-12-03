import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class Task1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
        Pattern pdont = Pattern.compile("do(n't)?\\(\\)");

        int task1 = 0;        
        boolean state = true;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            int i = 0;
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                Matcher matcher2 = pdont.matcher(line.substring(i, matcher.start()));
                i = matcher.end();
                while (matcher2.find()) {
                    state = matcher2.group().equals("do()");
                }
                if (!state) {
                    continue;
                }
                int a = Integer.parseInt(matcher.group(1));
                int b = Integer.parseInt(matcher.group(2));
                task1 += a * b;
            }
        }

        System.out.println(task1);
    }
}
