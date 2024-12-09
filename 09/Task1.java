import java.math.BigInteger;
import java.util.ArrayList; 
import java.util.List;
import java.util.Scanner;

class Span {
    int start;
    int length;
    int free_space;
    int id;

    Span(int start, int length, int free_space, int id) {
        this.start = start;
        this.length = length;
        this.free_space = free_space;
        this.id = id;
    }

    BigInteger get_score() {
        BigInteger score = BigInteger.ZERO;
        for (int i = 0; i < length; i++) {
            score = score.add(BigInteger.valueOf(id * (start + i)));
        }
        return score;
    }

    @Override
    public String toString() {
        return String.format("Span(start=%d, length=%d, free_space=%d, id=%d)", start, length, free_space, id);
    }
}

class Task1 {
    static BigInteger solve_task2(char[] input) {
        BigInteger task2 = BigInteger.ZERO;
        List<Span> spans = new ArrayList<>();
        int p = 0;
        for (int i = 0; i < input.length; i+=2) {
            spans.add(new Span(p, input[i], i + 1 < input.length ? input[i+1] : 0, i/2));
            p += input[i];
            if (i + 1 < input.length) {
                p += input[i+1];
            }
        }
        for (int i = spans.size() - 1; i >= 0; i--) {
            Span span = spans.get(i);
            for (int j = 0; j < i; j++) {
                Span target = spans.get(j);
                if (span.length <= target.free_space) {
                    Span prev = spans.get(i - 1);
                    assert prev != target;
                    span.start = target.start + target.length;
                    span.free_space = target.free_space - span.length;
                    target.free_space = 0;
                    prev.free_space += span.length;
                    spans.remove(i);
                    spans.add(j + 1, span);
                    if (j + 1 != i) {
                        i++;
                    }
                    break;
                }
            }
        }
        for (Span span : spans) {
            task2 = task2.add(span.get_score());
        }
        return task2;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[] input = scanner.nextLine().toCharArray();
        for (int i = 0; i < input.length; i++) {
            input[i] -= '0';
        }

        BigInteger task2 = solve_task2(input);

        BigInteger task1 = BigInteger.ZERO;
        int i = 0;
        int j = input.length - 1;
        int p = 0;

        while (i <= j) {
            int n = input[i];
            for (int k = 0; k < n; k++) {
                task1 = task1.add(BigInteger.valueOf((i/2) * (p++)));
            }
            i++;
            if (i > j) {
                break;
            }
            while (input[i] > 0) {
                if (input[j] == 0) {
                    j -= 2;
                }
                if (j < i) {
                    break;
                }
                task1 = task1.add(BigInteger.valueOf((j/2) * (p++)));
                input[i]--;
                input[j]--;
            }
            i++;
        }

        System.out.println(task1);
        System.out.println(task2);
    }
}
