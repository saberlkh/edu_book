package cn.tulingxueyuan.leetcode;

/**
 * @Classname LemonChange
 * @Description 柠檬水算法
 * @Date 2025/2/14 11:51
 * @Created by liukaihua
 */
public class LemonChange {

    public static void main(String[] args) {
        System.out.println(change(new int[]{5, 5, 10}));
    }

    public static boolean change(int[] bills) {
        int five = 0, ten = 0;
        for (int bill: bills) {
            if (bill == 5) {
                five++;
            } else if (bill == 10) {
                if (five == 0) {
                    return false;
                }
                five--;
                ten++;
            } else {
                if (five > 0 && ten > 0) {
                    five--;
                    ten--;
                } else if (five >= 3) {
                    five -= 3;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

}
