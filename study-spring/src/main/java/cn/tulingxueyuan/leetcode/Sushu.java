package cn.tulingxueyuan.leetcode;


/**
 * @Classname Sushu
 * @Description TODO
 * @Date 2025/2/11 16:31
 * @Created by liukaihua
 */
public class Sushu {

    public static int bf(int n) {
        int count = 0;
        for (int i = 2; i <= n; i++) {
            count += isPrime(i) ? 1 : 0;
        }
        return count;
    }

    public static boolean isPrime(int x) {
        for (int j = 2; j * j <= x; j++) {
            if (x % j == 0) {
                return false;
            }
        }
        return true;
    }

    public static int eratosthenes(int n) {
        boolean[] isPrime = new boolean[n];
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (!isPrime[i]) {
                count++;
                for (int j = i * i; j < n; j += i) {
                    isPrime[j] = true;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(eratosthenes(100));
        System.out.println(bf(100));
    }

}
