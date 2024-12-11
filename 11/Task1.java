import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

class CacheKey {
    BigInteger n;
    int k;

    public CacheKey(BigInteger n, int k) {
        this.n = n;
        this.k = k;
    }

    @Override
    public int hashCode() {
        return Objects.hash(n, k);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CacheKey cacheKey = (CacheKey) obj;
        return n.equals(cacheKey.n) && k == cacheKey.k;
    }
}

class Task1 {
     static BigInteger countAfter(BigInteger n, int k, HashMap<CacheKey, BigInteger> cache) {
        if (k == 0) {
            return BigInteger.ONE;
        }
        var key = new CacheKey(n, k);
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        String s = n.toString();
        BigInteger ret;
        if (n.equals(BigInteger.ZERO)) {
            ret = countAfter(BigInteger.ONE, k - 1, cache);
        }
        else if (s.length() % 2 == 0) {
            int h = s.length() / 2;
            BigInteger a = new BigInteger(s.substring(0, h));
            BigInteger b = new BigInteger(s.substring(h));
            ret = countAfter(a, k - 1, cache).add(countAfter(b, k - 1, cache));
        }
        else {
            ret = countAfter(n.multiply(BigInteger.valueOf(2024)), k - 1, cache);
        }
        cache.put(key, ret);
        return ret;
    }

    public static void main(String[] args) {
        List<BigInteger> numbers = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextInt()) {
            numbers.add(BigInteger.valueOf(scanner.nextInt()));
        }

        BigInteger task1 = BigInteger.ZERO;
        BigInteger task2 = BigInteger.ZERO;
        HashMap<CacheKey, BigInteger> cache = new HashMap<>();
        for (int i = 0; i < numbers.size(); i++) {
            task1 = task1.add(countAfter(numbers.get(i), 25, cache));
            task2 = task2.add(countAfter(numbers.get(i), 75, cache));
        }
        System.out.println(task1);
        System.out.println(task2);

    }
}
