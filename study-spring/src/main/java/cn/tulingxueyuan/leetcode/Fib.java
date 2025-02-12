package cn.tulingxueyuan.leetcode;

/**
 * @Classname Fib
 * @Description TODO
 * @Date 2025/2/12 16:37
 * @Created by liukaihua
 */
public class Fib {

    public static void main(String[] args) {
        System.out.println(calculate(10));
        System.out.println(calculate2(10));
        System.out.println(iterate(10));
    }

    public static int calculate(int num) {
        if (num == 0) {
            return 0;
        }
        if (num == 1) {
            return 1;
        }
        return calculate(num - 1) + calculate(num - 2);
    }

    public static int calculate2(int num) {
        int[] arr = new int[num + 1];
        return recurse(arr, num);
    }

    public static int iterate(int num) {
        if (num == 0) return 0;
        if (num == 1) return 1;
        int low = 0, high = 1;
        for (int i = 2; i <= num; i++) {
            int sum = low + high;
            low = high;
            high = sum;
        }
        return high;
    }

    private static int recurse(int[] arr, int num) {
        if (num == 0) {
            return 0;
        }
        if (num == 1) {
            return 1;
        }
        if (arr[num] != 0) {
            return arr[num];
        }
        return recurse(arr, num - 1) + recurse(arr, num - 2);
    }

}
