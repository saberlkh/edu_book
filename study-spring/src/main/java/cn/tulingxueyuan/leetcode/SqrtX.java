package cn.tulingxueyuan.leetcode;

/**
 * @Classname Sprt
 * @Description TODO
 * @Date 2025/2/11 18:22
 * @Created by liukaihua
 */
public class SqrtX {

    public static void main(String[] args) {
        System.out.println(newton(24));
    }

    public static int newton(int x) {
        if (x == 0) {
            return 0;
        }
        return (int)sqrt(x, x);
    }

    public static double sqrt(double i, int x) {
        double res = (i + x / i) / 2;
        if (res == i) {
            return i;
        } else {
            return sqrt(res, x);
        }
    }

    /**
     * 二分法
     * @param x
     * @return
     */
    public static int binarySearch(int x) {
        int index = -1, l = 0, r = x;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (mid * mid <= x) {
                index = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return index;
    }

}
